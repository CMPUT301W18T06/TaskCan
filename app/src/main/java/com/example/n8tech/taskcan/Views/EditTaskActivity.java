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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Image;
import com.example.n8tech.taskcan.Models.ImageList;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


/**
 * EditTaskActivity allows for changes to be made to task details.
 * It takes in the following inputs from the user:
 * <ul><li>task name</li>
 * <li>task description</li>
 * <li> task status </li>
 * <li>maximum bid on task</li>
 * <li>category</li></ul>
 * It updates any changes to the task's details.
 *
 * @author CMPUT301W18T06
 */
public class EditTaskActivity extends ActivityHeader  {
    public final static Integer EDIT_IMAGES_REQUEST_CODE = 0;
    private final static String RESULT_CODE = "EDITTASKACTIVITY_IMAGE_RESULT_CODE";

    final Integer GALLERY_ADD_IMAGE = 0;
    final Integer CAMERA_ADD_IMAGE = 1;
    final Integer EDIT_IMAGE = 2;

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
    private LatLng location;
    private ArrayAdapter<CharSequence> categorySpinnerAdapter;
    private ArrayAdapter<CharSequence> statusSpinnerAdapter;
    private FileIO fileIO = new FileIO();
    int PLACE_PICKER_REQUEST = 5;
    private int currentTaskIndex;
    private ImageList imageList;

    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentUser = CurrentUserSingleton.getUser();
        Log.i("current user", currentUser.getUsername());

        Bundle extras = getIntent().getExtras();
        currentTaskIndex = extras.getInt("taskIndex");
        task = this.currentUser.getMyTaskList().getTaskAtIndex(currentTaskIndex);
        findViewsByIdAndSetContent();

        try {
            imageList = task.getImageList();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(EditTaskActivity.this, "EcecutionException: Failure to get images", Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(EditTaskActivity.this, "InterruptedException: Failure to get images", Toast.LENGTH_LONG).show();
        }
        location = task.getLocation();
    }

    private void setCategorySpinnerContent() {
        categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        // set spinner category to be shown on activity
        spinnerPosition = categorySpinnerAdapter.getPosition(task.getCategory());
        categorySpinner.setSelection(spinnerPosition);
    }

    private void setTaskStatusSpinnerContent() {
        statusSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_status_spinner_array, android.R.layout.simple_spinner_item);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskStatusSpinner.setAdapter(statusSpinnerAdapter);

        // set spinner category to be shown on activity
        spinnerPosition = statusSpinnerAdapter.getPosition(task.getStatus());
        taskStatusSpinner.setSelection(spinnerPosition);
    }

    private void findViewsByIdAndSetContent() {
        maxBidText = (TextView) findViewById(R.id.edit_task_activity_money_edit_text);
        taskNameEditText = (EditText) findViewById(R.id.edit_task_activity_name_edit_text);
        taskDescriptionEditText = (EditText) findViewById(R.id.edit_task_activity_task_description_edit_text);
        categorySpinner = (Spinner) findViewById(R.id.edit_task_activity_category_spinner);
        taskStatusSpinner = (Spinner) findViewById(R.id.edit_task_activity_status_spinner);

        if (task.getMaximumBid() == -1){
            maxBidText.setText("");
        }else{
            maxBidText.setText(String.format(Locale.CANADA,"%.2f", task.getMaximumBid()));
        }

        taskNameEditText.setText(task.getTaskTitle());
        taskDescriptionEditText.setText(task.getDescription());

        // set category spinner content and set to task's category
        setCategorySpinnerContent();
        setTaskStatusSpinnerContent();
    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(EditTaskActivity.this, nextClass);
            startActivity(i);
        }
    }

    public void cancelButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MyTaskActivity.class);
        startActivity(intent);
    }

    // https://stackoverflow.com/questions/4671428/how-can-i-add-a-third-button-to-an-android-alert-dialog
    public void addPhotosButtonClick(View v) {
        String title = "Add Photo";
        String message = "Pick from gallery or take new photo?";
        String positive = "Gallery", neutral = "Cancel", negative = "Camera";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 0);
            }
        });
        builder.setNeutralButton(neutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
//        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePicture, 1);
//            }
//        });
        builder.create().show();
    }

    public void editLocationButtonClick(View v) {

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void saveButtonClick(View v) {
        String taskName;
        String taskDescription;
        double maximumBid;
        String maximumBidString;
        String category;
        String taskStatus;
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
        task.setStatus(taskStatus);

        category = categorySpinner.getSelectedItem().toString();
        task.setCategory(category);

        maximumBidString = maxBidText.getText().toString();

        if (!maximumBidString.equals("")) {
            maximumBid = Double.parseDouble(maximumBidString);
            task.setMaximumBid(Math.round(maximumBid*100.0)/100.0);                      // round to 2 decimal places
        } else {
            task.setMaximumBid(-1);
        }

        task.setLocation(this.location);

        Log.i("*** name", task.getTaskTitle());
        Log.i("*** desc", task.getDescription());
        Log.i("*** maximum bid",Double.toString(task.getMaximumBid()));
        Log.i("*** category",task.getCategory());
        Log.i("*** status",task.getStatus());

        if (valid) {
            // TODO update task in user's task list

            ElasticsearchController.UpdateTask updateTask
                    = new ElasticsearchController.UpdateTask();
            updateTask.execute(this.task);

            String completed = new String();
            try {
                completed = updateTask.get();
                Log.i("Testing", completed);
            } catch (Exception e) {
                Log.i("Error", e.toString());
            }

            UserList cacheList = this.fileIO.loadFromFile(getApplicationContext());
            cacheList.delUser(this.currentUser);
            cacheList.addUser(this.currentUser);
            this.fileIO.saveInFile(getApplicationContext(), cacheList);

            if (completed.equals("NoNetworkError")) {
                // add task to current user's myTasks list
                //currentUser.addTask(newTask);
                currentUser.replaceTaskAtIndex(currentTaskIndex,task);

                ElasticsearchController.UpdateUser updateUser
                        = new ElasticsearchController.UpdateUser();
                updateUser.execute(currentUser);


                //Intent intent = new Intent(v.getContext(), TaskDetailActivity.class);
                //intent.putExtra("taskIndex", currentTaskIndex);         // use this if going back to taskDetails

                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //v.getContext().startActivity(intent);
                finish();


            } else {
                //save for later when connection is there
            }
        } else {
            //Toast invalid
        }
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_edit_task;
    }

    @Override
    protected String getActivityTitle() {
        return "Edit Task";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);
        if (resultCode == RESULT_OK) {
            if (requestCode == this.CAMERA_ADD_IMAGE || requestCode == this.GALLERY_ADD_IMAGE) {
                // get image
                Uri selectedImage = returnedIntent.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                Image image = new Image(bitmap);
                // check img size
                if (this.sizeOf(bitmap) < R.integer.IMAGE_MAX_BYTE_SIZE) {
                    // store
                    imageList.addImage(image);

                    Toast.makeText(EditTaskActivity.this, "Image added successfully!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(EditTaskActivity.this, "Image size too large! (<65536bytes)",
                            Toast.LENGTH_LONG).show();
                }
            }
            else if (requestCode == this.PLACE_PICKER_REQUEST) {
                this.location = PlacePicker.getPlace(this, returnedIntent).getLatLng();
                String toastMsg = String.format("Place: %s", PlacePicker.getPlace(this, returnedIntent).getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
            else if (requestCode == this.EDIT_IMAGE) {
                this.imageList.setImages(returnedIntent.getExtras().<Image>getParcelableArrayList(RESULT_CODE));
            }
            else {
                throw new IllegalStateException();
            }
        }
    }

    private int sizeOf(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }


    public void viewImagesOnClick(View view) {
        try {
            if (this.task.getImageList().getSize() == 0) {
                Toast.makeText(getApplicationContext(), "No images to show! Please add image!",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Intent i = new Intent(getApplicationContext(), EditImageSlideActivity.class);
                Bundle b = new Bundle();
                /*
                for (Image image : this.task.getImageList().getImages()) {
                    image.recreateRecycledBitmap();
                }*/
                //b.putParcelableArrayList(EditImageSlideActivity.IMAGES_KEY, this.task.getImageList().getImages());

                b.putString(EditImageSlideActivity.RESULT_KEY, RESULT_CODE);
                i.putExtras(b);

                // send image list by putting it in current user singleton
                CurrentUserSingleton.setImageList(this.task.getImageList());
                startActivityForResult(i, EDIT_IMAGES_REQUEST_CODE);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(EditTaskActivity.this, "EcecutionException: Failure to get images", Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(EditTaskActivity.this, "InterruptedException: Failure to get images", Toast.LENGTH_LONG).show();
        }
    }
}
