package com.example.ayurveda_remedies.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.Context;

import com.example.ayurveda_remedies.R;

public class AnimationUtil {
    
    public static void fadeIn(View view, Context context) {
        Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        view.startAnimation(fadeIn);
        view.setVisibility(View.VISIBLE);
    }
    
    public static void slideUp(View view, Context context) {
        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        view.startAnimation(slideUp);
        view.setVisibility(View.VISIBLE);
    }
    
    public static void animateRecyclerItem(View view, Context context, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation);
        animation.setStartOffset(position * 50L); // Stagger animation
        view.startAnimation(animation);
    }
    
    public static void scaleIn(View view) {
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setAlpha(0.0f);
        view.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .alpha(1.0f)
            .setDuration(400)
            .start();
    }
    
    public static void pulse(View view) {
        view.animate()
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(150)
            .withEndAction(() -> {
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(150)
                    .start();
            })
            .start();
    }
}
