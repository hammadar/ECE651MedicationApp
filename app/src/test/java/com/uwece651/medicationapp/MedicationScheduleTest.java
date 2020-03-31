package com.uwece651.medicationapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MedicationScheduleTest {

    private MedicationSchedule medicationSchedule = new MedicationSchedule("1234");

    @Test
    public void All_MedicationScheduleTest() {
        this.medicationSchedule.setDailyFrequency("bid");
        this.medicationSchedule.setFridayChecked(true);
        this.medicationSchedule.setHoursFrequency("q2h");
        this.medicationSchedule.setMealCode("Before");
        this.medicationSchedule.setMealType("Dinner");
        this.medicationSchedule.setMedicalCode("q2h");
        this.medicationSchedule.setMondayChecked(true);
        this.medicationSchedule.setSaturdayChecked(false);
        this.medicationSchedule.setSundayChecked(false);
        this.medicationSchedule.setThursdayChecked(true);
        this.medicationSchedule.setTimeOfDayCode("Morning");
        this.medicationSchedule.setTuesdayChecked(true);
        this.medicationSchedule.setWednesdayChecked(true);
        //--------------------------------------------------
        assertEquals("Daily Frequency Test: ", "bid", this.medicationSchedule.getDailyFrequency());
        //--------------------------------------------------
        assertEquals("Friday Test: ", true, this.medicationSchedule.getFridayChecked());
        //--------------------------------------------------
        assertEquals("Hourly Freq Check: ", "q2h", this.medicationSchedule.getHoursFrequency());
        //---------------------------------------------------
        assertEquals("Meal Code Test: ", "Before", this.medicationSchedule.getMealCode());
        //-----------------------------------------------------
        assertEquals("Meal Type Test: ", "Dinner", this.medicationSchedule.getMealType());
        //-----------------------------------------------------
        assertEquals("Medical Code Test: ", "q2h", this.medicationSchedule.getMedicalCode());
        //--------------------------------------------------
        assertEquals("Monday Test: ", true, this.medicationSchedule.getMondayChecked());
        //-----------------------------------------------------
        assertEquals("Saturday Test: ", false, this.medicationSchedule.getSaturdayChecked());
        //----------------------------------------------------
        assertEquals("Sunday Test: ", false, this.medicationSchedule.getSundayChecked());
        //----------------------------------------------------
        assertEquals("Thursday Test: ", true, this.medicationSchedule.getThursdayChecked());
        //------------------------------------------------------
        assertEquals("Time of Day Test: ", "Morning", this.medicationSchedule.getTimeOfDayCode());
        //-------------------------------------------------------
        assertEquals("Tuesday Test: ", true, this.medicationSchedule.getTuesdayChecked());
        //-------------------------------------------------------
        assertEquals("Wednesday Test: ", true, this.medicationSchedule.getWednesdayChecked());
    }
}
