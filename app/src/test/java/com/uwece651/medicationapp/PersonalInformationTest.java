package com.uwece651.medicationapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonalInformationTest {

    @Test
    public void testConstructor() {
        PersonalInformation personalInformation = new PersonalInformation("123456");
        personalInformation.setName("Hammad");
        personalInformation.setType("Doctor");

        assertEquals("Testing uid: ", "123456", personalInformation.getUid());
        assertEquals("Testing name: ", "Hammad", personalInformation.getName());
        assertEquals("Testing type: ", "Doctor", personalInformation.getType());
    }
}