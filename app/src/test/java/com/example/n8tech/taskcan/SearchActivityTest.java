package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.SearchActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for SearchActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.SearchActivity
 * @author CMPUT301W18T06
 */
// TODO Fix solo errors
public class SearchActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public SearchActivityTest() {
        super(com.example.n8tech.taskcan.Views.SearchActivity.class);
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

    public void testSearchPage(){
        // TODO write test cases
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
