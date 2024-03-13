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
import androidx.annotation.StyleableRes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends MainActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button Login = (Button) findViewById(R.id.Login);
        EditText Name1 = (EditText) findViewById(R.id.usernameLg);
        EditText Password1 = (EditText) findViewById(R.id.passwordLg);

        TextView t1 = (TextView) findViewById(R.id.textView2);
        t1.setVisibility(View.INVISIBLE);

        OkHttpClient client = new OkHttpClient();




        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hashing hashed = new Hashing();
                String Password  = hashed.hash(Password1.getText().toString());
                String Username = Name1.getText().toString();

                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("username", Username);
                formBodyBuilder.add("password",Password);

                FormBody formBody = formBodyBuilder.build();
                Request request = new Request.Builder()
                        .url("https://lamp.ms.wits.ac.za/home/s2426592/login.php")
                        .post(formBody)
                        .build();

                if( !(Username.equals("") || Password.equals("")))
                {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful())
                            {
                                String res = response.body().string();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(res.equals("Done"))
                                        {
                                            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                            intent.putExtra("USERNAME", Username);
                                            startActivity(intent);
                                        } else if (res.equals("none")) {
                                            new StyleableToast
                                                    .Builder(Login.getContext())
                                                    .text("You have to create an account")
                                                    .textColor(Color.WHITE)
                                                    .backgroundColor(Color.BLACK)
                                                    .show();
                                        }
                                        else {
                                            new StyleableToast
                                                    .Builder(Login.getContext())
                                                    .text("Wrong Password")
                                                    .textColor(Color.WHITE)
                                                    .backgroundColor(Color.BLACK)
                                                    .show();
                                        }
                                    }
                                });
                            }
                        }

                    });
                }
                else {
                    new StyleableToast
                            .Builder(Login.getContext())
                            .text("Hello world!")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.BLACK)
                            .show();
                }
            }
        });
    }


}
