package com.example.studentcoursebooking_seg2105_group6.controllers.security;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AuthController {

    private static FirebaseFirestore db;

    public AuthController() {
        this.db = FirebaseFirestore.getInstance();
    }


    public Query login(User user) {
        return db.collection("users")
                .whereEqualTo("username", user.getUsername())
                .whereEqualTo("password" , user.getPassword());

    }

    public void signUp(User user) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> userDoc = mapper.convertValue(user, Map.class);

        // Add a new user to db
        db.collection("users")
                .add(userDoc)
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
}
