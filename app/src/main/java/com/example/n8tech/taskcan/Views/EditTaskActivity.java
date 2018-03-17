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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;


public class EditTaskActivity extends ActivityHeader  {
    private Spinner categorySpinner;
    private Spinner taskStatusSpinner;
    private Task task;
    private String taskId;
    private String activityName;
    private TextView statusText;
    private TextView maxBidText;
    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    private int spinnerPosition;
    private User currentUser;
    private ArrayAdapter<CharSequence> categorySpinnerAdapter;
    private ArrayAdapter<CharSequence> statusSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentUser= CurrentUserSingleton.getUser();
        Log.i("current user", currentUser.getUsername());

        findViewsByIdAndSetContent();


        // get info from intent
        Intent detailsIntent = getIntent();
        Bundle bundle= detailsIntent.getExtras();
        taskId = (String) bundle.get("task id");

        // TODO implement Elastic search here/load from file to get task information then set the editTexts
        // task =
        //



    }

    private void setCategorySpinnerContent() {
        categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        // set spinner category to be shown on activity
        spinnerPosition = categorySpinnerAdapter.getPosition("Other");       // TODO:  set item to be task.getCategory() when implemented
        categorySpinner.setSelection(spinnerPosition);
    }

    private void setTaskStatusSpinnerContent() {
        statusSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_status_spinner_array, android.R.layout.simple_spinner_item);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskStatusSpinner.setAdapter(statusSpinnerAdapter);

        // set spinner category to be shown on activity
        spinnerPosition = statusSpinnerAdapter.getPosition("Requested");       // TODO:  set item to be task.getStatus() when implemented
        taskStatusSpinner.setSelection(spinnerPosition);
    }

    private void findViewsByIdAndSetContent() {
        maxBidText = (TextView) findViewById(R.id.edit_task_activity_money_edit_text);
        taskNameEditText = (EditText) findViewById(R.id.edit_task_activity_name_edit_text);
        taskDescriptionEditText = (EditText) findViewById(R.id.edit_task_activity_task_description_edit_text);
        categorySpinner = (Spinner) findViewById(R.id.edit_task_activity_category_spinner);
        taskStatusSpinner = (Spinner) findViewById(R.id.edit_task_activity_status_spinner);

        // TODO get task information here and set editTexts
        /*
        maxBidText.setText(task.getMaximumBid());       // set double to string
        taskNameEditText.setText(task.getTaskTitle());
        taskDescriptionEditText.setText(task.getDescription());

        */
        // set category spinner content and set to task's category
        setCategorySpinnerContent();
        setTaskStatusSpinnerContent();

    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(EditTaskActivity.this, nextClass);
            i.putExtra(SignInActivity.USER_MESSAGE, super.currentUser);
            startActivity(i);
        }
    }

    public void addPhotosButtonClick(View v) {

    }

    public void editLocationButtonClick(View v) {
        // TODO: should this be a map page and drop a pin or just entering an address and validating that address ???
        Intent intent = new Intent(getApplicationContext(), EditTaskMapActivity.class);
        startActivity(intent);
    }

    public void saveButtonClick(View v) {
        String taskName;
        String taskDescription;
        double maximumBid;
        String maximumBidString;
        String category;
        String taskStatus;
        String location;            // TODO how to store location?
        boolean valid = Boolean.TRUE;



        taskName = taskNameEditText.getText().toString();
        if (taskName.length() < 30 && !taskName.equals("")){
            // task name is valid, set task name
            task.setTaskTitle(taskName);
        } else {
            Toast.makeText(EditTaskActivity.this, "Name must be between 0 and 30 characters", Toast.LENGTH_LONG).show();
            valid = Boolean.FALSE;
        }


        taskDescription = taskDescriptionEditText.getText().toString();
        if (taskDescription.length() < 300 ){
            // valid
            task.setDescription(taskDescription);
        } else {
            Toast.makeText(EditTaskActivity.this, "Description must be less than 300 characters", Toast.LENGTH_LONG).show();
            valid = Boolean.FALSE;
        }

        taskStatus = taskStatusSpinner.getSelectedItem().toString();
        task.setTaskStatus(taskStatus);

        category = categorySpinner.getSelectedItem().toString();
        task.setCategory(category);

        maximumBidString = maxBidText.getText().toString();

        if (!maximumBidString.equals("")) {
            maximumBid = Double.parseDouble(maximumBidString);
            task.setMaximumBid(Math.round(maximumBid*100.0)/100.0);                      // round to 2 decimal places
        } else {
            task.setMaximumBid(-1);
        }

        // TODO location validity testing
        //newTask.setLocation(?);

        // TODO set task owner to current user's uuid?

        // TODO: save task in elastic search or in file here

        Log.i("*** name", task.getTaskTitle());
        Log.i("*** desc", task.getDescription());
        Log.i("*** maximum bid",Double.toString(task.getMaximumBid()));
        Log.i("*** category",task.getCategory());
        Log.i("*** task uuid",task.getTaskId());

        if (valid) {
            // TODO update task in user's task list
            
            Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


    }

    public void deleteButtonClick(View v){
        // remove task from currentusers task list and go back to my task activity
        currentUser.removeTask(task);
        Intent intent = new Intent(getApplicationContext(), MyTaskActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_edit_task;
    }

    @Override
    protected String getActivityTitle() {
        return "Edit Task";
    }
}
