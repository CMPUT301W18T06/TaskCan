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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.n8tech.taskcan.Controller.NetworkConnectionController;
import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;
import com.example.n8tech.taskcan.Models.User;

import javax.xml.datatype.Duration;

/**
 * SignInActivity handles user log in which sets the current user.
 * It takes in the following inputs from the user:
 * <ul><li>username</li><
 * li>password</li></ul>
 * It also allows for the user to create an account through the Sign Up button.
 * A valid user account is needed to use the app.
 *
 * @see SignUpActivity
 * @author CMPUT301W18T06
 */
public class SignInActivity extends Activity {

    private static final String ERR_MSG = "Your email or password is incorrect.\nIf you don't remember your password... well that sucks!";
    private EditText username;
    private EditText password;
    private UserList cacheList;
    private FileIO fileIO = new FileIO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.username = findViewById(R.id.name_field);
        this.password = findViewById(R.id.password_field);
        this.cacheList = this.fileIO.loadFromFile(getApplicationContext());
        int size = this.cacheList.getSize();
        if (size != 0) {
            User user = this.cacheList.getUser(size - 1);
            this.username.setText(user.getUsername());
            this.password.setText(user.getPassword());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        this.cacheList = this.fileIO.loadFromFile(getApplicationContext());
    }

    public void signInButtonClick(View v) {

        String usernameText = this.username.getText().toString();
        String passwordText = this.password.getText().toString();

        Boolean offlineValid = false;
        Boolean onlineValid = false;
        User offlineUser = null;
        User onlineUser = null;

        //Check the cache if user exists.
        for (User user : this.cacheList) {
            //Remove once we have set things logins we can remember

            Log.i("Username", user.getUsername());
            Log.i("Password", user.getPassword());
            //Loop through all users within cache and see if they entered a valid combination
            if (user.getUsername().equals(usernameText)) {
                offlineUser = user;
                if (user.getPassword().equals(passwordText)) {
                    offlineValid = true;
                }
            }
        }

        /*
         * Connects to the server and finds all users that have
         * the beginning string for the email at minimum
         * Then checks if the password and email match
         * and marks valid to begin using the app.
         */
        ElasticsearchController.SearchUser searchUser
                = new ElasticsearchController.SearchUser();
        searchUser.execute(usernameText);

        UserList userList = new UserList();
        try {
            userList = searchUser.get();
        } catch (Exception e) {
            Log.i("Error", String.valueOf(e));
        }

        for(User user : userList) {
            Log.i("testing", user.getId() + user.getUsername() + ":" + user.getPassword());

            if(user.getUsername().equals(usernameText) && user.getPassword().equals(passwordText)) {

                onlineUser = user;
                onlineValid = true;
            }
        }

        //Change behaviour dependent on which users were valid
        if(onlineValid) {

            this.cacheList.delUser(onlineUser);
            this.cacheList.addUser(onlineUser);
            this.fileIO.saveInFile(getApplicationContext(), this.cacheList);

            CurrentUserSingleton.setUser(onlineUser);
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);

            return;

        } else if (offlineValid && !NetworkConnectionController.isConnected(this)) {

            //Need to check if cache has the email somewhere and delete it
            this.cacheList.delUser(offlineUser);
            this.cacheList.addUser(offlineUser);
            this.fileIO.saveInFile(getApplicationContext(), this.cacheList);
            CurrentUserSingleton.setUser(offlineUser);
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);

            return;

        }

        Toast toast = Toast.makeText(getApplicationContext(), this.ERR_MSG, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void signUpButtonClick(View v) {
        if (NetworkConnectionController.isConnected(this)) {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please Connect To A Network!", Toast.LENGTH_LONG).show();
        }
    }
}
