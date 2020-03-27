package com.uwece651.medicationapp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import junit.framework.TestCase;

import java.util.Objects;


public class firebase_fortestTest{

    private firebase_fortest firebase_fortest = new firebase_fortest();

    private FirebaseAuth mAuth;
    //protected void onCreate(Bundle savedInstanceState)
    //{
    //  super.onCreate(savedInstanceState);
    // mAuth = FirebaseAuth.getInstance();
    //FirebaseUser currentUser = mAuth.getCurrentUser();
    //}
    //mAuth = FirebaseAuth.getInstance();
    String currentUserName = "Doctor";
    //currentUserName=currentUser.getDisplayName()

    public void submitRegistrationInfo()
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


    public void registerUser(PersonalInformation user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference Users = db.collection("Users");
        Users.document(user.getUid()).set(user);
    }

    public void registerDoctor(Doctor doctor) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference Doctors = db.collection("Doctors");
        Doctors.document(doctor.getUid()).set(doctor);
        retrieveInfo(doctor);
    }

    // retrieve data from Firebase
    public void retrieveInfo(Doctor doctor)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference patientDb = db.collection("Doctors");
        String DoctorID = Objects.requireNonNull(doctor.getUid());
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        //CollectionReference patientDb = db.collection("Patients");
    }

    @Test
    public void testGetDoctorID() {
        firebase_fortestTest f= new firebase_fortestTest();
        f.submitRegistrationInfo();
        assertEquals("Testing DoctorID","9999",this.firebase_fortest.getDoctorID());
    }
}