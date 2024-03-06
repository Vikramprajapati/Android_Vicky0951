package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity  {

    CardView notice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       notice=findViewById(R.id.addNotice);
        notice.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, Upload_Notice.class);
            startActivity(intent);
        });


    }

}