package com.example.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admin.faculty.Faculties;
import com.example.admin.notice.DeleteNotice;
import com.example.admin.notice.Upload_notice;

public class MainActivity extends AppCompatActivity  {

    CardView notice,gallery,faculty,Ebook,registration,details_student,delete;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       notice=findViewById(R.id.addNotice);
       gallery=findViewById(R.id.addGalleryImage);
       faculty=findViewById(R.id.faculty);
       Ebook=findViewById(R.id.addEbook);
       registration=findViewById(R.id.registration);
       details_student = findViewById(R.id.upload_student_details);
       delete=findViewById(R.id.deleteNotice);


       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(MainActivity.this, DeleteNotice.class);
               startActivity(intent);
           }
       });

        notice.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, Upload_notice.class);
            startActivity(intent);
        });

        registration.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, student_registration.class);
            startActivity(intent);
        });



        gallery.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, Upload_image.class);
            startActivity(intent);
        });

        Ebook.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, Upload_pdf.class);
            startActivity(intent);
        });

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Faculties.class);
                startActivity(intent);
            }
        });
        details_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, upload_student_details.class);
                startActivity(intent);
            }
        });


    }

}