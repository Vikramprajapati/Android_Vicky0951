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
            Intent intent=new Intent(MainActivity.this, Upload_notice.class);
            startActivity(intent);
        });


    }

}