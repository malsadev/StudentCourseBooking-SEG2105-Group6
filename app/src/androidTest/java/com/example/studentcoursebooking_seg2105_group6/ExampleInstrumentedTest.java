package com.example.studentcoursebooking_seg2105_group6;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.studentcoursebooking_seg2105_group6.controllers.CourseController;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.studentcoursebooking_seg2105_group6", appContext.getPackageName());
    }
    @Test
    public void onCreate() {

        System.out.println("Test");
        System.out.println("please work");
        assertEquals("test", "test");
    }

    @Test
    public void testCreateCourse() {
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().getTargetContext());
        CourseController controller = new CourseController();
        Course course = new Course("testCourseName", "testCourseCode", "");
        controller.createCourseTest(course);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("coursesTest")
                .whereEqualTo("courseName", "testCourseName")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                assertEquals(true , true);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            assertEquals(true, false);
                        }
                    }
                });
    }

    @Test
    public void testFindCourse() {
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().getTargetContext());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("coursesTest")
                .whereEqualTo("courseName", "testCourseName")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                assertEquals("testCourseName", document.get("courseName"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            assertEquals(true, false);
                        }
                    }
                });
    }

    @Test
    public void testDeleteCourse() {
        FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().getTargetContext());
        CourseController controller = new CourseController();
        Course course = new Course("testCourseName", "testCourseCode", "");
        controller.deleteCourseTest(course);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("coursesTest")
                .whereEqualTo("courseName", "testCourseName")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                assertNull(document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            assertEquals(true, false);
                        }
                    }
                });
    }
}