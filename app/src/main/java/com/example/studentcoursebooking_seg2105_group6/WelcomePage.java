package com.example.studentcoursebooking_seg2105_group6;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentcoursebooking_seg2105_group6.models.User;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMesssageView);
        User signedUser = (User) getIntent().getSerializableExtra("signedUser");
        welcomeMessage.setText("Welcome " + signedUser.getRole() + " " + signedUser.getUsername());

        Button welcomeBackBtn=(Button)findViewById(R.id.welcomeBackBtn);
        Button createCourseBtn=(Button)findViewById(R.id.createCourseBtn);
        Button viewCourseBtn=(Button)findViewById(R.id.viewCourseBtn);

        //to enable buttons if admin use
        //createCourseBtn.setEnabled(condition);
        //viewCourseBtn.setEnabled(condition);

        //back button
        welcomeBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomePage.this, MainActivity.class);
                startActivity(intent);// should take to create account
            }
        });
        // take to create course
        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomePage.this, CreateCourse.class);
                startActivity(intent);// should take to create account
            }
        });
        //take to view course
        viewCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomePage.this, ViewCourse.class);
                startActivity(intent);// should take to create account
            }
        });
    }

}