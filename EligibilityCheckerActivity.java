package com.swe3.miniproject1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EligibilityCheckerActivity extends AppCompatActivity {

    private EditText editMaths, editAddMaths, editEnglish, editScience;
    private EditText editOther1, editOther2, editOther3;
    private Button checkButton;
    private TextView resultText;

    private static final String ONLINE_APPLICATION_URL = "https://gmi.vialing.com/oa/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibility_checker);

        // ActionBar Title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.check_eligibility_title));
        }

        // Initialize Inputs
        editMaths = findViewById(R.id.edit_maths_score);
        editAddMaths = findViewById(R.id.edit_additional_maths_score);
        editEnglish = findViewById(R.id.edit_english_score);
        editScience = findViewById(R.id.edit_science_score);
        editOther1 = findViewById(R.id.edit_other1_score);
        editOther2 = findViewById(R.id.edit_other2_score);
        editOther3 = findViewById(R.id.edit_other3_score);

        // Initialize Button + Result
        checkButton = findViewById(R.id.button_check_eligibility);
        resultText = findViewById(R.id.text_eligibility_result);

        checkButton.setText(getString(R.string.button_check_eligibility));
        resultText.setText(getString(R.string.enter_grades_prompt));
        resultText.setVisibility(View.VISIBLE);

        // Listener
        checkButton.setOnClickListener(v -> checkEligibility());
    }

    private void checkEligibility() {

        // Reset button back to default
        checkButton.setText(getString(R.string.button_check_eligibility));
        checkButton.setOnClickListener(v -> checkEligibility());
        resultText.setVisibility(View.GONE);

        // Fetch Inputs
        String mathsStr = editMaths.getText().toString().trim().toUpperCase(Locale.ROOT);
        String addMathsStr = editAddMaths.getText().toString().trim().toUpperCase(Locale.ROOT);
        String englishStr = editEnglish.getText().toString().trim().toUpperCase(Locale.ROOT);
        String scienceStr = editScience.getText().toString().trim().toUpperCase(Locale.ROOT);
        String other1Str = editOther1.getText().toString().trim().toUpperCase(Locale.ROOT);
        String other2Str = editOther2.getText().toString().trim().toUpperCase(Locale.ROOT);
        String other3Str = editOther3.getText().toString().trim().toUpperCase(Locale.ROOT);

        // Required fields must not be empty
        if (mathsStr.isEmpty() || addMathsStr.isEmpty() || englishStr.isEmpty()
                || scienceStr.isEmpty() || other1Str.isEmpty() || other2Str.isEmpty()) {

            Toast.makeText(this, getString(R.string.toast_enter_all_grades), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            // Convert grades using GradeUtils
            int mathsValue = GradeUtils.gradeToValue(mathsStr);
            int addMathsValue = GradeUtils.gradeToValue(addMathsStr);
            int englishValue = GradeUtils.gradeToValue(englishStr);
            int scienceValue = GradeUtils.gradeToValue(scienceStr);
            int other1Value = GradeUtils.gradeToValue(other1Str);
            int other2Value = GradeUtils.gradeToValue(other2Str);

            int other3Value = other3Str.isEmpty() ? 99 : GradeUtils.gradeToValue(other3Str);

            // Collect all grades (for min 3 credits check)
            List<Integer> allGrades = new ArrayList<>();
            allGrades.add(mathsValue);
            allGrades.add(addMathsValue);
            allGrades.add(englishValue);
            allGrades.add(scienceValue);
            allGrades.add(other1Value);
            allGrades.add(other2Value);
            if (!other3Str.isEmpty()) allGrades.add(other3Value);

            long creditCount = allGrades.stream()
                    .filter(g -> g <= 3)
                    .count();

            // Get eligible courses
            List<String> eligibleCourses = EligibilityUtils.getEligibleCourses(
                    mathsValue, addMathsValue, englishValue, scienceValue
            );

            // Final eligibility logic
            if (creditCount < 3 || eligibleCourses.isEmpty()) {
                resultText.setText(getString(R.string.status_not_eligible));
                resultText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            } else {
                StringBuilder resultBuilder = new StringBuilder();
                resultBuilder.append(getString(R.string.status_eligible)).append("\n");

                // Remove duplicates
                eligibleCourses.stream().distinct()
                        .forEach(course -> resultBuilder.append("• ").append(course).append("\n"));

                resultText.setText(resultBuilder.toString());
                resultText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

                // Change button
                checkButton.setText(getString(R.string.online_application_prompt));
                checkButton.setOnClickListener(v -> redirectToOnlineApplication());
            }

        } catch (IllegalArgumentException e) {
            Toast.makeText(this, getString(R.string.toast_invalid_grade), Toast.LENGTH_LONG).show();
            return;
        }

        resultText.setVisibility(View.VISIBLE);
    }

    private void redirectToOnlineApplication() {
        Toast.makeText(this, getString(R.string.toast_redirecting), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ONLINE_APPLICATION_URL)));
    }
}
