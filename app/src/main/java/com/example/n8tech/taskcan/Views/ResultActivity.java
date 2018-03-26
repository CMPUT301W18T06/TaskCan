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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Controller.SearchResultRecyclerAdapter;
import com.example.n8tech.taskcan.Controller.TaskViewRecyclerAdapter;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;

/** SearchActivity displays results from the current user's task search query.
 *
 * @see SearchActivity
 * @author CMPUT301W18T06
 */
public class ResultActivity extends ActivityHeader {
    public static final String SEARCH_MESSAGE = "com.example.n8tech.taskcan.SEARCH_MESSAGE";
    private TaskList resultTaskList = new TaskList();
    private User currentUser;
    private SearchResultRecyclerAdapter mAdapter;
    private TaskList myTaskList = new TaskList();
    private RecyclerView ResultRecyclerView;
    private RecyclerView.LayoutManager ResultLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ResultRecyclerView = findViewById(R.id.activity_result_result_recyclerview);

        this.currentUser = CurrentUserSingleton.getUser();
        this.myTaskList = this.currentUser.getMyTaskList();

        Intent i = getIntent();
        String searchQuery = i.getStringExtra(SEARCH_MESSAGE);
        ElasticsearchController.SearchTask searchTask
                = new ElasticsearchController.SearchTask();
        searchTask.execute(searchQuery, this.currentUser.getId());

        try {
            resultTaskList = searchTask.get();
        } catch (Exception e) {
            Log.i("Error", String.valueOf(e));
        }

        ResultRecyclerView.setHasFixedSize(true);

        ResultLayoutManager = new LinearLayoutManager(this);

        ResultRecyclerView.setLayoutManager(ResultLayoutManager);

        mAdapter = new SearchResultRecyclerAdapter(resultTaskList);
        ResultRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(ResultActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_result;
    }

    @Override
    protected String getActivityTitle() {
        return "Search Results";
    }
}
