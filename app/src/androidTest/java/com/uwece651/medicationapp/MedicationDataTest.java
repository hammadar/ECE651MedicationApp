package com.uwece651.medicationapp;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MedicationDataTest {

    @Test
    public void All_MedicationDataTest() {
        MedicationData medicationData = new MedicationData("1234");
        medicationData.setBrandName("BrandName");
        medicationData.setContraindications("Weirdness");
        medicationData.setDrugInteractions("Another Drug");
        medicationData.setGenericName("Meh");
        medicationData.setInstructions("Eat once a day");
        medicationData.setMissedDose("2");
        medicationData.setNotes("Urgh");
        medicationData.setOverdose("5");
        medicationData.setPrecautions("??");
        medicationData.setSideEffects("Death");
        medicationData.setStorage("Cold");
        medicationData.setUsedFor("Fun");
        //-------------------------------------
        assertEquals("Testing brand name: ", "BrandName", medicationData.getBrandName());
        //-------------------------------------
        assertEquals("Testing contraindictions: ", "Weirdness", medicationData.getContraindications());
        //---------------------------------------
        assertEquals("Testing drug interactions: ", "Another Drug", medicationData.getDrugInteractions());
        //--------------------------------------
        assertEquals("Testing generic name: ", "Meh", medicationData.getGenericName());
        //------------------------------------
        assertEquals("Testing instructions: ", "Eat once a day", medicationData.getInstructions());
        //-------------------------------------
        assertEquals("Testing missed dose: ", "2", medicationData.getMissedDose());
        //----------------------------------------
        assertEquals("Testing Notes: ", "Urgh", medicationData.getNotes());
        //------------------------------------
        assertEquals("Testing Overdose: ", "5", medicationData.getOverdose());
        //--------------------------------
        assertEquals("Testing precautions: ", "??", medicationData.getPrecautions());
        //-------------------------------------
        assertEquals("Testing side effects: ", "Death", medicationData.getSideEffects());
        //-----------------------------------------
        assertEquals("Testing storage: ", "Cold", medicationData.getStorage());
        //------------------------------------------
        assertEquals("Testing used for: ", "Fun", medicationData.getUsedFor());
    }
}
