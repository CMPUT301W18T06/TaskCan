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
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import com.example.n8tech.taskcan.Controller.TaskViewRecyclerAdapter;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * MyTaskActivity displays the list of tasks made by the current user.
 *
 * @author CMPUT301W18T06
 */
public class MyTaskActivity extends ActivityHeader {

    private TaskList myTaskList = new TaskList();
    private User currentUser;
    private TabHost mTabHost;
    private RecyclerView RequestedRecyclerView;
    private RecyclerView AssignedRecyclerView;
    private RecyclerView ArchivedRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabHost = findViewById(R.id.tabHost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("requestedTaskTab").setIndicator("Requested", null).setContent(R.id.requested));
        mTabHost.addTab(mTabHost.newTabSpec("assignedTaskTab").setIndicator("Assigned", null).setContent(R.id.assigned));
        mTabHost.addTab(mTabHost.newTabSpec("archivedTaskTab").setIndicator("Archived", null).setContent(R.id.archived));

        RequestedRecyclerView = findViewById(R.id.my_task_activity_recyclerview_requested);
        AssignedRecyclerView = findViewById(R.id.my_task_activity_recyclerview_assigned);
        ArchivedRecyclerView = findViewById(R.id.my_task_activity_recyclerview_archived);


    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(MyTaskActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mTabHost.setCurrentTab(0);

        currentUser = CurrentUserSingleton.getUser();
        myTaskList = new TaskList();

        for (Task task : currentUser.getMyTaskList()) {
            if (task.getStatus().intern() == "Requested") {
                myTaskList.addTask(task);
            }
        }

        RequestedRecyclerView.setHasFixedSize(true);
        AssignedRecyclerView.setHasFixedSize(true);
        ArchivedRecyclerView.setHasFixedSize(true);

        // TODO potentially change to use one layout manager instead which is set based on the active tab
        RecyclerView.LayoutManager requestedLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager assignedLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager archivedLayoutManager = new LinearLayoutManager(this);

        RequestedRecyclerView.setLayoutManager(requestedLayoutManager);
        AssignedRecyclerView.setLayoutManager(assignedLayoutManager);
        ArchivedRecyclerView.setLayoutManager(archivedLayoutManager);

        final TaskViewRecyclerAdapter mAdapter = new TaskViewRecyclerAdapter(myTaskList);
        RequestedRecyclerView.setAdapter(mAdapter);
        AssignedRecyclerView.setAdapter(mAdapter);
        ArchivedRecyclerView.setAdapter(mAdapter);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){

            @Override
            public void onTabChanged(String tabId) {
                int selectedTab = mTabHost.getCurrentTab();

                switch (selectedTab) {
                    case 0 :
                        myTaskList.clear();

                        for (Task task : currentUser.getMyTaskList()) {
                            if (task.getStatus().intern() == "Requested") {
                                myTaskList.addTask(task);
                            }
                        }
                        mAdapter.refresh(myTaskList);
                        break;

                    case 1 :
                        myTaskList.clear();

                        for (Task task : currentUser.getMyTaskList()) {
                            if (task.getStatus().intern() == "Assigned") {
                                myTaskList.addTask(task);
                            }
                        }
                        mAdapter.refresh(myTaskList);
                        break;

                    case 2 :
                        myTaskList.clear();

                        for (Task task : currentUser.getMyTaskList()) {
                            if (task.getStatus().intern() == "Done") {
                                myTaskList.addTask(task);
                            }
                        }
                        mAdapter.refresh(myTaskList);
                        break;
                }
            }
        });

        // Log.i("Testing", this.currentUser.getEmail());
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
        Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_task;
    }

    @Override
    protected String getActivityTitle() {
        return "My TaskList";
    }

}
