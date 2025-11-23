package com.swe3.miniproject1;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AboutUsActivity extends AppCompatActivity {

    private CardView cardVision, cardMission, cardBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        cardVision = findViewById(R.id.card_vision);
        cardMission = findViewById(R.id.card_mission);
        cardBackground = findViewById(R.id.card_background);

        // Initially hide all cards
        cardVision.setVisibility(View.INVISIBLE);
        cardMission.setVisibility(View.INVISIBLE);
        cardBackground.setVisibility(View.INVISIBLE);

        // Animate them one by one
        showCardWithDelay(cardVision, 0);
        showCardWithDelay(cardMission, 500); // 0.5s after vision
        showCardWithDelay(cardBackground, 1000); // 1s after vision
    }

    private void showCardWithDelay(final CardView card, int delayMillis) {
        new Handler().postDelayed(() -> {
            card.setVisibility(View.VISIBLE);

            // Fade-in animation
            AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
            fadeIn.setDuration(500); // half a second fade
            card.startAnimation(fadeIn);

        }, delayMillis);
    }
}
