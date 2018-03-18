package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.SignInActivity;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by nbelayne on 3/18/18.
 */

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
        solo.enterText((EditText) solo.getView(R.id.username_field), "admin");
        solo.enterText((EditText) solo.getView(R.id.password_field), "admin");
        solo.clickOnButton("Sign In");

        assertTrue(solo.waitForActivity("SearchActivity"));
        solo.goBack();
        assertTrue(solo.waitForActivity("SignInActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
