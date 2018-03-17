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


public class AddTaskActivity extends ActivityHeader  {
    private Spinner categorySpinner;
    private Task newTask;
    private String taskId;
    private String activityName;
    private TextView statusText;
    private EditText maxBidText;
    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    private TextView taskStatusText;
    private int spinnerPosition;
    private User currentUser;
    private ArrayAdapter<CharSequence> categorySpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentUser = CurrentUserSingleton.getUser();
        Log.i("current user", currentUser.getUsername());

        findViewsByIdAndSetContent();





        // TODO implement Elastic search post here/save from file to save task information
        //
        //
    }



    private void findViewsByIdAndSetContent() {
        maxBidText = (EditText) findViewById(R.id.add_task_activity_money_edit_text);
        taskNameEditText = (EditText) findViewById(R.id.add_task_activity_name_edit_text);
        taskDescriptionEditText = (EditText) findViewById(R.id.add_task_activity_task_description_edit_text);
        taskStatusText = (TextView) findViewById(R.id.add_task_activity_status_edit_text);
        categorySpinner = (Spinner) findViewById(R.id.add_task_activity_category_spinner);


        taskNameEditText.setText("");
        taskDescriptionEditText.setText("");
        taskStatusText.setText("Requested");            // TODO set to task.getStatus()

        // set category spinner content and set to task's category
        SetCategorySpinnerContent("Other");
    }


    private void SetCategorySpinnerContent(String category) {
        categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        // set spinner category to be shown on activity
        spinnerPosition = categorySpinnerAdapter.getPosition(category);       // default value as Other
        categorySpinner.setSelection(spinnerPosition);
    }



    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(AddTaskActivity.this, nextClass);
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

    public void cancelButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MyTaskActivity.class);
        startActivity(intent);

    }

    public void saveButtonClick(View v){
        String taskName;
        String taskDescription;
        double maximumBid;
        String maximumBidString;
        String category;
        String location;            // TODO how to store location?
        String taskStatus = "Requested";
        boolean valid = Boolean.TRUE;


        // get fields and do error checking
        newTask = new Task();

        taskName = taskNameEditText.getText().toString();
        if (taskName.length() < 30 && !taskName.equals("")){
            // task name is valid, set task name
            newTask.setTaskTitle(taskName);
        } else {
            Toast.makeText(AddTaskActivity.this, "Name must be between 0 and 30 characters", Toast.LENGTH_LONG).show();
            valid = Boolean.FALSE;
        }


        taskDescription = taskDescriptionEditText.getText().toString();
        if (taskDescription.length() < 300 ){
            // valid
            newTask.setDescription(taskDescription);
        } else {
            Toast.makeText(AddTaskActivity.this, "Description must be less than 300 characters", Toast.LENGTH_LONG).show();
            valid = Boolean.FALSE;
        }

        newTask.setTaskStatus(taskStatus);

        category = categorySpinner.getSelectedItem().toString();
        newTask.setCategory(category);

        maximumBidString = maxBidText.getText().toString();

        if (!maximumBidString.equals("")) {
            maximumBid = Double.parseDouble(maximumBidString);
            newTask.setMaximumBid(Math.round(maximumBid*100.0)/100.0);                      // round to 2 decimal places
        } else {
            newTask.setMaximumBid(-1);
        }

        // TODO location validity testing
        //newTask.setLocation(?);

        // TODO set task owner to current user's uuid?

        // TODO: save task in elastic search or in file here

        Log.i("*** name", newTask.getTaskTitle());
        Log.i("*** desc", newTask.getDescription());
        Log.i("*** maximum bid",Double.toString(newTask.getMaximumBid()));
        Log.i("*** category",newTask.getCategory());
        Log.i("*** task uuid",newTask.getTaskId());

        if (valid) {
            Intent intent = new Intent(getApplicationContext(), MyTaskActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // add task to current user's myTasks list
            currentUser.addTask(newTask);
            startActivity(intent);
        }

    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_task;
    }

    @Override
    protected String getActivityTitle() {
        return "Add Task";
    }
}