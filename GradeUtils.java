package com.swe3.miniproject1;

public class GradeUtils {
    public static int gradeToValue(String grade) {
        switch (grade.toUpperCase()) {
            case "A": return 1;
            case "B": return 2;
            case "C": return 3;
            case "D": return 4;
            case "E": return 5;
            default: throw new IllegalArgumentException("Invalid grade: " + grade);
        }
    }
}
