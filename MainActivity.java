package com.swe3.miniproject1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private CardView cardCourseListing;
    private CardView cardEligibilityChecker;
    private CardView cardEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize CardViews
        cardCourseListing = findViewById(R.id.card_course_listing);
        cardEligibilityChecker = findViewById(R.id.card_eligibility_checker);
        cardEnquiry = findViewById(R.id.card_enquiry);

        // Set click listeners
        setCardClick(cardCourseListing, CoursesActivity.class);
        setCardClick(cardEligibilityChecker, EligibilityCheckerActivity.class);
        setCardClick(cardEnquiry, EnquiryMenuActivity.class);
    }

    /**
     * Helper method to reduce repetitive code for CardView clicks
     *
     * @param cardView The CardView to attach the listener
     * @param activity The Activity class to launch
     */
    private void setCardClick(CardView cardView, Class<?> activity) {
        cardView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity)));
    }
}
