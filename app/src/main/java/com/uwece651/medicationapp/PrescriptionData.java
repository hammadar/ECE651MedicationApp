package com.uwece651.medicationapp;

public class PrescriptionData {
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

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

    public String getDailyFrequency() {
        return dailyFrequency;
    }

    public void setDailyFrequency(String dailyFrequency) {
        this.dailyFrequency = dailyFrequency;
    }

    public String getTimeBetweenDosages() {
        return timeBetweenDosages;
    }

    public void setTimeBetweenDosages(String timeBetweenDosages) {
        this.timeBetweenDosages = timeBetweenDosages;
    }

    public String getTimeBetweenDosagesUnits() {
        return timeBetweenDosagesUnits;
    }

    public void setTimeBetweenDosagesUnits(String timeBetweenDosagesUnits) {
        this.timeBetweenDosagesUnits = timeBetweenDosagesUnits;
    }

    private String patientId;
    private String medicationName;
    private Boolean isMondayChecked;
    private Boolean isTuesdayChecked;
    private Boolean isWednesdayChecked;
    private Boolean isThursdayChecked;
    private Boolean isFridayChecked;
    private Boolean isSaturdayChecked;
    private Boolean isSundayChecked;
    private String dailyFrequency;
    private String timeBetweenDosages;
    private String timeBetweenDosagesUnits;


    public PrescriptionData(String patientId, String medicationName, Boolean isMondayChecked, Boolean isTuesdayChecked, Boolean isWednesdayChecked, Boolean isThursdayChecked, Boolean isFridayChecked, Boolean isSaturdayChecked, Boolean isSundayChecked, String dailyFrequency, String timeBetweenDosages, String timeBetweenDosagesUnits) {
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.isMondayChecked = isMondayChecked;
        this.isTuesdayChecked = isTuesdayChecked;
        this.isWednesdayChecked = isWednesdayChecked;
        this.isThursdayChecked = isThursdayChecked;
        this.isFridayChecked = isFridayChecked;
        this.isSaturdayChecked = isSaturdayChecked;
        this.isSundayChecked = isSundayChecked;
        this.dailyFrequency = dailyFrequency;
        this.timeBetweenDosages = timeBetweenDosages;
        this.timeBetweenDosagesUnits = timeBetweenDosagesUnits;
    }
}
