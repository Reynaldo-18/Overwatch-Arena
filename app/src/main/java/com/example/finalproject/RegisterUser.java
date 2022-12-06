package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.regex.Pattern;


public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private EditText enterFullNameEditText;
    private EditText enterEmailEditText;
    private EditText enterPasswordEditText;
    private Button registerButton;
    private TextView loginTexView;

    private String enterFullName, enterEmail, enterPassword;

    private FirebaseAuth myAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user_activity);

        enterFullNameEditText = (EditText) findViewById(R.id.register_fullName_textView);
        enterEmailEditText = (EditText) findViewById(R.id.register_email_textView);
        enterPasswordEditText = (EditText) findViewById(R.id.register_password_textView);

        loginTexView = findViewById(R.id.login);
        loginTexView.setOnClickListener(this);

        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);

        myAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.login){
            Intent intent = new Intent(RegisterUser.this, LogInUser.class);
            startActivity(intent);
        } else if(view.getId() == R.id.register_button){
            this.takeUserInfo();
        }
    }

    public void takeUserInfo(){
        enterFullName = enterFullNameEditText.getText().toString();
        enterEmail = enterEmailEditText.getText().toString();
        enterPassword = enterPasswordEditText.getText().toString();

        //if user leaves name box empty the user will get a error
        if(enterFullName.trim().equalsIgnoreCase("")){
            enterFullNameEditText.setError("This field is blank!");

        //if user leaves email box empty the user will get a error
        }

        if(enterEmail.trim().equalsIgnoreCase("")){
            enterEmailEditText.setError("This field is blank!");

        //if user leaves password box empty the user will get a error
        }

        if(enterPassword.trim().equalsIgnoreCase("")){
            enterPasswordEditText.setError("This field is blank!");

        //this will make sure the email address is in proper format
        } else if(!Patterns.EMAIL_ADDRESS.matcher(enterEmail).matches()){
            enterEmailEditText.setError("Invalid Email!");

        //if user inputs password shorter than 8 characters than the user will get a error
        } else if(enterPassword.length() < 8 ) {
            enterPasswordEditText.setError("Password should be 8 characters minimum!");

        } else {
            this.registerUsers();
            this.addUserData();
        }
    }

    private void registerUsers(){

        //email and passwords will be used to create the users account
        myAuth.createUserWithEmailAndPassword(enterEmail, enterPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = myAuth.getCurrentUser();
                    user.sendEmailVerification();
                    Toast.makeText(RegisterUser.this, "You Registered!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterUser.this, LogInUser.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void addUserData(){
        enterFullName = enterFullNameEditText.getText().toString();
        enterEmail = enterEmailEditText.getText().toString();
        enterPassword = enterPasswordEditText.getText().toString();

        reference = firebaseDatabase.getReference();

        //UserInfo object is declared to store users full name, email and password
        UserInfo userInfo = new UserInfo(enterFullName, enterEmail, enterPassword);

        //  reference.child("user") This will create a reference to the location Users.
        // .child(enterFullName) This will create a location inside Users named after the user
        // .setValue(userInfo) The values of userInfo will be used to be stored inside of .child(enterFullName)
        //
        //Ex: user inputs: John Boyega, johnboyega@gmail.com, john1234
        //
        //  Users
        //   |
        //   |---John Boyega
        //           |
        //           |---userEmail: johnboyega@gmail.com
        //           |---userName: John Boyega
        //           |---userPassword: john1234
        reference.child("Users").child(enterFullName).setValue(userInfo);
    }
}
