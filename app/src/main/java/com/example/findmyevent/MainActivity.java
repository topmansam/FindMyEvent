package com.example.findmyevent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends Activity {

    private Button loginBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";   // Pattern for email to meet
    ProgressDialog pD;

    FirebaseAuth myAuth;
    FirebaseUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons and firebase authentication
        loginBtn = findViewById(R.id.loginButton);
        pD = new ProgressDialog(this);

        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        // Add fuction for login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize user input fields and extract user text
                EditText et = findViewById(R.id.userEmail);
                EditText pt = findViewById(R.id.userPassword);
                String email = String.valueOf(et.getText());
                String password = String.valueOf(pt.getText());

                // Check for all errors, if email matches the pattern and password is less than 6 characters
                if(!email.matches(emailPattern)){
                    et.setError("Invalid email syntax. Please enter actual email");
                }else if(password.isEmpty() || password.length() < 6){
                    pt.setError("Password must be at least 6 characters");
                }else{
                    // If no error caught, display dialog box
                    pD.setTitle("Loging in!");
                    pD.setMessage("Please wait while we Log you in");
                    pD.setCanceledOnTouchOutside(false);
                    pD.show();

                    // Sign in user with email and password
                    myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // If sign in is successful, send user to next activity
                                pD.dismiss();
                                sendtoNextActivity();
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            }else{
                                // If sign-in is not successful, show error message
                                pD.dismiss();
                                Toast.makeText(MainActivity.this, "Could not login because "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Initialize signup button
        Button signupBtn = findViewById(R.id.signupBtn);
        // Set click function for signup button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send user to sign Up page
                Intent intent = new Intent(MainActivity.this, signUP.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
    //  Send user to interest selection activity
    private void sendtoNextActivity() {
        Intent intent1 = new Intent(MainActivity.this, InterestsSelectionActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }

}