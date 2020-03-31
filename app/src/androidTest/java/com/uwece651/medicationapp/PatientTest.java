package com.uwece651.medicationapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PatientTest {

    private Patient patient = new Patient("123456");

    @Test
    public void All_PatientTest() {
        String[] prescriptions = {"1", "2", "3"};
        String[] visitHistoryIDs = {"1", "2", "3"};
        patient.setAssociatedPrescriptions(prescriptions);
        patient.setVisitHistoryIDs(visitHistoryIDs);
        patient.setDoctor("Vader");
        patient.setLastVisit("3");
        //-----------------------
        String[] prescriptions2 = {"1", "2", "3"};
        assertEquals("Testing prescriptions: ", prescriptions2, this.patient.getAssociatedPrescriptions() );
        //-----------------------
        String[] visitHistoryIDs2 = {"1", "2", "3"};
        assertEquals("Testing visit history IDS: ", visitHistoryIDs2, this.patient.getVisitHistoryIDs());
        //-----------------------
        assertEquals("Testing Doctor: ", "Vader", this.patient.getAssignedDoctor());
        //-----------------------
        assertEquals("Testing Last Visit: ", "3", this.patient.getLastVisit());
    }
}
