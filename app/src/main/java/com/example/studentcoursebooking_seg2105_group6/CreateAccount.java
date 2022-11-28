package com.example.studentcoursebooking_seg2105_group6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentcoursebooking_seg2105_group6.models.User;
import com.example.studentcoursebooking_seg2105_group6.controllers.security.AuthController;

public class CreateAccount extends AppCompatActivity {
    private String[] userTypes = {"student", "instructor", "admin"};
    private String userType;
    private boolean flagName, flagUser, flagPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Spinner spinner = findViewById(R.id.userTypeSpinner);
        EditText createName = (EditText)findViewById(R.id.createName);
        EditText createUser = (EditText)  findViewById(R.id.createUser);
        EditText createPw = (EditText)findViewById(R.id.createPw);
        Button createBtn = (Button)findViewById(R.id.createBtn);
        Button createBackBtn=(Button) findViewById(R.id.createBackBtn);

        AuthController authController = new AuthController();

        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userType = adapterView.getSelectedItem().toString();
                Toast.makeText(CreateAccount.this, userType, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        createName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                flagName=false;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("")){
                    createName.setError("This field cannot be blank");
                    flagName=false;
                }else{
                    flagName=true;
                }
            }
        });

        createUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                flagUser=false;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("")){
                    createUser.setError("This field cannot be blank");
                    flagUser=false;
                }else{
                    flagUser=true;
                }
            }
        });

        createPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                flagPw=false;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("")){
                    createPw.setError("This field cannot be blank");
                    flagPw=false;
                }else{
                    flagPw=true;
                }
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreateAccount.this,MainActivity.class); //back btrn

                if (flagName && flagPw && flagUser){
                    User userRegistration = new User(createUser.getText().toString(), createPw.getText().toString(), userType, createName.getText().toString());
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
                }else{
                    Toast.makeText(CreateAccount.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                }

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