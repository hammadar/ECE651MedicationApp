package com.uwece651.medicationapp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoctorTest {

    private Doctor doctor = new Doctor("123456");

    @Test
    public void before() {
        this.doctor.addPatient("Vader");
        this.doctor.addBookedAppointment("1234");
        this.doctor.addBookedAppointment("2345");
        this.doctor.addSpecialty("Nothing");
        //------------------------------
        String[] patients = {"Vader"};
        assertEquals("Get Patients Test: ", patients, this.doctor.getPatients());
        //----------------------------
        String[] specialties = {"Nothing"};
        assertEquals("Get Specialty Test: ", specialties, this.doctor.getSpecialties());
        //----------------------------
        String[] appointments = {"1234"};
        this.doctor.removeBookedAppointment("2345");
        assertEquals("Remove appointment test: ", appointments, this.doctor.getBookedAppointments());
    }

}
