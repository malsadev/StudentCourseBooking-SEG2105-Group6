package com.example.studentcoursebooking_seg2105_group6.models;
import java.io.Serializable;

public class Course implements Serializable {
    String courseName;
    int courseCode;

    public Course(){

    }

    public Course(String courseName, int courseCode){
        this.courseName = courseName;
        this.courseCode = courseCode;
    }
    public int getCode(){
        return this.courseCode;
    }



}
