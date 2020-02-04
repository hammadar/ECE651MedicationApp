package com.uwece651.medicationapp;


import java.time.LocalDate;
import java.util.Arrays;

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


    private String[] associatedPrescriptions;
    private String lastVisitID;
    private String[] visitHistoryIDs;
    private Doctor assignedDoctor;


    public Patient(String UID) {
        super(UID);
        super.setType("Patient");
        this.associatedPrescriptions = new String[0];
        this.visitHistoryIDs = new String[0];
    }


    public String getUID () {
        return super.getUID();
    }

    public String[] getAssociatedPrescriptions () {
        return this.associatedPrescriptions;
    }

    public String getLastVisit () {
        return this.lastVisitID;
    }

    public String[] getVisitHistoryIDs () {
        return this.visitHistoryIDs;
    }

    public Doctor getAssignedDoctor () {
        return this.assignedDoctor;
    }

    public void setAssociatedPrescriptions (String[] prescriptions) {
        this.associatedPrescriptions = prescriptions;
    }

    public void addPrescription (String prescription) {
        this.associatedPrescriptions = appArrayHandling.add(this.associatedPrescriptions, prescription);
    }

    public void emptyPrescriptions () {
        this.associatedPrescriptions = new String[0];
    }

    public void setLastVisit (String visitID) {
        this.lastVisitID = visitID;
    }

    public void addVisit (String visitID) {
        this.visitHistoryIDs = appArrayHandling.add(visitHistoryIDs, visitID);
    }

    public void setDoctor (Doctor doctor) {
        this.assignedDoctor = doctor;
    }


}


