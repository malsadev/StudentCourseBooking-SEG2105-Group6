package com.example.studentcoursebooking_seg2105_group6.models;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseName;
    private String courseCode;
    private String courseDescription="";

    public Course(){

    }

    public Course(String courseName, String courseCode){
        this.courseName = courseName;
        this.courseCode = courseCode;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String description) {
        courseDescription = description;
    }
}
