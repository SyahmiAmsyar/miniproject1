package com.swe3.miniproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    private ImageView introImage;
    private TextView title, subtitle;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Initialize views
        introImage = findViewById(R.id.img_intro);
        title = findViewById(R.id.text_intro_title);
        subtitle = findViewById(R.id.text_intro_subtitle);
        nextButton = findViewById(R.id.button_intro_next);

        // ===========================
        // 1️⃣ Image Animation
        // ===========================
        animateView(introImage, 0);

        // ===========================
        // 2️⃣ Title Animation
        // ===========================
        animateView(title, 400);  // delay 400ms after image

        // ===========================
        // 3️⃣ Subtitle Animation
        // ===========================
        animateView(subtitle, 800); // delay 800ms after image

        // ===========================
        // 4️⃣ Button Animation
        // ===========================
        animateView(nextButton, 1200); // delay 1200ms after image

        // ===========================
        // 5️⃣ Button Click
        // ===========================
        nextButton.setOnClickListener(v -> {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish(); // Prevent returning to intro
        });
    }

    /**
     * Helper method to animate any view with fade-in + slide-up
     * @param view The view to animate
     * @param delay Start offset in milliseconds
     */
    private void animateView(android.view.View view, long delay) {
        AnimationSet animationSet = new AnimationSet(true);

        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(800);
        fadeIn.setStartOffset(delay);

        TranslateAnimation slideUp = new TranslateAnimation(0, 0, 50, 0);
        slideUp.setDuration(800);
        slideUp.setStartOffset(delay);

        animationSet.addAnimation(fadeIn);
        animationSet.addAnimation(slideUp);
        animationSet.setFillAfter(true);

        view.startAnimation(animationSet);
    }
}
