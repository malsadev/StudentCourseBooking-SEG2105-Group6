package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class createAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        EditText userType = (EditText)  findViewById(R.id.userType);
        EditText createName = (EditText)findViewById(R.id.createName);
        EditText createUser = (EditText)  findViewById(R.id.createUser);
        EditText createPw = (EditText)findViewById(R.id.createPw);
        Button createBtn = (Button)findViewById(R.id.createBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(createAccount.this,MainActivity.class);
                startActivity(intent);// should take to main sign in page
            }
        });
    }
}