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
import android.widget.EditText;
import android.widget.TabHost;

import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyBidActivity extends ActivityHeader {
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabHost mTabHost = findViewById(R.id.tabHost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("pendingBidTab").setIndicator("Pending", null).setContent(R.id.pending));
        mTabHost.addTab(mTabHost.newTabSpec("assignedBidTab").setIndicator("Assigned", null).setContent(R.id.assigned));

    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(MyBidActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.currentUser = CurrentUserSingleton.getUser();


        Log.i("Testing", currentUser.getEmail());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
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
        return "My BidList";
    }

}
