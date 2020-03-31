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


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


@MediumTest
@RunWith(RobolectricTestRunner.class)
public class MedicalProfessionalAccessTest {

    private MedicalProfessionalAccess activity;

    @Before
    public void before() {
        activity = Robolectric.buildActivity(MedicalProfessionalAccess.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void test1() {
        assertNotNull(activity);

    }



}
