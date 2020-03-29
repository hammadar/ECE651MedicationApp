package com.uwece651.medicationapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

public class FirebaseTest {

    private PersonalInformation personalInformation;
    private Doctor doctor;
    //private Patient patient;


    @Before
    public void before() {
        doctor = makeDoctor();
        personalInformation = makeUser();
        registerUser(personalInformation);
        registerDoctor(doctor);

    }



    public Doctor makeDoctor() {
        Doctor doctor = new Doctor("9999");
        doctor.setName("Owner");
        doctor.setType("Doctor");
        return doctor;

    }

    public PersonalInformation makeUser() {
        PersonalInformation user = new PersonalInformation("9999");
        user.setName("Owner");
        user.setType("Doctor");
        return user;
    }


    public void registerUser(PersonalInformation user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference Users = db.collection("Users");
        Users.document(user.getUid()).set(user);
    }

    public void registerDoctor(Doctor doctor) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference Doctors = db.collection("Doctors");
        Doctors.document(doctor.getUid()).set(doctor);
    }

    @Test
    public void checkUserRegistered() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference Users = db.collection("Users");
        DocumentReference docRef = Users.document("9999");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        PersonalInformation user = document.toObject(PersonalInformation.class);
                        assertEquals("Testing User UID:", "9999", user.getUid());
                        assertEquals("Testing User Name:", "Owner", user.getName());
                        assertEquals("Testing User Type:", "Doctor", user.getType());

                    } else {
                        fail("Personal Information Test: Object not retrieved\n");
                    }
                } else {
                    fail("Personal Information Test: Connection to Firebase not established\n");
                }
            }
        });
    }

    @Test
    public void checkDoctorRegistered() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference doctors = db.collection("Doctors");
        DocumentReference docRef = doctors.document("9999");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Doctor doctor = document.toObject(Doctor.class);
                        assertEquals("Testing Doctor UID:", "9999", doctor.getUid());
                        assertEquals("Testing Doctor Name:", "Owner", doctor.getName());
                        assertEquals("Testing Doctor Type:", "Doctor", doctor.getType());

                    } else {
                        fail("Doctor Registered Test: Object not retrieved\n");
                    }
                } else {
                    fail("Doctor Registered Test: Connection to Firebase not established\n");
                }
            }
        });
    }


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

    /*
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
    }*/


    public void WaitForDB() throws InterruptedException {
        sleep(10000);
    }


}


