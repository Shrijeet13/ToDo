package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.HelperClass.HelperClass;
import com.example.todolist.Model.Task;

public class EditTask extends AppCompatActivity {

    EditText title, description;
    Button saveBtn;
    HelperClass dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        saveBtn = findViewById(R.id.saveBtn);
        dbhelper = new HelperClass(this);

        int taskId = getIntent().getIntExtra("taskId", -1);

        Task task = dbhelper.getTask(taskId);
        if (task != null) {
            title.setText(task.getTitle());
            description.setText(task.getDescription());
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get updated title and description
                String newTitle = title.getText().toString();
                String newDescription = description.getText().toString();

                // Retrieve taskId, time, and date from Intent extras
                Intent intent = getIntent();
                int taskId = intent.getIntExtra("taskId", -1); // Default to -1 if not passed
                String existingTime = intent.getStringExtra("taskTime"); // Get original time
                String existingDate = intent.getStringExtra("taskDate"); // Get original date

                if (taskId != -1) {
                    // Update the task in the database
                    dbhelper.updateToDo(taskId, newTitle, newDescription, existingTime, existingDate);

                    // Navigate back to MainActivity
                    Intent i = new Intent(EditTask.this, MainActivity.class);
                    startActivity(i);
                } else {
                    // Handle error case if taskId is missing
                    Toast.makeText(EditTask.this, "Error: Task ID not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
