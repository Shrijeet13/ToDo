package com.example.todolist.Model;

public class Task {
    private int id;
    private String title;
    private String description;
    private String time;
    private String date;

    public Task(int id, String title, String description, String time, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
