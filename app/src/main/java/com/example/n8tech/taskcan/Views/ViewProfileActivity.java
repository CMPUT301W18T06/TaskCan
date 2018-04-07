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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;

/**
 * ViewProfileActivity displays the profile details of a user.
 *
 * @author CMPUT301W18T06
 */
public class ViewProfileActivity extends ActivityHeader {

    private TextView username;
    private TextView profileName;
    private TextView email;
    private TextView contactInformation;

    private User currentUser;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button editButton = findViewById(R.id.view_profile_edit_profile_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(ViewProfileActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.currentUser = CurrentUserSingleton.getUser();

        if(currentUser.getEmail() != null) {
            Log.i("Testing", currentUser.getEmail());
        }

        profileName = findViewById(R.id.view_profile_name_display);
        profileName.setText(currentUser.getProfileName());

        username = findViewById(R.id.view_profile_username_display);
        username.setText(currentUser.getUsername());

        email = findViewById(R.id.view_profile_email_display);
        email.setText(currentUser.getEmail());

        contactInformation = findViewById(R.id.view_profile_phone_display);
        contactInformation.setText(currentUser.getPhoneNumber());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_profile;
    }

    @Override
    protected String getActivityTitle() {
        return "My Profile";
    }
}
