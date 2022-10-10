package com.example.studentcoursebooking_seg2105_group6.models;

public class Course {
    String courseName;
    int courseCode;

    public Course(){

    }

    public Course(String course){
        this.courseName = course;
    }
    public int getCode(int code){
        this.courseCode = code;
        return code;
    }



}
