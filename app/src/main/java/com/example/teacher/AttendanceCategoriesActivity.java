package com.example.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.common.DBHelper;
import com.example.admin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AttendanceCategoriesActivity extends AppCompatActivity {
    private TextView textViewYear1, textViewBranch1, textViewSemester1, textViewSubject1;
    private Button buttonFilterByMonth, buttonFilterByStudent, buttonFilterBySubject;
    private String selectedYear, selectedBranch, selectedSemester, selectedSubject;
    private DBHelper dbHelper;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_categories);
        textViewYear1 = findViewById(R.id.textViewYear1);
        textViewBranch1 = findViewById(R.id.textViewBranch1);
        textViewSemester1 = findViewById(R.id.textViewSemester1);
        textViewSubject1 = findViewById(R.id.textViewSubject1);
        buttonFilterByMonth = findViewById(R.id.buttonFilterByMonth);
        buttonFilterByStudent = findViewById(R.id.buttonFilterByStudent);
        buttonFilterBySubject = findViewById(R.id.buttonFilterBySubject);
        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        selectedYear = intent.getStringExtra("selectedYear");
        selectedBranch = intent.getStringExtra("selectedBranch");
        selectedSemester = intent.getStringExtra("selectedSemester");
        selectedSubject = intent.getStringExtra("selectedSubject");

        textViewYear1.setText(selectedYear);
        textViewBranch1.setText(selectedBranch);
        textViewSemester1.setText(selectedSemester);
        textViewSubject1.setText(selectedSubject);

        buttonFilterByMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthPickerDialog();

            }
        });

        buttonFilterByStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonFilterBySubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void showMonthPickerDialog() {
        final Calendar today = Calendar.getInstance();

        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_month_year_picker, null);

        final NumberPicker monthPicker = dialogView.findViewById(R.id.month_picker);
        final NumberPicker yearPicker = dialogView.findViewById(R.id.year_picker);

        // Set up the month picker
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setDisplayedValues(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        monthPicker.setValue(today.get(Calendar.MONTH));

        // Set up the year picker
        int currentYear = today.get(Calendar.YEAR);
        yearPicker.setMinValue(1990);
        yearPicker.setMaxValue(2030);
        yearPicker.setValue(currentYear);

        // Create and show the dialog
        new AlertDialog.Builder(this)
                .setTitle("Select Month and Year")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedMonth = monthPicker.getValue();
                        int monthYear = yearPicker.getValue();
//                        displayAttendanceForMonth(selectedYear, selectedMonth + 1); // Month is 0-indexed
                        Intent intent = new Intent(AttendanceCategoriesActivity.this, MonthActivity.class);
                        intent.putExtra("selectedYear", selectedYear);
                        intent.putExtra("selectedMonth", selectedMonth);
                        intent.putExtra("selectedBranch",selectedBranch);
                        intent.putExtra("selectedSubject",selectedSubject);
                        intent.putExtra(("monthYear"),monthYear);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


}