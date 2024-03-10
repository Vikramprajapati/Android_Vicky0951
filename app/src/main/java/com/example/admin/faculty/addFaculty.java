package com.example.admin.faculty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.admin.R;

import java.io.IOException;
import java.util.ArrayList;

public class addFaculty extends AppCompatActivity {

    ImageView addTeacherimage;
    Bitmap bitmap=null;
    EditText addTeacherEmail,addTeacherPost,addTeacherName;
    Spinner addteacherDepartment,addTeacherqualification;
    String[] department={"Select Department","CSE","EE","CE","ME","Admin","Account"};
    String[] qualification={"Select Qualification","M.Tech","B.Tech","PHD","ME","B.Sc","M.Sc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        addTeacherimage=findViewById(R.id.addTeacherImage);
        addTeacherName=findViewById(R.id.addTeacherName);
        addTeacherEmail=findViewById(R.id.addTeacherEmail);
        addTeacherPost=findViewById(R.id.addTeacherPost);
        addteacherDepartment=findViewById(R.id.addTeacherDepartment);
        addTeacherqualification=findViewById(R.id.addTeacherQualification);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(addFaculty.this, android.R.layout.simple_spinner_item,department);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        addteacherDepartment.setAdapter(adapter);


        addteacherDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item=adapterView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> a=new ArrayAdapter<>(addFaculty.this, android.R.layout.simple_spinner_item,qualification);
        a.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        addTeacherqualification.setAdapter(a);


        addTeacherqualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item=adapterView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });








        addTeacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void openGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK ){
            Uri uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            addTeacherimage.setImageBitmap(bitmap);
        }
    }
}
