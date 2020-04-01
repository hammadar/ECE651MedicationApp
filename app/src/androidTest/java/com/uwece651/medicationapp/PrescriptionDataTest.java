package com.uwece651.medicationapp;
import android.Manifest;

import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class PrescriptionDataTest {

    private PrescriptionData prescriptionData = new PrescriptionData("12345");

    @Rule
    public GrantPermissionRule readPermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CALENDAR);

    @Rule
    public GrantPermissionRule writePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_CALENDAR);

    @Before
    public void before() {
        Date Date1 = new GregorianCalendar(2020, 1, 1).getTime();
        Date Date2 =  new GregorianCalendar(2020, 1, 2).getTime();
        this.prescriptionData.setAssignedByDoctorID("12345");
        this.prescriptionData.setAssignedByDoctorName("Meow");
        this.prescriptionData.setEndDate(Date2);
        this.prescriptionData.setMedicationID("12345");
        this.prescriptionData.setMedicationName("Weed");
        this.prescriptionData.setNumberOfRefills(3);
        this.prescriptionData.setScheduleID("12345");
        this.prescriptionData.setScheduleShortName("now");
        this.prescriptionData.setStartDate(Date1);

    }

    @Test
    public void assignedByDoctorIDTest() {
        assertEquals("Assigned by Doctor ID Test: ", "12345", this.prescriptionData.getAssignedByDoctorID());
    }

    @Test
    public void assignedByDoctorNameTest() {
        assertEquals("Assigned by Doctor Name Test: ", "Meow", this.prescriptionData.getAssignedByDoctorName());
    }

    @Test
    public void endDateTest() {
        Date date2 = new GregorianCalendar(2020, 1, 2).getTime();
        assertEquals("End Date Test: ", date2, this.prescriptionData.getEndDate());
    }

    @Test
    public void medicationIDTest() {
        assertEquals("Medication ID Test: ", "12345", this.prescriptionData.getMedicationID());
    }

    @Test
    public void medicationNameTest() {
        assertEquals("Medication Name Test: ", "Weed", this.prescriptionData.getMedicationName());
    }

    @Test
    public void numberOfRefillsTest() {
        int n = 3;
        assertEquals("number of refills test: ",  n, ((int) this.prescriptionData.getNumberOfRefills()));
    }

    @Test
    public void scheduleIDTesT() {
        assertEquals("Schedule ID Test: ", "12345", this.prescriptionData.getScheduleID());
    }

    @Test
    public void scheduleShortNameTest() {
        assertEquals("Schedule Short Name Test: ", "now", this.prescriptionData.getScheduleShortName());
    }

    @Test
    public void startDateTest() {
        Date date1 =  new GregorianCalendar(2020, 1, 1).getTime();
        assertEquals("Start Date Test: ", date1, this.prescriptionData.getStartDate());
    }
}
