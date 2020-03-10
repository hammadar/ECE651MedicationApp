package com.uwece651.medicationapp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MedicationDataTest {

    private MedicationData medicationData = new MedicationData("1234");

    @Before
    public void before() {
        this.medicationData.setBrandName("BrandName");
        this.medicationData.setContraindictions("Weirdness");
        this.medicationData.setDrugInteractions("Another Drug");
        this.medicationData.setGenericName("Meh");
        this.medicationData.setInstructions("Eat once a day");
        this.medicationData.setMissedDose("2");
        this.medicationData.setNotes("Urgh");
        this.medicationData.setOverdose("5");
        this.medicationData.setPrecautions("??");
        this.medicationData.setSideEffects("Death");
        this.medicationData.setStorage("Cold");
        this.medicationData.setUsedFor("Fun");

    }

    @Test
    public void testBrandName(){
        assertEquals("Testing brand name: ", "BrandName", this.medicationData.getBrandName());
    }

    @Test
    public void testContraindictions() {
        assertEquals("Testing contraindictions: ", "Weirdness", this.medicationData.getContraindictions());
    }

    @Test
    public void testDrugInteractions() {
        assertEquals("Testing drug interactions: ", "Another Drug", this.medicationData.getDrugInteractions());
    }

    @Test
    public void testGenericName() {
        assertEquals("Testing generic name: ", "Meh", this.medicationData.getGenericName());
    }

    @Test
    public void testInstructions() {
        assertEquals("Testing instructions: ", "Eat once a day", this.medicationData.getInstructions());
    }

    @Test
    public void testMissedDose() {
        assertEquals("Testing missed dose: ", "2", this.medicationData.getMissedDose());
    }

    @Test
    public void testNotes() {
        assertEquals("Testing Notes: ", "Urgh", this.medicationData.getNotes());
    }

    @Test
    public void testOverdose() {
        assertEquals("Testing Overdose: ", "5", this.medicationData.getOverdose());
    }

    @Test
    public void testPrecautions() {
        assertEquals("Testing precautions: ", "??", this.medicationData.getPrecautions());
    }

    @Test
    public void testSideEffects() {
        assertEquals("Testing side effects: ", "Death", this.medicationData.getSideEffects());
    }

    @Test
    public void testStorage() {
        assertEquals("Testing storage: ", "Cold", this.medicationData.getStorage());
    }

    @Test
    public void testUsedFor() {
        assertEquals("Testing used for: ", "Fun", this.medicationData.getUsedFor());
    }
}
