package com.example.findmyevent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private Button loginBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog pD;

    FirebaseAuth myAuth;
    FirebaseUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = findViewById(R.id.loginButton);
        pD = new ProgressDialog(this);

        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.userEmail);
                EditText pt = findViewById(R.id.userPassword);
                String email = String.valueOf(et.getText());
                String password = String.valueOf(pt.getText());

                /*writeToFile("Email :"+email+"\nPassword :"+password, MainActivity.this);
                Intent intentMain = new Intent(MainActivity.this, homePage.class);
                startActivity(intentMain);*/

                if(!email.matches(emailPattern)){
                    et.setError("Invalid email syntax.");
                }else if(password.isEmpty() || password.length() < 6){
                    pt.setError("Password must be atleast 6 letters");
                }else{
                    pD.setTitle("Loging in!");
                    pD.setMessage("Please wait while we Log you in");
                    pD.setCanceledOnTouchOutside(false);
                    pD.show();

                    myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                pD.dismiss();
                                sendtoNextActivity();
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            }else{
                                pD.dismiss();
                                Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        TextView forgotTV = findViewById(R.id.forgotButton);
        forgotTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signUP.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void sendtoNextActivity() {
        Intent intent1 = new Intent(MainActivity.this, homePage.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }

    private void writeToFile(String data, Context context){
        try {
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("Info.txt", Context.MODE_PRIVATE));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed" + e);
        }
    }

    private String readFile(Context context){
        String word = "";
        try {
            InputStream inputStream = context.openFileInput("Info.txt");
            if(inputStream != null){
                InputStreamReader ISR = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(ISR);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                word = stringBuilder.toString();

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return word;
    }

    public void loginOC(Bundle savedInstances) {
        super.onCreate(savedInstances);





    }
}