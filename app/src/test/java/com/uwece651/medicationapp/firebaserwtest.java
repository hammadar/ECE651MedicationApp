package com.uwece651.medicationapp;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import junit.framework.TestCase;

import org.junit.Test;

public class firebaserwtest extends TestCase {

    @Test
    public void submitRegistrationInfo()
    {
        String testtype="Doctor";
        String testname="TestDoctor";

        PersonalInformation user;
        user = new PersonalInformation("9999");
        user.setName(testname);
        user.setType(testtype);

        Doctor doctor=new Doctor("9999");
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



}