package com.example.admin.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.NoticeData;
import com.example.admin.R;
import com.example.admin.Upload_notice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class addFaculty extends AppCompatActivity {

    ImageView addTeacherimage;
    Bitmap bitmap=null;
    EditText addTeacherEmail,addTeacherPost,addTeacherName,addTeacherNumber,addTeacherExp;
    Spinner addteacherDepartment,addTeacherqualification;
    String[] item={"Select Department","CSE","EE","CE","ME","Admin","Account"};
    String[] item2={"Select Qualification","M.Tech","B.Tech","PHD","ME","B.Sc","M.Sc"};
    String name,post,email,downloadUrl="",number,exp,qualification;
    Button addTeacher;
    ProgressDialog pd;
    StorageReference storageReference;
    DatabaseReference reference,dbRef;
    String department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        addTeacherimage=findViewById(R.id.addTeacherImage);
        addTeacherName=findViewById(R.id.addTeacherName);
        addTeacherEmail=findViewById(R.id.addTeacherEmail);
        addTeacherPost=findViewById(R.id.addTeacherPost);
        addteacherDepartment=findViewById(R.id.addTeacherDepartment);
        addTeacherNumber=findViewById(R.id.addTeacherMobile);

        addTeacherqualification=findViewById(R.id.addTeacherQualification);

        pd=new ProgressDialog(this);

        reference= FirebaseDatabase.getInstance().getReference().child("Teachers");
        storageReference= FirebaseStorage.getInstance().getReference();

        addTeacher=findViewById(R.id.addTeacherbtn);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(addFaculty.this, android.R.layout.simple_spinner_item,item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        addteacherDepartment.setAdapter(adapter);


        //set department on spinner

        addteacherDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                department=addteacherDepartment.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> a=new ArrayAdapter<>(addFaculty.this, android.R.layout.simple_spinner_item,item2);
        a.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        addTeacherqualification.setAdapter(a);



        //set qualification on spinner
        addTeacherqualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String qualification=addTeacherqualification.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


// add teacher
        addTeacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }
 // Check filed is empty or not
    private void checkValidation() {
        name=addTeacherName.getText().toString();
        post=addTeacherPost.getText().toString();
        email=addTeacherEmail.getText().toString();
        number=addTeacherNumber.getText().toString();
        exp=addTeacherExp.getText().toString();

        if(name.isEmpty()){
            addTeacherName.setError("Empty");
            addTeacherName.requestFocus();
        }else if(post.isEmpty()){
            addTeacherPost.setError("Empty");
            addTeacherPost.requestFocus();
        }else if(email.isEmpty()){
            addTeacherEmail.setError("Empty");
            addTeacherEmail.requestFocus();
        }else if(number.isEmpty()){
            addTeacherNumber.setError("Empty");
            addTeacherNumber.requestFocus();
        }else if(exp.isEmpty()){
            addTeacherExp.setError("Empty");
            addTeacherExp.requestFocus();
        }else if(department.equals("Select Department")){
            Toast.makeText(this, "Please Provide Teacher Department", Toast.LENGTH_SHORT).show();
        }else if(item2.equals("Select Qualification")){
            Toast.makeText(this, "Please Provide Teacher Qualification", Toast.LENGTH_SHORT).show();
        } else if (bitmap==null) {
          insertData();
        }
        else {
            pd.setTitle("Please wait...");
            pd.setMessage("Uploading...");
            pd.show();
            uploadImage();
        }

    }

    private void insertData() {
        reference=reference.child(department);
        final String uniqueKey=reference.push().getKey();

        TeacherData teacherData=new TeacherData(name,post,email,number,exp,uniqueKey,downloadUrl,qualification);

        reference.child(uniqueKey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(addFaculty.this,"Teacher Added",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(addFaculty.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg= baos.toByteArray();
        final StorageReference path;
        path=storageReference.child("Teachers").child(finalimg+"jpg");
        final UploadTask uploadTask=path.putBytes(finalimg);
        uploadTask.addOnCompleteListener(addFaculty.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }
                else {
                    pd.dismiss();
                    Toast.makeText(addFaculty.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
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
