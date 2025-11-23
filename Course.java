package com.swe3.miniproject1;

import java.io.Serializable;

public class Course implements Serializable {
    private String code;
    private String title;
    private String description;
    private String programType;
    private String duration;
    private String requirements;
    private String careerOpportunities;
    private String department;
    // Removed: private int headerImageResId;
    // Removed: private int iconResId;

    // 🚀 Constructor now takes 8 arguments
    public Course(String code, String title, String description, String programType,
                  String duration, String requirements, String careerOpportunities,
                  String department) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.programType = programType;
        this.duration = duration;
        this.requirements = requirements;
        this.careerOpportunities = careerOpportunities;
        this.department = department;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getProgramType() { return programType; }
    public String getDuration() { return duration; }
    public String getRequirements() { return requirements; }
    public String getCareerOpportunities() { return careerOpportunities; }
    public String getDepartment() { return department; }
    // Removed getHeaderImageResId() and getIconResId()
}