package com.example.ayurveda_remedies;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FeedbackActivity extends AppCompatActivity {

    private EditText etName, etEmail, etMessage;
    private RatingBar ratingBar;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        etName = findViewById(R.id.etFeedbackName);
        etEmail = findViewById(R.id.etFeedbackEmail);
        etMessage = findViewById(R.id.etFeedbackMessage);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmit = findViewById(R.id.btnSubmitFeedback);

        btnSubmit.setOnClickListener(v -> submitFeedback());
    }

    private void submitFeedback() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String message = etMessage.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save feedback locally
        saveFeedback(name, email, message, rating);

        Toast.makeText(this, "Thank you for your feedback! ⭐", Toast.LENGTH_LONG).show();
        finish();
    }

    private void saveFeedback(String name, String email, String message, float rating) {
        try {
            SharedPreferences prefs = getSharedPreferences("wellness_prefs", MODE_PRIVATE);
            String existing = prefs.getString("feedbacks", "[]");
            JSONArray feedbacks = new JSONArray(existing);

            JSONObject feedback = new JSONObject();
            feedback.put("name", name);
            feedback.put("email", email);
            feedback.put("message", message);
            feedback.put("rating", rating);
            feedback.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date()));

            feedbacks.put(feedback);
            prefs.edit().putString("feedbacks", feedbacks.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
