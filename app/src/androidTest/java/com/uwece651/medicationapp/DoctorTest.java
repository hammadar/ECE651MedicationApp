package com.uwece651.medicationapp;
import android.Manifest;

import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoctorTest {

    private Doctor doctor = new Doctor("123456");

    @Rule
    public GrantPermissionRule readPermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CALENDAR);

    @Rule
    public GrantPermissionRule writePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_CALENDAR);
    @Before
    public void before() {
        this.doctor.addPatient("Vader");
        this.doctor.addBookedAppointment("1234");
        this.doctor.addBookedAppointment("2345");
        this.doctor.addSpecialty("Nothing");

    }

    @Test
    public void getPatientTest() {
        String[] patients = {"Vader"};
        assertEquals("Get Patients Test: ", patients, this.doctor.getPatients());
    }

    @Test
    public void getSpecialtyTest() {
        String[] specialties = {"Nothing"};
        assertEquals("Get Specialty Test: ", specialties, this.doctor.getSpecialties());
    }

    @Test
    public void removeAppointmentTest() {
        String[] appointments = {"1234"};
        this.doctor.removeBookedAppointment("2345");
        assertEquals("Remove appointment test: ", appointments, this.doctor.getBookedAppointments());
    }

}
