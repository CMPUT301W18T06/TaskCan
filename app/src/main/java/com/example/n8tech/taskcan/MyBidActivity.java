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

public class MyBidActivity extends AppCompatActivity {

    public static final String SEARCH_MESSAGE = "com.example.n8tech.taskcan.SEARCH_MESSAGE";
    private EditText searchField;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bid);
        Toolbar mainToolbar = findViewById(R.id.menu_toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("My Bids");
        mainToolbar.setTitle(Html.fromHtml("<font color='#FFFFFFF'>My Bids </font>"));
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);

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

        Intent intent = getIntent();
        String userMsg = intent.getStringExtra(SignInActivity.USER_MESSAGE);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_menu_home: {
                                Intent goToHome = new Intent(MyBidActivity.this,SearchActivity.class);
                                startActivityForResult(goToHome, 1);

                                break;
                            }
                            case R.id.nav_menu_my_tasks: {
                                Intent goToTasks = new Intent(MyBidActivity.this, MyTaskActivity.class);
                                startActivityForResult(goToTasks, 1);

                                break;
                            }
                            case R.id.nav_menu_my_bids: {
                                //Intent goToBids = new Intent(MyTaskActivity.this, MyBidActivity.class);
                                //startActivityForResult(goToBids, 1);

                                break;
                            }
                            case R.id.nav_menu_my_profile: {
                                Intent goToProfile = new Intent(MyBidActivity.this, ViewProfileActivity.class);
                                startActivityForResult(goToProfile, 1);

                                break;
                            }
                            case R.id.nav_menu_sign_out: {
                                Intent goToSignIn = new Intent(MyBidActivity.this, SignInActivity.class);
                                startActivityForResult(goToSignIn, 1);

                                // Need to add sign out functionality

                                break;
                            }
                        }
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return false;
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
        //mainToolbar.setTitle(Html.fromHtml("<font color='#FFFFFFF'>Home </font>"));
    }

}
