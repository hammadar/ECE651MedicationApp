package com.uwece651.medicationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SignedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        String uid = getIntent().getStringExtra("userid");
        processUser(uid);

    }

    public void processUser(String userid) {
        //display relevant info

    }
}
