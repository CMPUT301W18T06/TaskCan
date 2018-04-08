package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.ViewBidsActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for ViewBidsActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.ViewBidsActivity
 * @author CMPUT301W18T06
 */
    // bids not implemented yet
public class ViewBidsActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ViewBidsActivityTest() {
        super(com.example.n8tech.taskcan.Views.ViewBidsActivity.class);
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

    public void testViewBidsPage(){

        solo.assertCurrentActivity("Wrong activity", ViewBidsActivity.class);
        // TODO write test cases
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
