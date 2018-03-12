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


import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.n8tech.taskcan.Models.ElasticsearchController;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

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

public class SignUpActivity extends AppCompatActivity {

    private static final String CACHE_FILE = "cache.sav";
    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText contactText;
    private UserList cacheList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loadFromFile();

        usernameText = findViewById(R.id.name_field);
        emailText = findViewById(R.id.email_field);
        passwordText = findViewById(R.id.password_field);
        contactText = findViewById(R.id.phone_field);

    }

    public void registerButtonClick(View v) {
        setResult(RESULT_OK);

        boolean usernameValid = true;
        boolean emailValid = true;
        boolean passwordValid = true;
        boolean contactValid = true;

        String name = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String contact = contactText.getText().toString();

        if (name.length() < 3 || !StringUtils.isAlphaSpace(name)) {
            usernameValid = false;
        }

        //Remove at some point, just to help with keeping things clean.
        if (name.equals("clearCache();")) {
            //cacheList = new ArrayList<User>();
            cacheList = new UserList();
            saveInFile();
        }

        if (!checkEmailValidity(email)) {
            emailValid = false;
        }

        if (password.length() <= 6) {
            passwordValid = false;
        }

        if (!checkContactValidity(contact)) {
            contactValid = false;
        }

        if (usernameValid && emailValid && passwordValid && contactValid) {
            //
            contact = contact.replace("-", "");
            contact = contact.replace(".", "");
            contact = contact.substring(0, 3) + "-" + contact.substring(3, 6) + "-" + contact.substring(6, contact.length());

            User newUser = new User(name, email, password, contact);

            ElasticsearchController.AddUser addUser
                    = new ElasticsearchController.AddUser();
            addUser.execute(newUser);

            String completed = "";
            try {
                completed = addUser.get();
                Log.i("Testing", completed);
            } catch (Exception e) {
                Log.i("Error", e.toString());
            }

            if(completed == "NoNetworkError") {
                //Completed without error, need a few different tests to add once ES is working as expected
                //Log.i("testing", newUser.getId());
                cacheList.addUser(newUser);
                saveInFile();

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                Gson gson = new Gson();
                intent.putExtra(SignInActivity.USER_MESSAGE, gson.toJson(newUser));
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
            if (!contactValid) {
                errMsg = errMsg + "Please enter a valid phone number.\n";
            }
            errMsg = errMsg.trim();

            Toast toast = Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean checkEmailValidity(String email) {
        /*
         * Will eventually check with ElasticSearch if email is already taken.
         */

        int emailAtIndex = email.indexOf("@");

        if (emailAtIndex == -1) {
            return false;
        }

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
            //cacheList = new ArrayList<User>();
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
