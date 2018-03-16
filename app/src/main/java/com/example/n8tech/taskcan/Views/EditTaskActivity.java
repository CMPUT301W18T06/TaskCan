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

import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;

import static com.example.n8tech.taskcan.Models.CurrentUserSingleton.getUser;

public class EditTaskActivity extends ActivityHeader  {
    private Spinner categorySpinner;
    private String taskId;
    private String activityName;
    private TextView statusText;
    private TextView maxBidText;
    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    private int spinnerPosition;
    //private CurrentUserSingleton currentUserSingleton;
    private User currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentUser= CurrentUserSingleton.getUser();
        Log.i("current user", currentUser.getUsername());

        statusText = (TextView) findViewById(R.id.edit_task_activity_status_set_text);
        maxBidText = (TextView) findViewById(R.id.edit_task_activity_money_edit_text);
        taskNameEditText = (EditText) findViewById(R.id.edit_task_activity_name_edit_text);
        taskDescriptionEditText = (EditText) findViewById(R.id.edit_task_activity_task_description_edit_text);
        categorySpinner = (Spinner) findViewById(R.id.edit_task_activity_category_spinner);



        // get info from intent
        Intent detailsIntent = getIntent();
        Bundle bundle= detailsIntent.getExtras();
        taskId = (String) bundle.get("task id");

        // TODO implement Elastic search here/load from file to get task information then set the editTexts
        //
        //

        // determine if Edit Task or New Task
        activityName = (String) bundle.get("edit or new");
        Log.i("activity name", activityName);

        // TODO set menu toolbar to be var activityName






        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(spinnerAdapter);

        // set spinner category
        spinnerPosition = spinnerAdapter.getPosition("Outdoors");       // set item to be task.getStatus() when implemented
        categorySpinner.setSelection(spinnerPosition);


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
        Intent intent = new Intent(getApplicationContext(), EditTaskMapActivity.class);
        startActivity(intent);
    }

    public void setSpinnerContent(Spinner spinner){
        // depending on whether in addTask state or edit task state content will be different
        final String[] categories = {"Other", "Outdoors", "Indoors", "Pick-Up", "Drop-Off", "Animal", "Children", "Home Repair", "Landscape", "Computers", "Food", "Academics"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, categories);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

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
