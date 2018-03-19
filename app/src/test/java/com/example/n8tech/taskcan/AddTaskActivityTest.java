package com.example.n8tech.taskcan;

import android.test.ActivityInstrumentationTestCase2;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.AddTaskActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for AddTaskActivity class.
 *
 * @see AddTaskActivity
 * @author CMPUT301W18T06
 */
public class AddTaskActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public AddTaskActivityTest() {
        super(com.example.n8tech.taskcan.Views.AddTaskActivity.class);
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

    public void testAddTaskPage(){
        // TODO write test cases
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
