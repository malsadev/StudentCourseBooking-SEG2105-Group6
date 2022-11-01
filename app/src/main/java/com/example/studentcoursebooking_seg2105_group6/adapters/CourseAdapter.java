package com.example.studentcoursebooking_seg2105_group6.adapters;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentcoursebooking_seg2105_group6.R;
import com.example.studentcoursebooking_seg2105_group6.ViewCourseDetail;
import com.example.studentcoursebooking_seg2105_group6.WelcomePage;
import com.example.studentcoursebooking_seg2105_group6.controllers.CourseController;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;

import java.util.ArrayList;

public class CourseAdapter extends ArrayAdapter<Course> {

    CourseController courseController;
    User signedUser;


    // constructor for our list view adapter.
    public CourseAdapter(@NonNull Context context, ArrayList<Course> courseArrayList, User signedUser) {
        super(context, 0, courseArrayList);
        this.courseController = new CourseController();
        this.signedUser = signedUser;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.course_lv_item, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        Course course = getItem(position);

        // initializing our UI components of list view item.
        TextView courseCodeText = listitemView.findViewById(R.id.idTVtext);
        TextView courseNameText = listitemView.findViewById(R.id.idIVcourseName);
        TextView changeCourseNameTxt = listitemView.findViewById(R.id.changeCourseNameTxt);
        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        courseNameText.setText(course.getCourseName());

        // in below line we are using Picasso to
        // load image from URL in our Image VIew.
       courseCodeText.setText(course.getCourseCode());

        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                //Toast.makeText(getContext(), "Course deleted is : " + course.getCourseName(), Toast.LENGTH_SHORT).show();
                //courseController.deleteCourse(course);
                Intent intent = new Intent(getContext(), ViewCourseDetail.class);
                //transfering user data and course data when going to view a specific course
                intent.putExtra("signedUser" , signedUser);
                intent.putExtra("courseDetails", course);
                getContext().startActivity(intent);
            }
        });
        return listitemView;
    }

}
