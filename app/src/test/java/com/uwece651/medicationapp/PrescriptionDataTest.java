package com.uwece651.medicationapp;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class PrescriptionDataTest {

    private PrescriptionData prescriptionData = new PrescriptionData("12345");

    @Test
    public void All_PrescriptionDataTest() {
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
        //--------------------------------
        assertEquals("Assigned by Doctor ID Test: ", "12345", this.prescriptionData.getAssignedByDoctorID());
        //------------------------------
        assertEquals("Assigned by Doctor Name Test: ", "Meow", this.prescriptionData.getAssignedByDoctorName());
        //------------------------------
        Date date2 = new GregorianCalendar(2020, 1, 2).getTime();
        assertEquals("End Date Test: ", date2, this.prescriptionData.getEndDate());
        //-------------------------------
        assertEquals("Medication ID Test: ", "12345", this.prescriptionData.getMedicationID());
        //------------------------------
        assertEquals("Medication Name Test: ", "Weed", this.prescriptionData.getMedicationName());
        //-------------------------------
        int n = 3;
        assertEquals("number of refills test: ",  n, ((int) this.prescriptionData.getNumberOfRefills()));
        //-------------------------------
        assertEquals("Schedule ID Test: ", "12345", this.prescriptionData.getScheduleID());
        //-------------------------------
        assertEquals("Schedule Short Name Test: ", "now", this.prescriptionData.getScheduleShortName());
        //-------------------------------
        Date date1 =  new GregorianCalendar(2020, 1, 1).getTime();
        assertEquals("Start Date Test: ", date1, this.prescriptionData.getStartDate());
    }
}
