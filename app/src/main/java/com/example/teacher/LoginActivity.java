package com.example.teacher;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText usernameEditText, passwordEditText;
    private Button loginButton;
    ProgressBar progress;
    FirebaseAuth firebaseAuth2;
    ImageView see;
    static  final String TAG="Student_login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setTitle("Faculty Login");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            TextView titleTextView = new TextView(this);
            titleTextView.setText("Faculty Login");
            titleTextView.setTextColor(Color.WHITE);
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            titleTextView.setTypeface(null, android.graphics.Typeface.BOLD);
            titleTextView.setGravity(Gravity.CENTER);

            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER
            );
            actionBar.setCustomView(titleTextView, params);
        }

        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        see=findViewById(R.id.hideshow);
        see.setImageResource(R.drawable.password_hide);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordEditText.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){

                    //if password is visible then hide this
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    //change Icon
                    see.setImageResource(R.drawable.password_hide);
                }else {
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    //change Icon
                    see.setImageResource(R.drawable.password_show);
                }
            }
        });
    }

    private void login() {
        String usernameOrEmail = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(TextUtils.isEmpty(usernameOrEmail)){
            usernameEditText.setError("Please enter email");
            usernameEditText.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()) {

            usernameEditText.setError("Invalid email");
            usernameEditText.requestFocus();

        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Please enter password");
            passwordEditText.requestFocus();
        }else {
            progress.setVisibility(View.VISIBLE);
            loginUser(usernameOrEmail,password);

        }


    }

    private void loginUser(String usernameOrEmail, String password) {
        firebaseAuth2.signInWithEmailAndPassword(usernameOrEmail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //get instance of current user

                    FirebaseUser facultyuser=firebaseAuth2.getCurrentUser();
                    Intent intent=new Intent(LoginActivity.this, dashboard_page.class);
                    startActivity(intent);

                }else {
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e){
                        usernameEditText.setError("User does not exists. Please get registered.");
                        usernameEditText.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        usernameEditText.setError("Invalid credentials.");
                        usernameEditText.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                progress.setVisibility(View.GONE);
            }
        });

    }
}
