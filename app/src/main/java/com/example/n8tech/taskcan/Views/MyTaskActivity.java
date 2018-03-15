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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MyTaskActivity extends ActivityHeader {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*  TabHost appears to require a Fragment layout and this needs to go into onCreateView
        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec requestsTab = tabHost.newTabSpec("Requests Tab");
        TabHost.TabSpec assignedTab = tabHost.newTabSpec("Assigned Tab");
        TabHost.TabSpec archivedTab = tabHost.newTabSpec("Archived Tab");

        requestsTab.setIndicator("Tab1");
        //requestsTab.setContent(new Intent(this,TabActivity1.class));

        assignedTab.setIndicator("Tab2");
        //assignedTab.setContent(new Intent(this,TabActivity2.class));

        archivedTab.setIndicator("Tab3");
        //archivedTab.setContent(new Intent(this,TabActivity3.class));

        tabHost.addTab(requestsTab);
        tabHost.addTab(assignedTab);
        tabHost.addTab(archivedTab); */

    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(MyTaskActivity.this, nextClass);
            i.putExtra(SignInActivity.USER_MESSAGE, super.currentUser);
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String userMsg = intent.getStringExtra(SignInActivity.USER_MESSAGE);
        setCurrentUser(userMsg);
        Gson gson = new Gson();
        this.currentUser = gson.fromJson(userMsg, new TypeToken<User>(){}.getType());

        Log.i("Testing", this.currentUser.getEmail());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyTaskActivity.this, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void newTaskButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_task;
    }

    @Override
    protected String getActivityTitle() {
        return "My Tasks";
    }

}
