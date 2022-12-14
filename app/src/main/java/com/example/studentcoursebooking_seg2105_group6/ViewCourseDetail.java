package com.example.studentcoursebooking_seg2105_group6;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentcoursebooking_seg2105_group6.adapters.AccountAdapter;
import com.example.studentcoursebooking_seg2105_group6.adapters.CourseAdapter;
import com.example.studentcoursebooking_seg2105_group6.controllers.AccountController;
import com.example.studentcoursebooking_seg2105_group6.controllers.CourseController;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.Instructor;
import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewCourseDetail extends AppCompatActivity {

    private static String instructorRole = "instructor";
    private static String adminRole = "admin";
    private static String studentRole = "student";
    private static CourseController courseController = new CourseController();
    private static AccountController accountController = new AccountController();
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_detail);

        //gets course data based on what was selected from the course list view
        Intent i = getIntent();
        Course thisCourse = (Course)(i.getSerializableExtra("course"));
        System.out.println(thisCourse.getCourseInstructor());
        final User signedUser = (User) i.getSerializableExtra("signedUser");

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
        Button viewStudentList = findViewById(R.id.viewStudentList);


        if (signedUser.getRole().equals(studentRole)){
            boolean enrolled = false;
            for (int j=0; j < thisCourse.getEnrolledStudents().size(); j++){
                if (thisCourse.getEnrolledStudents().get(j).getUsername().equals(signedUser.getUsername())){
                    enrolled=true;
                    break;
                }
            }
            if (enrolled){
                unEnrollCourse.setVisibility(View.VISIBLE);
            }else{
                enrollCourse.setVisibility(View.VISIBLE);
            }
        }else if (signedUser.getRole().equals(instructorRole)){
            if (thisCourse.getCourseInstructor().equals(signedUser.getUsername())){
                unteachCourseBtn.setVisibility(View.VISIBLE);
                editCourseBtn.setVisibility(View.VISIBLE);
                viewStudentList.setVisibility(View.VISIBLE);
            }else if (thisCourse.getCourseInstructor().equalsIgnoreCase("none")){
                teachCourseBtn.setVisibility(View.VISIBLE);
            }
        }else{
            editCourseBtn.setVisibility(View.VISIBLE);
            viewStudentList.setVisibility(View.VISIBLE);
        }

        //changes text to text corresponding to course details, taken from intent i
        courseTitle.setText(thisCourse.getCourseName());
        courseCode.setText(thisCourse.getCourseCode());
        courseDesc.setText(thisCourse.getCourseDescription());
        courseInstructorTxt.setText(thisCourse.getCourseInstructor());
        courseCapacityTxt.setText(thisCourse.getCourseCapacity());
        if (thisCourse.getCourseSchedule().isEmpty()){
            courseScheduleTxt.setText("Day: None"+"\nTime: None");
        }else {
            courseScheduleTxt.setText("Day: " + thisCourse.getCourseSchedule().get(thisCourse.getCourseSchedule().size() - 1).getDay() + "\nTime: " + thisCourse.getCourseSchedule().get(thisCourse.getCourseSchedule().size() - 1).getTime());
        }

        editCourseBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourseDetail.this, EditCourse.class);
                intent.putExtra("course", thisCourse);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);// should take to edit course
            }
        });

        viewStudentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourseDetail.this, ViewEnrolledStudents.class);
                intent.putExtra("course", thisCourse).putExtra("signedUser", signedUser);
                startActivity(intent);
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
                System.out.println("in listener");
                Intent intent = new Intent(ViewCourseDetail.this, WelcomePage.class);
                courseController.addInstructorCourse(thisCourse, signedUser); //adds user to course
                accountController.addStudentToCourse(signedUser, thisCourse); //adds course to user acc
                intent.putExtra("signedUser", signedUser);
                System.out.println("activity start");
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
                boolean flag = true;
                if (!(signedUser.getCourses().isEmpty() && thisCourse.getCourseSchedule().isEmpty())) {

                    for (int i = 0; i < signedUser.getCourses().size(); ++i) {
                        System.out.println(signedUser.getCourses().size());
                        String day2 = signedUser.getCourses().get(i).getCourseSchedule().get(0).getDay();
                        String time2 = signedUser.getCourses().get(i).getCourseSchedule().get(0).getTime();
                        for (int j = 0; j < signedUser.getCourses().get(i).getCourseSchedule().size(); ++j) {

                            String day = thisCourse.getCourseSchedule().get(j).getDay();
                            String time = thisCourse.getCourseSchedule().get(j).getTime();
                            System.out.println("test1 "+day+" "+time);
                            System.out.println("test2 "+day2+" "+time2);
                            if (day2.equals(day) && time2.equals(time)) {
                                AlertDialog alert = createEnrolErrorDialog();
                                alert.show();
                                flag = false;
                            }
                        }
                    }
                    if (flag) {
                        Intent intent = new Intent(ViewCourseDetail.this, WelcomePage.class);
                        signedUser.addCourse(thisCourse);
                        accountController.addStudentToCourse(signedUser, thisCourse);
                        courseController.addStudent(thisCourse, signedUser);
                        intent.putExtra("signedUser", signedUser);
                        startActivity(intent);
                        //create arraylist of all courses enrolled in
                    }
                }
            }
        });

        unEnrollCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourseDetail.this, WelcomePage.class);
                accountController.removeStudentFromCourse(thisCourse, signedUser);
                courseController.removeStudent(thisCourse, signedUser);
                signedUser.removeCourse(thisCourse);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);
            }
        });

    }

    private AlertDialog createEnrolErrorDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewCourseDetail.this);
        builder1.setMessage("Schedule Conflict: Unable to enrol in course.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder1.create();

        return alert;
    }

}