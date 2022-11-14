package com.example.studentcoursebooking_seg2105_group6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentcoursebooking_seg2105_group6.adapters.CourseAdapter;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class searchCourse extends AppCompatActivity {
    private String[] choiceArray = {"Name", "Code"};
    private String choice;
    private EditText searchEdit;
    FirebaseFirestore db;
    ListView courseListView;
    User signedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        Intent i = getIntent();
        db = FirebaseFirestore.getInstance();
        signedUser = (User) i.getSerializableExtra("signedUser");
        Spinner spinner = findViewById(R.id.searchSpinner);
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, choiceArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        searchEdit=findViewById(R.id.searchEditTxt);
        Button searchCourseBackBtn = findViewById(R.id.searchCourseBackBtn);
        Button searchCourseBtn = findViewById(R.id.searchBtn);
        courseListView = findViewById(R.id.idLVCourses2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice = adapterView.getSelectedItem().toString();
                if (choice=="Name"){
                    searchEdit.setHint("Ex: Intro to Software Engineering");
                }else{
                    searchEdit.setHint("Ex: SEG 105");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        searchCourseBackBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(searchCourse.this, ViewCourseList.class);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);// should take to view course list
            }
        });

        searchCourseBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {//below: tries to get objects in database in the collections path
                loadDatainListview();
            }
        });
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
                            ArrayList<Course> courseArrayList = new ArrayList<>();
                            String editText = searchEdit.getText().toString();
                            boolean flag = false;
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                Course course = d.toObject(Course.class);
                                String courseName = course.getCourseName();
                                String courseCode = course.getCourseCode();
                                if (choice == choiceArray[0] && courseName.equals(editText)){
                                    courseArrayList.add(course);
                                } else if (choice == choiceArray[1] && courseCode.equals(editText)) {
                                    courseArrayList.add(course);
                                }
                            }
                            Log.d("", courseArrayList.toString());
                            // after that we are passing our array list to our adapter class.
                            signedUser = (User) getIntent().getSerializableExtra("signedUser");
                            CourseAdapter adapter = new CourseAdapter(searchCourse.this, courseArrayList, signedUser);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            courseListView.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(searchCourse.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(searchCourse.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}