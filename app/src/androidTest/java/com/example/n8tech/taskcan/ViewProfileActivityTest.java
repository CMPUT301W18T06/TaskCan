package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.SignInActivity;
import com.example.n8tech.taskcan.Views.ViewProfileActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for ViewProfileActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.ViewProfileActivity
 * @author CMPUT301W18T06
 */
// TODO Fix solo errors
public class ViewProfileActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ViewProfileActivityTest() {
        super(com.example.n8tech.taskcan.Views.ViewProfileActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Instrumentation instrument = getInstrumentation();
        Activity activity = getActivity();
        solo = new Solo(instrument, activity);
    }

    public void testViewProfilePage(){
        solo.assertCurrentActivity("Wrong activity", ViewProfileActivity.class);
        solo.clickOnButton("Edit Profile");
        assertTrue(solo.waitForActivity("EditProfileActivity"));
        solo.goBack();
        assertTrue(solo.waitForActivity("ViewProfileActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
