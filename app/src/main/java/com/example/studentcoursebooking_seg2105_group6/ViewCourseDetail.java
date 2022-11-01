package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;

public class ViewCourseDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_detail);

        //gets course data based on what was selected from the course list view
        Intent i = getIntent();
        Course thisCourse = (Course)(i.getSerializableExtra("course"));
        User signedUser = (User) i.getSerializableExtra("signedUser");

        TextView courseTitle = findViewById(R.id.changeCourseNameTxt);
        TextView courseCode = findViewById(R.id.changeCourseCodeTxt);
        TextView courseDesc = findViewById(R.id.changeCourseDescriptionTxt);
        Button courseDetailBackBtn= findViewById(R.id.courseDetailBackBtn);
        Button editCourseBtn = findViewById(R.id.editCourse);

        //changes text to text corresponding to course details, taken from intent i
        courseTitle.setText(thisCourse.getCourseName());
        courseCode.setText(thisCourse.getCourseCode());
        courseDesc.setText(thisCourse.getCourseDescription());

        editCourseBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourseDetail.this, EditCourse.class);
                intent.putExtra("course", thisCourse);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);// should take to edit course
            }
        });

        courseDetailBackBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourseDetail.this, ViewCourseList.class);
                intent.putExtra("course", thisCourse);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);// should take to create account
            }
        });



    }


}