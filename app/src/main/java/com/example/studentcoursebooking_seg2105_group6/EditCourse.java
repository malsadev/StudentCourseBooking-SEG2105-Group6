package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentcoursebooking_seg2105_group6.controllers.CourseController;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.Date;
import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Collections;

public class EditCourse extends AppCompatActivity  {
    String[] days={"Monday", "Tuesday", "Wednesday","Thursday","Friday"};
    String[] times={"8:30AM-9:50AM", "10:00AM-11:20AM", "11:30AM-12:50PM","1:00PM- 2:20OM", "2:30PM-3:50AM","4:00PM-5:20PM","5:30PM-6:50PM","7:30PM-9:50PM"};
    Spinner dayOneSpinner;
    Spinner timeOneSpinner;
    Date dateChosen=new Date("","");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        Intent i = getIntent();

        //get course from course view activity
        Course ogCourse = (Course) i.getSerializableExtra("course");
        User signedUser = (User) i.getSerializableExtra("signedUser");

        EditText courseName = findViewById(R.id.courseNameEdit);
        EditText courseCode = findViewById(R.id.courseCodeEdit);
        EditText courseDesc = findViewById(R.id.courseDescEdit);
        Button saveChangesBtn = findViewById(R.id.saveChangesBtn);
        Button viewCourseBackBtn = findViewById(R.id.ViewCourseBackBtn);
        Button deleteCourseBtn = findViewById(R.id.deleteBtn);
        CourseController controller = new CourseController();
        dayOneSpinner=findViewById(R.id.dayOneSpinner);
        ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item, days);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOneSpinner.setAdapter(aa);
        dayOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String dayChosen = dayOneSpinner.getSelectedItem().toString();
                dateChosen.setDay(dayChosen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        timeOneSpinner=findViewById(R.id.timeOneSpinner);
        ArrayAdapter ab=new ArrayAdapter(this, android.R.layout.simple_spinner_item, times);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeOneSpinner.setAdapter(ab);
        timeOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String timeChosen = timeOneSpinner.getSelectedItem().toString();
                dateChosen.setTime(timeChosen);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Auto-fill textboxes since other users cant edit some boxes
        courseName.setText(ogCourse.getCourseName());
        courseCode.setText(ogCourse.getCourseCode());
        courseDesc.setText(ogCourse.getCourseDescription());


        //DISABLE FOR DIFFERENT USERS
        //disable if signed user ==false
        courseDesc.setFocusable(!(signedUser.getRole().equals("admin"))); //disable description
        //disable for instructor
        courseName.setFocusable(!(signedUser.getRole().equals("instructor")));//disable name
        courseCode.setFocusable(!(signedUser.getRole().equals("instructor")));//disable code

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                //creates new course object with entered details
                Course newCourse = new Course(courseName.getText().toString(), courseCode.getText().toString());
                newCourse.setCourseDescription(courseDesc.getText().toString());

                //updates original course by replacing it with edited course
                newCourse.addDate(dateChosen);
                controller.updateCourse(ogCourse, newCourse);
                Toast.makeText(EditCourse.this, "Course Updated Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditCourse.this, ViewCourseDetail.class);
                intent.putExtra("signedUser", signedUser);
                intent.putExtra("course", newCourse);
                startActivity(intent);// should take to view course details
            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.deleteCourse(ogCourse);
                Toast.makeText(EditCourse.this, "Course Successfully Deleted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditCourse.this, WelcomePage.class);
                intent.putExtra("signedUser", signedUser);
                startActivity(intent);// should take to view course details
            }
        });

        viewCourseBackBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditCourse.this, ViewCourseDetail.class);
                intent.putExtra("signedUser", signedUser);
                intent.putExtra("course", ogCourse);
                startActivity(intent);// should take to view course details
            }
        });
    }

}