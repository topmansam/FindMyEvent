package com.example.findmyevent;

import androidx.appcompat.app.AppCompatActivity;
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

public class signUP extends AppCompatActivity {

    EditText userEmail, user_Username, password, ConfirmPassword;
    Button signupBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog pD;

    FirebaseAuth myAuth;
    FirebaseUser myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize all use rinput fields and buttons
        userEmail = findViewById(R.id.RegisteredEmail);
        user_Username = findViewById(R.id.RegisteredUsername);
        password = findViewById(R.id.RegisteredPassword);
        ConfirmPassword = findViewById(R.id.RegisteredConfirmPassword);
        signupBtn = findViewById(R.id.signUpButton);
        pD = new ProgressDialog(this);

        // Authenticate Firebase instance and allow current user
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        // Function for sign up button
        signupBtn.setOnClickListener(v -> {
            // Store all infro gotten from the user in strings
            String email = String.valueOf(userEmail.getText());
            String username = user_Username.getText().toString();
            String pass = String.valueOf(password.getText());
            String confirmPass = String.valueOf(ConfirmPassword.getText());

            // Check for all errors, if email meets pattern, if there is a username, if passwords match
            if(!email.matches(emailPattern)){
                userEmail.setError("Invalid email syntax. Please enter an actual email");
            }else if(pass.isEmpty() || pass.length() < 6){
                password.setError("Password must be atleast 6 letters");
            } else if (username.isEmpty()) {
                user_Username.setError("Please enter a username");
            }else if(!pass.equals(confirmPass)){
                ConfirmPassword.setError("your passwords do not match");
            }else{
                // If all user info is accepted, show loading modal
                pD.setTitle("Registering");
                pD.setMessage("Please wait while we register you");
                pD.setCanceledOnTouchOutside(false);
                pD.show();

                // Create a new user in our firebase database with their username and password
                myAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            // If sign up is successful, then go to next activity
                            pD.dismiss();
                            sendtoNextActivity();
                            Toast.makeText(signUP.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }else{
                            // Else show error message
                            pD.dismiss();
                            Toast.makeText(signUP.this, "Was not abble to register you because "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }


        });
    }

    // Function to send user to interest selection page
    private void sendtoNextActivity() {
        Intent intent1 = new Intent(signUP.this, InterestsSelectionActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);

    }
}