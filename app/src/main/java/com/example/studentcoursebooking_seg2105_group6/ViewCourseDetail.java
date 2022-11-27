package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentcoursebooking_seg2105_group6.adapters.AccountAdapter;
import com.example.studentcoursebooking_seg2105_group6.controllers.AccountController;
import com.example.studentcoursebooking_seg2105_group6.controllers.CourseController;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.Instructor;
import com.example.studentcoursebooking_seg2105_group6.models.User;

import org.checkerframework.checker.units.qual.A;

public class ViewCourseDetail extends AppCompatActivity {

    private static String instructorRole = "instructor";
    private static String adminRole = "admin";
    private static String studentRole = "student";
    private static CourseController courseController = new CourseController();
    private static AccountController accountController = new AccountController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_detail);

        //gets course data based on what was selected from the course list view
        Intent i = getIntent();
        Course thisCourse = (Course)(i.getSerializableExtra("course"));
        System.out.println(thisCourse.getCourseInstructor());
        User signedUser = (User) i.getSerializableExtra("signedUser");

        TextView courseTitle = findViewById(R.id.changeCourseNameTxt);
        TextView courseCode = findViewById(R.id.changeCourseCodeTxt);
        TextView courseDesc = findViewById(R.id.changeCourseDescriptionTxt);
        TextView courseInstructorTxt = findViewById(R.id.instructorTxt);
        TextView courseCapacityTxt = findViewById(R.id.capacityTxt);
        TextView courseScheduleTxt = findViewById(R.id.scheduleDetailsTxt);

        Button courseDetailBackBtn= findViewById(R.id.courseDetailBackBtn);
        Button editCourseBtn = findViewById(R.id.editCourse);
        Button teachCourseBtn = findViewById(R.id.teachCourse);
        Button unteachCourseBtn = findViewById(R.id.unteachCourse);
        Button enrollCourse = findViewById(R.id.enrollCourse);
        Button unEnrollCourse = findViewById(R.id.unEnrollCourse);

        teachCourseBtn.setEnabled(signedUser.getRole().equals(instructorRole)
                && thisCourse.getCourseInstructor().equals("None")
        );

        editCourseBtn.setEnabled(thisCourse.getCourseInstructor().equals(signedUser.getUsername())
                || signedUser.getRole().equals(adminRole));

        unteachCourseBtn.setEnabled(editCourseBtn.isEnabled() && !teachCourseBtn.isEnabled() && !signedUser.getRole().equals(adminRole));

        enrollCourse.setEnabled(signedUser.getRole().equals(studentRole));

        unEnrollCourse.setEnabled(signedUser.getRole().equals(studentRole));

        //changes text to text corresponding to course details, taken from intent i
        courseTitle.setText(thisCourse.getCourseName());
        courseCode.setText(thisCourse.getCourseCode());
        courseDesc.setText(thisCourse.getCourseDescription());
        courseInstructorTxt.setText(thisCourse.getCourseInstructor());
        courseCapacityTxt.setText(thisCourse.getCourseCapacity());
        courseScheduleTxt.setText("Day: " +thisCourse.getCourseSchedule().get(thisCourse.getCourseSchedule().size()-1).getDay() + "\nTime: " + thisCourse.getCourseSchedule().get(thisCourse.getCourseSchedule().size()-1).getTime());


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

        teachCourseBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                System.out.println("in listerner");
                Intent intent = new Intent(ViewCourseDetail.this, WelcomePage.class);
                courseController.addInstructorCourse(thisCourse, signedUser);
                intent.putExtra("signedUser", signedUser);
                System.out.println("acitvity strta");
                startActivity(intent);// should take to create account
            }
        });

        unteachCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourseDetail.this, WelcomePage.class);
                courseController.removeInstructorFromCourse(thisCourse);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);// should take to create accountvladi
            }
        });

        enrollCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourseDetail.this, WelcomePage.class);

                accountController.addStudentToCourse(thisCourse, signedUser);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);
            }
        });




    }


}