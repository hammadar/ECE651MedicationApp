package com.uwece651.medicationapp;

import android.Manifest;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.uwece651.medicationapp.R;
import com.uwece651.medicationapp.RegistrationPage;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.fail;

public class DoctorRegistrationUIActivityTest {
@Rule
public ActivityTestRule<RegistrationPage> mActivityTestRule = new ActivityTestRule<>(RegistrationPage.class);

@Rule
public GrantPermissionRule readPermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CALENDAR);

@Rule
public GrantPermissionRule writePermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_CALENDAR);

    @Test
    public void patientRegistrationTest() {

        //Select Patient radio button
        ViewInteraction patientRadioButton = onView(
                allOf(withId(R.id.radio_med_prof), withText("Medical Professional"),
                        isDisplayed()));
        patientRadioButton.perform(click());

        //click Submit button
        ViewInteraction submitButton = onView(
                allOf(withId(R.id.submitRegistrationInfo), withText("Submit"),
                        isDisplayed()));
        submitButton.perform(click());

        try{
            onView(withId(R.id.doctorAccessRelativeLayout)).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e){
            fail("Doctor Access Page did not open\n");
        }
    }
}


