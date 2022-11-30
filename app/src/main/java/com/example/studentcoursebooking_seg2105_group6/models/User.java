package com.example.studentcoursebooking_seg2105_group6.models;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String role;
    private String password;
    private String name;
    private ArrayList<Course> courseList = new ArrayList<>();

    //serialization for cross-activity context transfer
    public User() {

    }

    public User(String username, String password) {
        this(username, password, "", "");
    }

    public User(String username, String password, String role, String name){
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourseList(){return courseList;}

    public void addCourse(Course course){courseList.add(course);}
}
