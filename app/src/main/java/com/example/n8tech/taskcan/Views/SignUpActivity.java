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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.*;

/**
 * SignUpActivity allows for the user to create an account.
 * It takes in the following inputs from the user:
 * <ul><li>username</li>
 * <li>password</li>
 * <li>email address</li>
 * <li>phone number</li></ul>
 * It creates a unique user profile with the provided details.
 * A valid account is needed to use the app.
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText profileNameText;
    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText phoneNumberText;
    private UserList cacheList;
    private FileIO fileIO = new FileIO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.cacheList = this.fileIO.loadFromFile(getApplicationContext());

        this.profileNameText = findViewById(R.id.name_field);
        this.usernameText = findViewById(R.id.username_field);
        this.emailText = findViewById(R.id.email_field);
        this.passwordText = findViewById(R.id.password_field);
        this.phoneNumberText = findViewById(R.id.task_view_activity_bid_amount);
    }

    public void registerButtonClick(View v) {
        setResult(RESULT_OK);

        boolean profileNameValid = true;
        boolean usernameValid = true;
        boolean emailValid = true;
        boolean passwordValid = true;
        boolean phoneNumberValid = true;

        String profileName = this.profileNameText.getText().toString();
        String username = this.usernameText.getText().toString();
        String email = this.emailText.getText().toString();
        String password = this.passwordText.getText().toString();
        String contact = this.phoneNumberText.getText().toString();

        if (profileName.length() < 2 || profileName.length() > 50 || !StringUtils.isAlphaSpace(profileName)) {
            profileNameValid = false;
        }
        //Remove at some point, just to help with keeping things clean.
        if (profileName.equals("clearCache();")) {
            this.cacheList = new UserList();
            this.fileIO.saveInFile(getApplicationContext(),this.cacheList);
        }

        if (!checkUsernameValidity(username)) {
            usernameValid = false;
        }

        if (!checkEmailValidity(email)) {
            emailValid = false;
        }

        if (password.length() < 6) {
            passwordValid = false;
        }

        if (!checkContactValidity(contact)) {
            phoneNumberValid = false;
        }

        if (profileNameValid && usernameValid && emailValid && passwordValid && phoneNumberValid) {

            Log.i("Testing", "in here");
            contact = contact.replace("-", "");
            contact = contact.replace(".", "");
            contact = contact.substring(0, 3) + "-" + contact.substring(3, 6) + "-" + contact.substring(6, contact.length());
            Log.i("Testing", profileName);
            User newUser = new User(profileName, username, email, password, contact);
            Log.i("Testing", newUser.getProfileName());

            ElasticsearchController.AddUser addUser
                    = new ElasticsearchController.AddUser();
            addUser.execute(newUser);

            String completed = new String();
            try {
                completed = addUser.get();
                Log.i("Testing", completed);
            } catch (Exception e) {
                Log.i("Error", e.toString());
            }

            if(completed == "NoNetworkError") {

                this.cacheList.addUser(newUser);
                this.fileIO.saveInFile(getApplicationContext(),this.cacheList);

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                CurrentUserSingleton.setUser(newUser);
                startActivity(intent);

            } else {
                String errMsg = "Cannot connect to the network currently.\nPlease try again later";
                Toast toast = Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            //Determine which sections are invalid and create a message

            String errMsg = "";
            if (!usernameValid) {
                errMsg = errMsg + "Please enter a valid username.\n";
            }
            if (!emailValid) {
                errMsg = errMsg + "Please enter a valid email.\n";
            }
            if (!passwordValid) {
                errMsg = errMsg + "Please enter a valid password. Length of at least 6.\n";
            }
            if (!phoneNumberValid) {
                errMsg = errMsg + "Please enter a valid phone number.\n";
            }
            errMsg = errMsg.trim();

            Toast toast = Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean checkUsernameValidity(String username) {

        if(username.length() < 8 || username.length() > 50 || !StringUtils.isAlphanumeric(username)) {
            return false;
        }

        //Check if email is already taken
        ElasticsearchController.SearchUser searchUser
                = new ElasticsearchController.SearchUser();
        searchUser.execute(username);

        UserList userList = new UserList();

        try {
            userList = searchUser.get();
        } catch (Exception e) {
            Log.i("Error", "Couldn't load users from server");
        }

        for(User user : userList) {
            Log.i("testing", user.getId() + user.getUsername());
            if(user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkEmailValidity(String email) {
        // regex checking if email is valid email
        boolean isProperEmail = Pattern.compile("^[a-z0-9]+[@][a-z0-9]+[.][a-z0-9]{2,}").matcher(email).matches();

        if (!isProperEmail){
            return false;
        }
        return true;
    }

    private boolean checkContactValidity(String contact) {

        contact = contact.replace("-", "");
        contact = contact.replace(".", "");

        if(contact.length() != 10) {
            return false;
        }
        return true;
    }
}
