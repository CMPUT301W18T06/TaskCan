package com.example.n8tech.taskcan.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Image;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;

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
    private TextView taskOwnerUsernameText;
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
        Bundle extras = getIntent().getExtras();
        currentTaskIndex = extras.getInt("taskIndex");
        task = this.currentUser.getMyTaskList().getTaskAtIndex(currentTaskIndex);
        findByIdsAndSetTextFields();
    }

    public void findByIdsAndSetTextFields() {
        taskNameText = findViewById(R.id.task_view_activity_name_text);
        taskDescriptionText = findViewById(R.id.task_view_activity_task_description_text);
        taskStatusText = findViewById(R.id.task_view_activity_status_text);
        taskCategoryText= findViewById(R.id.task_view_activity_category_text);
        taskOwnerUsernameText = findViewById(R.id.task_view_activity_requester_username_text);
        taskCurrentBidText = findViewById(R.id.task_view_activity_current_bid_text);
        taskMaxBidText = findViewById(R.id.task_view_activity_max_bid_text);

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
