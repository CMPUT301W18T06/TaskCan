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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.n8tech.taskcan.Controller.CategoryGridViewAdapter;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ViewCategoryActivity allows the user to select task categories
 * and browse tasks within those categories. This is accessible from SearchActivity.
 *
 * @see SearchActivity
 * @author CMPUT301W18T06
 */

public class ViewCategoryActivity extends ActivityHeader {
    GridView categoryGridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.categoryGridview = findViewById(R.id.activity_view_category_grid);


        this.initializeCategoryContents();
    }

    private void initializeCategoryContents() {
        this.categoryGridview.setAdapter(new CategoryGridViewAdapter(this));
    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(ViewCategoryActivity.this, nextClass);
            startActivity(i);
        }
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }*/

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_category;
    }

    @Override
    protected String getActivityTitle() {
        return "Browse Categories";
    }
}
