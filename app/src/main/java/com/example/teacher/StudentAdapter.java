package com.example.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admin.R;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context context;
    private ArrayList<Student> students;

    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item_layout, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRollNo;
        TextView textViewName;
        TextView textViewBranch;
        TextView textViewSemester; // Added
        TextView textViewYear; // Added

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRollNo = itemView.findViewById(R.id.textViewRollNo);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewBranch = itemView.findViewById(R.id.textViewBranch);
            textViewSemester = itemView.findViewById(R.id.textViewSemester); // Initialize
            textViewYear = itemView.findViewById(R.id.textViewYear); // Initialize
        }

        public void bind(Student student) {
            textViewRollNo.setText("Roll No: " + student.getRollNo());
            textViewName.setText("Name: " + student.getName());
            textViewBranch.setText("Branch: " + student.getBranch());
            textViewSemester.setText("Semester: " + student.getSemester()); // Set semester
            textViewYear.setText("Year: " + student.getYear()); // Set year
        }
    }
}
