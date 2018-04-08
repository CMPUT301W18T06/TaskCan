package com.example.n8tech.taskcan.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.BidList;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Image;
import com.example.n8tech.taskcan.Models.ImageList;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static java.lang.Math.round;

/**
 * ViewTaskActivity shows the task details screen opened when selecting the task of another user.
 *
 * @author CMPUT301W18T06
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
    private EditText bidAmountText;
    private ImageView taskThumbnail;
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
        taskThumbnail = findViewById(R.id.task_view_activity_image_thumbnail);


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
            taskCurrentBidText.setText(String.format(Locale.CANADA,"$%.2f", task.getCurrentBid()));
        }

        if (task.getMaximumBid() == -1){
            taskMaxBidText.setText("None");
        } else {
            taskMaxBidText.setText(String.format(Locale.CANADA,"$%.2f", task.getMaximumBid()));
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
        try {
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
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void requesterUsernameButtonClick(View v){
        Intent intent = new Intent(getApplicationContext(), ViewOtherUserProfileActivity.class);
        intent.putExtra("userId", task.getOwnerId());
        v.getContext().startActivity(intent);
    }

    //TODO: JAVADOCS FOR THIS NEW METHOD
    public boolean bidAmountExists(double newBidAmount){
        for (Bid bid : task.getBidList()){
            if (bid.getBidAmount() == newBidAmount){
                return true;
            }
        }
        return false;
    }

    public void confirmBidButton(View v){
        Bid bidToBeAdded = new Bid();
        User ownerOfTask = new User();
        BidList currentTasksBidList;
        double newBidAmount;
        int ownerIndex;
        //indexOfBidInCurrentTasksBidList, indexOfTaskInCurrentUserTaskList;
        //Boolean bidToBeAddedIsNew = true;

        ElasticsearchController.GetUser getUser
                = new ElasticsearchController.GetUser();
        getUser.execute(this.task.getOwnerId());

        try {
            ownerOfTask = getUser.get();
        } catch (Exception e) {
            Log.i("Error", String.valueOf(e));
        }
        ownerIndex = ownerOfTask.getMyTaskList().getIndexOfTask(task);
        Log.i("testing", String.valueOf(ownerIndex));

        bidAmountText = (EditText) findViewById(R.id.task_view_activity_bid_amount);
        newBidAmount = Double.parseDouble(bidAmountText.getText().toString());

        if(newBidAmount > task.getMaximumBid() && task.getMaximumBid()!= -1) {
            Toast.makeText(getApplicationContext(), "Your bid amount is greater than the" +
                    " maximum bid amount", Toast.LENGTH_LONG).show();
            return;
        }
        else if (newBidAmount < 0.01){
            Toast.makeText(getApplicationContext(), "Your bid amount is less than the" +
                    " minimum requires bid amount", Toast.LENGTH_LONG).show();
            return;
        }
        else if (bidAmountExists(newBidAmount)){
            Toast.makeText(getApplicationContext(), "Your bid amount already exists. Please" +
                    " choose another bid amount", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            bidToBeAdded.setBidAmount(newBidAmount);
        }

        //Get everything and change everything and then at the end use ES to update
        bidToBeAdded.setBidId(currentUser.getId());
        bidToBeAdded.setBidUsername(currentUser.getUsername());

        currentTasksBidList = task.getBidList();

        //Update task with bid
        int inBidList = currentTasksBidList.getBidIndex(bidToBeAdded);
        if(inBidList == -1) {
            //If new bidder
            task.addBidder(bidToBeAdded);
            if (task.getStatus().intern() == "Requested"){
                task.setStatus("Bidded");
            }
            currentUser.addBidTask(task);
        } else {
            //If old bidder
            task.getBidList().updateBid(bidToBeAdded, inBidList);
            int userIndex = currentUser.getBidTaskList().getIndexOfTask(task);
            currentUser.getBidTaskList().replaceAtIndex(userIndex, task);
        }

        ownerOfTask.replaceTaskAtIndex(ownerIndex, this.task);

        ElasticsearchController.UpdateTask updateTask
                = new ElasticsearchController.UpdateTask();
        updateTask.execute(this.task);

        ElasticsearchController.UpdateUser updateUser
                = new ElasticsearchController.UpdateUser();
        updateUser.execute(currentUser);

        ElasticsearchController.UpdateUser updateOwner
                = new ElasticsearchController.UpdateUser();
        updateOwner.execute(ownerOfTask);

        Intent seeBids = new Intent(getApplicationContext(), MyBidActivity.class);
        startActivity(seeBids);
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
