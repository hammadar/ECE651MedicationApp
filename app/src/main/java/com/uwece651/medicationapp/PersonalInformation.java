package com.uwece651.medicationapp;

public class PersonalInformation {
    private String uid;
    private String type;
    private String name;


    public PersonalInformation () {}

    public PersonalInformation (String uid) {
        this.uid = uid;
        this.type = null;
        //this.name = name;
    }

    public String getUid () {
        return this.uid;
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
