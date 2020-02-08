package com.uwece651.medicationapp;

public class Doctor extends PersonalInformation {

    private Patient[] patients;
    private String[] specialties;
    private String[] bookedAppointments;

    public Doctor (String UID) {
        super(UID);
        super.setType("Doctor");
        this.patients = new Patient[0];
        this.specialties = new String[0];
        this.bookedAppointments = new String[0];
    }

    public String getUid () { return super.getUid(); }

    public Patient[] getPatients() {
        return this.patients;
    }

    public String[] getSpecialties() {
        return this.specialties;
    }

    public String[] getBookedAppointments() { return this.bookedAppointments; }

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
