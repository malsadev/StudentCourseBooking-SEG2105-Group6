package com.example.studentcoursebooking_seg2105_group6;

import static org.junit.Assert.*;

import android.app.Activity;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {
    @Rule
    //Rule for activity to be launched
    public ActivityTestRule <MainActivity>  mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mActivity = null;
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testOnCreate(){
        View view = mActivity.findViewById(R.id.userLogin);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
    }
}