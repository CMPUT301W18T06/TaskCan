package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.ViewTaskOnMapsActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for ViewTaskOnMapsActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.ViewTaskOnMapsActivity
 * @author CMPUT301W18T06
 */
public class ViewTaskOnMapsActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ViewTaskOnMapsActivityTest() {
        super(com.example.n8tech.taskcan.Views.ViewTaskOnMapsActivity.class);
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

    public void testViewTaskOnMapsPage(){

        solo.assertCurrentActivity("Wrong activity", ViewTaskOnMapsActivity.class);
        // TODO write test cases
        // geolocation not yet implemented
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
