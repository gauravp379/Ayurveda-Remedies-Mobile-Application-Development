package com.example.ayurveda_remedies;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Simple Progress Tracker: persist entries, filter by date, show dosha balance
 */
public class ProgressActivity extends AppCompatActivity {

    private static final String PREFS = "wellness_prefs";
    private static final String PROG_KEY = "progress_entries_v1";

    private EditText etNote;
    private Spinner spDosha;
    private Button btnAddEntry;
    private LinearLayout entriesContainer;

    private Button btnFromDate, btnToDate, btnApplyFilter, btnClearFilter, btnOpenCalendar;
    private ProgressBar pbVata, pbPitta, pbKapha;
    private TextView tvVataPct, tvPittaPct, tvKaphaPct;

    private long filterFrom = -1; // inclusive start millis, -1 = no filter
    private long filterTo = -1;   // inclusive end millis

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        
        // Load profile picture and username
        ProfileUtils.loadProfileIntoViews(this, R.id.ivProfilePic, R.id.tvUserName);

        etNote = findViewById(R.id.etProgressNote);
        spDosha = findViewById(R.id.spProgressDosha);
        btnAddEntry = findViewById(R.id.btnAddProgressEntry);
        entriesContainer = findViewById(R.id.progressEntriesContainer);

        btnFromDate = findViewById(R.id.btnFilterFrom);
        btnToDate = findViewById(R.id.btnFilterTo);
        btnApplyFilter = findViewById(R.id.btnApplyProgressFilter);
        btnClearFilter = findViewById(R.id.btnClearProgressFilter);
        btnOpenCalendar = findViewById(R.id.btnOpenCalendar);

        pbVata = findViewById(R.id.pbVata);
        pbPitta = findViewById(R.id.pbPitta);
        pbKapha = findViewById(R.id.pbKapha);
        tvVataPct = findViewById(R.id.tvVataPct);
        tvPittaPct = findViewById(R.id.tvPittaPct);
        tvKaphaPct = findViewById(R.id.tvKaphaPct);

        // spinner options
        ArrayAdapter<String> ada = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                Arrays.asList("Vata", "Pitta", "Kapha"));
        spDosha.setAdapter(ada);

        // add entry
        btnAddEntry.setOnClickListener(v -> {
            String note = etNote.getText().toString().trim();
            String dosha = spDosha.getSelectedItem().toString();
            if (TextUtils.isEmpty(note)) {
                Toast.makeText(this, "Please enter a short note", Toast.LENGTH_SHORT).show();
                return;
            }
            addProgressEntry(dosha, note, Calendar.getInstance().getTimeInMillis());
            etNote.setText("");
            refreshEntries();
            Toast.makeText(this, "Entry added ✅", Toast.LENGTH_SHORT).show();
        });

        // date pickers for filters
        btnFromDate.setOnClickListener(v -> pickDate((millis) -> {
            filterFrom = millis;
            btnFromDate.setText("From: " + sdf.format(new Date(millis)));
        }));

        btnToDate.setOnClickListener(v -> pickDate((millis) -> {
            filterTo = millis;
            btnToDate.setText("To: " + sdf.format(new Date(millis)));
        }));

        btnApplyFilter.setOnClickListener(v -> refreshEntries());
        btnClearFilter.setOnClickListener(v -> {
            filterFrom = -1;
            filterTo = -1;
            btnFromDate.setText("From: Any");
            btnToDate.setText("To: Any");
            refreshEntries();
        });

        btnOpenCalendar.setOnClickListener(v -> {
            startActivity(new Intent(ProgressActivity.this, CalendarActivity.class));
        });

        // initial UI state
        btnFromDate.setText("From: Any");
        btnToDate.setText("To: Any");

        refreshEntries();
    }

    // Date picker helper with callback
    private interface DatePicked {
        void onPicked(long millis);
    }

    private void pickDate(DatePicked cb) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar picked = Calendar.getInstance();
                    picked.set(year, month, dayOfMonth, 0, 0, 0);
                    picked.set(Calendar.MILLISECOND, 0);
                    cb.onPicked(picked.getTimeInMillis());
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    // persist entry as JSON object inside JSONArray in SharedPreferences
    private void addProgressEntry(String dosha, String note, long dateMillis) {
        try {
            SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
            String raw = prefs.getString(PROG_KEY, "[]");
            JSONArray arr = new JSONArray(raw);

            JSONObject obj = new JSONObject();
            obj.put("id", UUID.randomUUID().toString());
            obj.put("dosha", dosha);
            obj.put("note", note);
            obj.put("date", dateMillis);

            // insert at beginning so newest appear first
            JSONArray newArr = new JSONArray();
            newArr.put(obj);
            for (int i = 0; i < arr.length(); i++) newArr.put(arr.get(i));

            prefs.edit().putString(PROG_KEY, newArr.toString()).apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // load entries, apply filter, update UI
    private void refreshEntries() {
        entriesContainer.removeAllViews();
        List<JSONObject> list = loadStoredEntries();

        // apply date filters if present
        List<JSONObject> filtered = new ArrayList<>();
        for (JSONObject o : list) {
            long date = o.optLong("date", 0);
            boolean ok = true;
            if (filterFrom != -1) {
                ok &= (date >= filterFrom);
            }
            if (filterTo != -1) {
                // include entire picked day: add 23:59:59 to filterTo
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(filterTo);
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                c.set(Calendar.MILLISECOND, 999);
                ok &= (date <= c.getTimeInMillis());
            }
            if (ok) filtered.add(o);
        }

        if (filtered.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText("No progress entries for the selected range.");
            tv.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));
            entriesContainer.addView(tv);
        } else {
            for (JSONObject o : filtered) {
                entriesContainer.addView(makeRowFrom(o));
            }
        }

        // update dosha balance based on filtered entries
        updateDoshaBalance(filtered);
    }

    private List<JSONObject> loadStoredEntries() {
        List<JSONObject> out = new ArrayList<>();
        try {
            SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
            String raw = prefs.getString(PROG_KEY, "[]");
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) {
                out.add(arr.getJSONObject(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    // create a simple row for each entry
    private android.view.View makeRowFrom(JSONObject o) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);
        int pad = dpToPx(12);
        row.setPadding(pad, pad, pad, pad);
        row.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dpToPx(8), 0, dpToPx(8));
        row.setLayoutParams(lp);

        String dosha = o.optString("dosha", "—");
        String note = o.optString("note", "");
        long date = o.optLong("date", 0);

        TextView tvDate = new TextView(this);
        tvDate.setText("📅 " + sdf.format(new Date(date)));
        tvDate.setTextSize(14f);
        tvDate.setTextColor(0xFF4CAF50);
        row.addView(tvDate);

        TextView tvMeta = new TextView(this);
        tvMeta.setText("Dosha: " + dosha);
        tvMeta.setTextSize(13f);
        tvMeta.setPadding(0, dpToPx(4), 0, 0);
        row.addView(tvMeta);

        TextView tvNote = new TextView(this);
        tvNote.setText(note);
        tvNote.setPadding(0, dpToPx(8), 0, 0);
        row.addView(tvNote);

        // small delete button row
        Button btnDelete = new Button(this);
        btnDelete.setText("Delete");
        btnDelete.setTextSize(12f);
        LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnLp.gravity = Gravity.END;
        btnLp.setMargins(0, dpToPx(8), 0, 0);
        btnDelete.setLayoutParams(btnLp);
        btnDelete.setOnClickListener(v -> {
            confirmAndDelete(o.optString("id", ""), () -> {
                refreshEntries();
            });
        });
        row.addView(btnDelete);

        return row;
    }

    private void confirmAndDelete(String id, Runnable after) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Remove this entry?")
                .setPositiveButton("Yes", (d, w) -> {
                    deleteEntry(id);
                    after.run();
                })
                .setNegativeButton("No", (d, w) -> d.dismiss())
                .show();
    }

    private void deleteEntry(String id) {
        try {
            SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
            String raw = prefs.getString(PROG_KEY, "[]");
            JSONArray arr = new JSONArray(raw);
            JSONArray out = new JSONArray();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                if (!id.equals(o.optString("id", ""))) out.put(o);
            }
            prefs.edit().putString(PROG_KEY, out.toString()).apply();
            Toast.makeText(this, "Deleted ❌", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // compute dosha percentages and set progressbars
    private void updateDoshaBalance(List<JSONObject> list) {
        int v=0,p=0,k=0;
        for (JSONObject o : list) {
            String d = o.optString("dosha", "");
            if ("Vata".equalsIgnoreCase(d)) v++;
            else if ("Pitta".equalsIgnoreCase(d)) p++;
            else if ("Kapha".equalsIgnoreCase(d)) k++;
        }
        int total = v + p + k;
        if (total == 0) {
            pbVata.setProgress(0); pbPitta.setProgress(0); pbKapha.setProgress(0);
            tvVataPct.setText("0%");
            tvPittaPct.setText("0%");
            tvKaphaPct.setText("0%");
            return;
        }
        int pv = Math.round(100f * v / total);
        int pp = Math.round(100f * p / total);
        int pk = 100 - pv - pp; // ensure sums to 100
        pbVata.setProgress(pv);
        pbPitta.setProgress(pp);
        pbKapha.setProgress(pk);
        tvVataPct.setText(pv + "%");
        tvPittaPct.setText(pp + "%");
        tvKaphaPct.setText(pk + "%");
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
