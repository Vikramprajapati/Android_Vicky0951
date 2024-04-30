

 package com.example.admin;

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
        import android.widget.Spinner;
        import android.widget.Toast;

        import com.example.admin.R;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;
        import com.squareup.picasso.Picasso;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.util.HashMap;

public class Update_student extends AppCompatActivity {


    ImageView updateStudentImage;
    EditText updatefullName, updatefatherName, updateemail, updatemobile, updatepassword, updateconPassword, updateparentMobile, updateDOB, updateGender, updateaddress;

    Button updateStudentBtn, deleteStudentbtn;
    Spinner update_spin_branch, update_spin_sem, update_spin_year;
    String fullName, fatherName, email, mobile, password, conPassword, parentMobile, DOB, address, gender, image, year, branch, sem;

    Bitmap bitmap;

    StorageReference storageReference;
    DatabaseReference reference;
    ProgressDialog pd;
    Uri downloaduri;

    String downloadUrl, category, uniqueKey;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        fullName = getIntent().getStringExtra("fullName");
        fatherName = getIntent().getStringExtra("fatherName");
        email = getIntent().getStringExtra("email");
        mobile = getIntent().getStringExtra("mobile");
        parentMobile = getIntent().getStringExtra("parentMobile");
        image = getIntent().getStringExtra("image");
        address = getIntent().getStringExtra("address");
        DOB = getIntent().getStringExtra("DOB");
        gender = getIntent().getStringExtra("gender");
        parentMobile = getIntent().getStringExtra("parentMobile");
        conPassword = getIntent().getStringExtra("conPassword");
        password = getIntent().getStringExtra("password");


        uniqueKey = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("category");

        updateStudentImage = findViewById(R.id.updateStudentImage);
        updatefatherName = findViewById(R.id.update_father_name);
        updateemail = findViewById(R.id.update_email);
        updateDOB = findViewById(R.id.update_dob);
        updateaddress = findViewById(R.id.update_address);
        updatefullName = findViewById(R.id.update_full_name);
        updatemobile = findViewById(R.id.update_mobile);
        updateparentMobile = findViewById(R.id.update_parent_mobile);
        updatepassword = findViewById(R.id.updater_password);
        updateGender = findViewById(R.id.update_gender);
        updateconPassword = findViewById(R.id.update_conpassword);
        update_spin_year = findViewById(R.id.update_year);
        update_spin_branch = findViewById(R.id.update_branch);
        update_spin_sem = findViewById(R.id.update_sem);
        updateStudentBtn = findViewById(R.id.update_student);
        deleteStudentbtn = findViewById(R.id.deleteBtn);
        pd = new ProgressDialog(this);


        storageReference = FirebaseStorage.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference().child("Students");


        try {
            Picasso.get().load(image).into(updateStudentImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        updatefullName.setText(fullName);
        updatefatherName.setText(fatherName);
        updateemail.setText(email);
        updatemobile.setText(mobile);
        updateparentMobile.setText(parentMobile);
        updateGender.setText(gender);
        updatepassword.setText(password);
        updateconPassword.setText(conPassword);
        updateDOB.setText(DOB);


      /*  updateTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        updateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = updateTeacherName.getText().toString();
                post = updateTeacherPost.getText().toString();
                email = updateTeacherEmail.getText().toString();
                number = updateTeacherMobile.getText().toString();
                qualification = updateTeacherQualification.getText().toString();
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

    private void checkValidation() {
        if (name.isEmpty()) {
            updateTeacherName.setError("Empty");
            updateTeacherName.requestFocus();
        } else if (post.isEmpty()) {
            updateTeacherPost.setError("Empty");
            updateTeacherPost.requestFocus();
        } else if (email.isEmpty()) {
            updateTeacherEmail.setError("Empty");
            updateTeacherEmail.requestFocus();
        } else if (number.isEmpty()) {
            updateTeacherMobile.setError("Empty");
            updateTeacherMobile.requestFocus();
        } else if (qualification.isEmpty()) {
            updateTeacherQualification.setError("Empty");
            updateTeacherQualification.requestFocus();
        } else {
            uploadImage();
        }

    }

    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference path;
        path = storageReference.child("Teachers").child(finalimg + "jpg");
        final UploadTask uploadTask = path.putBytes(finalimg);
        uploadTask.addOnCompleteListener(com.example.admin.faculty.UpdateTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(com.example.admin.faculty.UpdateTeacherActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {

        //get data

        HashMap hashMap = new HashMap();
        hashMap.put("name", name);
        hashMap.put("post", post);
        hashMap.put("email", email);
        hashMap.put("mobile", number);
        hashMap.put("qualification", qualification);
        hashMap.put("image", s);

        //update data

        reference.child(category).child(uniqueKey).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(com.example.admin.faculty.UpdateTeacherActivity.this, "Teacher Updeted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(com.example.admin.faculty.UpdateTeacherActivity.this, Update_faculty.class);

                //add flag to back home to update faculty
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.admin.faculty.UpdateTeacherActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData() {
        reference.child(category).child(uniqueKey).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(com.example.admin.faculty.UpdateTeacherActivity.this, "Teacher Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(com.example.admin.faculty.UpdateTeacherActivity.this, Update_faculty.class);

                        //add flag to back home to update faculty
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.example.admin.faculty.UpdateTeacherActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri downloaduri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), downloaduri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            updateStudentImage.setImageBitmap(bitmap);
        }
    }*/

}}