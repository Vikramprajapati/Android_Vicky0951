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
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.AddStudent;
import com.example.admin.R;
import com.example.admin.StudentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addFaculty extends AppCompatActivity {

    ImageView addTeacherimage;
    Bitmap bitmap=null;
    EditText addTeacherEmail,addTeacherPost,addTeacherName,addTeacherNumber,addTeacherQualification,addTeacherPassword,addTecherConPassword;
    Spinner addteacherDepartment;
    Uri downloaduri;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;
    ArrayList<String> departmentList = new ArrayList<>();
    String department;

    String name,post,email,downloadUrl="",number,qualification,password,conPassword;
    Button addTeacher;

    ProgressDialog pd;
    StorageReference storageReference;
    DatabaseReference reference, dbRef;

    static final String TAG = "AddStudent";


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
        addTeacherPassword=findViewById(R.id.addTeacherPassword);
        addTecherConPassword=findViewById(R.id.addTeacherConPassword);
        addTeacherQualification=findViewById(R.id.addTeacherQualification);


        pd=new ProgressDialog(this);
        storageReference= FirebaseStorage.getInstance().getReference();

        addTeacher=findViewById(R.id.addTeacherbtn);

        departmentList.add("Select Department");
        departmentList.add("Admin");
        departmentList.add("Account");
        departmentList.add("CSE");
        departmentList.add("EE");
        departmentList.add("CE");
        departmentList.add("ME");


        // setting the values in spinner 1
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentList);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        addteacherDepartment.setAdapter(adapter1);



        //select image
        addTeacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTeacherimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });


                addTeacher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //obtain the enter data

                        name = addTeacherName.getText().toString();
                        post = addTeacherPost.getText().toString();
                        email = addTeacherEmail.getText().toString();
                        number = addTeacherNumber.getText().toString();
                        qualification = addTeacherQualification.getText().toString();
                        password = addTeacherPassword.getText().toString();
                        conPassword = addTecherConPassword.getText().toString();


                        //validate mobile number
                        String mobileRegex = "[6-9][0-9]{9}";    //1st no can be 6,7,8 nd rest 9 nos. can be 0 to 9
                        Matcher mobilematcher;
                        Pattern mobilePattern = Pattern.compile(mobileRegex);
                        mobilematcher = mobilePattern.matcher(number);


                        //check empty field

                        if (name.isEmpty()) {
                            addTeacherName.setError("Empty");
                            addTeacherName.requestFocus();
                        } else if (post.isEmpty()) {
                            addTeacherPost.setError("Empty");
                            addTeacherPost.requestFocus();
                        } else if (email.isEmpty()) {
                            addTeacherEmail.setError("Empty");
                            addTeacherEmail.requestFocus();
                        } else if (number.isEmpty()) {
                            addTeacherNumber.setError("Empty");
                            addTeacherNumber.requestFocus();
                        }  else if (addteacherDepartment.getSelectedItem()== null ) {
                            Toast.makeText(addFaculty.this,"all three fields are required for selection ",Toast.LENGTH_LONG).show();

                        } else if (qualification.isEmpty()) {
                            addTeacherQualification.setError("Empty");
                            addTeacherQualification.requestFocus();
                        } else if (downloaduri == null) {
                            Toast.makeText(addFaculty.this, "Please Select Photo", Toast.LENGTH_SHORT).show();

                        } else if (TextUtils.isEmpty(password)) {
                            Toast.makeText(addFaculty.this, "Please Enter Password", Toast.LENGTH_LONG).show();
                            addTeacherPassword.setError("Empty");
                            addTeacherPassword.requestFocus();
                        } else if (TextUtils.isEmpty(conPassword)) {
                            Toast.makeText(addFaculty.this, "Please Enter Password", Toast.LENGTH_LONG).show();
                            addTecherConPassword.setError("Empty");
                            addTecherConPassword.requestFocus();
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(addFaculty.this, "Please re-enter Your Email", Toast.LENGTH_LONG).show();
                            addTeacherEmail.setError("Invalid email");
                            addTeacherEmail.requestFocus();
                        } else if (number.length() != 10) {
                            Toast.makeText(addFaculty.this, "Please re-enter Your Mobile Number", Toast.LENGTH_LONG).show();
                            addTeacherNumber.setError("Invalid Number");
                            addTeacherNumber.requestFocus();
                        } else if (!mobilematcher.find()) {
                            Toast.makeText(addFaculty.this, "Please re-enter Your Mobile Number", Toast.LENGTH_LONG).show();
                            addTeacherNumber.setError("Mobile Number is not valid.");
                            addTeacherNumber.requestFocus();

                        } else if (password.length() < 6) {
                            Toast.makeText(addFaculty.this, "Password Should be at least 6 digits", Toast.LENGTH_LONG).show();
                            addTeacherPassword.setError("Password too weak");
                            addTeacherPassword.requestFocus();
                        } else if (!password.equals(conPassword)) {
                            Toast.makeText(addFaculty.this, "Enter same password", Toast.LENGTH_LONG).show();
                            addTecherConPassword.setError("Password Confirmation is required");
                            addTecherConPassword.requestFocus();
                            //clear entered password


                            addTecherConPassword.clearComposingText();
                        } else {
                            pd.setTitle("Please wait...");
                            pd.setMessage("Uploading...");
                            pd.show();
                            uploadImage();


                        }

                    }
                });

            }


        });
    }

    private void openGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK ){
            downloaduri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),downloaduri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            addTeacherimage.setImageBitmap(bitmap);
        }
    }

    private void uploadImage() {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg= baos.toByteArray();
        final StorageReference path;
        path=storageReference.child("Registered Teacher").child(finalimg+"jpg");
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
                                    registerTeacher( name,  post,  email,  number, downloadUrl,  qualification,  password);
                                }
                            });
                        }
                    });
                }
                else {

                    Toast.makeText(addFaculty.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

    private void registerTeacher(String name, String post, String email,String  number, String downloadUrl, String qualification,  String password) {

        FirebaseAuth auth1=FirebaseAuth.getInstance();
        auth1.createUserWithEmailAndPassword(email,password).addOnCompleteListener(addFaculty.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {



                if(task.isSuccessful()){

                    FirebaseUser firebaseUser=auth1.getCurrentUser();



                    //update display name of user
                    UserProfileChangeRequest profileChangeReques=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    firebaseUser.updateProfile(profileChangeReques);

                    //store data into firebase
                    TeacherData teacherData=new TeacherData( name,  post,  email,  number, downloadUrl,  qualification,  password);

                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Registered Teachers");
                    databaseReference.child(firebaseUser.getUid()).setValue(teacherData).addOnCompleteListener(new OnCompleteListener<Void>() {


                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            uploadImage();
                            if(task.isSuccessful()){

                                pd.dismiss();
                                Toast.makeText(addFaculty.this,"Student registered successfully. ",Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(addFaculty.this,"Student registered failed. Please try again.",Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }

                        }
                    });





                }// ckeck weak password and email already exist in firebase
                else {
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e){
                        addTeacherPassword.setError("Your password is too weak.Kindly use a mix of alphabets, numbers and special character");
                        addTeacherPassword.requestFocus();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e){
                        addTeacherEmail.setError("Your Email is invalid or already in use. Kindly re-enter");
                        addTeacherEmail.requestFocus();
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        addTeacherEmail.setError("User is already registered with this email. Use another email.");
                        addTeacherEmail.requestFocus();
                    }catch (Exception e){

                        Log.e(TAG, e.getMessage());
                        Toast.makeText(addFaculty.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                    pd.dismiss();
                }


            }
        });

    }


}








