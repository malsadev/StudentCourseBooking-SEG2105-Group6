package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.example.studentcoursebooking_seg2105_group6.controllers.security.AuthController;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        EditText userType = (EditText)  findViewById(R.id.userType);
        EditText createName = (EditText)findViewById(R.id.createName);
        EditText createUser = (EditText)  findViewById(R.id.createUser);
        EditText createPw = (EditText)findViewById(R.id.createPw);
        Button createBtn = (Button)findViewById(R.id.createBtn);
        Button createBackBtn=(Button) findViewById(R.id.createBackBtn);


        AuthController authController = new AuthController();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreateAccount.this,MainActivity.class); //back btrn

                User userRegistration = new User(createUser.getText().toString(), createPw.getText().toString(), userType.getText().toString(), createName.getText().toString());
                authController.signUp(userRegistration);

                //dialog construction and show
                AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateAccount.this);
                builder1.setMessage("Successfully registered user.");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();

                startActivity(intent);// should take to main sign in page
            }
        });

        createBackBtn.setOnClickListener(new View.OnClickListener() {//when button clicked
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreateAccount.this,MainActivity.class);
                startActivity(intent);// should take to create account

            }
        });
    }
}