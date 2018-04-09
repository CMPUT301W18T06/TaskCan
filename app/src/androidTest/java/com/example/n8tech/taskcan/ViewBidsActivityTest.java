package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Views.MyTaskActivity;
import com.example.n8tech.taskcan.Views.ViewBidsActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for MyTaskActivity, TaskDetailActivity, ViewBidsActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.ViewBidsActivity
 * @author CMPUT301W18T06
 */
// TODO: MAKE THIS PASS
public class ViewBidsActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public ViewBidsActivityTest() {
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

    public void testViewBidsPage(){
        User user = new User();
        Task task = new Task("Task", "Task description", "testCaseUser", "AWKsWYuEWYXyFXWHYo_M", "Other");


        ElasticsearchController.AddTask addTask
                = new ElasticsearchController.AddTask();
        addTask.execute(task);

        ElasticsearchController.GetUser getUser
                = new ElasticsearchController.GetUser();
        getUser.execute("AWKsWYuEWYXyFXWHYo_M");
        TaskList taskList = new TaskList();
        try {
            user = getUser.get();
        } catch (Exception e) {
            Log.i("Error", "Couldn't load user from server");
        }


        CurrentUserSingleton.setUser(user);
        user.addTask(task);

        solo.assertCurrentActivity("Wrong activity", MyTaskActivity.class);

    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
