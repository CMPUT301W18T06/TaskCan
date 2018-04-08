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
import com.robotium.solo.Solo;

/**
 * Intent testing for ViewOtherUserProfile class.
 *
 * @see com.example.n8tech.taskcan.Views.ViewOtherUserProfileActivity
 * @author CMPUT301W18T06
 */
public class ViewOtherUserProfileActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ViewOtherUserProfileActivityTest() {
        super(com.example.n8tech.taskcan.Views.ViewOtherUserProfileActivity.class);
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

    public void testViewOtherUserProfile(){
        // TODO write test cases
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
