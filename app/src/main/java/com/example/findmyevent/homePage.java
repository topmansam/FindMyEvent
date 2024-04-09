package com.example.findmyevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class homePage extends AppCompatActivity {

    TextView nameTV, passwordTV;
    LinkedList<String> inf = new LinkedList<>();
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        readFile(homePage.this);

        String name = inf.get(0);
        password = inf.get(1);

        nameTV = findViewById(R.id.textView2);
        nameTV.setText(name);

        passwordTV = findViewById(R.id.textView4);
        passwordTV.setText(password);
    }

    private void readFile(Context context){
        String word = "";
        try {
            InputStream inputStream = context.openFileInput("Info.txt");
            if(inputStream != null){
                InputStreamReader ISR = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(ISR);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    String[] data = receiveString.split(":");
                    inf.add(data[1]);
                }
                inputStream.close();
                word = stringBuilder.toString();

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}