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
import android.view.MenuItem;
import android.widget.EditText;

import com.example.n8tech.taskcan.R;

public class MyBidActivity extends ActivityHeader {

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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_bid;
    }

    @Override
    protected String getActivityTitle() {
        return "My Bids";
    }

}
