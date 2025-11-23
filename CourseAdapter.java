package com.swe3.miniproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> courseList;
    private OnCourseClickListener listener;

    // Interface for click callbacks
    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }

    // Constructor
    public CourseAdapter(Context context, List<Course> courseList, OnCourseClickListener listener) {
        this.context = context;
        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);

        // Set course data
        holder.titleTextView.setText(course.getTitle());
        holder.programTypeTextView.setText(course.getProgramType());

        // **1. Dynamic Image Logic Implementation**
        int iconResId = getCourseIconResource(course);
        holder.iconImageView.setImageResource(iconResId);

        // 💡 CRITICAL FIX: Remove the tint that was applied in the XML
        // This will allow your color icons to show their true colors.
        holder.iconImageView.setColorFilter(null);

        // Click listener
        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCourseClick(course);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    /**
     * Helper method to determine the correct drawable resource ID based on the course data.
     */
    private int getCourseIconResource(Course course) {
        String programType = course.getProgramType();
        // Assumes Course.getDepartment() exists and works based on CoursesActivity.java
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

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView iconImageView;
        TextView titleTextView;
        TextView programTypeTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_course_item);
            iconImageView = itemView.findViewById(R.id.course_icon);
            titleTextView = itemView.findViewById(R.id.text_course_title);
            programTypeTextView = itemView.findViewById(R.id.text_programme_type);
        }
    }
}