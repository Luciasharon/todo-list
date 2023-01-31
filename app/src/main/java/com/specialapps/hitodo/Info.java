package com.specialapps.hitodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Info extends AppCompatActivity {
    BottomNavigationView bottomNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        bottomNavigator = findViewById(R.id.bottom_nav);
        bottomNavigator.setSelectedItemId(R.id.info);

        TextView tv = findViewById(R.id.info_text);
        tv.setText(
                Html.fromHtml(
                        "<h1>Info: Your daily todo list</h1>" +
                                "<p>" +
                                "Your daily todo list is an app developed for tracking your daily activities." +
                                "</p>"
                )
        );

        bottomNavigator.setOnNavigationItemSelectedListener((item)->{
            switch(item.getItemId()){
                case R.id.add_todo:
                    startActivity(new Intent(getApplicationContext(), AddToDo.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.info:
                    return true;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }
}