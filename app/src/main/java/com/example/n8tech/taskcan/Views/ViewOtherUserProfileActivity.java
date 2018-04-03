/*
 *  Copyright (c) 2018 Alexander Filbert, Carolyn Binns, Jeanna Somoza, JingMing Huang, Matthew Quigley, Nathanael Belayneh
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.n8tech.taskcan.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;

/**
 * ViewOtherProfileActivity displays the profile details of a user that is not the current user.
 *
 * @author CMPUT301W18T06
 */
public class ViewOtherUserProfileActivity extends ActivityHeader {

    private TextView username;
    private TextView profileName;
    private TextView email;
    private TextView contactInformation;

    private User user;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(ViewOtherUserProfileActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // TODO get userIdString from bundle

        Bundle extras = getIntent().getExtras();
        String userIdString = extras.getString("userId");

        ElasticsearchController.GetUser getUser
                = new ElasticsearchController.GetUser();
        getUser.execute(userIdString);


        try {
            user = getUser.get();
            Log.i("Got user", user.getUsername());
        } catch (Exception e) {
            Log.i("Error", String.valueOf(e));
        }


        //this.user = CurrentUserSingleton.getUser();          // TODO change to using elastic search to get the requester's profile info

        if(user.getEmail() != null) {
            Log.i("Testing", user.getEmail());
        }

        profileName = findViewById(R.id.view_other_profile_name_display);
        profileName.setText(user.getProfileName());

        username = findViewById(R.id.view_other_profile_username_display);
        username.setText(user.getUsername());

        email = findViewById(R.id.view_other_profile_email_display);
        email.setText(user.getEmail());

        contactInformation = findViewById(R.id.view_other_profile_phone_display);
        contactInformation.setText(user.getPhoneNumber());
    }

    /*
    @Override
    public void onBackPressed() {
        // TODO make sure all the task info is still there in viewtaskActivity?
        super.onBackPressed();
        Intent intent = new Intent(ViewOtherUserProfileActivity.this, ViewTaskActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }*/

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_other_user_profile;
    }

    @Override
    protected String getActivityTitle() {
        return "Profile View";
    }
}
