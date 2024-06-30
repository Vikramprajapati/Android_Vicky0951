package com.example.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.admin.R;

public class AttendanceCategoriesActivity extends AppCompatActivity {
    private TextView textViewYear1, textViewBranch1, textViewSemester1, textViewSubject1;
    private Button buttonFilterByMonth, buttonFilterByStudent, buttonFilterBySubject;
    private String selectedYear, selectedBranch, selectedSemester, selectedSubject;
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
        Intent intent = getIntent();
        selectedYear = intent.getStringExtra("selectedYear");
        selectedBranch = intent.getStringExtra("selectedBranch");
        selectedSemester = intent.getStringExtra("selectedSemester");
        selectedSubject = intent.getStringExtra("selectedSubject");

        textViewYear1.setText( selectedYear);
        textViewBranch1.setText(selectedBranch);
        textViewSemester1.setText(selectedSemester);
        textViewSubject1.setText(selectedSubject);
        buttonFilterByMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}