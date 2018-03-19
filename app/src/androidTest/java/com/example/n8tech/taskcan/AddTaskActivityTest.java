package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.n8tech.taskcan.Views.AddTaskActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for AddTaskActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.AddTaskActivity
 * @author CMPUT301W18T06
 */
// TODO Fix solo errors
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

    public void testAddTask(){
        // TODO write test cases
        solo.assertCurrentActivity("Wrong activity", AddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_name_edit_text), "Task name");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_task_description_edit_text), "This is what the task does.");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_money_edit_text), "0.50");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_status_edit_text), "Requested");
        View view1 = solo.getView("add_task_activity_category_spinner");
        solo.clickOnView(view1);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 1));
        solo.clickOnButton("Save");
        assertTrue(solo.waitForActivity("MyTaskActivity"));
            }

    public void testCancel(){
        solo.assertCurrentActivity("Wrong activity", AddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_name_edit_text), "Task name");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_task_description_edit_text), "This is what the task does.");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_money_edit_text), "0.50");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_status_edit_text), "Requested");
        View view1 = solo.getView("add_task_activity_category_spinner");
        solo.clickOnView(view1);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 1));
        solo.clickOnButton("Cancel");
        assertTrue(solo.waitForActivity("ViewProfileActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
