package com.swe3.miniproject1;

import java.util.ArrayList;
import java.util.List;

public class EligibilityUtils {

    public static List<String> getEligibleCourses(int maths, int addMaths, int english, int science) {
        List<String> courses = new ArrayList<>();

        if (maths <= 3 && english <= 3 && science <= 3) {
            courses.add("All Electrical & IT Diplomas");
            courses.add("All Mechanical Diplomas");
        }

        if (maths <= 2 && english <= 2 && science <= 2) {
            courses.add("DSE Software Engineering");
            courses.add("DNS Network Security");
            courses.add("DPM Product Design & Manufacturing");

            courses.add("GAPP Private");
            courses.add("GUFP Private");

            if (addMaths <= 2) {
                courses.add("GAPP Sponsored");
                courses.add("GUFP Sponsored");
            }
        }

        return courses;
    }
}
