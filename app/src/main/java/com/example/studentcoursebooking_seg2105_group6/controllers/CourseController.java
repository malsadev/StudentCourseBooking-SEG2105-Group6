package com.example.studentcoursebooking_seg2105_group6.controllers;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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


//    delete a course controller...Myrtille
    public void deleteCourse(Course course){
        db.collection("courses").document("courseCode")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Success Delete Course", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Error deleting document", e);
                    }
                });
    }

    //Method for access the course code
    public String CourseCode(){
        Course courseToDelete = new Course();
        String courseCode = courseToDelete.getCourseCode();
        return courseCode;
    }

    //I found this online, and I think it's the right code to use - Myrtille
//    import { doc, updateDoc, deleteField } from "firebase/firestore";
//
//const userRef = doc(db, "users/dWE72sOcV1CRuA0ngRt5");
//
//    // Remove "age" and "sex" fields from the document
//    await updateDoc(userRef, {
//        "age": deleteField(),
//                "sex": deleteField()
//    });


}
