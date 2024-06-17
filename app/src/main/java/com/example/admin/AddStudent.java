package com.example.admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.EmployeeInfo;
import com.example.admin.faculty.TeacherData;
import com.example.admin.faculty.Update_faculty;
import com.example.admin.faculty.addFaculty;
import com.example.teacher.upload_assignment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.units.qual.A;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddStudent extends AppCompatActivity {

    ImageView addStudentimage;
    Uri downloaduri;
    private FirebaseAuth auth;
    Spinner spin_year, spin_branch, spin_sem;
    String year, branch, sem;

DatePickerDialog picker;

    Bitmap bitmap = null;

    EditText AddfullName, AddfatherName, Addemail, Addmobile, Addpassword, AddconPassword, AddparentMobile, AddDOB, AddGender, Addaddress;

    String fullName, fatherName, email, mobile, password, conPassword, parentMobile, DOB, address, downloadUrl = "", gender;

    ProgressBar progressBar;
    StorageReference storageReference;

    FirebaseUser firebaseUser;
    ProgressDialog pd;


    Button addStudent;
    ArrayList<String> yearList = new ArrayList<>();
    ArrayList<String> branchList = new ArrayList<>();
    ArrayList<String> semList = new ArrayList<>();


    DatabaseReference reference, dbRef;

    static final String TAG = "AddStudent";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        addStudentimage = findViewById(R.id.addStudentImage);
        AddfatherName = findViewById(R.id.editText_register_father_name);
        Addemail = findViewById(R.id.editText_register_email);
        AddDOB = findViewById(R.id.editText_register_dob);
        Addaddress = findViewById(R.id.editText_register_address);
        AddfullName = findViewById(R.id.editText_register_full_name);
        Addmobile = findViewById(R.id.editText_register_mobile);
        AddparentMobile = findViewById(R.id.editText_register_parent_mobile);
        Addpassword = findViewById(R.id.editText_register_password);
        AddGender=findViewById(R.id.editText_register_gender);
        AddconPassword = findViewById(R.id.editText_register_conpassword);
        spin_year = findViewById(R.id.year_category2);
        spin_branch = findViewById(R.id.branch_category2);
        spin_sem = findViewById(R.id.sem_category2);
        addStudent = findViewById(R.id.addStudent);
        storageReference = FirebaseStorage.getInstance().getReference();

        progressBar=findViewById(R.id.progressbar);
        pd = new ProgressDialog(this);

        yearList.add("Select Year");
        yearList.add("4 year");
        yearList.add("3 year");
        yearList.add("2 year");
        yearList.add("1 year");
        branchList.add("Select Branch");
        semList.add("Select semester");
        // setting the values in spinner 1
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearList);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin_year.setAdapter(adapter1);
// creating action when spinner values will be chosen
        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                year=spin_year.getSelectedItem().toString();
                if (adapterView.getItemAtPosition(position).equals("1 year")) {
                    branchList.clear();
                    semList.clear();
                    branchList.add("Select Branch");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");  // adding values of 2spinner in array
                    semList.add("Select SEM");
                    semList.add("1 Sem");
                    semList.add("2 Sem");
                    fillSpinner2();
                } else if(adapterView.getItemAtPosition(position).equals("3 year")) {
                    branchList.clear();
                    semList.clear();
                    semList.add("Select SEM");
                    semList.add("5 Sem");
                    semList.add("6 Sem");
                    branchList.add("Select Branch");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");
                    fillSpinner2();
                }
                else if(adapterView.getItemAtPosition(position).equals("2 year")) {
                    branchList.clear();
                    semList.clear();
                    semList.add("Select SEM");
                    semList.add("3 Sem");   // adding values of 2spinner in array
                    semList.add("4 Sem");
                    branchList.add("Select Branch");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");
                    fillSpinner2();
                }
                else if(adapterView.getItemAtPosition(position).equals("4 year")) {
                    branchList.clear();
                    semList.clear();
                    semList.add("Select SEM");
                    semList.add("7 Sem");
                    semList.add("8 Sem");
                    branchList.add("Select Branch");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");
                    fillSpinner2();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        spin_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView1, View view1, int position1, long id1) {
                branch = adapterView1.getItemAtPosition(position1).toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView1) {

            }
        });
        spin_sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView2, View view2, int position2, long id2) {
                sem = adapterView2.getItemAtPosition(position2).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //select image
        addStudentimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addStudentimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });


                //set datepicker on edittext



        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //obtain the enter data

                fullName=AddfullName.getText().toString();
                fatherName=AddfatherName.getText().toString();
                email=Addemail.getText().toString();
                mobile=Addmobile.getText().toString();
                parentMobile=AddparentMobile.getText().toString();
                password=Addpassword.getText().toString();
                conPassword=AddconPassword.getText().toString();
                address=Addaddress.getText().toString();
                DOB=AddDOB.getText().toString();
                gender=AddGender.getText().toString();

                //validate mobile number
                String mobileRegex="[6-9][0-9]{9}";    //1st no can be 6,7,8 nd rest 9 nos. can be 0 to 9
                Matcher mobilematcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobilematcher=mobilePattern.matcher(mobile);


                //check empty field

                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(AddStudent.this,"Please Enter Your Full Name",Toast.LENGTH_LONG).show();
                    AddfullName.setError("Empty");
                    AddfullName.requestFocus();
                }
                else  if(TextUtils.isEmpty(fatherName)){
                    Toast.makeText(AddStudent.this,"Please Enter Your Father Name",Toast.LENGTH_LONG).show();
                    AddfatherName.setError("Empty");
                    AddfatherName.requestFocus();
                }else  if(TextUtils.isEmpty(gender)){
                    Toast.makeText(AddStudent.this,"Please Enter Your Gender",Toast.LENGTH_LONG).show();
                    AddGender.setError("Empty");
                    AddGender.requestFocus();
                }
                else  if(TextUtils.isEmpty(email)){
                    Toast.makeText(AddStudent.this,"Please Enter Your Email",Toast.LENGTH_LONG).show();
                    Addemail.setError("Empty");
                    Addemail.requestFocus();
                }
                else  if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(AddStudent.this,"Please Enter Your Mobile Number",Toast.LENGTH_LONG).show();
                    Addmobile.setError("Empty");
                    Addmobile.requestFocus();
                }
                else  if(TextUtils.isEmpty(parentMobile)){
                    Toast.makeText(AddStudent.this,"Please Enter Your Parent Number",Toast.LENGTH_LONG).show();
                    AddparentMobile.setError("Empty");
                    AddparentMobile.requestFocus();
                }
                else  if(TextUtils.isEmpty(password)){
                    Toast.makeText(AddStudent.this,"Please Enter Password",Toast.LENGTH_LONG).show();
                    Addpassword.setError("Empty");
                    Addpassword.requestFocus();
                }
                else  if(TextUtils.isEmpty(conPassword)){
                    Toast.makeText(AddStudent.this,"Please Enter Password",Toast.LENGTH_LONG).show();
                    AddconPassword.setError("Empty");
                    AddconPassword.requestFocus();
                }
                else  if(TextUtils.isEmpty(DOB)){
                    Toast.makeText(AddStudent.this,"Please Enter DOB",Toast.LENGTH_LONG).show();
                    AddDOB.setError("Empty");
                    AddDOB.requestFocus();
                }
                else  if(TextUtils.isEmpty(address)){
                    Toast.makeText(AddStudent.this,"Please Enter Address",Toast.LENGTH_LONG).show();
                    Addaddress.setError("Empty");
                    Addaddress.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(AddStudent.this,"Please re-enter Your Email",Toast.LENGTH_LONG).show();
                    Addemail.setError("Invalid email");
                    Addemail.requestFocus();
                } else if (mobile.length() !=10) {
                    Toast.makeText(AddStudent.this,"Please re-enter Your Mobile Number",Toast.LENGTH_LONG).show();
                    Addmobile.setError("Invalid Number");
                    Addmobile.requestFocus();
                } else if (!mobilematcher.find()) {
                    Toast.makeText(AddStudent.this,"Please re-enter Your Mobile Number",Toast.LENGTH_LONG).show();
                    Addmobile.setError("Mobile Number is not valid.");
                    Addmobile.requestFocus();

                } else if (parentMobile.length() !=10) {
                    Toast.makeText(AddStudent.this,"Please re-enter  Mobile Number",Toast.LENGTH_LONG).show();
                    AddparentMobile.setError("Invalid Number");
                    AddparentMobile.requestFocus();
                }
                else if (password.length()<6) {
                    Toast.makeText(AddStudent.this,"Password Should be at least 6 digits",Toast.LENGTH_LONG).show();
                    Addpassword.setError("Password too weak");
                    Addpassword.requestFocus();
                }
                else if (!password.equals(conPassword)) {
                    Toast.makeText(AddStudent.this,"Enter same password",Toast.LENGTH_LONG).show();
                    AddconPassword.setError("Password Confirmation is required");
                    AddconPassword.requestFocus();
                    //clear entered password

                    Addpassword.clearComposingText();
                    AddconPassword.clearComposingText();
                }
                else if (spin_year.getSelectedItem()== null || spin_branch.getSelectedItem()== null ||spin_sem.getSelectedItem()== null) {
                    Toast.makeText(AddStudent.this,"all three fields are required for selection ",Toast.LENGTH_LONG).show();

                }else if (downloaduri==null) {
                    Toast.makeText(AddStudent.this, "Please Select Photo", Toast.LENGTH_LONG).show();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK ){
            downloaduri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),downloaduri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            addStudentimage.setImageBitmap(bitmap);
        }
    }
    public void fillSpinner2(){

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchList);
        adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin_branch.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semList);
        adapter3.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin_sem.setAdapter(adapter3);
    }
    private void uploadImage() {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg= baos.toByteArray();
        final StorageReference path;
        path=storageReference.child("Registered Student").child(finalimg+"jpg");
        final UploadTask uploadTask=path.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddStudent.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    registerUser(fullName,  fatherName,  email,  mobile,  password,  parentMobile,  DOB,  sem,branch,address, downloadUrl,  gender);
                                }
                            });
                        }
                    });
                }
                else {

                    Toast.makeText(AddStudent.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }
    private void registerUser(String fullName, String fatherName, String email, String mobile, String password, String parentMobile, String dob, String sem, String branch, String address, String downloadUrl, String gender) {

        FirebaseAuth auth1=FirebaseAuth.getInstance();
        auth1.createUserWithEmailAndPassword(email,password).addOnCompleteListener(AddStudent.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {



                if(task.isSuccessful()){

                    FirebaseUser firebaseUser=auth1.getCurrentUser();



                    //update display name of user
                    UserProfileChangeRequest profileChangeReques=new UserProfileChangeRequest.Builder().setDisplayName(fullName).build();
                    firebaseUser.updateProfile(profileChangeReques);

                    //store data into firebase
                    StudentData studentData=new StudentData( fatherName,  email,  mobile,  password,  parentMobile,  DOB,  sem,branch,address, downloadUrl,gender);

                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Registered Student");
                    databaseReference.child(firebaseUser.getUid()).setValue(studentData).addOnCompleteListener(new OnCompleteListener<Void>() {


                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            uploadImage();
                            if(task.isSuccessful()){
                                //send verification mail
                                firebaseUser.sendEmailVerification();
                                pd.dismiss();
                                Toast.makeText(AddStudent.this,"Student registered successfully. Please Verify your Email.",Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(AddStudent.this,"Student registered failed. Please try again.",Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }

                        }
                    });





                }// ckeck weak password and email already exist in firebase
                else {
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e){
                        Addpassword.setError("Your password is too weak.Kindly use a mix of alphabets, numbers and special character");
                        Addpassword.requestFocus();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e){
                        Addemail.setError("Your Email is invalid or already in use. Kindly re-enter");
                        Addemail.requestFocus();
                    }
                    catch (FirebaseAuthUserCollisionException  e){
                        Addemail.setError("User is already registered with this email. Use another email.");
                        Addemail.requestFocus();
                    }catch (Exception e){

                        Log.e(TAG, e.getMessage());
                        Toast.makeText(AddStudent.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                    pd.dismiss();
                }


            }
        });

    }


}

