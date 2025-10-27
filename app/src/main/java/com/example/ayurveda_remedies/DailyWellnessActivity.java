package com.example.ayurveda_remedies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.util.Log;
import android.provider.Settings;
import java.util.Calendar;

public class DailyWellnessActivity extends AppCompatActivity {

    private static final String PREFS = "wellness_prefs";
    private static final String STREAK_SUFFIX = "_streak";
    private static final String LAST_DONE_SUFFIX = "_lastdone";
    private static final String HOUR_SUFFIX = "_hour";
    private static final String MIN_SUFFIX = "_min";
    private static final String HISTORY_SUFFIX = "_history";

    Button btnYogaSet, btnYogaCancel, btnYogaDone;
    TextView tvYogaStreak, tvYogaTime;

    Button btnMeditSet, btnMeditCancel, btnMeditDone;
    TextView tvMeditStreak, tvMeditTime;

    Button btnDietSet, btnDietCancel, btnDietDone;
    TextView tvDietStreak, tvDietTime;

    Button backHomeButton;
    Button btnOpenCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_wellness);

        ProfileUtils.loadProfileIntoViews(this, R.id.ivProfilePic, R.id.tvUserName);


        Button btnProgress = findViewById(R.id.btnProgress);
        btnProgress.setOnClickListener(v -> {
            Intent intent = new Intent(DailyWellnessActivity.this, ProgressActivity.class);
            startActivity(intent);
        });

        // Ensure channel exists for notifications
        try {
            ReminderReceiver.createChannelIfNeeded(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // find views
        btnYogaSet = findViewById(R.id.btnYogaSetReminder);
        btnYogaCancel = findViewById(R.id.btnYogaCancelReminder);
        btnYogaDone = findViewById(R.id.btnYogaMarkDone);
        tvYogaStreak = findViewById(R.id.tvYogaStreak);
        tvYogaTime = findViewById(R.id.tvYogaTime);

        btnMeditSet = findViewById(R.id.btnMeditSetReminder);
        btnMeditCancel = findViewById(R.id.btnMeditCancelReminder);
        btnMeditDone = findViewById(R.id.btnMeditMarkDone);
        tvMeditStreak = findViewById(R.id.tvMeditStreak);
        tvMeditTime = findViewById(R.id.tvMeditTime);

        btnDietSet = findViewById(R.id.btnDietSetReminder);
        btnDietCancel = findViewById(R.id.btnDietCancelReminder);
        btnDietDone = findViewById(R.id.btnDietMarkDone);
        tvDietStreak = findViewById(R.id.tvDietStreak);
        tvDietTime = findViewById(R.id.tvDietTime);

        backHomeButton = findViewById(R.id.backHomeButton);
        btnOpenCalendar = findViewById(R.id.btnOpenCalendarDaily);

        updateStreakText("yoga", tvYogaStreak);
        updateStreakText("medit", tvMeditStreak);
        updateStreakText("diet", tvDietStreak);

        updateScheduledTimeText("yoga", tvYogaTime);
        updateScheduledTimeText("medit", tvMeditTime);
        updateScheduledTimeText("diet", tvDietTime);

        // Set handlers
        btnYogaSet.setOnClickListener(v -> pickTimeAndSchedule("Morning Yoga", "yoga", tvYogaTime));
        btnMeditSet.setOnClickListener(v -> pickTimeAndSchedule("Midday Meditation", "medit", tvMeditTime));
        btnDietSet.setOnClickListener(v -> pickTimeAndSchedule("Evening Light Meal", "diet", tvDietTime));

        btnYogaCancel.setOnClickListener(v -> confirmAndCancel("yoga", "Cancel Yoga reminder?", tvYogaTime));
        btnMeditCancel.setOnClickListener(v -> confirmAndCancel("medit", "Cancel Meditation reminder?", tvMeditTime));
        btnDietCancel.setOnClickListener(v -> confirmAndCancel("diet", "Cancel Diet reminder?", tvDietTime));

        btnYogaDone.setOnClickListener(v -> confirmAndMarkDone("yoga", tvYogaStreak));
        btnMeditDone.setOnClickListener(v -> confirmAndMarkDone("medit", tvMeditStreak));
        btnDietDone.setOnClickListener(v -> confirmAndMarkDone("diet", tvDietStreak));

        if (backHomeButton != null) {
            backHomeButton.setOnClickListener(v -> {
                Intent intent = new Intent(DailyWellnessActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        if (btnOpenCalendar != null) {
            btnOpenCalendar.setOnClickListener(v -> startActivity(new Intent(DailyWellnessActivity.this, CalendarActivity.class)));
        }
    }

    // Confirmation dialogs
    private void confirmAndCancel(String keyPrefix, String message, TextView tvTime) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which) -> {
                    cancelReminder(keyPrefix);
                    updateScheduledTimeText(keyPrefix, tvTime);
                    Toast.makeText(this, "Reminder canceled ❌", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setCancelable(true)
                .show();
    }

    private void confirmAndMarkDone(String keyPrefix, TextView tvStreak) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Mark this task done for today?")
                .setPositiveButton("Yes", (dialog, which) -> markDone(keyPrefix, tvStreak))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setCancelable(true)
                .show();
    }

    // TimePicker and scheduling
    private void pickTimeAndSchedule(String title, String keyPrefix, TextView tvTime) {
        final Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        new TimePickerDialog(this, (TimePicker view, int selectedHour, int selectedMinute) -> {
            scheduleDailyReminder(this, selectedHour, selectedMinute, title, keyPrefix);
            saveScheduledTime(keyPrefix, selectedHour, selectedMinute);
            updateScheduledTimeText(keyPrefix, tvTime);
            Toast.makeText(DailyWellnessActivity.this, "Reminder set for " + String.format("%02d:%02d ⏰", selectedHour, selectedMinute), Toast.LENGTH_SHORT).show();
        }, hour, minute, true).show();
    }

    private static final String TAG = "DailyWellnessActivity";

    private void scheduleDailyReminder(Context ctx, int hour, int minute, String title, String keyPrefix) {
        AlarmManager alarmMgr = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ctx, ReminderReceiver.class);
        intent.putExtra(ReminderReceiver.EXTRA_TITLE, title);
        intent.putExtra(ReminderReceiver.EXTRA_KEY, keyPrefix);

        int reqCode = (keyPrefix + "_alarm").hashCode();
        PendingIntent pending = PendingIntent.getBroadcast(ctx, reqCode, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar now = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        if (!cal.after(now)) {
            cal.add(Calendar.DATE, 1);
        }

        long triggerAtMillis = cal.getTimeInMillis();
        Log.i(TAG, "Scheduling alarm for " + title + " at " + cal.getTime().toString());

        if (alarmMgr == null) {
            Log.e(TAG, "AlarmManager is null — cannot schedule alarm.");
            return;
        }

        boolean exactAllowed = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                exactAllowed = alarmMgr.canScheduleExactAlarms();
            } catch (Exception e) {
                exactAllowed = false;
            }
        } else {
            exactAllowed = true;
        }

        if (exactAllowed) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending);
            } else {
                alarmMgr.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending);
            }

            int repeatReq = (keyPrefix + "_daily_repeat").hashCode();
            PendingIntent repeatPending = PendingIntent.getBroadcast(ctx, repeatReq, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, AlarmManager.INTERVAL_DAY, repeatPending);

        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending);
            int repeatReq = (keyPrefix + "_daily_repeat").hashCode();
            PendingIntent repeatPending = PendingIntent.getBroadcast(ctx, repeatReq, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, AlarmManager.INTERVAL_DAY, repeatPending);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                try {
                    Intent intentSettings = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                    intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(intentSettings);
                } catch (Exception e) {
                    Log.w(TAG, "Could not open exact-alarm settings: " + e.getMessage());
                }
            }
        }
    }

    private void saveScheduledTime(String keyPrefix, int hour, int minute) {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        prefs.edit().putInt(keyPrefix + HOUR_SUFFIX, hour).putInt(keyPrefix + MIN_SUFFIX, minute).apply();
    }

    private void cancelReminder(String keyPrefix) {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        int reqCode = (keyPrefix + "_alarm").hashCode();

        PendingIntent existing = PendingIntent.getBroadcast(this, reqCode, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_NO_CREATE);
        if (existing != null) {
            if (alarmMgr != null) alarmMgr.cancel(existing);
            existing.cancel();
        } else {
            PendingIntent p = PendingIntent.getBroadcast(this, reqCode, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            if (alarmMgr != null) alarmMgr.cancel(p);
            if (p != null) p.cancel();
        }

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        prefs.edit().remove(keyPrefix + HOUR_SUFFIX).remove(keyPrefix + MIN_SUFFIX).apply();
    }

    private void updateScheduledTimeText(String keyPrefix, TextView tv) {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        int hour = prefs.getInt(keyPrefix + HOUR_SUFFIX, -1);
        int min = prefs.getInt(keyPrefix + MIN_SUFFIX, -1);
        if (hour == -1 || min == -1) tv.setText("Reminder: Not set");
        else tv.setText("Reminder: " + String.format("%02d:%02d", hour, min));
    }

    private void markDone(String keyPrefix, TextView tvStreak) {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String lastDoneKey = keyPrefix + LAST_DONE_SUFFIX;
        String streakKey = keyPrefix + STREAK_SUFFIX;

        long lastDone = prefs.getLong(lastDoneKey, 0);
        long today = startOfDayMillis(System.currentTimeMillis());
        if (lastDone == today) {
            Toast.makeText(this, "Already marked done for today ✅", Toast.LENGTH_SHORT).show();
            return;
        }

        long yesterday = startOfDayMillis(System.currentTimeMillis() - AlarmManager.INTERVAL_DAY);
        int streak = prefs.getInt(streakKey, 0);
        if (lastDone == yesterday) streak++; else streak = 1;

        prefs.edit().putLong(lastDoneKey, today).putInt(streakKey, streak).apply();
        tvStreak.setText("🔥 Streak: " + streak + " days");
        Toast.makeText(this, "Marked done for today! 🎉", Toast.LENGTH_SHORT).show();

        // History logging
        long nowMillis = System.currentTimeMillis();
        appendToHistory(keyPrefix, nowMillis);
    }

    private void appendToHistory(String keyPrefix, long timestampMillis) {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String histKey = keyPrefix + HISTORY_SUFFIX;
        String existing = prefs.getString(histKey, "");
        StringBuilder sb = new StringBuilder();
        if (existing != null && !existing.trim().isEmpty()) {
            sb.append(existing);
            sb.append(',');
        }
        sb.append(Long.toString(timestampMillis));
        prefs.edit().putString(histKey, sb.toString()).apply();
    }

    private void updateStreakText(String keyPrefix, TextView tv) {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        int streak = prefs.getInt(keyPrefix + STREAK_SUFFIX, 0);
        tv.setText("🔥 Streak: " + streak + " days");
    }

    private long startOfDayMillis(long timeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeMillis);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
}
