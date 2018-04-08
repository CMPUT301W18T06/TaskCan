package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Views.AddTaskActivity;

import com.example.n8tech.taskcan.Views.TaskDetailActivity;
import com.example.n8tech.taskcan.Views.ViewTaskActivity;
import com.robotium.solo.Solo;

/**
 * Intent testing for ViewTaskActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.ViewTaskActivity
 * @author CMPUT301W18T06
 */

public class ViewTaskActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ViewTaskActivityTest() {
        super(com.example.n8tech.taskcan.Views.ViewTaskActivity.class);
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

    public void testViewTask(){

        solo.assertCurrentActivity("Wrong activity", ViewTaskActivity.class);
        // TODO write test cases
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
