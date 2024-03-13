package com.example.mize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginbtn = (Button) findViewById(R.id.loginBtn);
        Button signupbtn = (Button) findViewById(R.id.signupBtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(inLogin);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inSignup = new Intent(getApplicationContext(), SignUp.class);
                startActivity(inSignup);
            }
        });
    }
}