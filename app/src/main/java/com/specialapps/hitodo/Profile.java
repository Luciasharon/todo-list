package com.specialapps.hitodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView profilePhoto = findViewById(R.id.profile_photo);
        Glide.with(this)
                .load("https://picsum.photos/200")
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(profilePhoto);

        bottomNavigator = findViewById(R.id.bottom_nav);
        bottomNavigator.setSelectedItemId(R.id.profile);

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
                    startActivity(new Intent(getApplicationContext(), Info.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.profile:
                    return true;
            }
            return false;
        });
    }
}