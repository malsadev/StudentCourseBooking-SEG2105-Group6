package com.example.studentcoursebooking_seg2105_group6.controllers;

import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.Date;
import com.example.studentcoursebooking_seg2105_group6.models.Instructor;
import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.Arrays;
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

    public void updateCourse(Course ogCourse, Course newCourse){
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
                                                "courseDescription", newCourse.getCourseDescription(),
                                                "courseSchedule", newCourse.getCourseSchedule(),"courseCapacity",newCourse.getCourseCapacity())

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
                                System.out.println("found course" + document.getId());
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

    public void addStudent(Course course, User user) {
        System.out.println("in method");
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
                                        .update("enrolledStudents", FieldValue.arrayUnion(user));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void removeStudent(Course course, User user) {
        db.collection("courses").whereEqualTo("courseCode", course.getCourseCode())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                db.collection("courses").document(document.getId()).
                                        update("enrolledStudents", FieldValue.arrayUnion(user).delete())
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


    public void addInstructorCourse(Course course, User instructor) {
        System.out.println("in method");
        db.collection("courses")
                .whereEqualTo("courseCode", course.getCourseCode())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println("found course" + document.getId());
                                db.collection("courses").document(document.getId())
                                        .update("courseInstructor" , instructor.getUsername())
                                        .addOnSuccessListener((doc) -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                                        .addOnFailureListener((e) -> Log.w(TAG, "Error deleting document", e));

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void removeInstructorFromCourse(Course course){
        db.collection("courses")
                .whereEqualTo("courseCode", course.getCourseCode())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println("found course" + document.getId());
                                db.collection("courses").document(document.getId())
                                        .update("courseInstructor" , "None")
                                        .addOnSuccessListener((doc) -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                                        .addOnFailureListener((e) -> Log.w(TAG, "Error deleting document", e));
                                db.collection("courses").document(document.getId())
                                        .update("courseDescription" , "None")
                                        .addOnSuccessListener((doc) -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                                        .addOnFailureListener((e) -> Log.w(TAG, "Error deleting document", e));
                                db.collection("courses").document(document.getId())
                                        .update("courseCapacity" , "None")
                                        .addOnSuccessListener((doc) -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                                        .addOnFailureListener((e) -> Log.w(TAG, "Error deleting document", e));
                                db.collection("courses").document(document.getId())
                                        .update("courseSchedule" , Arrays.asList(new Date("None", "None")))
                                        .addOnSuccessListener((doc) -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                                        .addOnFailureListener((e) -> Log.w(TAG, "Error deleting document", e));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void createCourseTest(Course course){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> courseData = mapper.convertValue(course, Map.class);

        db.collection("coursesTest")
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

    public void deleteCourseTest(Course course) {

        db.collection("coursesTest")
                .whereEqualTo("courseCode", course.getCourseCode())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println("found course" + document.getId());
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


    public void addStudentToCourse(Course thisCourse, User signedUser) {

    }
}
