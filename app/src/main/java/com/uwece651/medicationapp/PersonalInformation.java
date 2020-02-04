package com.uwece651.medicationapp;

public class PersonalInformation {
    private String UID;
    private String type;

    public PersonalInformation (String UID) {
        this.UID = UID;
        this.type = null;
    }

    public String getUID () {
        return this.UID;
    }

    public String getType () {
        return this.type;
    }

    public void setType (String type) {
        this.type = type;
    }
}
