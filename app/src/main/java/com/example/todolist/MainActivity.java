package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.TaskAdapter;
import com.example.todolist.HelperClass.HelperClass;
import com.example.todolist.Model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    HelperClass helperClass;
    FloatingActionButton add;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helperClass = new HelperClass(this);
        add = findViewById(R.id.btn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch tasks from database
        taskList = helperClass.getAllTasks();

        // Set up adapter with a click listener
        adapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task, int position) {
                Intent intent = new Intent(MainActivity.this, EditTask.class);
                intent.putExtra("taskId", task.getId());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        // Floating Action Button click to add a new task
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
            }
        });
    }
}
