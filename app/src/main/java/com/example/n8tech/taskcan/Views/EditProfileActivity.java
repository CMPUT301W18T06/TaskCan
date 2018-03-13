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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EditProfileActivity extends ActivityHeader {

    private User currentUser;
    private Gson gson = new Gson();

    private EditText displayName;
    private EditText id;
    private EditText email;
    private EditText contactInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String userMsg = intent.getStringExtra(SignInActivity.USER_MESSAGE);
        setCurrentUser(userMsg);
        currentUser = gson.fromJson(userMsg, new TypeToken<User>(){}.getType());

        Log.i("Testing", currentUser.getEmail());

        /*id = findViewById(R.id.edit_profile_username_display);
        if(currentUser.getId() == null) {
            id.setText("Not yet set");
        } else {
            id.setText("");
        }*/
        displayName = findViewById(R.id.edit_profile_name_display);
        displayName.setText(currentUser.getUsername());
        email = findViewById(R.id.edit_profile_email_display);
        email.setText(currentUser.getEmail());
        contactInformation = findViewById(R.id.edit_profile_phone_display);
        contactInformation.setText(currentUser.getContactInformation());
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
    }*/

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected String getActivityTitle() {
        return "Edit Profile";
    }
}
