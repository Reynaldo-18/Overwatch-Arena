package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class LogInUser extends AppCompatActivity implements View.OnClickListener{

    private EditText enterEmailEditText;
    private EditText enterPasswordEditText;
    private Button loginButton;
    private TextView forgotPasswordTexView;
    private TextView registerTexView;

    private FrameLayout googleLogin;
    private String enterEmailLogin, enterPasswordLogin;

    private FirebaseAuth myAuth;

    private String isVerified;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);

        enterEmailEditText = (EditText) findViewById(R.id.enter_email);
        enterPasswordEditText = (EditText) findViewById(R.id.enter_password);
        loginButton = (Button) findViewById(R.id.log_in_button);
        loginButton.setOnClickListener(this);
        forgotPasswordTexView = (TextView) findViewById(R.id.forgot_password_textView);
        forgotPasswordTexView.setOnClickListener(this);
        registerTexView = (TextView) findViewById(R.id.register_textView);
        registerTexView.setOnClickListener(this);

        myAuth = FirebaseAuth.getInstance();

//        if(myAuth.getCurrentUser() != null){
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(this, LogInUser.class);
//            startActivity(intent);
//        }
    }

    @Override
    public void onClick(View view) {
        //if the user clicks on the register textView they will be take to the RegisterUser Class
        if(view.getId() == R.id.register_textView){
            Intent intent = new Intent(this, RegisterUser.class);
            startActivity(intent);


        //if the user clicks on forgot password textView they will be take to ResetPassword Class
        } else if(view.getId() == R.id.forgot_password_textView){
            Intent intent = new Intent(this, ResetPassword.class);
            startActivity(intent);


        } else if(view.getId() == R.id.log_in_button){
            this.login();
        }
    }


    private void login() {
        enterEmailLogin = enterEmailEditText.getText().toString();
        enterPasswordLogin = enterPasswordEditText.getText().toString();

        boolean enabled = true;

        //if the user leaves email box empty than they will get a error warning
        if(enterEmailLogin.trim().equalsIgnoreCase("")){
            enterEmailEditText.setError("Email is required!");
        //it validates the email from the user
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(enterEmailLogin).matches()){
            enterEmailEditText.setError("Invalid Email!");

        //if the user leaves the password box empty they will get an error warning
        }

        if(enterPasswordLogin.trim().equalsIgnoreCase("")){
            enterPasswordEditText.setError("Password is required!");

        }

        if(enterPasswordLogin.length() < 8) {
            enterPasswordEditText.setError("Password is short");

        } else{
            //it signs in with the info the user registered with
            myAuth.signInWithEmailAndPassword(enterEmailLogin, enterPasswordLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    //if the email and password from the user matches they will be logged in
                    if(task.isSuccessful()){
                        //if the email is verified than the user will log in and be taken to main activity
                        FirebaseUser user = myAuth.getCurrentUser();

                        if(user.isEmailVerified()){
                            Toast.makeText(LogInUser.this, "Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogInUser.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            user.sendEmailVerification();
                            Toast.makeText(LogInUser.this, "Verify Email", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(LogInUser.this, "Logged In Failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}




