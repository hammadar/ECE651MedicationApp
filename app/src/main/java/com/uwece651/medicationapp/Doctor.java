package com.uwece651.medicationapp;

public class Doctor {

    private String uid;
    private Patient[] patients;
    private String[] specialties;
    private String[] bookedAppointments;

    public Doctor (String uid) {
        this.uid = uid;
        this.patients = new Patient[0];
        this.specialties = new String[0];
        this.bookedAppointments = new String[0];
    }

    public Patient[] getPatients() {
        return this.patients;
    }

    public String[] getSpecialties() {
        return this.specialties;
    }

    public String[] getBookedAppointments() {
        return this.getBookedAppointments();
    }

    public void addSpecialty (String specialty) {
        this.specialties = appArrayHandling.add(this.specialties, specialty);
    }

    public void addPatient (Patient patient) {
        this.patients = appArrayHandling.add(this.patients, patient);
    }

    public void addBookedAppointment(String appointmentID) {
        this.bookedAppointments = appArrayHandling.add(this.bookedAppointments, appointmentID);
    }

    public void removeBookedAppointment(String appointmentID) {
        this.bookedAppointments = appArrayHandling.remove(this.bookedAppointments, appointmentID);
    }




}
