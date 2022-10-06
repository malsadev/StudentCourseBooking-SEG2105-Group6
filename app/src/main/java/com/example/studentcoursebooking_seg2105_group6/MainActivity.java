package com.example.studentcoursebooking_seg2105_group6;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.example.studentcoursebooking_seg2105_group6.security.AuthController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


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

        AuthController authController = new AuthController();

        //when button clicked
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User loginUser =new User(username.getText().toString(), password.getText().toString());
                Query loginQuery = authController.login(loginUser);

                loginQuery.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().isEmpty()) { //credentials now found in db
                                        username.setText("");
                                        password.setText("");
                                        AlertDialog alert = createLoginErrorDialog();
                                        alert.show();
                                    }
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Intent intent = new Intent(MainActivity.this, WelcomePage.class);
                                        intent.putExtra("signedUser" , document.toObject(User.class));
                                        startActivity(intent);
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                }
        });


        //when button clicked
        createActBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                    //take to new create account page
                    Intent intent=new Intent(MainActivity.this,CreateAccount.class);
                    startActivity(intent);// should take to create account

            }
        });


    }

    private AlertDialog createLoginErrorDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage("Please enter valid credentials");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder1.create();

        return alert;
    }
}