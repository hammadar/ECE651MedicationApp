package com.uwece651.medicationapp;


import androidx.test.filters.LargeTest;
import androidx.test.filters.MediumTest;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;



import static org.junit.Assert.fail;


@MediumTest
@RunWith(RobolectricTestRunner.class)
public class MedicalProfessionalAccessTest {

    @Rule
    public ActivityTestRule<MedicalProfessionalAccess> rule = new ActivityTestRule<>(MedicalProfessionalAccess.class);


    @Test
    public void test1() {
        MedicalProfessionalAccess activity = rule.getActivity();

    }



}
