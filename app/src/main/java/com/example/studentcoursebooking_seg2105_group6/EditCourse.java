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
import com.google.firebase.firestore.DocumentReference;

public class EditCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        Intent i = getIntent();

        //get course from course view activity
        Course ogCourse = (Course)i.getSerializableExtra("course");
        User signedUser = (User) i.getSerializableExtra("signedUser");

        EditText courseName = findViewById(R.id.courseNameEdit);
        EditText courseCode = findViewById(R.id.courseCodeEdit);
        EditText courseDesc = findViewById(R.id.courseDescEdit);
        Button saveChangesBtn = findViewById(R.id.saveChangesBtn);
        Button viewCourseBackBtn = findViewById(R.id.ViewCourseBackBtn);
        Button deleteCourseBtn = findViewById(R.id.deleteBtn);
        CourseController controller = new CourseController();

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                //creates new course object with entered details
                Course newCourse = new Course(courseName.getText().toString(), courseCode.getText().toString());
                newCourse.setCourseDescription(courseDesc.getText().toString());

                //updates original course by replacing it with edited course
                controller.updateCourse(ogCourse, newCourse);
                Toast.makeText(EditCourse.this, "Course Updated Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditCourse.this, ViewCourseDetail.class);
                intent.putExtra("signedUser", signedUser);
                intent.putExtra("course", newCourse);
                startActivity(intent);// should take to view course details
            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.deleteCourse(ogCourse);
                Toast.makeText(EditCourse.this, "Course Successfully Deleted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditCourse.this, WelcomePage.class);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);// should take to view course details
            }
        });

        viewCourseBackBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditCourse.this, ViewCourseDetail.class);
                intent.putExtra("signedUser", signedUser);
                intent.putExtra("course", ogCourse);
                startActivity(intent);// should take to view course details
            }
        });
    }

}