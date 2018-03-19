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

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * EditProfileActivity allows for changes to be made to user profile details.
 * It takes in the following inputs from the user:
 * <ul><li>display name</li>
 * <li>email address</li>
 * <li>phone number</li></ul>
 * It updates any changes to the current user's profile.
 *
 * @author CMPUT301W18T06
 */
public class EditProfileActivity extends ActivityHeader {
    private User currentUser;
    private UserList cacheList;


    private EditText displayName;
    private EditText email;
    private EditText phoneNumber;
    private String newDisplayName;
    private String newEmail;
    private String newPhoneNumber;
    private FileIO fileIO = new FileIO();

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
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

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

        if (newDisplayName.length() < 1 || !StringUtils.isAlphaSpace(newDisplayName)){
            valid = Boolean.FALSE;
            Toast.makeText(EditProfileActivity.this, "Please enter valid name", Toast.LENGTH_LONG).show();
        }

        if (!checkContactValidity()){
            valid = Boolean.FALSE;
            Toast.makeText(EditProfileActivity.this, "Please enter valid phone number", Toast.LENGTH_LONG).show();
        }

        if (!newEmail.equals(currentUser.getEmail()) && !checkEmailValidity(newEmail)){
            valid = Boolean.FALSE;
            Toast.makeText(EditProfileActivity.this, "Please enter valid email address", Toast.LENGTH_LONG).show();
        }

        if (valid){
            cacheList = fileIO.loadFromFile(getApplicationContext());
            cacheList.delUser(currentUser);

            newPhoneNumber = newPhoneNumber.replace("-", "");
            newPhoneNumber = newPhoneNumber.replace(".", "");
            newPhoneNumber = newPhoneNumber.substring(0, 3) + "-" + newPhoneNumber.substring(3, 6) + "-"
                    + newPhoneNumber.substring(6, newPhoneNumber.length());

            currentUser.setEmail(newEmail);
            currentUser.setPhoneNumber(newPhoneNumber);
            currentUser.setUsername(newDisplayName);

            // TODO save with elastic search / in file
            cacheList.addUser(currentUser);

            ElasticsearchController.UpdateUser updateUser
                    = new ElasticsearchController.UpdateUser();
            updateUser.execute(currentUser);

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
        // regex checking if email is valid email
        boolean isProperEmail = Pattern.compile("^[a-z0-9]+[@][a-z0-9]+[.][a-z0-9]{2,}").matcher(email).matches();

        if (!isProperEmail){
            return false;
        }

        //Check if email is already taken
        ElasticsearchController.SearchUser searchUser
                = new ElasticsearchController.SearchUser();
        searchUser.execute(email);

        UserList userList = new UserList();

        try {
            userList = searchUser.get();
        } catch (Exception e) {
            Log.i("Error", "Couldn't load users from server");
            return false;
        }

        for(User user : userList) {
            Log.i("testing", user.getId() + user.getEmail());
            if(user.getEmail().equals(email)) {
                return false;
            }
        }
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
