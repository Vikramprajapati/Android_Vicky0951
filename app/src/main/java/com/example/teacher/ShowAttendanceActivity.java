package com.example.teacher;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import com.example.admin.DBHelper;
import com.example.admin.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowAttendanceActivity extends AppCompatActivity {
    private RecyclerView attendanceRecyclerView;
    private AttendanceAdapter adapter;
    private ArrayList<Attendance> attendanceList;
    private TextView textViewYear;
    private TextView textViewBranch;
    private Button buttonSelectDate,otherFilters;
    private TextView textViewSelectedDate2;
    private TextView textViewSemester;
    private TextView textViewSubject;
    private DBHelper dbHelper;
    private String selectedYear,selectedBranch,selectedSemester,selectedSubject,selectedDate2;

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
        textViewSelectedDate2=findViewById(R.id.textViewSelectedDate2);
        buttonSelectDate = findViewById(R.id.buttonSelectDate2);
        otherFilters=findViewById(R.id.otherfilters);

        // Retrieve data from Intent extras
        Intent intent = getIntent();
         selectedYear = intent.getStringExtra("selectedYear");
         selectedBranch = intent.getStringExtra("selectedBranch");
         selectedSemester = intent.getStringExtra("selectedSemester");
         selectedSubject = intent.getStringExtra("selectedSubject");
         selectedDate2=intent.getStringExtra("selectedDate1");

        // Set data to TextViews
        textViewYear.setText(selectedYear);
        textViewBranch.setText(selectedBranch);
        textViewSemester.setText(selectedSemester);
        textViewSubject.setText(selectedSubject);
        textViewSelectedDate2.setText(selectedDate2);

        attendanceRecyclerView = findViewById(R.id.attendanceRecyclerView);
        dbHelper = new DBHelper(this);
        attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        attendanceList = getIntent().getParcelableArrayListExtra("attendanceList");

        adapter = new AttendanceAdapter(this, attendanceList,false);
        attendanceRecyclerView.setAdapter(adapter);
        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        otherFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowAttendanceActivity.this, AttendanceCategoriesActivity.class);
                intent.putExtra("selectedYear",selectedYear);
                intent.putExtra("selectedBranch", selectedBranch);
                intent.putExtra("selectedSemester",selectedSemester);
                intent.putExtra("selectedSubject", selectedSubject);
                startActivity(intent);
            }
        });
            }
           // adapter.notifyDataSetChanged();
           private void showDatePickerDialog() {
               DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                       new DatePickerDialog.OnDateSetListener() {
                           @Override
                           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                               textViewSelectedDate2.setText("Selected Date: " +dayOfMonth + "/" + (month + 1) + "/" + year);
                               loadAttendance1(dayOfMonth + "/" + (month + 1) + "/" + year);
                           }
                       }, Calendar.getInstance().get(Calendar.YEAR),
                       Calendar.getInstance().get(Calendar.MONTH),
                       Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
               datePickerDialog.show();
           }
    private void loadAttendance1(String date) {

        Cursor cursor = dbHelper.getFilteredAttendance(selectedBranch, selectedYear, selectedSubject, date);
        attendanceList.clear();
        if (cursor.moveToFirst()) {
            do {
                String rollNo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ATTENDANCE_ROLL_NO));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ATTENDANCE_NAME));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ATTENDANCE_STATUS));
                attendanceList.add(new Attendance(rollNo, name, status));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

}