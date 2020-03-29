package com.uwece651.medicationapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class firebase_test_jlenos {

    private FirebaseAuth mAuth;
    private FirebaseApp firebaseApp;
    private static final FirebaseOptions OPTIONS =
            new FirebaseOptions.Builder()
                    .setApplicationId("1:242727984314:android:1de9adc2be1be7f108b683")
                    .setApiKey("AIzaSyCHWCTurnALaJtiZHJGC1C3hdl8zluXBv4")
                    .setDatabaseUrl("https://ece651medicationappv2.firebaseio.com")
                    .setProjectId("ece651medicationappv2")
                    .build();

    //@Before
    //public void before() {
    //    Context instrumentationContext = InstrumentationRegistry.getInstrumentation().getContext();
    //   firebaseApp = FirebaseApp.initializeApp(instrumentationContext , OPTIONS);
    //    Log.d("Firebase", firebaseApp.getName());
    //    Log.d("FirebaseAuth", "Current User UID: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
    // }

    @Test
    public void getUserNameFromDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference User = db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.d("DB","Getting Name for ID: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        User.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d("DB","found id");
                    Object name_field = document.get("name");
                    if (name_field != null) {
                        String name_value = (String) document.get("name");
                        Log.d("DB", "User has name: " + name_value);
                    }

                } else {
                    Log.d("DB", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Test
    public void registerUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        PersonalInformation user = new PersonalInformation("9999");
        user.setName("Owner");
        user.setType("Doctor");
        Log.d("Class UID:", user.getUid());
        CollectionReference Users = db.collection("Users");
        Users.document(user.getUid()).set(user);
        Users.document("1").set(user);
        Log.d("DB", "Finished setting user");
    }
    @Test
    public void retrieveInfo()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userDb = db.collection("Users");
        userDb.whereEqualTo("uid", "9999")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Object uid_field = document.get("uid");
                                if (uid_field != null) {
                                    String uid_value = (String) document.get("name");
                                    Log.d("DB", uid_value);
                                } else {
                                    Log.d("DB", "Error getting documents: ", task.getException());
                                }
                            }
                        }
                    }
                });
        Log.d("DB", "Finished getting user");
    }

    @Test
    public void WaitForDB() throws InterruptedException {
        sleep(1000);
    }
}

