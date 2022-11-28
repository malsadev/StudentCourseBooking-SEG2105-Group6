package com.example.studentcoursebooking_seg2105_group6.controllers;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AccountController {
    private static FirebaseFirestore db;
    public AccountController(){db=FirebaseFirestore.getInstance();}


    public void deleteUser(User user) {
        db.collection("users")
                .whereEqualTo("username", user.getUsername())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                db.collection("users").document(document.getId())
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

    public void addStudentToCourse(Course course, User user) {
        db.collection("users")
                .whereEqualTo("username", user.getUsername())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                db.collection("users").document(document.getId())
                                        .update(
                                                "courses", FieldValue.arrayUnion(course)
                                        )
                                        .addOnSuccessListener((doc) -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                                        .addOnFailureListener((e) -> Log.w(TAG, "Error deleting document", e));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    //unEnroll from a course
    public void removeStudentFromCourse(Course course, User user) {
        db.collection("users").whereEqualTo("username", user.getUsername())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                db.collection("users").document(document.getId()).
                                        update("courses", FieldValue.arrayUnion(course).delete())
                                        .addOnSuccessListener((doc) -> Log.d(TAG, "Unenrolled from Course"))
                                        .addOnFailureListener((e) -> Log.w(TAG, "Error updating document", e));
                                //update((Map<String, Object> unenroll = new HashMap<>());
                                // unenroll.put("course.courseCode", FieldValue.delete());
                            }
                        } else{
                            Log.d(TAG, "Error getting documents", task.getException());
                        }
                    }
                });
    }


    //List of all enrolled courses
    public ArrayList<String> enrolledCoursesList(Course course, User user){
        ArrayList<String> coursesList = new ArrayList<>();
        db.collection("users")
                .whereEqualTo("courses", course.getCourseCode())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           coursesList.add(course.getCourseCode());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return coursesList;
    }

}
