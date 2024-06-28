package com.example.teacher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;

import java.util.ArrayList;

public class ShowAttendanceActivity extends AppCompatActivity {
    private RecyclerView attendanceRecyclerView;
    private AttendanceAdapter adapter;
    private ArrayList<Attendance> attendanceList;
    private TextView textViewYear;
    private TextView textViewBranch;
    private TextView textViewSemester;
    private TextView textViewSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            TextView titleTextView = new TextView(this);
            titleTextView.setText("Attendance");
            titleTextView.setTextColor(Color.WHITE);
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            titleTextView.setTypeface(null, android.graphics.Typeface.BOLD);
            titleTextView.setGravity(Gravity.CENTER);

            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER
            );
            actionBar.setCustomView(titleTextView, params);
        }
        textViewYear = findViewById(R.id.textViewYear);
        textViewBranch = findViewById(R.id.textViewBranch);
        textViewSemester = findViewById(R.id.textViewSemester);
        textViewSubject = findViewById(R.id.textViewSubject);

        // Retrieve data from Intent extras
        Intent intent = getIntent();
        String selectedYear = intent.getStringExtra("selectedYear");
        String selectedBranch = intent.getStringExtra("selectedBranch");
        String selectedSemester = intent.getStringExtra("selectedSemester");
        String selectedSubject = intent.getStringExtra("selectedSubject");

        // Set data to TextViews
        textViewYear.setText(selectedYear);
        textViewBranch.setText(selectedBranch);
        textViewSemester.setText(selectedSemester);
        textViewSubject.setText(selectedSubject);

        attendanceRecyclerView = findViewById(R.id.attendanceRecyclerView);
        attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        attendanceList = getIntent().getParcelableArrayListExtra("attendanceList");

        adapter = new AttendanceAdapter(this, attendanceList,false);
        attendanceRecyclerView.setAdapter(adapter);

            }
           // adapter.notifyDataSetChanged();


}