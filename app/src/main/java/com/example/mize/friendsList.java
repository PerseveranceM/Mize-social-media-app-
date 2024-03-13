package com.example.mize;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class friendsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriends);

        ListView listViewNames = findViewById(R.id.listViewNames);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2426592/viewfriends.php/")
                .build();

        String username = getIntent().getStringExtra("USERNAME");

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
                            try {
                                ArrayList<String> names;
                                names = processJSON(res,username);

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(friendsList.this, R.layout.list_item_layout, R.id.textViewName, names) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);

                                        Button buttonAction = view.findViewById(R.id.buttonAction);
                                        buttonAction.setText("Add Friend");

                                        // Generate a unique ID for the button based on the element's hashCode
                                        int uniqueButtonId = names.get(position).hashCode();
                                        buttonAction.setId(uniqueButtonId);

                                        buttonAction.setOnClickListener(new View.OnClickListener() {
                                            private boolean buttonClicked = false;

                                            @Override
                                            public void onClick(View v) {
                                                if (!buttonClicked) {
                                                    buttonClicked = true;
                                                    int buttonId = v.getId();
                                                    String clickedName = findNameById(buttonId, names);

                                                    if (clickedName != null) {

                                                        OkHttpClient client1 = new OkHttpClient();
                                                        // Create a FormBody builder
                                                        FormBody.Builder formBodyBuilder = new FormBody.Builder();
                                                        formBodyBuilder.add("username", username);
                                                        formBodyBuilder.add("friend",clickedName);

                                                        FormBody formBody = formBodyBuilder.build();

                                                        //Create a request
                                                        Request request1 = new Request.Builder()
                                                                .url("https://lamp.ms.wits.ac.za/home/s2426592/addfriends.php/")
                                                                .post(formBody)
                                                                .build();


                                                        client1.newCall(request1).enqueue(new Callback() {
                                                            @Override
                                                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                                                e.printStackTrace();
                                                            }

                                                            @Override
                                                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                                if(response.isSuccessful())
                                                                {
                                                                    String resp = response.body().string();

                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            new StyleableToast
                                                                                    .Builder(friendsList.this)
                                                                                    .text(resp)
                                                                                    .textColor(Color.WHITE)
                                                                                    .backgroundColor(Color.BLACK)
                                                                                    .show();
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    }
                                                    buttonAction.setBackgroundColor(Color.GRAY);
                                                }
                                            }
                                        });


                                        return view;
                                    }
                                };

                                listViewNames.setAdapter(adapter);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });

                }
            }
        });
    }

    public ArrayList<String> processJSON(String json,String username) throws JSONException {
        ArrayList<String> NamePasWrd =  new ArrayList<>();

        JSONArray all =  new JSONArray(json);

        for(int i = 0 ; i < all.length(); i++)
        {
            JSONObject item = all.getJSONObject(i);
            String name =  item.getString("username");
            NamePasWrd.add(name);
        }

        NamePasWrd.remove(username);

        return NamePasWrd;
    }
    private String findNameById(int buttonId, ArrayList<String> names) {
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            int uniqueButtonId = name.hashCode();

            if (uniqueButtonId == buttonId) {
                return name;
            }
        }
        return null;
    }




}
