package com.specialapps.hitodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigator;
    private RecyclerView recyclerView;
    ArrayList<TodoDatabase.TodoData> allTodo;

    private class TodoViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TodoDatabase.TodoData mData = null;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mView = itemView;
        }

        public void setData(TodoDatabase.TodoData data){
            this.mData = data;
            ((TextView) mView.findViewById(R.id.todo_text)).setText(data.text);
            TextView startDate = mView.findViewById(R.id.todo_start_date);
            startDate.setText(data.startDate);
            TextView endDate = mView.findViewById(R.id.todo_end_date);
            endDate.setText(data.endDate);

            // Add event listener for update
            mView.findViewById(R.id.todo_edit_button).setOnClickListener((e)->{
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Edit Todo");
                final EditText input = new EditText(MainActivity.this);
                input.setText(mData.text);
                builder.setView(input);

                builder.setPositiveButton("OK", (d,a)->{
                    // Here update the field into the database
                    TodoDatabase db = new TodoDatabase(MainActivity.this);
                    db.update(mData.id, input.getText().toString());
                    loadAllData();
                    recyclerView.getAdapter().notifyDataSetChanged();
                });

                builder.setNeutralButton("Cancel",(d,a)->{ d.cancel();});
                builder.show();
            });

            // Add event listener for delete
            mView.findViewById(R.id.todo_delete_button).setOnClickListener((e)->{
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Did you complete this task?");
                builder.setPositiveButton("Yes", (d,a)->{
                    // Delete the field from database
                    TodoDatabase db = new TodoDatabase(MainActivity.this);
                    db.delete(mData.id);
                    loadAllData();
                    recyclerView.getAdapter().notifyDataSetChanged();
                });
                builder.setNeutralButton("No", (d,a)->d.cancel());
                builder.show();
            });

            mView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        }
    }

    private class MRecyclerViewAdapter extends RecyclerView.Adapter<TodoViewHolder>{
        @NonNull
        @Override
        public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.todo_view, null, false);
            return new TodoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
            holder.setData(allTodo.get(position));
        }

        @Override
        public int getItemCount() {
            return allTodo.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAllData();
        bottomNavigator = findViewById(R.id.bottom_nav);
        bottomNavigator.setSelectedItemId(R.id.home);

        recyclerView = findViewById(R.id.home_recycler_view);
        recyclerView.setAdapter(new MRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigator.setOnNavigationItemSelectedListener((item)->{
            switch(item.getItemId()){
                case R.id.add_todo:
                    startActivity(new Intent(getApplicationContext(), AddToDo.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
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

    private void loadAllData(){
        TodoDatabase db = new TodoDatabase(this);
        allTodo = db.getAllData();
    }
}