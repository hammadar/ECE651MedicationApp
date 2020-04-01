package com.uwece651.medicationapp;

import android.Manifest;

import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonalInformationTest {

    @Rule
    public GrantPermissionRule readPermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CALENDAR);

    @Rule
    public GrantPermissionRule writePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_CALENDAR);

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
