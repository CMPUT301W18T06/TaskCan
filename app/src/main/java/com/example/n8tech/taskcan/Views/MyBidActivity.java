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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;

import com.example.n8tech.taskcan.Controller.BidViewRecyclerAdapter;
import com.example.n8tech.taskcan.Controller.TaskViewRecyclerAdapter;
import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.BidList;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * MyBidActivity displays the list of tasks containing pending
 * or assigned bids made by the current user.
 * List shows task title, owner username, status and current bid.
 *
 * @author CMPUT301W18T06
 */
public class MyBidActivity extends ActivityHeader {
    private User currentUser;
    private TabHost mTabHost;
    private TaskList myTaskList = new TaskList();
    private BidList myBidList = new BidList();
    private RecyclerView PendingRecyclerView;
    private RecyclerView AssignedRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabHost = findViewById(R.id.tabHost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("pendingBidTab").setIndicator("Pending", null).setContent(R.id.pending));
        mTabHost.addTab(mTabHost.newTabSpec("assignedBidTab").setIndicator("Assigned", null).setContent(R.id.assigned));

        PendingRecyclerView = findViewById(R.id.my_bid_activity_recyclerview_pending);
        AssignedRecyclerView = findViewById(R.id.my_bid_activity_recyclerview_assigned);

    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(MyBidActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTabHost.setCurrentTab(0);

        this.currentUser = CurrentUserSingleton.getUser();
        myTaskList = new TaskList();

        // TODO get a list of tasks user has bid on
        // add tasks that the user has bid on into a tasklist
        for (Task task : currentUser.getBidTaskList()){
            if (task.getStatus().intern() == ("Bidded") || task.getStatus().intern() == ("Assigned")){
                this.myTaskList.addTask(task);
            }
        }

        if(currentUser.getEmail() != null) {
            Log.i("Testing", currentUser.getEmail());
        }

        PendingRecyclerView.setHasFixedSize(true);
        AssignedRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager pendingLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager assignedLayoutManager = new LinearLayoutManager(this);

        PendingRecyclerView.setLayoutManager(pendingLayoutManager);
        AssignedRecyclerView.setLayoutManager(assignedLayoutManager);

        final BidViewRecyclerAdapter mAdapter = new BidViewRecyclerAdapter(myTaskList);
        PendingRecyclerView.setAdapter(mAdapter);
        AssignedRecyclerView.setAdapter(mAdapter);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){

            @Override
            public void onTabChanged(String tabId) {
                int selectedTab = mTabHost.getCurrentTab();

                switch (selectedTab) {
                    case 0 :
                        myTaskList.clear();

                        for (Task task : currentUser.getBidTaskList()){
                            if (task.getStatus().intern() == "Bidded" || task.getStatus().intern() == "Assigned"){
                                myTaskList.addTask(task);
                            }
                        }
                        mAdapter.refresh(myTaskList);
                        break;

                    case 1 :
                        myTaskList.clear();

                        for (Task task : currentUser.getBidTaskList()){
                            if (task.getStatus().intern() == "Assigned" && task.getProviderUsername().intern() == currentUser.getUsername()){
                                myTaskList.addTask(task);
                            }
                        }
                        mAdapter.refresh(myTaskList);
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyBidActivity.this, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_bid;
    }

    @Override
    protected String getActivityTitle() {
        return "My Bid List";
    }

}
