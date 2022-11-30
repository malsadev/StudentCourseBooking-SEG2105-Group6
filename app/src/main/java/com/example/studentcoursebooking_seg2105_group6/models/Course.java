package com.example.studentcoursebooking_seg2105_group6.models;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Course implements Serializable {
    private String courseName;
    private String courseCode;
    private String courseDescription = "";
    private ArrayList<Date> courseSchedule = new ArrayList<>();
    private String courseInstructor = "None";
    private String courseCapacity = "None";
    private ArrayList<User> enrolledStudents = new ArrayList<>();


    public Course() {

    }

    public Course(String courseName, String courseCode, String courseCapacity) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseCapacity = courseCapacity;
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

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public String getCourseCapacity() {
        return courseCapacity;
    }

    public void setCourseCapacity(String courseCapacity) {
        this.courseCapacity = courseCapacity;
    }

    public ArrayList<Date> getCourseSchedule() {
        return courseSchedule;
    }

    public void setCourseSchedule(ArrayList<Date> courseSchedule) {
        this.courseSchedule = courseSchedule;
    }

    public void addDate(Date date) {
        courseSchedule.add(date);
    }

    public void addStudent(User user) {enrolledStudents.add(user);
    }

    @PropertyName("enrolledStudents")
    public ArrayList<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(ArrayList<User> list){enrolledStudents=list;}
}

