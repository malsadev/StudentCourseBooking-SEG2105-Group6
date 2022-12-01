package com.example.studentcoursebooking_seg2105_group6.models;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String role;
    private String password;
    private String name;
    private ArrayList<Course> courses = new ArrayList<>();

    //serializaation for cross-activity context transfer
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

    @PropertyName("courses")
    public ArrayList<Course> getCourses(){return courses;}

    public void addCourse(Course course){courses.add(course);}

    public void setCourses(ArrayList<Course> list){ courses=list;}

    public void removeCourse(Course course){
        for(int i=0; i<courses.size(); ++i){
            if (course.getCourseCode().equals(courses.get(i).getCourseCode())){
                courses.remove(i);
                break;
            }
        }
    }
}
