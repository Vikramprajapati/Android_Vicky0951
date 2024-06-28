package com.example.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Attendance> attendanceList;
    private boolean isTakingAttendance;

    public AttendanceAdapter(Context context, ArrayList<Attendance> attendanceList, boolean isTakingAttendance) {
        this.context = context;
        this.attendanceList = attendanceList;
        this.isTakingAttendance = isTakingAttendance;
    }

    private static final int VIEW_TYPE_SIMPLE = 0;
    private static final int VIEW_TYPE_TAKE_ATTENDANCE = 1;

    @Override
    public int getItemViewType(int position) {
        return isTakingAttendance ? VIEW_TYPE_TAKE_ATTENDANCE : VIEW_TYPE_SIMPLE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TAKE_ATTENDANCE) {
            View view = LayoutInflater.from(context).inflate(R.layout.attendance_table_row_2, parent, false);
            return new AttendanceTakeViewHolder(view, attendanceList);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.attendance_table_row, parent, false);
            return new AttendanceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);
        if (holder instanceof AttendanceViewHolder) {
            ((AttendanceViewHolder) holder).bind(attendance);
        } else {
            ((AttendanceTakeViewHolder) holder).bind(attendance);
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }
    public void setTakingAttendance(boolean takingAttendance) {
        isTakingAttendance = takingAttendance;
        notifyDataSetChanged(); // Refresh the RecyclerView
    }

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRollNo;
        TextView textViewName;
        TextView textViewDate;
        TextView textViewSubject;
        TextView textViewStatus;



        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRollNo = itemView.findViewById(R.id.textViewRollNo);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
//            textViewSubject = itemView.findViewById(R.id.textViewSubject);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
        }

            public void bind (Attendance attendance){
                textViewRollNo.setText(attendance.getRollNo());
                textViewName.setText(attendance.getName());
                textViewDate.setText(attendance.getDate());
//                textViewSubject.setText(attendance.getSubject());
                textViewStatus.setText(attendance.getStatus());
            }
        }

    public static class AttendanceTakeViewHolder extends RecyclerView.ViewHolder {

            TextView textViewRollNo;
            TextView textViewName;
            TextView textViewDate;
            TextView textViewSubject;
            TextView textViewStatus;
        Button buttonToggleAttendance;
        ArrayList<Attendance> attendanceList;

            public AttendanceTakeViewHolder(@NonNull View itemView,ArrayList<Attendance> attendanceList) {
                super(itemView);
                this.attendanceList=attendanceList;
                textViewRollNo = itemView.findViewById(R.id.textViewRollNo);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewDate = itemView.findViewById(R.id.textViewDate);
                textViewSubject = itemView.findViewById(R.id.textViewSubject);
                textViewStatus = itemView.findViewById(R.id.textViewStatus);
                buttonToggleAttendance = itemView.findViewById(R.id.buttonToggleAttendance);
                buttonToggleAttendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Attendance attendance = attendanceList.get(getAdapterPosition());
                        int position = getAdapterPosition();

                        // Toggle status
                        if (attendance.getStatus().equals("A")) {
                            attendance.setStatus("P");
                            buttonToggleAttendance.setText("P");
                        } else {
                            attendance.setStatus("A");
                            buttonToggleAttendance.setText("A");
                        }
//                        notifyItemChanged(position);
                    }
                });
        }

        public void bind(Attendance attendance) {
            textViewRollNo.setText(attendance.getRollNo());
            textViewName.setText(attendance.getName());
            textViewDate.setText(attendance.getDate());
            textViewSubject.setText(attendance.getSubject());
            buttonToggleAttendance.setText(attendance.getStatus());
        }
    }
}

