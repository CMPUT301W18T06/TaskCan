package com.example.n8tech.taskcan;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.EditText;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.Views.SignUpActivity;

import com.robotium.solo.Solo;

/**
 * Intent testing for SignUpActivity class.
 *
 * @see com.example.n8tech.taskcan.Views.SignUpActivity
 * @author CMPUT301W18T06
 */
public class SignUpActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public SignUpActivityTest() {
        super(com.example.n8tech.taskcan.Views.SignUpActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }
    
    public void setUp() throws Exception {
        super.setUp();
        Instrumentation instrument = getInstrumentation();
        Activity activity = getActivity();
        solo = new Solo(instrument, activity);
    }

    public void testSignUpPage(){
        ElasticsearchController.SearchUser searchUser
                = new ElasticsearchController.SearchUser();
        searchUser.execute("userName");
        UserList userList = new UserList();
        try {
            userList = searchUser.get();
        } catch (Exception e) {
            Log.i("Error", "Couldn't load users from server");
        }

        if(userList.getSize() > 0) {
            ElasticsearchController.DeleteUser deleteUser
                    = new ElasticsearchController.DeleteUser();
            for(User newUser : userList){
                deleteUser.execute(newUser);
            }
        }
        solo.assertCurrentActivity("Wrong activity", SignUpActivity.class);


        solo.enterText((EditText) solo.getView(R.id.name_field), "User");
        solo.enterText((EditText) solo.getView(R.id.username_field), "user");
        solo.enterText((EditText) solo.getView(R.id.email_field), "user@n8tech.com");
        solo.enterText((EditText) solo.getView(R.id.password_field), "Password");
        solo.enterText((EditText) solo.getView(R.id.task_view_activity_bid_amount), "780-987-6542");
        solo.clickOnButton("Register");
        solo.waitForText("Please enter a valid username.\n");

        solo.clearEditText((EditText) solo.getView(R.id.name_field));
        solo.clearEditText((EditText) solo.getView(R.id.username_field));
        solo.clearEditText((EditText) solo.getView(R.id.email_field));
        solo.clearEditText((EditText) solo.getView(R.id.password_field));
        solo.clearEditText((EditText) solo.getView(R.id.task_view_activity_bid_amount));

        solo.enterText((EditText) solo.getView(R.id.name_field), "User");
        solo.enterText((EditText) solo.getView(R.id.username_field), "userName");
        solo.enterText((EditText) solo.getView(R.id.email_field), "user@n8tech.com");
        solo.enterText((EditText) solo.getView(R.id.password_field), "pass");
        solo.enterText((EditText) solo.getView(R.id.task_view_activity_bid_amount), "780-987-6542");
        solo.clickOnButton("Register");
        solo.waitForText("Please enter a valid password. Length of at least 6.\n");

        solo.clearEditText((EditText) solo.getView(R.id.name_field));
        solo.clearEditText((EditText) solo.getView(R.id.username_field));
        solo.clearEditText((EditText) solo.getView(R.id.email_field));
        solo.clearEditText((EditText) solo.getView(R.id.password_field));
        solo.clearEditText((EditText) solo.getView(R.id.task_view_activity_bid_amount));

        solo.enterText((EditText) solo.getView(R.id.name_field), "User");
        solo.enterText((EditText) solo.getView(R.id.username_field), "userName");
        solo.enterText((EditText) solo.getView(R.id.email_field), "user.com");
        solo.enterText((EditText) solo.getView(R.id.password_field), "Password");
        solo.enterText((EditText) solo.getView(R.id.task_view_activity_bid_amount), "780-987-6542");
        solo.clickOnButton("Register");
        solo.waitForText("Please enter a valid email.\n");

        solo.clearEditText((EditText) solo.getView(R.id.name_field));
        solo.clearEditText((EditText) solo.getView(R.id.username_field));
        solo.clearEditText((EditText) solo.getView(R.id.email_field));
        solo.clearEditText((EditText) solo.getView(R.id.password_field));
        solo.clearEditText((EditText) solo.getView(R.id.task_view_activity_bid_amount));

        solo.enterText((EditText) solo.getView(R.id.name_field), "User");
        solo.enterText((EditText) solo.getView(R.id.username_field), "userName");
        solo.enterText((EditText) solo.getView(R.id.email_field), "user@n8tech.com");
        solo.enterText((EditText) solo.getView(R.id.password_field), "Password");
        solo.enterText((EditText) solo.getView(R.id.task_view_activity_bid_amount), "1");
        solo.clickOnButton("Register");
        solo.waitForText("Please enter a valid phone number.\n");

        solo.clearEditText((EditText) solo.getView(R.id.name_field));
        solo.clearEditText((EditText) solo.getView(R.id.username_field));
        solo.clearEditText((EditText) solo.getView(R.id.email_field));
        solo.clearEditText((EditText) solo.getView(R.id.password_field));
        solo.clearEditText((EditText) solo.getView(R.id.task_view_activity_bid_amount));

        solo.enterText((EditText) solo.getView(R.id.name_field), "User");
        solo.enterText((EditText) solo.getView(R.id.username_field), "username");
        solo.enterText((EditText) solo.getView(R.id.email_field), "user@n8tech.com");
        solo.enterText((EditText) solo.getView(R.id.password_field), "Password");
        solo.enterText((EditText) solo.getView(R.id.task_view_activity_bid_amount), "780-987-6542");
        solo.clickOnButton("Register");
        assertTrue(solo.waitForActivity("SearchActivity"));
        }



    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
