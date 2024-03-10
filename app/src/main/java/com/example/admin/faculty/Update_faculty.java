package com.example.admin.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Update_faculty extends AppCompatActivity {


    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

         fab = findViewById(R.id.fab);

         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Update_faculty.this,addFaculty.class));
             }
         });

    }
}