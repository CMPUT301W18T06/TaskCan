package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import com.example.n8tech.taskcan.FileIO;
import android.util.Log;
import android.widget.EditText;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.Views.EditProfileActivity;

import com.robotium.solo.Solo;


/**
 * Intent testing for EditProfileActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.EditProfileActivity
 * @author CMPUT301W18T06
 */

// TODO: MAKE THIS PASS BY FIXING CACHE?
public class EditProfileActivityTest extends ActivityInstrumentationTestCase2{
    private Solo solo;

    public EditProfileActivityTest() {
        super(com.example.n8tech.taskcan.Views.EditProfileActivity.class);
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


    public void testEditProfilePage(){
        User user = new User();

        ElasticsearchController.GetUser getUser
                = new ElasticsearchController.GetUser();
        getUser.execute("AWKsWYuEWYXyFXWHYo_M");
        TaskList taskList = new TaskList();
        try {
            user = getUser.get();
        } catch (Exception e) {
            Log.i("Error", "Couldn't load user from server");
        }


        FileIO fileIO = new FileIO();
        CurrentUserSingleton currentUser = new CurrentUserSingleton();
        currentUser.setUser(user);
        UserList cacheList = fileIO.loadFromFile(InstrumentationRegistry.getContext());
        cacheList.addUser(user);
        fileIO.saveInFile(InstrumentationRegistry.getContext(), cacheList);

        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edit_profile_name_display), "John");
        solo.enterText((EditText) solo.getView(R.id.edit_profile_email_display), "john@n8tech.com");
        solo.enterText((EditText) solo.getView(R.id.edit_profile_phone_display), "780-555-5555");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForActivity("ViewProfileActivity"));
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
