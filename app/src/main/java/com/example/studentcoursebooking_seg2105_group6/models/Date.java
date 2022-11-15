package com.example.studentcoursebooking_seg2105_group6.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Date implements Serializable {
    private String day;
    private String time;

    public Date(String day, String time) {
        this.day = day;
        this.time = time;
    }

    public Date() {
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
