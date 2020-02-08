package com.uwece651.medicationapp;

public class PersonalInformation {
    private String UID;
    private String type;
    private String name;


    public PersonalInformation () {}

    public PersonalInformation (String UID) {
        this.UID = UID;
        this.type = null;
        //this.name = name;
    }

    public String getUID () {
        return this.UID;
    }

    public String getType () {
        return this.type;
    }

    public String getName () { return this.name; }

    public void setType (String type) {
        this.type = type;
    }

    public void setName (String name) { this.name = name; }
}
