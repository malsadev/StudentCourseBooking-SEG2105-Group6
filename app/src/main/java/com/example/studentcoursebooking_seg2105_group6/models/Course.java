package com.example.studentcoursebooking_seg2105_group6.models;

import java.io.Serializable;
import java.util.ArrayList;


public class Course implements Serializable {
    private class Date implements Serializable{
        private String day;
        private String time;
        public Date(String date, String time){
            this.day=day;
            this.time=time;
        }
        public String getDay(){
            return day;
        }
        public String getTime(){
            return time;
        }
        public void setDay(String day){
            this.day=day;
        }
        public void setTime(String time){
            this.time=time;
        }
    }
    private String courseName;
    private String courseCode;
    private String courseDescription = "";
    private ArrayList<Date> courseSchedule = new ArrayList<>();


    public Course() {

    }

    public Course(String courseName, String courseCode) {
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

    public ArrayList<Date> getCourseSchedule(){
        return courseSchedule;
    }
    public void addDate(String day, String time){
        Date date=new Date(day, time);
        courseSchedule.add(date);
    }
    // public String getCourseSchedule() {
     //   return courseSchedule;
    //}
    //create class time object
    //create class variable for course

}