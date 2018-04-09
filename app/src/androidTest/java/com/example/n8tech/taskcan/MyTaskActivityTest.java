package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

import com.example.n8tech.taskcan.Views.MyTaskActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for MyTaskActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.MyTaskActivity
 * @author CMPUT301W18T06
 */

public class MyTaskActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public MyTaskActivityTest() {
        super(com.example.n8tech.taskcan.Views.MyTaskActivity.class);
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
        solo.assertCurrentActivity("Wrong activity", MyTaskActivity.class);
        solo.clickOnButton("New Task");
        assertTrue(solo.waitForActivity("AddTaskActivity"));
        solo.goBack();
        assertTrue(solo.waitForActivity("MyTaskActivity"));
        solo.goBack();
        assertTrue(solo.waitForActivity("SearchActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
