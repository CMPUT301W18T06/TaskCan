package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.EditTaskMapActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for EditTaskMapActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.EditTaskMapActivity
 * @author CMPUT301W18T06
 */
// TODO Fix solo errors
public class EditTaskMapActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public EditTaskMapActivityTest() {
        super(com.example.n8tech.taskcan.Views.EditTaskMapActivity.class);
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

    public void testEditTaskMapPage(){
        // TODO write test cases
        // location services not implemented yet
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
