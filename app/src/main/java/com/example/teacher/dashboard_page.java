package com.example.teacher;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admin.R;

public class dashboard_page extends AppCompatActivity {
 CardView attendance1,marks1,table1,assignment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard_page);
        attendance1=findViewById(R.id.attendance);
        marks1=findViewById(R.id.marks);
        table1=findViewById(R.id.table);
        assignment1=findViewById(R.id.assignment);

        attendance1.setOnClickListener(v -> {
            Intent intent=new Intent(dashboard_page.this, ViewAttendance.class);
            startActivity(intent);
        });
        marks1.setOnClickListener(v -> {
            Intent intent=new Intent(dashboard_page.this, upload_marks.class);
            startActivity(intent);
        });
        table1.setOnClickListener(v -> {
            Intent intent=new Intent(dashboard_page.this, upload_time_table.class);
            startActivity(intent);
        });
        assignment1.setOnClickListener(v -> {
            Intent intent=new Intent(dashboard_page.this, upload_assignment.class);
            startActivity(intent);
        });


    }
}