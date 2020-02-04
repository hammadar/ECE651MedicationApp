package com.uwece651.medicationapp;

import java.time.LocalDate;
import java.time.LocalTime;

public class MedicationSchedule {
    private String uid;
    private String mealcode; // Before / After / During
    private String mealtype; // Breakfast / Lunch / Dinner / All
    private String timeofdaycode; // Morning / Afternoon / Evening / Bedtime / Night / Ad Lib
    private String dailyfrequency; // daily / bid / tid / qid / 5daily
    private String hoursfrequency; // hourly / q2h / q3h / q4h / q6h / q8h / q12h
    private LocalTime[] notificationhours; // Generated from the above selections, but can selectively add/remove additional times as needed ?

    public MedicationSchedule (String uid) {
        this.uid = uid;
        this.mealcode = null;
        this.mealtype = null;
        this.timeofdaycode = null;
        this.dailyfrequency = null;
        this.hoursfrequency = null;
        this.notificationhours = new LocalTime[0];
    }

    public String getUid () {
        return this.uid;
    }

    public String getmealcode () {
        return this.mealcode;
    }

    public void setmealcode (String mealcode) {
        this.mealcode = mealcode;
    }

    public String getmealtype () {
        return this.mealtype;
    }

    public void setmealtype (String mealtype) {
        this.mealtype = mealtype;
    }

    public String gettimeofdaycode () {
        return this.timeofdaycode;
    }

    public void settimeofdaycode (String timeofdaycode) {
        this.timeofdaycode = timeofdaycode;
    }

    public String getdailyfrequency () {
        return this.dailyfrequency;
    }

    public void setdailyfrequency (String dailyfrequency) {
        this.dailyfrequency = dailyfrequency;
    }

    public String gethoursfrequency () {
        return this.hoursfrequency;
    }

    public void sethoursfrequency (String hoursfrequency) {
        this.hoursfrequency = hoursfrequency;
    }

    public LocalTime[] getNotificationHours() {
        return this.notificationhours;
    }

    public void addnotificationhour(LocalTime dosagetime) {
        this.notificationhours = appArrayHandling.add(this.notificationhours, dosagetime);
    }

    public void removenotificationhour(LocalTime dosagetime) {
        this.notificationhours = appArrayHandling.remove(this.notificationhours, dosagetime);
    }
}
}
