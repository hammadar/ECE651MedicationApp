package com.uwece651.medicationapp;

import android.Manifest;

import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class PatientTest {

    private Patient patient = new Patient("123456");

    @Rule
    public GrantPermissionRule readPermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CALENDAR);

    @Rule
    public GrantPermissionRule writePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_CALENDAR);

    @Before
    public void before() {
        String[] prescriptions = {"1", "2", "3"};
        String[] visitHistoryIDs = {"1", "2", "3"};
        patient.setAssociatedPrescriptions(prescriptions);
        patient.setVisitHistoryIDs(visitHistoryIDs);
        patient.setDoctor("Vader");
        patient.setLastVisit("3");
    }


    @Test
    public void prescriptionTest() {

        String[] prescriptions = {"1", "2", "3"};
        assertEquals("Testing prescriptions: ", prescriptions, this.patient.getAssociatedPrescriptions() );
    }

    @Test
    public void visitHistoryIDsTest() {
        String[] visitHistoryIDs = {"1", "2", "3"};
        assertEquals("Testing visit history IDS: ", visitHistoryIDs, this.patient.getVisitHistoryIDs());
    }

    @Test
    public void doctorTest() {
        assertEquals("Testing Doctor: ", "Vader", this.patient.getAssignedDoctor());
    }

    @Test
    public void lastVisitTest() {
        assertEquals("Testing Last Visit: ", "3", this.patient.getLastVisit());
    }
}
