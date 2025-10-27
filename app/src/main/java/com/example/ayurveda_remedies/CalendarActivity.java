package com.example.ayurveda_remedies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayurveda_remedies.adapter.SymptomAdapter;
import com.example.ayurveda_remedies.model.Symptom;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * CalendarActivity - view notes per date and add new notes for a selected date.
 */
public class CalendarActivity extends AppCompatActivity {

    private static final String PREFS = "wellness_prefs";
    private static final String SYMPTOMS_KEY = "symptom_logs";

    CalendarView calendarView;
    TextView tvSelectedDate;
    RecyclerView rvCalendarNotes;
    FloatingActionButton fabAdd;
    Button btnViewAll;

    List<Symptom> allSymptoms = new ArrayList<>();
    List<Symptom> notesForSelectedDate = new ArrayList<>();
    SymptomAdapter adapter;

    // keep selected day's start/end millis
    long selectedDayStart = 0;
    long selectedDayEnd = 0;


    SimpleDateFormat displayFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat fullFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        
        // Load profile picture and username
        ProfileUtils.loadProfileIntoViews(this, R.id.ivProfilePic, R.id.tvUserName);

        calendarView = findViewById(R.id.calendarView);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        rvCalendarNotes = findViewById(R.id.rvCalendarNotes);
        fabAdd = findViewById(R.id.fabAddNote);
        btnViewAll = findViewById(R.id.btnViewAll);

        rvCalendarNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SymptomAdapter(notesForSelectedDate, position -> {
            confirmDelete(position);
        });
        rvCalendarNotes.setAdapter(adapter);

        loadAllNotes();

        // initialize selected date to today
        long today = calendarView.getDate(); // milliseconds at midnight (device)
        setSelectedDateRange(today);
        refreshNotesForSelectedDate();

        calendarView.setOnDateChangeListener((CalendarView view, int year, int month, int dayOfMonth) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, dayOfMonth);
            setSelectedDateRange(c.getTimeInMillis());
            refreshNotesForSelectedDate();
        });

        fabAdd.setOnClickListener(v -> showAddDialogForSelectedDate());

        btnViewAll.setOnClickListener(v -> {
            // Export all notes JSON to clipboard
            exportAllNotesToClipboard();
        });
    }

    private void loadAllNotes() {
        allSymptoms.clear();
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String json = prefs.getString(SYMPTOMS_KEY, null);
        if (json == null) return;
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                long ts = obj.optLong("timestamp", System.currentTimeMillis());
                String date = obj.optString("date", "");
                String text = obj.optString("text", "");
                allSymptoms.add(new Symptom(ts, date, text));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveAllNotes() {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        JSONArray arr = new JSONArray();
        try {
            for (Symptom s : allSymptoms) {
                JSONObject obj = new JSONObject();
                obj.put("timestamp", s.timestamp);
                obj.put("date", s.date);
                obj.put("text", s.text);
                arr.put(obj);
            }
            prefs.edit().putString(SYMPTOMS_KEY, arr.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSelectedDateRange(long anyTimeOnDayMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(anyTimeOnDayMillis);
        // set to start of day
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        selectedDayStart = c.getTimeInMillis();

        // end of day
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        selectedDayEnd = c.getTimeInMillis();

        tvSelectedDate.setText("Selected: " + displayFmt.format(new Date(selectedDayStart)));
    }

    private void refreshNotesForSelectedDate() {
        loadAllNotes(); // reload in case changed by other screens
        notesForSelectedDate.clear();
        for (Symptom s : allSymptoms) {
            if (s.timestamp >= selectedDayStart && s.timestamp <= selectedDayEnd) {
                notesForSelectedDate.add(s);
            }
        }
        adapter.updateList(notesForSelectedDate);
    }

    private void showAddDialogForSelectedDate() {
        final EditText input = new EditText(this);
        input.setHint("Enter note for " + displayFmt.format(new Date(selectedDayStart)));
        input.setMinLines(2);
        input.setMaxLines(4);
        input.setPadding(50, 30, 50, 30);

        new AlertDialog.Builder(this)
                .setTitle("Add Note")
                .setView(input)
                .setPositiveButton("Add", (d, w) -> {
                    String txt = input.getText().toString().trim();
                    if (TextUtils.isEmpty(txt)) {
                        Toast.makeText(this, "Enter text to add", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // create timestamp: use current hour/min but date set to selected date
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(selectedDayStart);
                    Calendar now = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
                    cal.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
                    cal.set(Calendar.SECOND, now.get(Calendar.SECOND));
                    long ts = cal.getTimeInMillis();
                    String dateStr = fullFmt.format(new Date(ts));
                    Symptom s = new Symptom(ts, dateStr, txt);
                    // insert at front of master list for newest-first ordering
                    allSymptoms.add(0, s);
                    saveAllNotes();
                    refreshNotesForSelectedDate();
                    Toast.makeText(this, "Note added ✅", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .show();
    }

    private void confirmDelete(int pos) {
        // pos is index within notesForSelectedDate
        new AlertDialog.Builder(this)
                .setTitle("Delete note?")
                .setMessage("Delete this note permanently?")
                .setPositiveButton("Yes", (d, w) -> {
                    Symptom s = notesForSelectedDate.get(pos);
                    removeSymptomByTimestamp(s.timestamp);
                    saveAllNotes();
                    refreshNotesForSelectedDate();
                    Toast.makeText(this, "Deleted ❌", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (d, w) -> {
                    adapter.notifyDataSetChanged();
                })
                .show();
    }

    private void removeSymptomByTimestamp(long ts) {
        for (int i = 0; i < allSymptoms.size(); i++) {
            if (allSymptoms.get(i).timestamp == ts) {
                allSymptoms.remove(i);
                return;
            }
        }
    }

    private void exportAllNotesToClipboard() {
        if (allSymptoms.isEmpty()) {
            Toast.makeText(this, "No notes to export", Toast.LENGTH_SHORT).show();
            return;
        }
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        try {
            // generate JSON string
            JSONArray arrOut = new JSONArray();
            for (Symptom s : allSymptoms) {
                JSONObject obj = new JSONObject();
                obj.put("timestamp", s.timestamp);
                obj.put("date", s.date);
                obj.put("text", s.text);
                arrOut.put(obj);
            }
            ClipData clip = ClipData.newPlainText("AyurvedaAllNotes", arrOut.toString());
            cm.setPrimaryClip(clip);
            Toast.makeText(this, "All notes exported to clipboard 📋", Toast.LENGTH_LONG).show();
        } catch (JSONException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Export failed", Toast.LENGTH_SHORT).show();
        }
    }
}
