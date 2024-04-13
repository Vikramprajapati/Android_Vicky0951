package com.example.admin.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacherActivity extends AppCompatActivity {


    ImageView updateTeacherImage;
    EditText updateTeacherName,updateTeacherPost,updateTeacherEmail,updateTeacherMobile,updateTeacherQualification;

    Button updateTeacherBtn,deleteTeacherbtn;

    String name,post,email,number,qualification,image;

    Bitmap bitmap;

    StorageReference storageReference;
    DatabaseReference reference;
    ProgressDialog pd;
    Uri downloaduri;

    String downloadUrl,category,uniqueKey;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        name=getIntent().getStringExtra("name");
        post=getIntent().getStringExtra("post");
        email=getIntent().getStringExtra("email");
        number=getIntent().getStringExtra("mobile");
        qualification=getIntent().getStringExtra("qualification");
        image=getIntent().getStringExtra("image");

         uniqueKey=getIntent().getStringExtra("key");
         category=getIntent().getStringExtra("category");

        pd=new ProgressDialog(this);

      updateTeacherName=findViewById(R.id.UpdateTeacherName);
      updateTeacherPost=findViewById(R.id.UpdateTeacherPost);
      updateTeacherImage=findViewById(R.id.updateTeacherImage);
      updateTeacherEmail=findViewById(R.id.UpdateTeacherEmail);
      updateTeacherMobile=findViewById(R.id.UpdateTeacherMobile);
      updateTeacherQualification=findViewById(R.id.UpdateTeacherQualification);
      updateTeacherBtn=findViewById(R.id.updateTeacherbtn);
      deleteTeacherbtn=findViewById(R.id.deleteTeacherbtn);

        reference= FirebaseDatabase.getInstance().getReference().child("Teachers");
        storageReference= FirebaseStorage.getInstance().getReference();



        try {
            Picasso.get().load(image).into(updateTeacherImage);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }

        updateTeacherName.setText(name);
        updateTeacherPost.setText(post);
        updateTeacherEmail.setText(email);
        updateTeacherMobile.setText(number);
        updateTeacherQualification.setText(qualification);

        updateTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        updateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               name=updateTeacherName.getText().toString();
                post=updateTeacherPost.getText().toString();
                email=updateTeacherEmail.getText().toString();
                number=updateTeacherMobile.getText().toString();
                qualification=updateTeacherQualification.getText().toString();
                checkValidation();
            }
        });

        deleteTeacherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });


    }

    private void checkValidation(){
        if(name.isEmpty()){
            updateTeacherName.setError("Empty");
            updateTeacherName.requestFocus();
        }else if(post.isEmpty()){
            updateTeacherPost.setError("Empty");
            updateTeacherPost.requestFocus();
        }else if(email.isEmpty()){
            updateTeacherEmail.setError("Empty");
            updateTeacherEmail.requestFocus();
        }
        else if(number.isEmpty()){
            updateTeacherMobile.setError("Empty");
            updateTeacherMobile.requestFocus();
        }else if(qualification.isEmpty()){
            updateTeacherQualification.setError("Empty");
            updateTeacherQualification.requestFocus();
        }
        else {
            uploadImage();
        }

    }

    private void uploadImage() {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg= baos.toByteArray();
        final StorageReference path;
        path=storageReference.child("Teachers").child(finalimg+"jpg");
        final UploadTask uploadTask=path.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UpdateTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                }
                else {
                    pd.dismiss();
                    Toast.makeText(UpdateTeacherActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s ) {

        //get data

        HashMap hashMap=new HashMap();
        hashMap.put("name",name);
        hashMap.put("post",post);
        hashMap.put("email",email);
        hashMap.put("mobile",number);
        hashMap.put("qualification",qualification);
        hashMap.put("image",s);

        //update data

        reference.child(category).child(uniqueKey).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateTeacherActivity.this,"Teacher Updeted Successfully",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateTeacherActivity.this,Update_faculty.class);

                //add flag to back home to update faculty
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacherActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData()
    {
       reference.child(category).child(uniqueKey).removeValue()
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       Toast.makeText(UpdateTeacherActivity.this,"Teacher Deleted Successfully",Toast.LENGTH_SHORT).show();
                       Intent intent=new Intent(UpdateTeacherActivity.this,Update_faculty.class);

                       //add flag to back home to update faculty
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                      Toast.makeText(UpdateTeacherActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
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
            Uri downloaduri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),downloaduri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            updateTeacherImage.setImageBitmap(bitmap);
        }
    }

}