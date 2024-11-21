package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.HelperClass.HelperClass;
import com.example.todolist.Model.ReminderWorker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTask extends AppCompatActivity {

    HelperClass dbhelper;
    EditText title, descp;
    CalendarView date;
    TimePicker time;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbhelper = new HelperClass(this);
        title = findViewById(R.id.title);
        descp = findViewById(R.id.desp);
        date = findViewById(R.id.calendarView);
        time = findViewById(R.id.timePicker);
        save = findViewById(R.id.button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Etitle = title.getText().toString();
                String Edesp = descp.getText().toString();

                // Get time in hours and minutes
                int hour = time.getHour();
                int minute = time.getMinute();
                String formattedTime = String.format("%02d:%02d", hour, minute);

                // Get selected date from CalendarView
                long selectedDate = date.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String Edate = sdf.format(new Date(selectedDate));

                // Save to database
                int taskId = dbhelper.addToDo(Etitle, Edesp, formattedTime, Edate);

                // Schedule reminder
                scheduleReminder(taskId, Etitle, Edesp, selectedDate, hour, minute);

                // Navigate to MainActivity
                Intent intent = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void scheduleReminder(int taskId, String title, String description, long selectedDate, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedDate);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        long delay = calendar.getTimeInMillis() - System.currentTimeMillis();

        if (delay > 0) {
            androidx.work.Data data = new androidx.work.Data.Builder()
                    .putInt("taskId", taskId)
                    .putString("title", title)
                    .putString("description", description)
                    .build();

            androidx.work.OneTimeWorkRequest workRequest = new androidx.work.OneTimeWorkRequest.Builder(ReminderWorker.class)
                    .setInitialDelay(delay, java.util.concurrent.TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .build();

            androidx.work.WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
        }
    }

}
