package com.example.studentcoursebooking_seg2105_group6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studentcoursebooking_seg2105_group6.adapters.AccountAdapter;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewEnrolledStudents extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_enrolled_students);

        Intent i = getIntent();
        User signedUser = (User)(i.getSerializableExtra("signedUser"));
        Course thisCourse = (Course) (i.getSerializableExtra("course"));
        ListView courseListView = findViewById(R.id.studentList);
        Button backButton = findViewById(R.id.viewStudentsBackBtn);

        backButton.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEnrolledStudents.this, ViewCourseDetail.class);
                intent.putExtra("course", thisCourse);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);// should take to create account
            }
        });

        //loading listView
        AccountAdapter adapter = new AccountAdapter(ViewEnrolledStudents.this, thisCourse.getEnrolledStudents(), signedUser);
        courseListView.setAdapter(adapter);

    }


}