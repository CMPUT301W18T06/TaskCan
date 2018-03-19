package com.example.n8tech.taskcan;

import android.test.ActivityInstrumentationTestCase2;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.MyTaskActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for MyTaskActivity class.
 *
 * @see MyTaskActivity
 * @author CMPUT301W18T06
 */
public class MyTaskActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public MyTaskActivityTest() {
        super(com.example.n8tech.taskcan.Views.MyBidActivity.class);
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

    public void testMyTasksPage(){
        // TODO write test cases
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
