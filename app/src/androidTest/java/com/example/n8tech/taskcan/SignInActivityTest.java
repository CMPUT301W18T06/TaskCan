package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.SignInActivity;

import com.robotium.solo.Solo;

/**
 * Intent testing for SignInActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.SignInActivity
 * @author CMPUT301W18T06
 */
// TODO Fix solo errors
public class SignInActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public SignInActivityTest() {
        super(com.example.n8tech.taskcan.Views.SignInActivity.class);
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

    public void testSignInPage(){
        solo.assertCurrentActivity("Wrong activity", SignInActivity.class);
        // test account already set on the ElasticSearch server
        solo.enterText((EditText) solo.getView(R.id.name_field), "testtest");
        solo.enterText((EditText) solo.getView(R.id.password_field), "password");
        solo.clickOnButton("Sign In");

        assertTrue(solo.waitForActivity("SearchActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
