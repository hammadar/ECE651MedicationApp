package com.uwece651.medicationapp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MedicationDataTest {

    private MedicationData medicationData = new MedicationData("1234");

    @Test
    public void All_MedicationDataTest() {
        this.medicationData.setBrandName("BrandName");
        this.medicationData.setContraindications("Weirdness");
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
        //-------------------------------------
        assertEquals("Testing brand name: ", "BrandName", this.medicationData.getBrandName());
        //-------------------------------------
        assertEquals("Testing contraindictions: ", "Weirdness", this.medicationData.getContraindications());
        //---------------------------------------
        assertEquals("Testing drug interactions: ", "Another Drug", this.medicationData.getDrugInteractions());
        //--------------------------------------
        assertEquals("Testing generic name: ", "Meh", this.medicationData.getGenericName());
        //------------------------------------
        assertEquals("Testing instructions: ", "Eat once a day", this.medicationData.getInstructions());
        //-------------------------------------
        assertEquals("Testing missed dose: ", "2", this.medicationData.getMissedDose());
        //----------------------------------------
        assertEquals("Testing Notes: ", "Urgh", this.medicationData.getNotes());
        //------------------------------------
        assertEquals("Testing Overdose: ", "5", this.medicationData.getOverdose());
        //--------------------------------
        assertEquals("Testing precautions: ", "??", this.medicationData.getPrecautions());
        //-------------------------------------
        assertEquals("Testing side effects: ", "Death", this.medicationData.getSideEffects());
        //-----------------------------------------
        assertEquals("Testing storage: ", "Cold", this.medicationData.getStorage());
        //------------------------------------------
        assertEquals("Testing used for: ", "Fun", this.medicationData.getUsedFor());
    }
}
