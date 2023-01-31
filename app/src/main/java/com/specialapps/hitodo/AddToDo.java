package com.specialapps.hitodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Date;

public class AddToDo extends AppCompatActivity {
    BottomNavigationView bottomNavigator;
    private Date startDate;
    private Date endDate;
    private EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        bottomNavigator = findViewById(R.id.bottom_nav);
        bottomNavigator.setSelectedItemId(R.id.add_todo);

        textInput = findViewById(R.id.add_todo_text);
        Button setStartDate = findViewById(R.id.add_todo_startdate);
        Button setEndDate = findViewById(R.id.add_todo_enddate);
        Button addButton = findViewById(R.id.add_todo_add);

        setStartDate.setOnClickListener((e)->{
            new DateTimePicker(AddToDo.this, (date)->{
                startDate = date;
                TextView s = findViewById(R.id.add_todo_startdate_text);
                s.setText(startDate.toString().substring(4,16));
            });
        });

        setEndDate.setOnClickListener((e)->{
            new DateTimePicker(AddToDo.this, (date)->{
                endDate = date;
                TextView s = findViewById(R.id.add_todo_enddate_text);
                s.setText(endDate.toString().substring(4,16));
            });
        });

        addButton.setOnClickListener((e)->{
            if(textInput.getText().toString().length()==0){
                Toast.makeText(AddToDo.this, "Please write down the task", Toast.LENGTH_SHORT).show();
                return;
            }

            if(startDate == null){
                Toast.makeText(AddToDo.this, "Please select start date", Toast.LENGTH_SHORT).show();
                return;
            }

            if(endDate == null){
                Toast.makeText(AddToDo.this, "Please select end date", Toast.LENGTH_SHORT).show();
                return;
            }

            if(startDate.getTime()-endDate.getTime()>0){
                Toast.makeText(AddToDo.this, "Start Date can't be past End Date", Toast.LENGTH_SHORT).show();
            }

            TodoDatabase db = new TodoDatabase(AddToDo.this);
            db.create(textInput.getText().toString(), startDate.getTime(), endDate.getTime());

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(0,0);
        });

        bottomNavigator.setOnNavigationItemSelectedListener((item)->{
            switch(item.getItemId()){
                case R.id.add_todo:
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
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }
}