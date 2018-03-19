package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.ResultActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for ResultActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.ResultActivity
 * @see com.example.n8tech.taskcan.Views.SearchActivity
 * @author CMPUT301W18T06
 */
// TODO Fix solo errors
public class ResultActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ResultActivityTest() {
        super(com.example.n8tech.taskcan.Views.ResultActivity.class);
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

    public void testResultsPage(){
        solo.assertCurrentActivity("Wrong activity", ResultActivity.class);

        // TODO write test cases
        // not implemented yet
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
