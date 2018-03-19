package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.n8tech.taskcan.Views.ViewCategoryActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for ViewCategoryActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.ViewCategoryActivity
 * @author CMPUT301W18T06
 */
// TODO Fix solo errors
public class ViewCategoryActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ViewCategoryActivityTest() {
        super(com.example.n8tech.taskcan.Views.ViewCategoryActivity.class);
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

    public void testViewCategoriesPage(){
        // TODO write test cases
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
