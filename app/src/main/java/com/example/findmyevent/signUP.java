package com.example.findmyevent;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.os.Bundle;

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

        userEmail = findViewById(R.id.RegisteredEmail);
        user_Username = findViewById(R.id.RegisteredUsername);
        password = findViewById(R.id.RegisteredPassword);
        ConfirmPassword = findViewById(R.id.RegisteredConfirmPassword);
        signupBtn = findViewById(R.id.signUpButton);
        pD = new ProgressDialog(this);

        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(userEmail.getText());
                String username = user_Username.getText().toString();
                String pass = String.valueOf(password.getText());
                String confirmPass = String.valueOf(ConfirmPassword.getText());

                if(!email.matches(emailPattern)){
                    userEmail.setError("Invalid email syntax.");
                }else if(pass.isEmpty() || pass.length() < 6){
                    password.setError("Password must be atleast 6 letters");
                } else if (username.isEmpty()) {
                    user_Username.setError("Please enter a username");
                }else if(!pass.equals(confirmPass)){
                    ConfirmPassword.setError("your passwords do not match");
                }else{
                    pD.setTitle("Registering");
                    pD.setMessage("Please wait while we register you");
                    pD.setCanceledOnTouchOutside(false);
                    pD.show();

                    myAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                pD.dismiss();
                                sendtoNextActivity();
                                Toast.makeText(signUP.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            }else{
                                pD.dismiss();
                                Toast.makeText(signUP.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });
    }

    private void sendtoNextActivity() {
        Intent intent1 = new Intent(signUP.this, InterestsSelectionActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);

    }
}