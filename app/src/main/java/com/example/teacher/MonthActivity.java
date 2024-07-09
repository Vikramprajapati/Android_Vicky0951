package com.example.teacher;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.common.DBHelper;
import com.example.admin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthActivity extends AppCompatActivity {
    DBHelper dbHelper;
    private String selectedYear, selectedBranch, selectedSubject;
    int month, monthYear;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_month);
        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        selectedYear = intent.getStringExtra("selectedYear");
        month = intent.getIntExtra("selectedMonth", 0);
        selectedBranch = intent.getStringExtra("selectedBranch");
        selectedSubject = intent.getStringExtra("selectedSubject");
        monthYear = intent.getIntExtra("monthYear", 0);
        tableLayout = findViewById(R.id.tablemonthLayoutAttendance);
        displayAttendanceForMonth(monthYear, month);


    }

    private void displayAttendanceForMonth(int year, int month) {
        // Fetch attendance data from DBHelper
        String monthYr = String.format("%02d/%04d", month, year);

        Cursor cursor = dbHelper.getMonthlyAttendance(selectedBranch, selectedYear, selectedSubject, monthYr);
        // Clear existing views if any
        tableLayout.removeAllViews();

        TableRow headersRow = new TableRow(this);
//        headersRow.setBackgroundColor(Color.LTGRAY);
        // Add student name column header
        TextView textViewNameHeader = new TextView(this);
        textViewNameHeader.setText("Student Name");
        textViewNameHeader.setGravity(Gravity.CENTER);
        textViewNameHeader.setPadding(8, 8, 8, 8);
        textViewNameHeader.setBackgroundResource(R.drawable.cell_border);
        headersRow.addView(textViewNameHeader);


        // Populate dates as headers
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // Month is 0-indexed
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());

        for (int i = 1; i <= daysInMonth; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            Date date = calendar.getTime();
            String dateString = dateFormat.format(date);

            TextView textViewDate = new TextView(this);
            textViewDate.setText(dateString);
            textViewDate.setGravity(Gravity.CENTER);
            textViewDate.setPadding(8, 8, 8, 8);
            textViewNameHeader.setBackgroundResource(R.drawable.cell_border);
            headersRow.addView(textViewDate);
        }

        tableLayout.addView(headersRow);

        // Add data rows (student names and attendance statuses)
        if (cursor.moveToFirst()) {
            do {
//                String rollNo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ATTENDANCE_ROLL_NO));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ATTENDANCE_NAME));

                TableRow dataRow = new TableRow(this);

                // Add student name column
                TextView textViewName = new TextView(this);
                textViewName.setText(name);
                textViewName.setGravity(Gravity.START);
                textViewName.setPadding(8, 8, 8, 8);
                textViewNameHeader.setBackgroundResource(R.drawable.cell_border);
                dataRow.addView(textViewName);

                // Add attendance status for each date in the month
                for (int i = 1; i <= daysInMonth; i++) {
                    calendar.set(Calendar.DAY_OF_MONTH, i);
                    Date date = calendar.getTime();
                    String dateString = dateFormat.format(date);

                    String status = getAttendanceStatusForDate(cursor, dateString);

                    TextView textViewStatus = new TextView(this);
                    textViewStatus.setText(status);
                    textViewStatus.setGravity(Gravity.CENTER);
                    textViewStatus.setPadding(8, 8, 8, 8);
                    textViewNameHeader.setBackgroundResource(R.drawable.cell_border);
                    dataRow.addView(textViewStatus);
                }

                tableLayout.addView(dataRow);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    private String getAttendanceStatusForDate(Cursor cursor, String dateString) {
        String status = cursor.getString(cursor.getColumnIndexOrThrow(dateString));
        // Assuming your status field stores 'P' or 'A', handle null or empty status appropriately
        if (status == null || status.isEmpty()) {
            return "";
        } else {
            return status;
        }
    }
}