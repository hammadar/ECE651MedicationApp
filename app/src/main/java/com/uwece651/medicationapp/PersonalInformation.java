package com.uwece651.medicationapp;

public class PersonalInformation {
    private String uid;
    private String type;

    public PersonalInformation (String uid) {
        this.uid = uid;
        this.type = null;
    }

    public String getUid () {
        return this.uid;
    }

    public String getType () {
        return this.type;
    }

    public void setType (String type) {
        this.type = type;
    }
}
