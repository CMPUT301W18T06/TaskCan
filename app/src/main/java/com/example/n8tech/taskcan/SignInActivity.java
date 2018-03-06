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

package com.example.n8tech.taskcan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SignInActivity extends Activity {

    private static final String EXTRA_MESSAGE = "com.example.n8tech.taskcan.MESSAGE";
    private static final String CACHE_FILE = "cache.sav";
    private static final String ERR_MSG = "Your email or password is incorrect.\nIf you don't remember your password, find a guy named Nathanael";
    private EditText username;
    private EditText password;
    private ArrayList<User> cacheList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);

        Button signInButton = findViewById(R.id.sign_in_button);
        Button signUpButton = findViewById(R.id.sign_up_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean validCombination = false;

                for (User user : cacheList) {
                    //Remove once we have set things logins we can remember
                    Log.i("Email", user.getEmail());
                    Log.i("Password", user.getPassword());
                    //Loop through all users within cache and see if they entered a valid combination
                    if (user.getEmail().equals(username.getText().toString()) && user.getPassword().equals(password.getText().toString())) {

                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.putExtra(EXTRA_MESSAGE, username.toString());
                        startActivity(intent);
                        validCombination = true;
                    }
                }

                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //Admin entry remove eventually
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                    validCombination = true;
                }

                /*
                 * Check against json from elasticsearch if that does not work
                 */
                //Add code here

                //Add that user from the elasticsearch to the cache file and save it.
                //Do same as lines 74-76
                if(!validCombination) {
                    Toast toast = Toast.makeText(getApplicationContext(), ERR_MSG, Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }

    private void loadFromFile() {
        //Load a given JSON file

        try {
            FileInputStream fis = openFileInput(CACHE_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-23
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            cacheList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            cacheList = new ArrayList<User>();
            Log.i("No File", "Created New File");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
