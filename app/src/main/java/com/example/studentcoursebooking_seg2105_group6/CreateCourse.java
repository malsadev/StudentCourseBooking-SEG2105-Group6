package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentcoursebooking_seg2105_group6.controllers.CourseController;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;

public class CreateCourse extends AppCompatActivity {
    private boolean nameFlag, codeFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        EditText courseName = findViewById(R.id.courseName);
        EditText courseCode = findViewById(R.id.courseCode);
        Button createCourseBackBtn=(Button)findViewById(R.id.createCourseBackBtn);
        Button createdCourseBtn=(Button)findViewById(R.id.createdCourseBtn);
        String courseCapacity="";

        courseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameFlag=false;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("")){
                    courseName.setError("This field cannot be blank");
                    nameFlag=false;
                }else{
                    nameFlag=true;
                }
            }
        });

        courseCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                codeFlag=false;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("")){
                    courseCode.setError("This field cannot be blank");
                    codeFlag=false;
                }else{
                    codeFlag=true;
                }
            }
        });

        User signedUser = (User) getIntent().getSerializableExtra("signedUser");
        CourseController controller = new CourseController();

        //take back to welcome page after course creation
        createdCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(codeFlag && nameFlag) {
                    Course course = new Course(courseName.getText().toString(), courseCode.getText().toString(), courseCapacity);
                    controller.createCourse(course);
                    Toast.makeText(CreateCourse.this, "Course created successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(CreateCourse.this, WelcomePage.class);
                    intent.putExtra("signedUser", signedUser);
                    startActivity(intent);// should take to Main account
                }else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateCourse.this);
                    builder1.setMessage("Failed to Create Course.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
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