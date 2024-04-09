package com.example.findmyevent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    String email;
    String password;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.userEmail);
                EditText pt = findViewById(R.id.userPassword);
                email = String.valueOf(et.getText());
                password = String.valueOf(pt.getText());

                writeToFile("Email :"+email+"\nPassword :"+password, MainActivity.this);

                Intent intentMain = new Intent(MainActivity.this, homePage.class);
                startActivity(intentMain);

            }
        });
    }

    private void writeToFile(String data, Context context){
        try {
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("Info.txt", Context.MODE_PRIVATE));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed" + e.toString());
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