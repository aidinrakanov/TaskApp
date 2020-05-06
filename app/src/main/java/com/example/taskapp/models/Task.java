package com.example.taskapp.models;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String desc;

    public Task() {
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
