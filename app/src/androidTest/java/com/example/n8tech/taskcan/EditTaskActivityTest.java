package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.n8tech.taskcan.Views.EditProfileActivity;
import com.example.n8tech.taskcan.Views.EditTaskActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for EditTaskActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.EditTaskActivity
 * @author CMPUT301W18T06
 */
public class EditTaskActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public EditTaskActivityTest() {
        super(com.example.n8tech.taskcan.Views.EditTaskActivity.class);
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

    public void testEditTaskPage(){
        solo.assertCurrentActivity("Wrong activity", EditTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_name_edit_text), "Task name");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_task_description_edit_text), "This is what the task does.");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_money_edit_text), "0.50");
        View view1 = solo.getView("edit_task_activity_status_spinner");
        solo.clickOnView(view1);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 1));
        View view2 = solo.getView("add_task_activity_category_spinner");
        solo.clickOnView(view2);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 1));
        solo.clickOnButton("Save");
        assertTrue(solo.waitForActivity("MyTaskActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}

