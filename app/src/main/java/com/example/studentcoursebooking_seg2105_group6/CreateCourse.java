package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.studentcoursebooking_seg2105_group6.models.User;

public class CreateCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        Button createCourseBackBtn=(Button)findViewById(R.id.createCourseBackBtn);
        Button createdCourseBtn=(Button)findViewById(R.id.createdCourseBtn);

        User signedUser = (User) getIntent().getSerializableExtra("signedUser");

        //take back to welcome page after course creation
        createdCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateCourse.this, WelcomePage.class);
                intent.putExtra("signedUser" , signedUser);
                startActivity(intent);// should take to Main account
            }
        });

        createCourseBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateCourse.this, WelcomePage.class);
                intent.putExtra("signedUser" , signedUser);
                startActivity(intent);// should take to Welcome account
            }
        });
    }
}