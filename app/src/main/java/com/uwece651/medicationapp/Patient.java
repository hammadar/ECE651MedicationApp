package com.uwece651.medicationapp;


import com.google.firebase.firestore.Exclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Patient extends PersonalInformation {
    // Each user is identified by a Used ID, this is also linked to their Google Sign-in so that we can automatically determine
    // which user dataset the currently logged in user is associated with.

    // Data Required:
    // 1 UserID
    // 1 Associated_Prescriptions (Array of Prescription IDs)
    // 1 Last_Visit
    // 1 Visit_History_Ids (array of numeric IDs)
    // 1 Assigned_Doctor

    // Public Class Visit_Info {
    //   1 Visit_ID
    //   1 Visit_DateTime
    //   1 Visit_Duration
    //   1
    // }


    private List<String> associatedPrescriptions;
    private String lastVisitID;
    private List<String> visitHistoryIDs;
    private String assignedDoctor; //Doctor UID


    public Patient() {}

    public Patient(String UID) {
        super(UID);
        super.setType("Patient");
        this.associatedPrescriptions = new ArrayList<>();
        this.visitHistoryIDs = new ArrayList<>();
    }


    public String getUid () {
        return super.getUid();
    }

    @Exclude
    public String[] getAssociatedPrescriptions () {
        return this.associatedPrescriptions.toArray(new String[this.associatedPrescriptions.size()]);
    }

    public String getLastVisit () {
        return this.lastVisitID;
    }

    @Exclude
    public String[] getVisitHistoryIDs () {
        return this.visitHistoryIDs.toArray(new String[this.visitHistoryIDs.size()]);
    }

    public String getAssignedDoctor () {
        return this.assignedDoctor;
    }

    public void setAssociatedPrescriptions (String[] prescriptions) {
        this.associatedPrescriptions = Arrays.asList(prescriptions);
    }

    public void addPrescription (String prescription) {
        this.associatedPrescriptions.add(prescription);
        //this.associatedPrescriptions = appArrayHandling.add(this.associatedPrescriptions, prescription);
    }

    public void emptyPrescriptions () {
        this.associatedPrescriptions = new ArrayList<>();
    }

    public void setLastVisit (String visitID) {
        this.lastVisitID = visitID;
    }

    public void addVisit (String visitID) {
        this.visitHistoryIDs.add(visitID);
    }

    public void setDoctor (String doctor) {
        this.assignedDoctor = doctor;
    }


}


