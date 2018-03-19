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
import android.widget.TextView;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * TaskDetailActivity displays the details of a specific task
 * and allows for the current user to place a bid.
 *
 * @author CMPUT301W18T06
 */
public class TaskDetailActivity extends ActivityHeader {
    private User currentUser;
    private Task task;
    private TextView taskNameText;
    private TextView taskDescriptionText;
    private TextView taskStatusText;
    private TextView taskCategoryText;
    private TextView taskOwnerUsernameText;
    private TextView taskCurrentBidText;
    private TextView taskMaxBidText;
    private int currentTaskIndex;
    private FileIO fileIO = new FileIO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentUser = CurrentUserSingleton.getUser();

        Bundle extras = getIntent().getExtras();
        currentTaskIndex = extras.getInt("taskIndex");
        task = this.currentUser.getMyTaskList().getTaskAtIndex(currentTaskIndex);
        findByIdsAndSetTextFields();

    }

    public void findByIdsAndSetTextFields() {
        taskNameText = (TextView) findViewById(R.id.task_details_activity_name_text);
        taskDescriptionText = (TextView) findViewById(R.id.task_details_activity_task_description_text);
        taskStatusText = (TextView) findViewById(R.id.task_details_activity_status_text);
        taskCategoryText= (TextView) findViewById(R.id.task_details_activity_category_text);
        taskOwnerUsernameText = (TextView) findViewById(R.id.task_details_activity_requester_username_text);
        taskCurrentBidText = (TextView) findViewById(R.id.task_details_activity_current_bid_text);
        taskMaxBidText = (TextView) findViewById(R.id.task_details_activity_max_bid_text);

        // set based on current task
        taskNameText.setText(task.getTaskTitle());

        // if empty task description set text to none
        if (task.getDescription().equals("")){
            taskDescriptionText.setText("None");
        } else {
            taskDescriptionText.setText(task.getDescription());
        }

        taskStatusText.setText(task.getStatus());
        taskCategoryText.setText(task.getCategory());
        taskOwnerUsernameText.setText(currentUser.getUsername());

        if (task.getCurrentBid() == -1){
            taskCurrentBidText.setText("None");
        }else{
            taskCurrentBidText.setText(String.format(Locale.CANADA,"%.2f", task.getCurrentBid()));
        }

        if (task.getMaximumBid() == -1){
            taskMaxBidText.setText("None");
        } else {
            taskMaxBidText.setText(String.format(Locale.CANADA,"%.2f", task.getMaximumBid()));
        }
    }


    public void deleteButtonClick(View v){
        // remove task from currentusers task list and go back to my task activity
        UserList cacheList = this.fileIO.loadFromFile(getApplicationContext());
        cacheList.delUser(this.currentUser);
        currentUser.removeTask(task);


        ElasticsearchController.UpdateUser updateUser
                = new ElasticsearchController.UpdateUser();
        updateUser.execute(currentUser);

        cacheList.addUser(this.currentUser);
        this.fileIO.saveInFile(getApplicationContext(), cacheList);

        Intent intent = new Intent(getApplicationContext(), MyTaskActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void editButtonClick(View view){
        Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
        intent.putExtra("taskIndex", currentTaskIndex);
        view.getContext().startActivity(intent);
    }

    public void viewBidsButtonClick(View v){
        Intent intent = new Intent(getApplicationContext(), ViewBidsActivity.class);
        startActivity(intent);
    }

    public void taskDetailLocationButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewTaskOnMapsActivity.class);
        intent.putExtra("taskIndex", currentTaskIndex);
        v.getContext().startActivity(intent);
    }

    public void viewImagesButtonClick(View v){

    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(TaskDetailActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected String getActivityTitle() {
        return "Task Details";
    }
}
