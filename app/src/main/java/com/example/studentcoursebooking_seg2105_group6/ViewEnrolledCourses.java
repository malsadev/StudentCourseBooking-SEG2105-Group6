package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.studentcoursebooking_seg2105_group6.adapters.CourseAdapter;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewEnrolledCourses extends AppCompatActivity {

    private ArrayList<Course> courseArrayList;
    private User signedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();


        setContentView(R.layout.activity_view_enrolled_courses);

        Button backButton = findViewById(R.id.enrolledCoursesBackBtn);
        ListView courseListView = findViewById(R.id.coursesLV);

        signedUser = (User) (i.getSerializableExtra("signedUser"));
        courseArrayList=signedUser.getCourses();
        CourseAdapter adapter = new CourseAdapter(ViewEnrolledCourses.this, courseArrayList, signedUser);
        courseListView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEnrolledCourses.this, WelcomePage.class);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);
            }
        });

    }

}