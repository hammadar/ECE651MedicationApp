package com.uwece651.medicationapp;

import java.time.LocalDate;
import java.util.Date;



public class PrescriptionData {
    private String PrescriptionID;
    private String MedicationID;
    private String ScheduleID;
    private Date StartDate;
    private Date EndDate;
    private String MedicationName;
    private String ScheduleShortName;
    private Integer NumberOfRefills;
    private String AssignedByDoctorID;
    private String AssignedByDoctorName;

    public PrescriptionData() {}

    public PrescriptionData(String PrescriptionID) {
        this.PrescriptionID = PrescriptionID;
        this.MedicationID = null;
        this.ScheduleID = null;
        this.StartDate = null;
        this.EndDate = null;
        this.MedicationName = null;
        this.ScheduleShortName = null;
        this.NumberOfRefills = null;
        this.AssignedByDoctorID = null;
        this.AssignedByDoctorName = null;
    }

    public String getPrescriptionID () {
        return this.PrescriptionID;
    }

    public void setPrescriptionID (String prescriptionID) {
        this.PrescriptionID = prescriptionID;
    }

    public String getMedicationID () {
        return this.MedicationID;
    }

    public void setMedicationID (String MedicationID) {
        this.MedicationID = MedicationID;
    }

    public String getScheduleID () {
        return this.ScheduleID;
    }

    public void setScheduleID (String ScheduleID) {
        this.ScheduleID = ScheduleID;
    }
    public Date getStartDate () {
        return this.StartDate;
    }

    public void setStartDate (Date StartDate) {
        this.StartDate = StartDate;
    }
    public Date getEndDate () {
        return this.EndDate;
    }

    public void setEndDate (Date EndDate) {
        this.EndDate = EndDate;
    }
    public String getMedicationName () {
        return this.MedicationName;
    }

    public void setMedicationName (String MedicationName) {
        this.MedicationName = MedicationName;
    }
    public String getScheduleShortName () {
        return this.ScheduleShortName;
    }

    public void setScheduleShortName (String ScheduleShortName) {
        this.ScheduleShortName = ScheduleShortName;
    }
    public Integer getNumberOfRefills () {
        return this.NumberOfRefills;
    }

    public void setNumberOfRefills (Integer NumberOfRefills) {
        this.NumberOfRefills = NumberOfRefills;
    }
    public String getAssignedByDoctorID () {
        return this.AssignedByDoctorID;
    }

    public void setAssignedByDoctorID (String AssignedByDoctorID) {
        this.AssignedByDoctorID = AssignedByDoctorID;
    }
    public String getAssignedByDoctorName () {
        return this.AssignedByDoctorName;
    }

    public void setAssignedByDoctorName (String AssignedByDoctorName) {
        this.AssignedByDoctorName = AssignedByDoctorName;
    }

}
