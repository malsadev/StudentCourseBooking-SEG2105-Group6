package com.example.studentcoursebooking_seg2105_group6.controllers;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.Map;

public class CourseController {
    private static FirebaseFirestore db;
    public CourseController(){db=FirebaseFirestore.getInstance();}

    public void createCourse(Course course){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> courseData = mapper.convertValue(course, Map.class);

        db.collection("courses")
                .add(courseData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    public void updateCourse(Course ogCourse, Course newCourse ){
        db.collection("courses")
                .whereEqualTo("courseCode", ogCourse.getCourseCode())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                db.collection("courses").document(document.getId())
                                        .update(
                                                "courseCode", newCourse.getCourseCode(),
                                                "courseName", newCourse.getCourseName(),
                                                "courseDescription", newCourse.getCourseDescription()
                                                )

                                        .addOnSuccessListener((doc) -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                                        .addOnFailureListener((e) -> Log.w(TAG, "Error updating document", e));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void deleteCourse(Course course) {
        db.collection("courses")
                .whereEqualTo("courseCode", course.getCourseCode())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                db.collection("courses").document(document.getId())
                                        .delete()
                                        .addOnSuccessListener((doc) -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                                        .addOnFailureListener((e) -> Log.w(TAG, "Error deleting document", e));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
