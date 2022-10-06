package com.example.studentcoursebooking_seg2105_group6;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.studentcoursebooking_seg2105_group6.models.User;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMesssageView);
        User signedUser = (User) getIntent().getSerializableExtra("signedUser");
        welcomeMessage.setText("Welcome " + signedUser.getRole() + " " + signedUser.getUsername());

    }
}