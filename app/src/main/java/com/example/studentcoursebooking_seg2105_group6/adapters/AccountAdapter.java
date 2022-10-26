package com.example.studentcoursebooking_seg2105_group6.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentcoursebooking_seg2105_group6.MainActivity;
import com.example.studentcoursebooking_seg2105_group6.R;
import com.example.studentcoursebooking_seg2105_group6.WelcomePage;
import com.example.studentcoursebooking_seg2105_group6.models.Course;
import com.example.studentcoursebooking_seg2105_group6.models.User;

import java.util.ArrayList;

public class AccountAdapter extends ArrayAdapter<User> {
    // constructor for our list view adapter.
    public AccountAdapter(@NonNull Context context, ArrayList<User> userArrayList) {
        super(context, 0, userArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.account_lv_item, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        User user = getItem(position);

        // initializing our UI components of list view item.
        TextView accountUsernameText = listitemView.findViewById(R.id.idTVtext);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        accountUsernameText.setText(user.getUsername());

        // in below line we are using Picasso to
        // load image from URL in our Image VIew.

        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + user.getUsername(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);// should take to create account
            }
        });
        return listitemView;
    }

}
