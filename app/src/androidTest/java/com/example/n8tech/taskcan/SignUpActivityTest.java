package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.SearchActivity;
import com.example.n8tech.taskcan.Views.SignUpActivity;

import com.robotium.solo.Solo;

/**
 * Intent testing for SignUpActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.SignUpActivity
 * @author CMPUT301W18T06
 */
// TODO Fix solo errors
public class SignUpActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public SignUpActivityTest() {
        super(com.example.n8tech.taskcan.Views.SignUpActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }
    
    public void setUp() throws Exception {
        super.setUp();
        Instrumentation instrument = getInstrumentation();
        Activity activity = getActivity();
        solo = new Solo(instrument, activity);
    }

    public void testSignUpPage(){
        solo.assertCurrentActivity("Wrong activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.name_field), "User");
        solo.enterText((EditText) solo.getView(R.id.username_field), "userName");
        solo.enterText((EditText) solo.getView(R.id.email_field), "user@n8tech.com");
        solo.enterText((EditText) solo.getView(R.id.password_field), "Password");
        solo.enterText((EditText) solo.getView(R.id.phone_field), "780-987-6542");
        solo.clickOnButton("Register");
        //assertTrue(solo.waitForActivity("SearchActivity"));
        assertTrue(solo.waitForActivity(SearchActivity.class));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
