package com.uwece651.medicationapp;

import java.time.LocalDate;
import java.time.LocalTime;

public class MedicationSchedule {
    private String ScheduleID;
    private String MedicalCode; // q2h etc..
    private String MealCode; // Before / After / During
    private String MealType; // Breakfast / Lunch / Dinner / All
    private String TimeOfDayCode; // Morning / Afternoon / Evening / Bedtime / Night / Ad Lib
    private String DailyFrequency; // daily / bid / tid / qid / 5daily
    private String HoursFrequency; // hourly / q2h / q3h / q4h / q6h / q8h / q12h
    //private LocalTime[] NotificationHours; // Generated from the above selections, but can selectively add/remove additional times as needed ?
    private Boolean isMondayChecked;
    private Boolean isTuesdayChecked;
    private Boolean isWednesdayChecked;
    private Boolean isThursdayChecked;
    private Boolean isFridayChecked;
    private Boolean isSaturdayChecked;
    private Boolean isSundayChecked;

    public MedicationSchedule() { }

    public MedicationSchedule (String ScheduleID) {
        this.ScheduleID = ScheduleID;
        this.MealCode = null;
        this.MealType = null;
        this.TimeOfDayCode = null;
        this.DailyFrequency = null;
        this.HoursFrequency = null;
        //this.NotificationHours = new LocalTime[0];
        this.isMondayChecked = null;
        this.isTuesdayChecked = null;
        this.isWednesdayChecked = null;
        this.isThursdayChecked = null;
        this.isFridayChecked = null;
        this.isSaturdayChecked = null;
        this.isSundayChecked = null;
    }

    public String getScheduleID () {
        return this.ScheduleID;
    }

    public String getMedicalCode () {
        return this.MedicalCode;
    }

    public void setMedicalCode (String MedicalCode) {
        this.MedicalCode = MedicalCode;
    }
    
    public String getMealCode () {
        return this.MealCode;
    }

    public void setMealCode (String MealCode) {
        this.MealCode = MealCode;
    }

    public String getMealType () {
        return this.MealType;
    }

    public void setMealType (String MealType) {
        this.MealType = MealType;
    }

    public String getTimeOfDayCode () {
        return this.TimeOfDayCode;
    }

    public void setTimeOfDayCode (String TimeOfDayCode) {
        this.TimeOfDayCode = TimeOfDayCode;
    }

    public String getDailyFrequency () {
        return this.DailyFrequency;
    }

    public void setDailyFrequency (String DailyFrequency) {
        this.DailyFrequency = DailyFrequency;
    }

    public String getHoursFrequency () {
        return this.HoursFrequency;
    }

    public void setHoursFrequency (String HoursFrequency) {
        this.HoursFrequency = HoursFrequency;
    }

//    public LocalTime[] getNotificationHours() {
//        return this.NotificationHours;
//    }

//    public void addNotificationHours(LocalTime dosagetime) {
//        this.NotificationHours = appArrayHandling.add(this.NotificationHours, dosagetime);
//    }
//
//    public void removeNotificationHours(LocalTime dosagetime) {
//        this.NotificationHours = appArrayHandling.remove(this.NotificationHours, dosagetime);
//    }

    public Boolean getMondayChecked() {
        return isMondayChecked;
    }

    public void setMondayChecked(Boolean mondayChecked) {
        isMondayChecked = mondayChecked;
    }

    public Boolean getTuesdayChecked() {
        return isTuesdayChecked;
    }

    public void setTuesdayChecked(Boolean tuesdayChecked) {
        isTuesdayChecked = tuesdayChecked;
    }

    public Boolean getWednesdayChecked() {
        return isWednesdayChecked;
    }

    public void setWednesdayChecked(Boolean wednesdayChecked) {
        isWednesdayChecked = wednesdayChecked;
    }

    public Boolean getThursdayChecked() {
        return isThursdayChecked;
    }

    public void setThursdayChecked(Boolean thursdayChecked) {
        isThursdayChecked = thursdayChecked;
    }

    public Boolean getFridayChecked() {
        return isFridayChecked;
    }

    public void setFridayChecked(Boolean fridayChecked) {
        isFridayChecked = fridayChecked;
    }

    public Boolean getSaturdayChecked() {
        return isSaturdayChecked;
    }

    public void setSaturdayChecked(Boolean saturdayChecked) {
        isSaturdayChecked = saturdayChecked;
    }

    public Boolean getSundayChecked() {
        return isSundayChecked;
    }

    public void setSundayChecked(Boolean sundayChecked) {
        isSundayChecked = sundayChecked;
    }
}

