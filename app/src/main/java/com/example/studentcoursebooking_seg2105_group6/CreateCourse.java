package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentcoursebooking_seg2105_group6.controllers.CourseController;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;

public class CreateCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        EditText courseName = findViewById(R.id.courseName);
        EditText courseCode = findViewById(R.id.courseCode);
        Button createCourseBackBtn=(Button)findViewById(R.id.createCourseBackBtn);
        Button createdCourseBtn=(Button)findViewById(R.id.createdCourseBtn);

        User signedUser = (User) getIntent().getSerializableExtra("signedUser");
        CourseController controller = new CourseController();

        //take back to welcome page after course creation
        createdCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course course = new Course(courseName.getText().toString(), courseCode.getText().toString());
                controller.createCourse(course);
                Toast.makeText(CreateCourse.this, "Course created successfully", Toast.LENGTH_LONG).show();

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