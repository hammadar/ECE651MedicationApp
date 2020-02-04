package com.uwece651.medicationapp;

import java.time.LocalDate;

public class Visit {

    private String visitID;
    private LocalDate visitDate;
    private int visitDuration; //duration in minutes

    public Visit (String visitID, LocalDate visitDate, int visitDuration) {
        this.visitID = visitID;
        this.visitDate = visitDate;
        this.visitDuration = visitDuration;
    }

    public String getVisitID () { return this.visitID;}

    public LocalDate getVisitDate() { return this.visitDate;}

    public int getVisitDuration () { return this.visitDuration;}

}
