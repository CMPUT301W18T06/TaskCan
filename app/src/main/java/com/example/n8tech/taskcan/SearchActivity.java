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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.Html;

public class SearchActivity extends AppCompatActivity {

    public static final String SEARCH_MESSAGE = "com.example.n8tech.taskcan.SEARCH_MESSAGE"; // what is this???
    private EditText searchField;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.searchField = findViewById(R.id.search_activity_search_field);

        Intent intent = getIntent();
        String userMsg = intent.getStringExtra(SignInActivity.USER_MESSAGE);

        this.CreateToolBar();
        this.SetNavigationViewItemSelected();
    }

    private void SetNavigationViewItemSelected() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_menu_home: {
                                //Intent goToHome = new Intent(SearchActivity.this,SearchActivity.class);
                                //startActivityForResult(goToHome, 1);

                                break;
                            }
                            case R.id.nav_menu_my_tasks: {
                                Intent goToTasks = new Intent(SearchActivity.this, MyTaskActivity.class);
                                startActivityForResult(goToTasks, 1);

                                break;
                            }
                            case R.id.nav_menu_my_bids: {
                                Intent goToBids = new Intent(SearchActivity.this, MyBidActivity.class);
                                startActivityForResult(goToBids, 1);

                                break;
                            }
                            case R.id.nav_menu_my_profile: {
                                Intent goToProfile = new Intent(SearchActivity.this, ViewProfileActivity.class);
                                startActivityForResult(goToProfile, 1);

                                break;
                            }
                            case R.id.nav_menu_sign_out: {
                                Intent goToSignIn = new Intent(SearchActivity.this,SignInActivity.class);
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

    private void CreateToolBar() {
        Toolbar mainToolbar = findViewById(R.id.menu_toolbar);
        this.mDrawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Home");
        mainToolbar.setTitle(Html.fromHtml("<font color='#FFFFFFF'>Home </font>"));
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
        //mainToolbar.setTitle(Html.fromHtml("<font color='#FFFFFFF'>Home </font>"));
    }


    public void searchButton_onClick(View view) {
        String searchText = searchField.getText().toString();
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra(this.SEARCH_MESSAGE, searchText);
        startActivity(intent);
    }

    public void browseButton_onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewCategoryActivity.class);
        startActivity(intent);
    }

    public void mapButton_onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewTaskOnMapsActivity.class);
        startActivity(intent);
    }
}
