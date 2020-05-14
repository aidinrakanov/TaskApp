package com.example.taskapp.models;

import android.widget.TextView;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Task implements Serializable {

    public Task(TextView textTitle, TextView textDesc) {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String desc;

    public Task(int id) {
        this.id = id;
    }

    public Task() {
        this.title = title;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public String getDesc() {
        return desc;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
