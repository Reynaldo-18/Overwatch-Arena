package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener{

    private EditText reset;
    private Button resetButton;

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);

        reset = findViewById(R.id.reset_editTexView);

        resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);

        myAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        String enteringEmailReset = reset.getText().toString();

        //This will send an error if no email is entered
        if(enteringEmailReset.trim().equalsIgnoreCase("")){
            reset.setError("Email is required!");
        }

        //This will check that the email is a properly structured
        // Ex: rey129@gmail.com not weowqeii.com
        if(!Patterns.EMAIL_ADDRESS.matcher(enteringEmailReset).matches()){
            reset.setError("Invalid Email!");
        }

        //This will send a password reset link to the users email
        myAuth.sendPasswordResetEmail(enteringEmailReset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(ResetPassword.this, LogInUser.class);
                    startActivity(intent);
                    Toast.makeText(ResetPassword.this, "Email Sent!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
