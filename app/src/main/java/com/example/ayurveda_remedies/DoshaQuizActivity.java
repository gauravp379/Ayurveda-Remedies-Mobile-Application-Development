package com.example.ayurveda_remedies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class DoshaQuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvCounter;
    private RadioGroup rgOptions;
    private RadioButton rb1, rb2, rb3;
    private Button btnPrev, btnNext, btnSubmit;

    // simple question model
    private static class Question implements Serializable {
        final String text;
        final String opt1;
        final String opt2;
        final String opt3;
        // opt1 -> Vata, opt2 -> Pitta, opt3 -> Kapha (convention used here)
        Question(String q, String o1, String o2, String o3) {
            text = q; opt1 = o1; opt2 = o2; opt3 = o3;
        }
    }

    // 10 questions array
    private final Question[] questions = new Question[] {
            new Question("How would you describe your body frame?",
                    "Thin, light frame with prominent bones", 
                    "Medium, muscular, well-proportioned", 
                    "Large, solid, well-built frame"),
            new Question("What is your energy level throughout the day?",
                    "Comes in bursts, fluctuates easily", 
                    "Moderate and steady, good stamina", 
                    "Consistent and enduring, slow to start"),
            new Question("How is your digestion?",
                    "Irregular, sensitive, prone to gas/bloating", 
                    "Strong, fast metabolism, rarely skip meals", 
                    "Slow and steady, can skip meals easily"),
            new Question("What is your sleep pattern?",
                    "Light sleeper, difficulty falling asleep", 
                    "Moderate sleep, wake up refreshed", 
                    "Deep, long sleep, hard to wake up"),
            new Question("How do you handle stress?",
                    "Get anxious and worried easily", 
                    "Become irritable and frustrated", 
                    "Withdraw and become quiet"),
            new Question("What is your skin type?",
                    "Dry, rough, cool to touch", 
                    "Warm, oily, prone to rashes", 
                    "Thick, smooth, cool and moist"),
            new Question("How do you learn best?",
                    "Quick to learn, quick to forget", 
                    "Sharp intellect, good retention", 
                    "Slow to learn but never forget"),
            new Question("What is your typical body temperature?",
                    "Cold hands and feet, prefer warmth", 
                    "Usually warm, dislike heat", 
                    "Moderate, tolerate cold well"),
            new Question("How would you describe your speech?",
                    "Fast, talkative, sometimes scattered", 
                    "Clear, precise, argumentative", 
                    "Slow, deliberate, calm and gentle"),
            new Question("What is your general physical activity preference?",
                    "Prefer light, varied activities", 
                    "Enjoy competitive, intense exercise", 
                    "Prefer steady, gentle activities")
    };

    // stores selection index per question: 0,1,2 or -1 for none
    private final int[] answers = new int[questions.length];

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosha_quiz);

        ProfileUtils.loadProfileIntoViews(this, R.id.ivProfilePic, R.id.tvUserName);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Dosha Quiz");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        tvQuestion = findViewById(R.id.tvQuestion);
        tvCounter = findViewById(R.id.tvQuestionCounter);
        rgOptions = findViewById(R.id.rgOptions);
        rb1 = findViewById(R.id.rbOpt1);
        rb2 = findViewById(R.id.rbOpt2);
        rb3 = findViewById(R.id.rbOpt3);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);

        // init answers
        for (int i = 0; i < answers.length; i++) answers[i] = -1;

        loadQuestion(0);

        btnPrev.setOnClickListener(v -> {
            saveAnswerForCurrent();
            if (currentIndex > 0) {
                currentIndex--;
                loadQuestion(currentIndex);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (!saveAnswerForCurrent()) {
                Toast.makeText(this, "Please select an option before continuing", Toast.LENGTH_SHORT).show();
                return;
            }
            if (currentIndex < questions.length - 1) {
                currentIndex++;
                loadQuestion(currentIndex);
            }
        });

        btnSubmit.setOnClickListener(v -> {
            if (!saveAnswerForCurrent()) {
                Toast.makeText(this, "Please select an option before submitting", Toast.LENGTH_SHORT).show();
                return;
            }
            // compute scores
            int vata = 0, pitta = 0, kapha = 0;
            for (int ans : answers) {
                if (ans == 0) vata++;
                else if (ans == 1) pitta++;
                else if (ans == 2) kapha++;
            }

            // pass to result activity
            Intent intent = new Intent(DoshaQuizActivity.this, QuizResultActivity.class);
            intent.putExtra("score_vata", vata);
            intent.putExtra("score_pitta", pitta);
            intent.putExtra("score_kapha", kapha);
            startActivity(intent);
            finish();
        });
    }

    private void loadQuestion(int index) {
        Question q = questions[index];
        tvQuestion.setText(q.text);
        rb1.setText(q.opt1);
        rb2.setText(q.opt2);
        rb3.setText(q.opt3);
        tvCounter.setText("Question " + (index + 1) + " / " + questions.length);

        // restore previous selection
        rgOptions.clearCheck();
        int prev = answers[index];
        if (prev == 0) rb1.setChecked(true);
        else if (prev == 1) rb2.setChecked(true);
        else if (prev == 2) rb3.setChecked(true);

        // prev/next/submit button visibility
        btnPrev.setEnabled(index != 0);
        
        if (index == questions.length - 1) {
            btnNext.setEnabled(false);
            btnSubmit.setVisibility(android.view.View.VISIBLE);
        } else {
            btnNext.setEnabled(true);
            btnSubmit.setVisibility(android.view.View.GONE);
        }
    }

    // returns true if saved (an option was selected)
    private boolean saveAnswerForCurrent() {
        int checkedId = rgOptions.getCheckedRadioButtonId();
        int sel = -1;
        if (checkedId == R.id.rbOpt1) sel = 0;
        else if (checkedId == R.id.rbOpt2) sel = 1;
        else if (checkedId == R.id.rbOpt3) sel = 2;

        if (sel == -1) return false;
        answers[currentIndex] = sel;
        return true;
    }
}
