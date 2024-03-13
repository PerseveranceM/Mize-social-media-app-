package com.example.mize;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignUp extends MainActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

       Button submit = (Button)findViewById(R.id.SubmitBtn);
        EditText naam = (EditText)findViewById(R.id.usernameSU);
        EditText phone = (EditText)findViewById(R.id.PhoneNO);
        EditText paswrd = (EditText)findViewById(R.id.passwordSU);
        EditText Re_Pas = (EditText)findViewById(R.id.Re_enterPass);
        TextView t = (TextView)findViewById(R.id.textView3);
        t.setVisibility(View.INVISIBLE);

        Hashing hashed = new Hashing();
        OkHttpClient client = new OkHttpClient();
        OkHttpClient client1 = new OkHttpClient();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = naam.getText().toString();
                String cell = phone.getText().toString();
                String password = hashed.hash(paswrd.getText().toString());
                String Re_Password = hashed.hash(Re_Pas.getText().toString());

                // Create a FormBody builder
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("username", username);
                formBodyBuilder.add("phone_no", cell);
                formBodyBuilder.add("password", password);

                FormBody formBody = formBodyBuilder.build();


                Request request = new Request.Builder()
                        .url("https://lamp.ms.wits.ac.za/home/s2426592/sign.php/")
                        .post(formBody)
                        .build();


                if(Validateinput(username,password,Re_Password,t))
                {
                    client1.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful())
                            {
                                String respo = response.body().string();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(respo.equals("Done"))
                                        {
                                            Intent in = new Intent(getApplicationContext(), Login.class);
                                            startActivity(in);
                                        }
                                        else{
                                            if (respo.equals("Username already exists."))
                                            {
                                                new StyleableToast
                                                        .Builder(SignUp.this)
                                                        .text(respo)
                                                        .textColor(Color.WHITE)
                                                        .backgroundColor(Color.BLACK)
                                                        .show();
                                                naam.setText("");
                                                phone.setText("");
                                                paswrd.setText("");
                                                Re_Pas.setText("");
                                            }
                                        }
//

                                    }
                                });
                            }
                        }
                    });
                }
                else
                {
                    naam.setText("");
                    phone.setText("");
                    paswrd.setText("");
                    Re_Pas.setText("");
                }

            }
        });


    }


    public boolean Validateinput(String username, String password, String Re_Password, TextView t)  {

        boolean RetVal = true;
        if( username.equals("")|| password.equals(""))
        {
            new StyleableToast
                    .Builder(SignUp.this)
                    .text("Username and Password have to be the same ")
                    .textColor(Color.WHITE)
                    .backgroundColor(Color.BLACK)
                    .show();
            RetVal = false;
        }
        else {
            if(!Re_Password.equals(password))
            {
                new StyleableToast
                        .Builder(SignUp.this)
                        .text("Re_Enter Password and Password have to be the same ")
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.BLACK)
                        .show();
                RetVal = false;
            }
        }

        return RetVal;
    }


}


