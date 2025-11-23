package com.swe3.miniproject1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.swe3.miniproject1.databinding.ActivityCoursesBinding;
import com.google.android.material.chip.Chip;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CoursesActivity extends AppCompatActivity {

    private ActivityCoursesBinding binding;
    private List<Course> allCourses;
    private List<Course> filteredCourses;
    private CourseAdapter adapter;

    private String currentProgramFilter = "All Programmes";
    private String currentSearchQuery = "";

    // Programme type constants
    private static final String ALL = "All Programmes";
    private static final String DIPLOMA = "Diploma";
    private static final String GAPP = "GAPP";
    private static final String GUFP = "GUFP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Programme Portfolio");
        }

        // Generate course data
        allCourses = generateCourseData();
        filteredCourses = new ArrayList<>(allCourses);

        // 🚀 Adapter initialization with Context, List, and OnCourseClickListener
        adapter = new CourseAdapter(this, filteredCourses, course -> {
            Intent intent = new Intent(CoursesActivity.this, CourseDetailsActivity.class);
            intent.putExtra("course_object", course);
            startActivity(intent);
        });

        binding.recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewCourses.setAdapter(adapter);

        setupFilterChips();
        setupSearchFunctionality();
        filterCourses();
    }

    private void setupFilterChips() {
        final String[] filterOptions = {ALL, DIPLOMA, GAPP, GUFP};
        binding.chipGroupFilters.removeAllViews();

        for (String filterText : filterOptions) {
            Chip chip = new Chip(this, null, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setText(filterText);
            chip.setCheckable(true);
            chip.setId(View.generateViewId());

            chip.setOnClickListener(v -> {
                for (int i = 0; i < binding.chipGroupFilters.getChildCount(); i++) {
                    Chip otherChip = (Chip) binding.chipGroupFilters.getChildAt(i);
                    if (otherChip != chip) otherChip.setChecked(false);
                }
                chip.setChecked(true);
                currentProgramFilter = chip.getText().toString();
                filterCourses();
            });

            if (filterText.equals(ALL)) chip.setChecked(true);
            binding.chipGroupFilters.addView(chip);
        }
    }

    private void setupSearchFunctionality() {
        binding.edittextSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s.toString().toLowerCase(Locale.getDefault());
                filterCourses();
            }
            @Override public void afterTextChanged(Editable s) { }
        });
    }

    private void filterCourses() {
        filteredCourses.clear();
        String query = currentSearchQuery.trim().toLowerCase(Locale.getDefault());

        for (Course c : allCourses) {
            boolean matchesProgram = currentProgramFilter.equals(ALL) ||
                    c.getProgramType().trim().equalsIgnoreCase(currentProgramFilter.trim());

            boolean matchesSearch = query.isEmpty() ||
                    c.getTitle().toLowerCase(Locale.getDefault()).contains(query) ||
                    c.getCode().toLowerCase(Locale.getDefault()).contains(query) ||
                    c.getDescription().toLowerCase(Locale.getDefault()).contains(query);

            if (matchesProgram && matchesSearch) {
                filteredCourses.add(c);
            }
        }

        adapter.notifyDataSetChanged();
        binding.recyclerViewCourses.setVisibility(filteredCourses.isEmpty() ? View.GONE : View.VISIBLE);
        binding.textEmptyState.setVisibility(filteredCourses.isEmpty() ? View.VISIBLE : View.GONE);
    }

    /**
     * Generates the comprehensive list of GMI courses using the 8-argument Course constructor.
     */
    private List<Course> generateCourseData() {
        List<Course> courses = new ArrayList<>();

        // Common Data Definitions
        String diplomaType = DIPLOMA;
        String diplomaDuration = "3 Years (6 Semesters)";
        String diplomaEntry = "SPM: At least 3 credits including Mathematics, a Science/Technical subject, and one other subject. Meets GMI's general admission criteria.";

        String gappEntry = "SPM: Sponsored - A in English, Maths, Add Maths, Physics, Chemistry + 2 others (Sponsored) / C grade for Private students. Specific requirements apply.";
        String gufpEntry = "SPM / O-Levels or equivalent. Meet GMI foundation programme criteria for engineering degrees.";


        // =======================================================
        // --- DIPLOMA COURSES (8 Arguments) ---
        // =======================================================

        // 1. Electrical Engineering Cluster
        courses.add(new Course("DME", "Diploma of Mechatronics Engineering Technology",
                "Integrates mechanical, electrical, telecommunications, control, and computer engineering for product design and manufacturing.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Mechatronics Engineer, Robotics Specialist, Automation Technician.",
                "Electrical Engineering"));

        courses.add(new Course("DPI", "Diploma of Engineering Technology (Instrumentation & Control)",
                "Focuses on designing, installing, maintaining, and calibrating industrial measurement and control systems.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Instrumentation and Control Technician, Process Engineer, Calibration Specialist.",
                "Electrical Engineering"));

        courses.add(new Course("DEEC", "Diploma of Electronics Engineering Technology (Computer)",
                "Specializes in hardware and software interfaces, embedded systems, and computer peripherals.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Electronics Specialist, Embedded Systems Developer, PC/Server Technician.",
                "Electrical Engineering"));

        courses.add(new Course("DAET", "Autotronics Engineering Technology",
                "Covers the electrical, electronic, and software systems in modern vehicles (Automotive Electronics).",
                diplomaType, diplomaDuration, diplomaEntry,
                "Autotronics Technician, Vehicle Diagnostics Specialist, Automotive Service Manager.",
                "Electrical Engineering"));

        courses.add(new Course("DSPD", "Diploma in Engineering Technology (Sustainable Energy & Power Distribution)",
                "Training in renewable energy systems, grid integration, and efficient power distribution technologies.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Power Distribution Technician, Sustainable Energy Specialist, Electrical Maintenance Planner.",
                "Electrical Engineering"));

        // 2. Mechanical Engineering Cluster
        courses.add(new Course("DID", "Diploma in Engineering Technology (Industrial Design)",
                "Applies engineering principles to optimize product aesthetics, function, and manufacturing efficiency.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Industrial Designer, CAD Specialist, Product Development Assistant.",
                "Mechanical Engineering"));

        courses.add(new Course("DIQE", "Diploma in Industrial Quality Engineering Technology",
                "Focuses on quality assurance, statistical process control, metrology, and implementing quality standards (e.g., ISO).",
                diplomaType, diplomaDuration, diplomaEntry,
                "Quality Control Inspector, QA/QC Engineer, Metrologist.",
                "Mechanical Engineering"));

        courses.add(new Course("DIPDE", "Diploma in Innovative Product Design Engineering Technology",
                "A practical course combining mechanical design, creativity, and manufacturing feasibility.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Product Designer, R&D Technician, Prototyping Specialist.",
                "Mechanical Engineering"));

        courses.add(new Course("DAM", "Diploma in Engineering Technology (Advanced Maintenance)",
                "Focuses on predictive, preventive, and corrective maintenance techniques for complex industrial systems and machinery.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Advanced Maintenance Engineer, Reliability Technician, Industrial Asset Manager.",
                "Mechanical Engineering"));

        courses.add(new Course("DMEC", "Diploma of Mechanical Engineering Technology (CNC Precision)",
                "Specialized training in Computer Numerical Control (CNC) machining, programming, and high-precision manufacturing.",
                diplomaType, diplomaDuration, diplomaEntry,
                "CNC Programmer, Precision Machinist, Production Supervisor.",
                "Mechanical Engineering"));

        courses.add(new Course("DMTM", "Diploma in Engineering Technology (Machine Tools Maintenance)",
                "Focuses on the maintenance, troubleshooting, and repair of industrial machine tools and equipment.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Machine Maintenance Technician, Industrial Fitter, Service Engineer.",
                "Mechanical Engineering"));

        courses.add(new Course("DMEM", "Diploma of Mechanical Engineering Technology (Manufacturing)",
                "Covers core mechanical design, production processes, and management in a manufacturing environment.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Manufacturing Technician, Production Planner, Industrial Technologist.",
                "Mechanical Engineering"));

        courses.add(new Course("DPT", "Diploma in Precision Tooling Engineering Technology",
                "Training in designing and fabricating tools, dies, and molds for mass production.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Tool & Die Maker, Precision Fabricator, Mold Specialist.",
                "Mechanical Engineering"));

        // 3. Computer and Information Cluster
        courses.add(new Course("DSE", "Diploma in Software Engineering",
                "Focuses on coding, software architecture, application development, and system analysis.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Software Developer, Junior Programmer, Systems Analyst.",
                "Computer & Information"));

        courses.add(new Course("DCS", "Diploma in Cyber Security Technology",
                "Covers network security, threat analysis, ethical hacking, and digital forensics.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Cyber Security Analyst, Network Security Technician, IT Auditor.",
                "Computer & Information"));

        courses.add(new Course("DCM", "Diploma in Creative Multimedia",
                "Training in digital content creation, graphic design, 3D modeling, and interactive media development.",
                diplomaType, diplomaDuration, diplomaEntry,
                "Multimedia Designer, 3D Modeler, Digital Content Creator.",
                "Computer & Information"));

        // =======================================================
        // --- PREPARATORY / FOUNDATION COURSES (8 Arguments) ---
        // =======================================================
        courses.add(new Course("GAPP", "German A-Levels Programme",
                "Preparatory program leading to the German Higher Education Entrance Qualification for studies in Germany.",
                GAPP, "22 Months + 6 Months German Language", gappEntry,
                "Direct entry to University of Applied Sciences (UAS) in Germany or Malaysia.",
                "Foundation"));

        courses.add(new Course("GUFP", "GMI-UTP Foundation Programme",
                "One-year intensive foundation course providing the academic basis for engineering and technology degrees.",
                GUFP, "1 Year", gufpEntry,
                "Entry to UTP or GMI Diploma Programmes.",
                "Foundation"));

        return courses;
    }
}