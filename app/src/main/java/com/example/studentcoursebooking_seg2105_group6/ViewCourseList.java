package com.example.studentcoursebooking_seg2105_group6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studentcoursebooking_seg2105_group6.adapters.CourseAdapter;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewCourseList extends AppCompatActivity {

    ListView coursesLV;
    ArrayList<Course> courseArrayList;
    FirebaseFirestore db;
    User signedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist_view);



        coursesLV = findViewById(R.id.idLVCourses);
        courseArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // here we are calling a method
        // to load data in our list view.
        loadDatainListview();


//        Button viewCourseBackBtn=(Button)findViewById(R.id.viewCourseBackBtn);
//
//        viewCourseBackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ViewCourse.this, WelcomePage.class);
//                startActivity(intent);// should take to Welcome account
//            }
//        });
    }

    private void loadDatainListview() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("courses").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                Course course = d.toObject(Course.class);

                                // after getting data from Firebase we are
                                // storing that data in our array list
                                courseArrayList.add(course);
                            }
                            Log.d("", courseArrayList.toString());
                            // after that we are passing our array list to our adapter class.
                            signedUser = (User) getIntent().getSerializableExtra("signedUser");
                            CourseAdapter adapter = new CourseAdapter(ViewCourseList.this, courseArrayList, signedUser);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            coursesLV.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(ViewCourseList.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(ViewCourseList.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
