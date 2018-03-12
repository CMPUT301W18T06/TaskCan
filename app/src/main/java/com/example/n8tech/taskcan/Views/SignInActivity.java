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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.n8tech.taskcan.Models.ElasticsearchController;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;
import com.example.n8tech.taskcan.Views.SignUpActivity;
import com.example.n8tech.taskcan.Models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SignInActivity extends Activity {

    public static final String USER_MESSAGE = "com.example.n8tech.taskcan.USER_MESSAGE";
    private static final String CACHE_FILE = "cache.sav";
    private static final String ERR_MSG = "Your email or password is incorrect.\nIf you don't remember your password... well that sucks!";
    private EditText username;
    private EditText password;
    private UserList cacheList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }

    public void signInButtonClick(View v) {

        //Need to reverse order for security purposes
        //Should check server before local cache

        Gson gson = new Gson();

        boolean validCombination = false;
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();

        for (User user : cacheList) {
            //Remove once we have set things logins we can remember
            Log.i("Email", user.getEmail());
            Log.i("Password", user.getPassword());
            //Loop through all users within cache and see if they entered a valid combination
            if (user.getEmail().equals(usernameText) && user.getPassword().equals(passwordText)) {

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra(USER_MESSAGE, gson.toJson(user)); //Change to userId once not null
                startActivity(intent);
                validCombination = true;
                return;
            }
        }

        if(usernameText.equals("admin") && passwordText.equals("admin")) {
            //Admin entry remove eventually
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            intent.putExtra(USER_MESSAGE, "admin");
            startActivity(intent);
            validCombination = true;
            return;
        }

        ElasticsearchController.GetUser getUser
                = new ElasticsearchController.GetUser();
        getUser.execute("email", usernameText);

        ArrayList<User> userList = new ArrayList<>();
        try {
            userList = getUser.get();
        } catch (Exception e) {
            Log.i("Error", "Couldn't load users from server");
        }

        for(User user : userList) {
            Log.i("testing", user.getId() + user.getEmail() + ":" + user.getPassword());

            if(user.getEmail().equals(usernameText) && user.getPassword().equals(passwordText)) {
                cacheList.addUser(user);
                saveInFile();

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra(USER_MESSAGE, gson.toJson(user));
                startActivity(intent);
                validCombination = true;
                return;
            }

        }

        if(!validCombination) {
            Toast toast = Toast.makeText(getApplicationContext(), ERR_MSG, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void signUpButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    private void loadFromFile() {
        //Load a given JSON file

        try {
            FileInputStream fis = openFileInput(CACHE_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-23
            Type listType = new TypeToken<UserList>(){}.getType();
            cacheList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            cacheList = new UserList();
            Log.i("No File", "Created New File");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    private void saveInFile() {
        //Save SubList to a JSON file

        try {
            FileOutputStream fos = openFileOutput(CACHE_FILE,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(cacheList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
