package com.example.ayurveda_remedies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {

    private TextView tvDoshaName, tvDoshaDesc;
    private TextView tvVataPercent, tvPittaPercent, tvKaphaPercent;
    private ProgressBar pbVata, pbPitta, pbKapha;
    private Button btnSave, btnRetake;

    private static final String PREFS = "AyurvedaPrefs";
    private static final String KEY_DOSHA = "user_dosha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Your Results");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        tvDoshaName = findViewById(R.id.tvDoshaName);
        tvDoshaDesc = findViewById(R.id.tvDoshaDesc);
        tvVataPercent = findViewById(R.id.tvVataPercent);
        tvPittaPercent = findViewById(R.id.tvPittaPercent);
        tvKaphaPercent = findViewById(R.id.tvKaphaPercent);
        pbVata = findViewById(R.id.pbVata);
        pbPitta = findViewById(R.id.pbPitta);
        pbKapha = findViewById(R.id.pbKapha);
        btnSave = findViewById(R.id.btnSaveDosha);
        btnRetake = findViewById(R.id.btnRetake);

        int vata = getIntent().getIntExtra("score_vata", 0);
        int pitta = getIntent().getIntExtra("score_pitta", 0);
        int kapha = getIntent().getIntExtra("score_kapha", 0);

        int total = vata + pitta + kapha;
        int vataPercent = total > 0 ? (vata * 100) / total : 0;
        int pittaPercent = total > 0 ? (pitta * 100) / total : 0;
        int kaphaPercent = total > 0 ? (kapha * 100) / total : 0;

        // Set percentage bars
        pbVata.setMax(100);
        pbPitta.setMax(100);
        pbKapha.setMax(100);
        
        pbVata.setProgress(vataPercent);
        pbPitta.setProgress(pittaPercent);
        pbKapha.setProgress(kaphaPercent);

        tvVataPercent.setText("Vata: " + vataPercent + "%");
        tvPittaPercent.setText("Pitta: " + pittaPercent + "%");
        tvKaphaPercent.setText("Kapha: " + kaphaPercent + "%");

        // Decide dominant dosha
        String dominant = decideDominant(vata, pitta, kapha);
        tvDoshaName.setText(capitalize(dominant));
        tvDoshaDesc.setText(getDoshaDescription(dominant));

        btnSave.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_DOSHA, dominant);
            // Save quiz scores for profile display
            editor.putInt("quiz_vata", vata);
            editor.putInt("quiz_pitta", pitta);
            editor.putInt("quiz_kapha", kapha);
            editor.apply();

            Intent intent = new Intent(QuizResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnRetake.setOnClickListener(v -> {
            Intent i = new Intent(QuizResultActivity.this, DoshaQuizActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        });
    }

    private String decideDominant(int vata, int pitta, int kapha) {
        if (vata > pitta && vata > kapha) return "vata";
        if (pitta > vata && pitta > kapha) return "pitta";
        if (kapha > vata && kapha > pitta) return "kapha";

        // handle ties
        if (vata == pitta && vata > kapha) return "vata-pitta";
        if (vata == kapha && vata > pitta) return "vata-kapha";
        if (pitta == kapha && pitta > vata) return "pitta-kapha";

        return "balanced";
    }

    private String capitalize(String s) {
        if (s == null) return "";
        String[] parts = s.split("-");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String p = parts[i];
            if (p.length() > 0) sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1));
            if (i < parts.length - 1) sb.append("-");
        }
        return sb.toString();
    }

    private String getDoshaDescription(String doshaKey) {
        switch (doshaKey) {
            case "vata":
                return "Vata types are creative, energetic, but may be prone to dryness, irregular digestion and restless sleep. Favor warm, grounding foods and regular routines.";
            case "pitta":
                return "Pitta types are driven, sharp digestion, warm body, can be irritable. Favor cooling foods, moderate spices, and calming routines.";
            case "kapha":
                return "Kapha types are steady and strong, but may be sluggish, gain weight easily, and have slow digestion. Favor light, warming foods and active routines.";
            case "vata-pitta":
                return "Vata-Pitta combination — balance both: grounding routines for Vata and cooling moderation for Pitta.";
            case "vata-kapha":
                return "Vata-Kapha combination — aim for warmth and steady routines to balance both.";
            case "pitta-kapha":
                return "Pitta-Kapha combination — balance heat of Pitta with lighter Kapha-reducing practices.";
            default:
                return "Balanced constitution — keep a regular diet, seasonal awareness, and simple daily routine (Dinacharya).";
        }
    }
}
