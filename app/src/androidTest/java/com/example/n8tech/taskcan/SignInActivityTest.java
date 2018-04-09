package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Views.SignInActivity;

import com.robotium.solo.Solo;

/**
 * Intent testing for SignInActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.SignInActivity
 * @author CMPUT301W18T06
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
        // test account already set on the ElasticSearch server
        solo.enterText((EditText) solo.getView(R.id.name_field), "username");
        solo.enterText((EditText) solo.getView(R.id.password_field), "wrongpassword");
        solo.clickOnButton("Sign In");
        solo.waitForText("Your email or password is incorrect.\nIf you don't remember your password... well that sucks!");

        solo.clearEditText((EditText) solo.getView(R.id.name_field));
        solo.clearEditText((EditText) solo.getView(R.id.password_field));

        solo.enterText((EditText) solo.getView(R.id.name_field), "testCaseUser");
        solo.enterText((EditText) solo.getView(R.id.password_field), "password");
        solo.clickOnButton("Sign In");
        assertTrue(solo.waitForActivity("SearchActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
