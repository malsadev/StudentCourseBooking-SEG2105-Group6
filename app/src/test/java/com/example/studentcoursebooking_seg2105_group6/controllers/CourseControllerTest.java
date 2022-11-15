package com.example.studentcoursebooking_seg2105_group6.controllers;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studentcoursebooking_seg2105_group6.MainActivity;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CourseControllerTest extends TestCase {


//    @BeforeClass
//    public static void setUpRxSchedulers() {
//        Scheduler immediate = new Scheduler() {
//            @Override
//            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
//                // this prevents StackOverflowErrors when scheduling with a delay
//                return super.scheduleDirect(run, 0, unit);
//            }
//
//            @Override
//            public Worker createWorker() {
//                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
//            }
//        };
//
//        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
//        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
//        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
//        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
//    }


//    @Before
//    public void setup() {
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
//    }




    @Test
    public void testCreateCourse() {
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
                                assertEquals("testCourseName" , document.get("courseName"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Test
    public void testUpdateCourse() {
    }

    @Test
    public void testDeleteCourse() {
    }

    @Test
    public void testRemoveInstructorFromCourse() {
    }
}