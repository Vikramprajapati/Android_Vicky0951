package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity  {

    CardView notice,gallery,faculty,Ebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       notice=findViewById(R.id.addNotice);
       gallery=findViewById(R.id.addGalleryImage);
       faculty=findViewById(R.id.faculty);
       Ebook=findViewById(R.id.addEbook);

        notice.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, Upload_Notice.class);
            startActivity(intent);
        });

        faculty.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,faculty.class);
            startActivity(intent);
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Upload_Notice.class);
                startActivity(intent);
            }
        });
        Ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Upload_Ebook.class);
                startActivity(intent);
            }
        });

    }

}