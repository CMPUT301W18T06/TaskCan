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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Image;
import com.example.n8tech.taskcan.Models.ImageList;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * TaskDetailActivity displays the details of a specific task
 * and allows for the current user to place a bid.
 *
 * @author CMPUT301W18T06
 */
public class TaskDetailActivity extends ActivityHeader {
    public static final String IMAGES_KEY = "TaskDetailActivity_IMAGESKEY";
    private User currentUser;
    private Task task;
    private TextView taskNameText;
    private TextView taskDescriptionText;
    private TextView taskStatusText;
    private TextView taskCategoryText;
    private TextView taskCurrentBidText;
    private TextView taskMaxBidText;
    private ImageView taskThumbnail;
    private Button editButton;
    private Button deleteButton;
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

        Type taskType = new TypeToken<Task>(){}.getType();
        Intent intent = getIntent();
        Gson gson = new Gson();

        task = gson.fromJson(intent.getStringExtra("currentTask"), taskType);     // change this to the right task from the search
//        Log.i("taskid", task.getId());
        this.currentTaskIndex = this.currentUser.getMyTaskList().getIndexOfTask(task);
        findByIdsAndSetTextFields();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Type taskType = new TypeToken<Task>(){}.getType();
        Intent intent = getIntent();
        Gson gson = new Gson();
        task = gson.fromJson(intent.getStringExtra("currentTask"), taskType);     // change this to the right task from the search
        findByIdsAndSetTextFields();
        //this.currentTaskIndex = this.currentUser.getMyTaskList().getIndexOfTask(task);

        // dont allow edit if task status is "done"
        if (task.getStatus().equals("Done")) {
            editButton = (Button) findViewById(R.id.task_details_activity_edit_button);
            deleteButton = (Button) findViewById(R.id.task_details_activity_delete_button);
            editButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void findByIdsAndSetTextFields() {
        taskNameText = (TextView) findViewById(R.id.task_details_activity_name_text);
        taskDescriptionText = (TextView) findViewById(R.id.task_details_activity_task_description_text);
        taskStatusText = (TextView) findViewById(R.id.task_details_activity_status_text);
        taskCategoryText= (TextView) findViewById(R.id.task_details_activity_category_text);
        taskCurrentBidText = (TextView) findViewById(R.id.task_details_activity_current_bid_text);
        taskMaxBidText = (TextView) findViewById(R.id.task_details_activity_max_bid_text);
        taskThumbnail = findViewById(R.id.task_details_image_thumbnail);

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

        try {
            taskThumbnail.setImageBitmap(task.getImageList().getImage(0).getImageBitmap());
            taskThumbnail.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ImageList il = new ImageList();
                    try {
                        if (task.getImageListId().size() == 0) {
                            Toast.makeText(getApplicationContext(), "No images to show! Please add image!",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent i = new Intent(getApplicationContext(), ViewImageSlideActivity.class);
                            Bundle b = new Bundle();
                            for (Image image : task.getImageList().getImages()) {
                                image.recreateRecycledBitmap();
                                il.addImage(image);
                            }
                            b.putParcelableArrayList(IMAGES_KEY, il.getImages());
                            i.putExtras(b);
                            startActivity(i);
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e){
            Log.i("ThumbnailError", "Could not load image");
        }
    }


    public void deleteButtonClick(View v){

        //Remove bidders and update them.
        for(Bid bid : task.getBidList()) {
            ElasticsearchController.GetUser getUser
                    = new ElasticsearchController.GetUser();
            getUser.execute(bid.getBidId());

            User user = new User();
            try {
                user = getUser.get();
            } catch (Exception e) {
                Log.i("Error", e.toString());
            }

            user.removeBidTask(task.getId());
            ElasticsearchController.UpdateUser updateUser
                    = new ElasticsearchController.UpdateUser();
            updateUser.execute(user);
        }

        for(String id : task.getImageListId()) {
            ElasticsearchController.DeleteImage deleteImage
                    = new ElasticsearchController.DeleteImage();
            deleteImage.execute(id);
        }

        ElasticsearchController.DeleteTask deleteTask
                = new ElasticsearchController.DeleteTask();
        deleteTask.execute(this.task);

        String completed = new String();
        try {
            completed = deleteTask.get();
            Log.i("Testing", completed);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

        if(completed.equals("NoNetworkError")) {
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
        } else {
            //add offline functionality
        }
    }

    public void editButtonClick(View view){
        Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
        intent.putExtra("taskIndex", this.currentTaskIndex);
        view.getContext().startActivity(intent);
    }

    public void viewBidsButtonClick(View v){
        Intent intent = new Intent(getApplicationContext(), ViewBidsActivity.class);
        Log.i("Testing", String.valueOf(this.currentTaskIndex));
        intent.putExtra("taskIndex", this.currentTaskIndex);
        startActivity(intent);
    }

    public void taskDetailLocationButtonClick(View v) {
        if (task.getLocation() == null) {
            Toast.makeText(getApplicationContext(), "No location specified!",
                    Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), ViewTaskOnMapsActivity.class);
            Gson gson = new Gson();
            intent.putExtra("currentTask", gson.toJson(task));
            v.getContext().startActivity(intent);
        }
    }

    public void viewImagesButtonClick(View v){
        ImageList il = new ImageList();
        try {
            if (this.task.getImageListId().size() == 0) {
                Toast.makeText(getApplicationContext(), "No images to show! Please add image!",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Intent i = new Intent(getApplicationContext(), ViewImageSlideActivity.class);
                Bundle b = new Bundle();
                for (Image image : this.task.getImageList().getImages()) {
                    image.recreateRecycledBitmap();
                    il.addImage(image);
                }
                b.putParcelableArrayList(this.IMAGES_KEY, il.getImages());
                i.putExtras(b);
                startActivity(i);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
