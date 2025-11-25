package com.swe3.miniproject1;

import android.content.Intent; // Import Intent for navigation
import android.os.Bundle;
import android.view.View;
import android.widget.Button; // Import Button
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;

// ... (imports remain the same) ...

public class CourseDetailsActivity extends AppCompatActivity {

    private Course course;
    private ImageView headerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // 1. Setup Toolbar/ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 2. Find the Header ImageView
        headerImage = findViewById(R.id.course_header_image);

        // 3. Retrieve the Course object from the Intent
        if (getIntent().hasExtra("course_object")) {
            course = (Course) getIntent().getSerializableExtra("course_object");

            if (course != null) {
                // Set the image dynamically using the ID stored in the Course object
                setHeaderImage(course);
                // Populate the rest of the details
                populateDetails(course);
            }
        }

        // 4. Setup the new button listener
        setupEligibilityButton(); // This calls the revised method below
    }

    /**
     * Sets up the click listener for the "Check Eligibility" button
     * to navigate to EligibilityCheckerActivity.
     */
    private void setupEligibilityButton() {
        // --- UPDATED ID HERE ---
        Button checkEligibilityButton = findViewById(R.id.btn_check_eligibility);

        if (checkEligibilityButton != null) {
            checkEligibilityButton.setOnClickListener(v -> {
                // Intent to start the EligibilityCheckerActivity
                Intent intent = new Intent(CourseDetailsActivity.this, EligibilityCheckerActivity.class);
                startActivity(intent);
            });
        }
    }

    // ... (rest of the CourseDetailsActivity methods like setHeaderImage and populateDetails) ...

    private void setHeaderImage(Course course) {
        // Fetch the image ID directly from the Course object
        int imageResId = course.getImageResId();

        // Set the image resource
        headerImage.setImageResource(imageResId);

        // Remove any tint
        headerImage.setColorFilter(null);
    }

    /**
     * Populates all TextViews and sets up the accordion click listeners.
     */
    private void populateDetails(Course course) {
        // --- 1. Find Core Views ---
        TextView title = findViewById(R.id.course_title);
        TextView programTypeLabel = findViewById(R.id.label_program_type);
        TextView durationLabel = findViewById(R.id.label_duration);

        // --- 2. Find Accordion Headers and Content ---
        TextView headerAbout = findViewById(R.id.header_about);
        TextView contentAbout = findViewById(R.id.content_about);

        TextView headerRequirements = findViewById(R.id.header_requirements);
        TextView contentRequirements = findViewById(R.id.content_requirements);

        TextView headerCareer = findViewById(R.id.header_career);
        TextView contentCareer = findViewById(R.id.content_career);

        // --- 3. Set Core Details ---
        title.setText(course.getTitle());
        programTypeLabel.setText(course.getProgramType());
        durationLabel.setText(course.getDuration());

        // Set the CollapsingToolbar title
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle(course.getCode());
        }

        // --- 4. Set Accordion Content ---

        // About Section
        headerAbout.setText("About " + course.getTitle());
        contentAbout.setText(course.getDescription());

        // Entry Requirements Section
        headerRequirements.setText("Entry Requirements");
        contentRequirements.setText(course.getRequirements());

        // Career Opportunities Section
        headerCareer.setText("Career Opportunities");
        contentCareer.setText(course.getCareerOpportunities());

        // --- 5. Setup Accordion Click Listeners (to toggle visibility) ---

        View.OnClickListener toggleVisibility = v -> {
            TextView contentView = null;
            if (v.getId() == R.id.header_about) contentView = contentAbout;
            else if (v.getId() == R.id.header_requirements) contentView = contentRequirements;
            else if (v.getId() == R.id.header_career) contentView = contentCareer;

            if (contentView != null) {
                if (contentView.getVisibility() == View.GONE) {
                    contentView.setVisibility(View.VISIBLE);
                } else {
                    contentView.setVisibility(View.GONE);
                }
            }
        };

        headerAbout.setOnClickListener(toggleVisibility);
        headerRequirements.setOnClickListener(toggleVisibility);
        headerCareer.setOnClickListener(toggleVisibility);
    }
}
