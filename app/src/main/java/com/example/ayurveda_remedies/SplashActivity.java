package com.example.ayurveda_remedies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500; // 2.5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Animate splash elements
        ImageView ivLogo = findViewById(R.id.ivLogo);
        TextView tvAppName = findViewById(R.id.tvAppName);
        TextView tvTagline = findViewById(R.id.tvTagline);
        
        // Logo scale in animation
        ivLogo.setScaleX(0.0f);
        ivLogo.setScaleY(0.0f);
        ivLogo.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setDuration(800)
            .start();
        
        // App name fade in
        tvAppName.setAlpha(0.0f);
        tvAppName.animate()
            .alpha(1.0f)
            .setStartDelay(400)
            .setDuration(600)
            .start();
        
        // Tagline slide up
        tvTagline.setTranslationY(50);
        tvTagline.setAlpha(0.0f);
        tvTagline.animate()
            .translationY(0)
            .alpha(1.0f)
            .setStartDelay(800)
            .setDuration(600)
            .start();

        // Navigate to MainActivity after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, SPLASH_DURATION);
    }
}
