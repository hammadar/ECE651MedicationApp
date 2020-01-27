package com.uwece651.medicationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignedInActivity extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);
        db = FirebaseFirestore.getInstance();

        String uid = getIntent().getStringExtra("userid");
        processUser(uid);

    }

    public void processUser(String userid) {
        //display relevant info

    }
}
