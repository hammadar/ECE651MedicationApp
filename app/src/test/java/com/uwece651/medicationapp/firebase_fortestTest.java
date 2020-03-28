package com.uwece651.medicationapp;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.manipulation.Ordering;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



import junit.framework.TestCase;

import java.util.Objects;



public class firebase_fortestTest{

    private FirebaseAuth mAuth;
    private FirebaseApp firebaseApp;
    private PersonalInformation personalInformation;
    private Doctor doctor;
    private Patient patient;
    private static final FirebaseOptions OPTIONS =
            new FirebaseOptions.Builder()
            .setApplicationId("1:242727984314:android:1de9adc2be1be7f108b683")
            .setApiKey("AIzaSyCHWCTurnALaJtiZHJGC1C3hdl8zluXBv4")
            .setDatabaseUrl("https://ece651medicationappv2.firebaseio.com")
            .setProjectId("ece651medicationappv2")
            .build();
    @Mock
    private Context mockApplicationContext;
    @Mock
    private Resources mockContextResources;
    @Mock
    private SharedPreferences mockSharedPreferences;




    public FirebaseAuth initAndReturnFirebaseAuth() {
        FirebaseAuth authMock = FirebaseAuth.getInstance(firebaseApp);//mock(FirebaseAuth.class);
        FirebaseUser mockFirebaseUser = mock(FirebaseUser.class);
        when(authMock.getCurrentUser()).thenReturn(mockFirebaseUser);
        return authMock;
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





    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(mockApplicationContext.getApplicationContext()).thenReturn(mockApplicationContext);
        when(mockApplicationContext.getResources()).thenReturn(mockContextResources);

        firebaseApp = FirebaseApp.initializeApp(mockApplicationContext, OPTIONS);

        mAuth = initAndReturnFirebaseAuth();
        makePersonalInformation();
        makeDoctor();
        registerUser(personalInformation);
        registerDoctor(doctor);
    }




    /*public void submitRegistrationInfo()
    {
        String testtype="Doctor";
        String testname="TestDoctor";

        PersonalInformation user;
        user = new PersonalInformation("9999");
        user.setName(testname);
        user.setType(testtype);

        Doctor doctor=new Doctor("9999");

        registerUser(user);
        registerDoctor(doctor);
    }




    // retrieve data from Firebase
    public void retrieveInfo(Doctor doctor)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference patientDb = db.collection("Doctors");
        String DoctorID = Objects.requireNonNull(doctor.getUid());
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        //CollectionReference patientDb = db.collection("Patients");
    }*/

    public PersonalInformation makePersonalInformation() {
        PersonalInformation personalInformation = new PersonalInformation("123456789");
        personalInformation.setName("Vader");
        personalInformation.setType("Doctor");
        return personalInformation;
    }

    public Doctor makeDoctor() {
        Doctor doctor = new Doctor("123456789");
        doctor.setName("Vader");
        doctor.setType("Doctor");
        doctor.addBookedAppointment("54321");
        return doctor;

    }

    public Doctor removeAppointment(Doctor doctor) {
        doctor.removeBookedAppointment("54321");
        return doctor;
    }

    @Test
    public void checkUserRegistered() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference Users = db.collection("Users");
        DocumentReference docRef = Users.document("123456789");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        PersonalInformation user = document.toObject(PersonalInformation.class);
                        assertEquals("Testing User UID:", "123456789", user.getUid());
                        assertEquals("Testing User Name:", "Vader", user.getName());
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
}