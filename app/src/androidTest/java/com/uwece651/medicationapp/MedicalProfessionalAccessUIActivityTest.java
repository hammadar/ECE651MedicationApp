package com.uwece651.medicationapp;

import android.Manifest;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

public class MedicalProfessionalAccessUIActivityTest {

    @Rule
    public ActivityTestRule<MedicalProfessionalAccess> medicalProfessionalAccessActivityTestRule = new ActivityTestRule<>(MedicalProfessionalAccess.class);

    @Rule
    public GrantPermissionRule readPermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CALENDAR);

    @Rule
    public GrantPermissionRule writePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_CALENDAR);


    @Test
    public void buttonsTest() {
        //ViewInteraction retrievePatientDataButton = onView(allOf(withId(R.id.retrievePatientInfo), withText("Retrieve Patient Data"), isDisplayed()));
        onView(withId(R.id.retrievePatientInfo)).check(matches(isClickable()));
        onView(withId(R.id.addNewPatient)).check(matches(isClickable()));
        onView(withId(R.id.savePatientData)).check(matches(isClickable()));
        onView(withId(R.id.addNewMedication)).check(matches(isClickable()));
    }



}
