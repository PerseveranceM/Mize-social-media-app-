package com.example.mize;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChatActivity extends AppCompatActivity {

    private ListView listView;
    private BottomNavigationView bottomNavigationView;
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        listView = findViewById(R.id.listViewNames);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewFlipper = findViewById(R.id.viewFlipper);

        String username = getIntent().getStringExtra("USERNAME");

        // Set up the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"Name 1", "Name 2", "Name 3"});
        listView.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_charts) {
                    viewFlipper.setDisplayedChild(0);
                    return true;
                } else if (item.getItemId() == R.id.action_status) {
//                    viewFlipper.setDisplayedChild(1);
                    Intent inte = new Intent(getApplicationContext(), Status.class);
                    startActivity(inte);
                    return true;
                } else if (item.getItemId() == R.id.action_addfriends) {

//                    viewFlipper.setDisplayedChild(2);
                    Intent in = new Intent(getApplicationContext(),friendsList.class);
                    in.putExtra("USERNAME",username);
                    startActivity(in);
                    return true;
                }
                return false;
            }
        });
    }
}
