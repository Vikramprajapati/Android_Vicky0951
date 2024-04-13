package com.example.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admin.faculty.Update_faculty;

public class MainActivity extends AppCompatActivity  {

    CardView notice,gallery,faculty,Ebook,registration;
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

        notice.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, Upload_notice.class);
            startActivity(intent);
        });

        registration.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, AddAccount.class);
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
                Intent intent=new Intent(MainActivity.this, Update_faculty.class);
                startActivity(intent);
            }
        });


    }

}