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

package com.example.n8tech.taskcan.Views ;

import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.Html;

import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class SearchActivity extends ActivityHeader {

    public static final String SEARCH_MESSAGE = "com.example.n8tech.taskcan.SEARCH_MESSAGE";
    private EditText searchField;
    private User currentUser;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchField = findViewById(R.id.search_activity_search_field);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String userMsg = intent.getStringExtra(SignInActivity.USER_MESSAGE);
        setCurrentUser(userMsg);

        currentUser = gson.fromJson(userMsg, new TypeToken<User>(){}.getType());

        Log.i("Testing", currentUser.getEmail());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
    }

    public void searchButtonClick(View v) {
        String searchText = searchField.getText().toString();
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra(SEARCH_MESSAGE, searchText);
        startActivity(intent);
    }

    public void browseButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewCategoryActivity.class);
        startActivity(intent);
    }

    public void mapButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewTaskOnMapsActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search;
    }

    @Override
    protected String getActivityTitle() {
        return "Home";
    }


}
