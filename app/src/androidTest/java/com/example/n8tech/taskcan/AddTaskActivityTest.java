package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
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
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.Views.AddTaskActivity;

import com.example.n8tech.taskcan.Views.SignUpActivity;
import com.example.n8tech.taskcan.Views.TaskDetailActivity;
import com.robotium.solo.Solo;

import org.junit.Test;


/**
 * Intent testing for AddTaskActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.AddTaskActivity
 * @author CMPUT301W18T06
 */

public class AddTaskActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private User currentUser;
    private FileIO fileIO = new FileIO();
    private final String  ES_id = "AWKoJhQDGiQvuO01t16E";

    public AddTaskActivityTest() {
        super(com.example.n8tech.taskcan.Views.AddTaskActivity.class);
        this.currentUser = new User();
        //TODO: set this var to CurrentUserSingleton();
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
        ElasticsearchController.GetUser getUser
                = new ElasticsearchController.GetUser();
        getUser.execute(ES_id);

        try {
            currentUser = getUser.get();
        } catch (Exception e) {
            Log.i("Error", String.valueOf(e));
        }

        UserList cacheList = this.fileIO.loadFromFile(getActivity().getApplicationContext());

        this.currentUser.setProfileName("Test");
        this.currentUser.setUsername("tester");
        this.currentUser.setEmail("test@ualberta.ca");
        this.currentUser.setPassword("tester");
        this.currentUser.setPhoneNumber("4034034030");
        cacheList.addUser(this.currentUser);

        this.fileIO.saveInFile(getActivity().getApplicationContext(), cacheList);

        CurrentUserSingleton.setUser(currentUser);
        solo.assertCurrentActivity("Wrong activity", AddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_name_edit_text), "Task name");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_task_description_edit_text), "This is what the task does.");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_money_edit_text), "500.00");
        solo.pressSpinnerItem(0, 10);
        Boolean isCorrectCategory = false;
        isCorrectCategory = solo.isSpinnerTextSelected(0,"Other");
        assertTrue(isCorrectCategory);
        solo.pressSpinnerItem(0, -3);
        isCorrectCategory = solo.isSpinnerTextSelected(0,"Automotive Services");
        assertTrue(isCorrectCategory);
        //solo.clickOnButton("Save");
        //assertTrue(solo.waitForActivity("MyTaskActivity"));

        ElasticsearchController.SearchTask searchTask
                = new ElasticsearchController.SearchTask();
        searchTask.execute("This is what the task does.");
        TaskList taskList = new TaskList();
        try {
            taskList = searchTask.get();
        } catch (Exception e) {
            Log.i("Error", "Couldn't load users from server");
        }

        for(Task newTask : taskList){
            ElasticsearchController.DeleteTask deleteTask
                = new ElasticsearchController.DeleteTask();
            deleteTask.execute(newTask);
        }
    }

    // intent testing for TaskDetailActivity
    public void testTaskDetailsPage(){
        solo.assertCurrentActivity("Wrong activity", AddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_name_edit_text), "Task name");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_task_description_edit_text), "This is what the task does.");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_money_edit_text), "0.50");
        solo.pressSpinnerItem(0, 10);
        Boolean isCorrectCategory = false;
        isCorrectCategory = solo.isSpinnerTextSelected(0,"Other");
        assertTrue(isCorrectCategory);
        solo.pressSpinnerItem(0, -3);
        isCorrectCategory = solo.isSpinnerTextSelected(0,"Automotive Services");
        assertTrue(isCorrectCategory);
        //solo.clickOnButton("Save");
        //assertTrue(solo.waitForActivity("MyTaskActivity"));

        solo.assertCurrentActivity("Wrong activity", TaskDetailActivity.class);

        solo.clickOnButton("Edit");
        assertTrue(solo.waitForActivity("EditTaskActivity"));

        solo.clickOnButton("View Location");
        assertTrue(solo.waitForActivity("ViewTaskOnMapsActivity"));
        solo.goBack();

        solo.clickOnButton("Delete");
        assertTrue(solo.waitForActivity("MyTaskActivity"));
        assertFalse(solo.searchText("Task name"));
    }

    public void testCancel(){
        solo.assertCurrentActivity("Wrong activity", AddTaskActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_name_edit_text), "Task name");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_task_description_edit_text), "This is what the task does.");
        solo.enterText((EditText) solo.getView(R.id.add_task_activity_money_edit_text), "0.50");
        View view1 = solo.getView("add_task_activity_category_spinner");
        solo.clickOnView(view1);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 1));
        solo.clickOnButton("Cancel");
        assertTrue(solo.waitForActivity("MyTaskActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
