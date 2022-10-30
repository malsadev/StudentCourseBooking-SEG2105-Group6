package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewCourseDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_detail);
        Button courseDetailBackBtn=(Button) findViewById(R.id.courseDetailBackBtn);
        courseDetailBackBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewCourseDetail.this,ViewCourseList.class);
                startActivity(intent);// should take to create account

            }
        });
    }


}