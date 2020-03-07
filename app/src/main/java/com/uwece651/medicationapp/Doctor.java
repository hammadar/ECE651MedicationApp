package com.uwece651.medicationapp;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends PersonalInformation {

    private List<String> patients; //patient uids
    private List<String> specialties;
    private List<String> bookedAppointments;

    public Doctor () {}

    public Doctor (String UID) {
        super(UID);
        super.setType("Doctor");
        this.patients = new ArrayList<>();
        this.specialties = new ArrayList<>();
        this.bookedAppointments = new ArrayList<>();
    }

    public String getUid () { return super.getUid(); }

    @Exclude
    public String[] getPatients() {
        return this.patients.toArray(new String[this.patients.size()]);
    }

    @Exclude
    public String[] getSpecialties() {
        return this.specialties.toArray(new String[this.specialties.size()]);
    }

    @Exclude
    public String[] getBookedAppointments() { return this.bookedAppointments.toArray(new String[this.bookedAppointments.size()]); }

    public void addSpecialty (String specialty) {
        this.specialties.add(specialty);
    }

    public void addPatient (String patient) {
        this.patients.add(patient);
    }

    public void addBookedAppointment(String appointmentID) {
        this.bookedAppointments.add(appointmentID);
    }

    public void removeBookedAppointment(String appointmentID) {
        this.bookedAppointments.remove(appointmentID);
    }




}
