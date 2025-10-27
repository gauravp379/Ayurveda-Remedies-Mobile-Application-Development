package com.example.ayurveda_remedies;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

/**
 * ReminderReceiver: Shows notification with Snooze action, reschedules next-day alarms
 */
public class ReminderReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID = "wellness_channel";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_KEY = "extra_key";
    public static final String ACTION_SNOOZE = "action_snooze";

    private static final String TAG = "ReminderReceiver";

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: action=" + (intent == null ? null : intent.getAction()));
        if (intent == null) return;

        String action = intent.getAction();
        String title = intent.getStringExtra(EXTRA_TITLE);
        if (title == null) title = "Wellness Reminder";
        String keyPrefix = intent.getStringExtra(EXTRA_KEY);

        if (ACTION_SNOOZE.equals(action)) {
            Log.i(TAG, "Snooze received for: " + title);
            scheduleSnooze(context, title, keyPrefix);
            return;
        }

        // Normal alarm -> show notification then reschedule next day
        createChannelIfNeeded(context);

        // Build Snooze pending intent
        Intent snoozeIntent = new Intent(context, ReminderReceiver.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_TITLE, title);
        snoozeIntent.putExtra(EXTRA_KEY, keyPrefix);
        int snoozeReq = (title + "_snooze_action").hashCode();
        PendingIntent snoozePending = PendingIntent.getBroadcast(context, snoozeReq, snoozeIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Open-app pending intent
        Intent openApp = new Intent(context, MainActivity.class);
        openApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent openPending = PendingIntent.getActivity(context, (title + "_open").hashCode(), openApp,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText("Time for " + title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(openPending)
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_menu_recent_history, "Snooze", snoozePending);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        try {
            nm.notify((title + "_notif").hashCode(), builder.build());
            Log.i(TAG, "Notification shown for: " + title);
        } catch (SecurityException e) {
            Log.e(TAG, "No notification permission", e);
        }

        // RESCHEDULE: schedule next day's exact alarm for this keyPrefix
        if (keyPrefix != null && !keyPrefix.isEmpty()) {
            rescheduleNextDay(context, keyPrefix, title);
        } else {
            Log.w(TAG, "No keyPrefix present — cannot reschedule");
        }
    }

    private void scheduleSnooze(Context context, String title, String keyPrefix) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent snoozeIntent = new Intent(context, ReminderReceiver.class);
        snoozeIntent.putExtra(EXTRA_TITLE, title);
        snoozeIntent.putExtra(EXTRA_KEY, keyPrefix);
        int req = (title + "_snooze").hashCode();
        PendingIntent pending = PendingIntent.getBroadcast(context, req, snoozeIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10);

        if (alarmMgr != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending);
            } else {
                alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending);
            }
        }
        Log.i(TAG, "Snoozed " + title + " for 10 minutes.");
    }

    private void rescheduleNextDay(Context context, String keyPrefix, String title) {
        try {
            SharedPreferences prefs = context.getSharedPreferences("wellness_prefs", Context.MODE_PRIVATE);
            int hour = prefs.getInt(keyPrefix + "_hour", -1);
            int minute = prefs.getInt(keyPrefix + "_min", -1);

            if (hour == -1 || minute == -1) {
                Log.w(TAG, "No saved time for keyPrefix " + keyPrefix);
                return;
            }

            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReminderReceiver.class);
            intent.putExtra(EXTRA_TITLE, title);
            intent.putExtra(EXTRA_KEY, keyPrefix);

            int reqCode = (keyPrefix + "_alarm").hashCode();
            PendingIntent pending = PendingIntent.getBroadcast(context, reqCode, intent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1); // next day
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            long triggerAt = cal.getTimeInMillis();

            if (alarmMgr == null) {
                Log.e(TAG, "AlarmManager null");
                return;
            }

            boolean exactAllowed = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                try {
                    exactAllowed = alarmMgr.canScheduleExactAlarms();
                } catch (Throwable t) {
                    exactAllowed = false;
                }
            } else {
                exactAllowed = true;
            }

            if (exactAllowed) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pending);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmMgr.setExact(AlarmManager.RTC_WAKEUP, triggerAt, pending);
                } else {
                    alarmMgr.set(AlarmManager.RTC_WAKEUP, triggerAt, pending);
                }
                Log.i(TAG, "Rescheduled exact alarm for " + keyPrefix);
            } else {
                alarmMgr.set(AlarmManager.RTC_WAKEUP, triggerAt, pending);
                Log.w(TAG, "Exact alarms not allowed; scheduled non-exact");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error rescheduling: " + e.getMessage(), e);
        }
    }

    public static void createChannelIfNeeded(Context context) {
        if (context == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Wellness Reminders";
            String description = "Daily wellness reminder notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager mgr = context.getSystemService(NotificationManager.class);
            if (mgr != null) mgr.createNotificationChannel(channel);
        }
    }
}
