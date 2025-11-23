package com.swe3.miniproject1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class CourseDetailsActivity extends AppCompatActivity {

    private Course course;
    private ImageView headerImage; // Corresponds to @+id/course_header_image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Assuming your layout file is named activity_course_details
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
            // The Course class implements Serializable, so this is correct
            course = (Course) getIntent().getSerializableExtra("course_object");

            if (course != null) {
                // Set the image dynamically
                setHeaderImage(course);
                // Populate the rest of the details
                populateDetails(course);
            }
        }
    }

    /**
     * Maps the Course data to a specific drawable resource ID and sets it on the header ImageView.
     */
    private void setHeaderImage(Course course) {
        int imageResId = getCourseIconResource(course);

        // Set the image resource
        headerImage.setImageResource(imageResId);

        // Remove any tint to ensure the image shows its true colors
        headerImage.setColorFilter(null);
    }

    /**
     * Helper method to determine the correct drawable resource ID based on the course data.
     */
    private int getCourseIconResource(Course course) {
        String programType = course.getProgramType();
        String department = course.getDepartment();

        if (programType.equalsIgnoreCase("GAPP")) {
            return R.drawable.gapp_image;
        } else if (programType.equalsIgnoreCase("GUFP")) {
            return R.drawable.gufp_image;
        } else if (programType.equalsIgnoreCase("Diploma")) {

            // --- DIPLOMA SUB-DIVISION LOGIC ---
            if (department != null) {
                if (department.equalsIgnoreCase("Electrical Engineering")) {
                    return R.drawable.eed_image;
                } else if (department.equalsIgnoreCase("Mechanical Engineering")) {
                    return R.drawable.med_image;
                } else if (department.equalsIgnoreCase("Computer & Information")) {
                    return R.drawable.cid_image;
                }
            }
            // Fallback for Diploma courses without a recognized department
            return R.drawable.placeholder_course;
        }

        // Default fallback image if type is not recognized
        return R.drawable.placeholder_course;
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
        // 🚀 FINAL FIX APPLIED HERE: Using the correct method getRequirements()
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