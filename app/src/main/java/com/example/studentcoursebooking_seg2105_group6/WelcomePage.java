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
    private static String adminRole = "admin";

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
        Button viewUserBtn = (Button) findViewById(R.id.viewUsersBtn);

       createCourseBtn.setEnabled(signedUser.getRole().equals(adminRole));
       viewCourseBtn.setEnabled(signedUser.getRole().equals(adminRole));
       viewUserBtn.setEnabled(signedUser.getRole().equals(adminRole));

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
                intent.putExtra("signedUser" , signedUser);
                startActivity(intent);// should take to create account
            }
        });
        //take to view course
        viewCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomePage.this, ViewCourseList.class);
                intent.putExtra("signedUser" , signedUser);
                startActivity(intent);// should take to create account
            }
        });


        viewUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomePage.this, ViewAccountList.class);
                intent.putExtra("signedUser" , signedUser);
                startActivity(intent);// should take to create account
            }
        });
    }

}