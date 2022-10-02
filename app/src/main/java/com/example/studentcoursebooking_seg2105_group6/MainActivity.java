package com.example.studentcoursebooking_seg2105_group6;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //function find id of button on screen
        // R represents list of all ressources that exists in application
        // sub class id, find id associated with button 1
        EditText username = (EditText)  findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        Button createActBtn=(Button)findViewById(R.id.createActBtn);
        //when button clicked
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if login correct
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin123")){
                    //take to new welcome page
                    Intent intent=new Intent(MainActivity.this,welcomePage.class);
                    startActivity(intent);// should take to welcome page
                }//will create else if, if login credentials dont match
            }
        });
        //when button clicked
        createActBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                    //take to new create account page
                    Intent intent=new Intent(MainActivity.this,createAccount.class);
                    startActivity(intent);// should take to create account

            }
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // check db write
        Map<String, Object> user = new HashMap<>();
        user.put("user", "mustafa");
        user.put("password", "1234");
        user.put("role", "student");

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
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


        //check db read
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}