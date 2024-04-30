package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Student_login extends AppCompatActivity {

    EditText email,pass;
    Button login;
    ImageView show;

    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    static  final String TAG="Student_login";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        getSupportActionBar().setTitle("Login");

        email=findViewById(R.id.login_mail);
        pass=findViewById(R.id.login_password);
        login=findViewById(R.id.loginBtn);

         show=findViewById(R.id.show_hide);
         show.setImageResource(R.drawable.password_hide);



        progressBar=findViewById(R.id.progressBar);
        firebaseAuth=FirebaseAuth.getInstance();


        //show and hide password

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){

                    //if password is visible then hide this
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    //change Icon
                    show.setImageResource(R.drawable.password_hide);
                }else {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    //change Icon
                    show.setImageResource(R.drawable.password_show);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail=email.getText().toString();
                String textpwd=pass.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    email.setError("Please enter email");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {

                    email.setError("Invalid email");
                    email.requestFocus();


                } else if (TextUtils.isEmpty(textpwd)) {
                    pass.setError("Please enter email");
                    pass.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textpwd);

                }
            }
        });
    }

    private void loginUser(String Email, String Pwd) {
        firebaseAuth.signInWithEmailAndPassword(Email,Pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //get instance of current user

                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                     Intent intent=new Intent(Student_login.this,Student_Profile.class);
                     startActivity(intent);


                }else {
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e){
                        email.setError("User does not exists. Please register.");
                        email.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        email.setError("Invalid credentials.");
                        email.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(Student_login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }


}