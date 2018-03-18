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
import android.widget.EditText;
import android.widget.Toast;

import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;
import org.apache.commons.lang3.StringUtils;


public class EditProfileActivity extends ActivityHeader {
    private static final String CACHE_FILE = "cache.sav";
    private User currentUser;
    private UserList cacheList;


    private EditText displayName;
    private EditText email;
    private EditText phoneNumber;
    private String newDisplayName;
    private String newEmail;
    private String newPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.displayName = findViewById(R.id.edit_profile_name_display);
        this.email = findViewById(R.id.edit_profile_email_display);
        this.phoneNumber = findViewById(R.id.edit_profile_phone_display);

    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(EditProfileActivity.this, nextClass);
            i.putExtra(SignInActivity.USER_MESSAGE, super.currentUser);
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String userMsg = intent.getStringExtra(SignInActivity.USER_MESSAGE);
        //this.currentUser = this.gson.fromJson(userMsg, new TypeToken<User>(){}.getType());

        this.currentUser = CurrentUserSingleton.getUser();

        Log.i("Testing", this.currentUser.getEmail());

        this.displayName.setText(this.currentUser.getUsername());
        this.email.setText(this.currentUser.getEmail());
        this.phoneNumber.setText(this.currentUser.getPhoneNumber());
    }

    public void saveButtonClick(View v){
        // TODO validity checking. should they be able to change email/username?
        Boolean valid = Boolean.TRUE;

        newDisplayName = displayName.getText().toString();
        newEmail = email.getText().toString();
        newPhoneNumber = phoneNumber.getText().toString();

        if (newDisplayName.length() < 3 || !StringUtils.isAlphaSpace(newDisplayName)){
            valid = Boolean.FALSE;
            Toast.makeText(EditProfileActivity.this, "Name must be more than 3 characters", Toast.LENGTH_LONG).show();
        }

        if (!checkContactValidity()){
            valid = Boolean.FALSE;
            Toast.makeText(EditProfileActivity.this, "Please enter valid phone number", Toast.LENGTH_LONG).show();
        }

        if (!checkEmailValidity(newEmail)){
            valid = Boolean.FALSE;
            Toast.makeText(EditProfileActivity.this, "Please enter valid email address", Toast.LENGTH_LONG).show();
        }

        if (valid){
            currentUser.setEmail(newEmail);
            currentUser.setPhoneNumber(newPhoneNumber);
            currentUser.setUsername(newDisplayName);
            // TODO save with elastic search / in file

            Intent intent = new Intent(getApplicationContext(), ViewProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


    }

    private boolean checkContactValidity() {
        // TODO check this is actually a phone number, ie #s not letters.

        newPhoneNumber = newPhoneNumber.replace("-", "");
        newPhoneNumber = newPhoneNumber.replace(".", "");

        if(newPhoneNumber.length() != 10) {
            return false;
        }
        return true;
    }

    private boolean checkEmailValidity(String email) {
        /*
         * Will eventually check with ElasticSearch if email is already taken.
         */

        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");



        if (atIndex == -1) {
            return Boolean.FALSE;
        }
        if (dotIndex == -1) {
            return Boolean.FALSE;
        }
        if (dotIndex == (email.length()-1)){
            // no chars after dot
            return Boolean.FALSE;
        }
        if ((atIndex - dotIndex) > 0){
            // "@" comes after last "." in email
            return Boolean.FALSE;
        }


        //TODO elastic search checking ? not sure how this works @Q
        /*
        for(User user : cacheList) {
            if(user.getEmail().equals(email)){
                return false;
            }
        }

        ElasticsearchController.GetUser getUser
                = new ElasticsearchController.GetUser();
        getUser.execute("email", email);

        ArrayList<User> userList = new ArrayList<>();

        try {
            userList = getUser.get();
        } catch (Exception e) {
            Log.i("Error", "Couldn't load users from server");
        }

        for(User user : userList) {
            Log.i("testing", user.getId() + user.getEmail());
            if(user.getEmail() == email) {
                return false;
            }

        }*/

        return true;
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
