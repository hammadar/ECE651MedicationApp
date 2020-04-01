package com.uwece651.medicationapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MedicationScheduleTest {

    private MedicationSchedule medicationSchedule = new MedicationSchedule("1234");

    @Before
    public void before() {
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

    }

    @Test
    public void dailyFrequencyTest() {
        assertEquals("Daily Frequency Test: ", "bid", this.medicationSchedule.getDailyFrequency());
    }

    @Test
    public void fridayCheck() {
        assertEquals("Friday Test: ", true, this.medicationSchedule.getFridayChecked());
    }

    @Test
    public void hoursCheck() {
        assertEquals("Hourly Freq Check: ", "q2h", this.medicationSchedule.getHoursFrequency());
    }

    @Test
    public void mealCodeCheck() {
        assertEquals("Meal Code Test: ", "Before", this.medicationSchedule.getMealCode());
    }

    @Test
    public void mealTypeCheck() {
        assertEquals("Meal Type Test: ", "Dinner", this.medicationSchedule.getMealType());
    }

    @Test
    public void medicalCodeCheck() {
        assertEquals("Medical Code Test: ", "q2h", this.medicationSchedule.getMedicalCode());
    }

    @Test
    public void mondayCheck() {
        assertEquals("Monday Test: ", true, this.medicationSchedule.getMondayChecked());
    }

    @Test
    public void saturdayCheck() {
        assertEquals("Saturday Test: ", false, this.medicationSchedule.getSaturdayChecked());
    }

    @Test
    public void sundayCheck() {
        assertEquals("Sunday Test: ", false, this.medicationSchedule.getSundayChecked());
    }

    @Test
    public void thursdayCheck() {
        assertEquals("Thursday Test: ", true, this.medicationSchedule.getThursdayChecked());
    }

    @Test
    public void timeOfDayCheck() {
        assertEquals("Time of Day Test: ", "Morning", this.medicationSchedule.getTimeOfDayCode());
    }

    @Test
    public void tuesdayCheck() {
        assertEquals("Tuesday Test: ", true, this.medicationSchedule.getTuesdayChecked());
    }

    @Test
    public void wednesdayCheck() {
        assertEquals("Wednesday Test: ", true, this.medicationSchedule.getWednesdayChecked());
    }
}
