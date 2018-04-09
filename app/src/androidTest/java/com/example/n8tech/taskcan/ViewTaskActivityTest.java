package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Views.AddTaskActivity;


import com.example.n8tech.taskcan.Views.SearchActivity;
import com.example.n8tech.taskcan.Views.TaskDetailActivity;
import com.example.n8tech.taskcan.Views.ViewTaskActivity;
import com.robotium.solo.Solo;

/**
 * Intent testing for SearchActivity, ResultActivity, ViewTaskActivity, ViewOtherUserProfileActivity classes.
 *
 * @see com.example.n8tech.taskcan.Views.ViewTaskActivity
 * @author CMPUT301W18T06
 */

public class ViewTaskActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;


    public ViewTaskActivityTest() {
        //super(com.example.n8tech.taskcan.Views.ViewTaskActivity.class);
        super(SearchActivity.class);
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
        User user = new User();

        ElasticsearchController.GetUser getUser
                = new ElasticsearchController.GetUser();
        getUser.execute("AWKozkWeQiQvuO01t210");
        TaskList taskList = new TaskList();
        try {
            user = getUser.get();
        } catch (Exception e) {
            Log.i("Error", "Couldn't load user from server");
        }

        CurrentUserSingleton.setUser(user);

        solo.assertCurrentActivity("Wrong activity", SearchActivity.class);
        solo.clickOnButton("Search");

        assertTrue(solo.waitForActivity("ResultActivity"));
        solo.clickInRecyclerView(0);

        assertTrue(solo.waitForActivity("ViewTaskActivity"));
        solo.enterText((EditText) solo.getView(R.id.task_view_activity_bid_amount), "abcd");
        solo.clickOnButton("Confirm Bid");
        solo.waitForText("Please enter a valid number");
        solo.clearEditText((EditText) solo.getView(R.id.task_view_activity_bid_amount));

        solo.clickOnView(solo.getView(R.id.task_view_activity_requester_username_button));
        assertTrue(solo.waitForActivity("ViewOtherUserProfileActivity"));
        solo.goBack();

        solo.enterText((EditText) solo.getView(R.id.task_view_activity_bid_amount), "0.01");
        solo.clickOnButton("Confirm Bid");
        assertTrue(solo.waitForActivity("ResultActivity"));
        solo.clearEditText((EditText) solo.getView(R.id.task_view_activity_bid_amount));

    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
