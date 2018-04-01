package com.example.n8tech.taskcan.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Image;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Locale;

/**
 * Task screen opened when selecting task of another user
 */

public class ViewTaskActivity extends ActivityHeader{

    public static final String IMAGES_KEY = "TaskDetailActivity_IMAGESKEY";
    private User currentUser;
    private Task task;
    private TextView taskNameText;
    private TextView taskDescriptionText;
    private TextView taskStatusText;
    private TextView taskCategoryText;
    private Button taskOwnerUsernameButton;
    private TextView taskCurrentBidText;
    private TextView taskMaxBidText;
    private int currentTaskIndex;
    private FileIO fileIO = new FileIO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.currentUser = CurrentUserSingleton.getUser();

        // TODO this needs to get the task via id, not index in current user's tasklist
        // TODO not one of the current user's tasks. need to look up task uuid via elastic search.
        Type taskType = new TypeToken<Task>(){}.getType();
        Intent intent = getIntent();
        Gson gson = new Gson();

        task = gson.fromJson(intent.getStringExtra("currentTask"), taskType);         // change this to the right task from the search
        findByIdsAndSetTextFields();
    }

    public void findByIdsAndSetTextFields() {
        taskNameText = (TextView) findViewById(R.id.task_view_activity_name_text);
        taskDescriptionText = (TextView) findViewById(R.id.task_view_activity_task_description_text);
        taskStatusText = (TextView) findViewById(R.id.task_view_activity_status_text);
        taskCategoryText= (TextView) findViewById(R.id.task_view_activity_category_text);
        taskOwnerUsernameButton = (Button) findViewById(R.id.task_view_activity_requester_username_button);
        taskCurrentBidText = (TextView) findViewById(R.id.task_view_activity_current_bid_text);
        taskMaxBidText = (TextView) findViewById(R.id.task_view_activity_max_bid_text);


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
        taskOwnerUsernameButton.setText(task.getOwnerUsername());

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

    public void taskDetailLocationButtonClick(View v) {
        if (task.getLocation() == null) {
            Toast.makeText(getApplicationContext(), "No location specified!",
                    Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), ViewTaskOnMapsActivity.class);
            intent.putExtra("taskIndex", currentTaskIndex);
            v.getContext().startActivity(intent);
        }
    }

    public void viewImagesButtonClick(View v){
        if (this.task.getImageList().getSize() == 0) {
            Toast.makeText(getApplicationContext(), "No images to show! Please add image!",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(getApplicationContext(), ViewImageSlideActivity.class);
            Bundle b = new Bundle();
            for (Image image : this.task.getImageList().getImages()) {
                image.recreateRecycledBitmap();
            }
            b.putParcelableArrayList(this.IMAGES_KEY, this.task.getImageList().getImages());
            i.putExtras(b);
            startActivity(i);
        }
    }

    public void requesterUsernameButtonClick(View v){
        Intent intent = new Intent(getApplicationContext(), ViewOtherUserProfileActivity.class);
        intent.putExtra("userId", task.getOwnerId());
        v.getContext().startActivity(intent);
    }


    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(ViewTaskActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_task;
    }

    @Override
    protected String getActivityTitle() {
        return "Task Details";
    }
}
