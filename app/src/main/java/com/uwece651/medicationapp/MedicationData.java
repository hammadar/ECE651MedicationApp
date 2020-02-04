package com.uwece651.medicationapp;

public class MedicationData {
    private String uid;
    private String BrandName;
    private String GenericName;
    private String UsedFor;
    private String Instructions;
    private String SideEffects;
    private String Precautions;
    private String DrugInteractions;
    private String Overdose;
    private String Notes;
    private String MissedDose;
    private String Storage;
    private String Contraindictions;


    public MedicationData (String uid) {
        this.uid = uid;
        this.BrandName = null;
        this.GenericName = null;
        this.UsedFor = null;
        this.Instructions = null;
        this.SideEffects = null;
        this.Precautions = null;
        this.DrugInteractions = null;
        this.Overdose = null;
        this.Notes = null;
        this.MissedDose = null;
        this.Storage = null;
        this.Contraindictions = null;
    }

    public String getUid () {
        return this.uid;
    }

    public String getBrandName() {
        return this.BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }
    public String getGenericName() {
        return this.GenericName;
    }

    public void setGenericName(String GenericName) {
        this.GenericName = GenericName;
    }
    public String getUsedFor() {
        return this.UsedFor;
    }

    public void setUsedFor(String UsedFor) {
        this.UsedFor = UsedFor;
    }
    public String getInstructions() {
        return this.Instructions;
    }

    public void setInstructions(String Instructions) {
        this.Instructions = Instructions;
    }
    public String getSideEffects() {
        return this.SideEffects;
    }

    public void setSideEffects(String SideEffects) {
        this.SideEffects = SideEffects;
    }
    public String getPrecautions() {
        return this.Precautions;
    }

    public void setPrecautions(String Precautions) {
        this.Precautions = Precautions;
    }
    public String getDrugInteractions() {
        return this.DrugInteractions;
    }

    public void setDrugInteractions(String DrugInteractions) {
        this.DrugInteractions = DrugInteractions;
    }
    public String getOverdose() {
        return this.Overdose;
    }

    public void setOverdose(String Overdose) {
        this.Overdose = Overdose;
    }
    public String getNotes() {
        return this.Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }
    public String getMissedDose() {
        return this.MissedDose;
    }

    public void setMissedDose(String MissedDose) {
        this.MissedDose = MissedDose;
    }
    public String getStorage() {
        return this.Storage;
    }

    public void setStorage(String Storage) {
        this.Storage = Storage;
    }
    public String getContraindictions() {
        return this.Contraindictions;
    }

    public void setContraindictions(String Contraindictions) {
        this.Contraindictions = Contraindictions;
    }
}

