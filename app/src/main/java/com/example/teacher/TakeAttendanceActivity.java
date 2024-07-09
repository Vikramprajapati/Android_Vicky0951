package com.example.teacher;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.Attendance;
import com.example.common.AttendanceAdapter;
import com.example.common.DBHelper;
import com.example.admin.R;

import java.util.ArrayList;
import java.util.Calendar;

public class TakeAttendanceActivity extends AppCompatActivity {
    private RecyclerView recyclerViewAttendance;
    private String selectedYear, selectedBranch, selectedSemester, selectedSubject, selectedDate;
    private TextView textViewSelectedDetails, textViewSelectedDate;
    private Button buttonSelectDate, buttonSubmitAttendance;
    private AttendanceAdapter attendanceAdapter;
    private ArrayList<Attendance> studentList = new ArrayList<>();
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        dbHelper = new DBHelper(this);
        textViewSelectedDetails = findViewById(R.id.textViewSelectedDetails);
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);
        buttonSelectDate = findViewById(R.id.buttonSelectDate);
        buttonSubmitAttendance = findViewById(R.id.buttonSubmitAttendance);
        recyclerViewAttendance = findViewById(R.id.recyclerViewAttendance);
        // Get the selected details from the Intent
        Intent intent = getIntent();
        selectedYear = intent.getStringExtra("selectedYear");
        selectedBranch = intent.getStringExtra("selectedBranch");
        selectedSemester = intent.getStringExtra("selectedSemester");
        selectedSubject = intent.getStringExtra("selectedSubject");

        // Display the selected details
        String selectedDetails = selectedYear + " " + selectedBranch + " " + selectedSemester + " " + selectedSubject;
        textViewSelectedDetails.setText(selectedDetails);
        recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(this));
        attendanceAdapter = new AttendanceAdapter(this, studentList,true);
        recyclerViewAttendance.setAdapter(attendanceAdapter);
        // Initialize DBHelper


        // Load students data
        loadStudentsData();
        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        buttonSubmitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate == null) {
                    Toast.makeText(TakeAttendanceActivity.this, "Please select a date first.", Toast.LENGTH_SHORT).show();
                } else {
                    submitAttendance();
                }
            }
        });

    }
    private void loadStudentsData() {
        Cursor cursor = dbHelper.getFilteredStudents( selectedBranch,selectedYear);
        if (cursor.moveToFirst()) {
            do {
                String rollNo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STUDENT_ROLL_NO));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_STUDENT_NAME));
                // For attendance, initially mark all students as absent or create a default status
                studentList.add(new Attendance(rollNo, name,selectedDate,selectedYear,selectedBranch,selectedSemester, selectedSubject, "A"));

            } while (cursor.moveToNext());
        }
        else {
            // Display toast message if no data is available
            Toast.makeText(TakeAttendanceActivity.this, "No data available for the selected criteria", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        attendanceAdapter.notifyDataSetChanged();
    }
    private void submitAttendance() {
        for (Attendance attendance : studentList) {
            dbHelper.insertAttendance(attendance.getRollNo(), attendance.getName(),
                    selectedDate, selectedYear,selectedBranch,selectedSemester,selectedSubject, attendance.getStatus());
        }
        Toast.makeText(this, "Attendance submitted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        textViewSelectedDate.setText("Selected Date: " + selectedDate);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}