package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.EditProfileActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for EditProfileActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.EditProfileActivity
 * @author CMPUT301W18T06
 */
// TODO Fix solo errors
public class EditProfileActivityTest extends ActivityInstrumentationTestCase2{
    private Solo solo;

    public EditProfileActivityTest() {
        super(com.example.n8tech.taskcan.Views.EditProfileActivity.class);
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

    public void testEditProfilePage(){
        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_profile_username_display), "john123");
        solo.enterText((EditText) solo.getView(R.id.edit_profile_name_display), "John");
        solo.enterText((EditText) solo.getView(R.id.edit_profile_email_display), "john@n8tech.com");
        solo.enterText((EditText) solo.getView(R.id.edit_profile_phone_display), "780-555-5555");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForActivity("MyTaskActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
