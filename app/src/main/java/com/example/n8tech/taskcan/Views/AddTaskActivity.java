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

import com.example.n8tech.taskcan.FileIO;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.Image;
import com.example.n8tech.taskcan.Models.ImageList;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.example.n8tech.taskcan.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * AddTaskActivity handles the creation of a new task made by the current user.
 * It takes in the following inputs from the user:
 * <ul><li>task name</li>
 * <li>task description</li>
 * <li>maximum bid on task</li>
 * <li>category</li></ul>
 * It adds a new task to user's My Tasks list.
 *
 * @author CMPUT301W18T06
 */

public class AddTaskActivity extends ActivityHeader {
    public final static Integer EDIT_IMAGES_REQUEST_CODE = 0;
    private final static String RESULT_CODE = "ADDTASKACTIVITY_IMAGE_RESULT_CODE";

    final Integer GALLERY_ADD_IMAGE = 0;
    final Integer CAMERA_ADD_IMAGE = 1;
    final Integer EDIT_IMAGE = 2;
    final int PLACE_PICKER_REQUEST = 3;

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
    private LatLng location;
    private ArrayAdapter<CharSequence> categorySpinnerAdapter;
    private ImageList imageList;
    private FileIO fileIO = new FileIO();
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.imageList = new ImageList();
        this.currentUser = CurrentUserSingleton.getUser();

        findViewsByIdAndSetContent();

    }


    private void findViewsByIdAndSetContent() {
        maxBidText = (EditText) findViewById(R.id.add_task_activity_money_edit_text);
        taskNameEditText = (EditText) findViewById(R.id.add_task_activity_name_edit_text);
        taskDescriptionEditText = (EditText) findViewById(R.id.add_task_activity_task_description_edit_text);
        taskStatusText = (TextView) findViewById(R.id.add_task_activity_status_edit_text);
        categorySpinner = (Spinner) findViewById(R.id.add_task_activity_category_spinner);


        taskNameEditText.setText("");
        taskDescriptionEditText.setText("");
        taskStatusText.setText("Requested");
        maxBidText.setText("");


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



    // https://stackoverflow.com/questions/4671428/how-can-i-add-a-third-button-to-an-android-alert-dialog
    public void addPhotosButtonClick(View v) {
        if (this.imageList.getSize() == 10){
            Toast.makeText(AddTaskActivity.this, "Maximum of 10 photos reached for this task", Toast.LENGTH_LONG).show();
            return;
        }

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

        builder.create().show();
    }

    // https://developers.google.com/places/android-api/placepicker
    public void editLocationButtonClick(View v) {
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void cancelButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MyTaskActivity.class);
        startActivity(intent);

    }

    public void saveButtonClick(View v) {
        String taskName;
        String taskDescription;
        double maximumBid;
        String maximumBidString;
        String category;
        String taskStatus = "Requested";
        boolean valid = Boolean.TRUE;

        // get fields and do error checking
        newTask = new Task();

        taskName = taskNameEditText.getText().toString();
        if (taskName.length() <= 30 && !taskName.equals("")) {
            // task name is valid, set task name
            newTask.setTaskTitle(taskName);
        } else {
            Toast.makeText(AddTaskActivity.this, "Name must be between 0 and 30 characters", Toast.LENGTH_LONG).show();
            valid = Boolean.FALSE;
        }

        taskDescription = taskDescriptionEditText.getText().toString();
        newTask.setDescription(taskDescription);
        newTask.setStatus(taskStatus);

        category = categorySpinner.getSelectedItem().toString();
        newTask.setCategory(category);

        maximumBidString = maxBidText.getText().toString();

        if (maximumBidString.equals("")){
            newTask.setMaximumBid(-1);
        }
        else if ((!maximumBidString.contains(".")) || (maximumBidString.indexOf(".") + 3 >= maximumBidString.length())){
            maximumBid = Double.parseDouble(maximumBidString);
            newTask.setMaximumBid(maximumBid);
        } else {
            Toast.makeText(AddTaskActivity.this, "Please enter valid CAD", Toast.LENGTH_LONG).show();
            valid = Boolean.FALSE;
        }

        // TODO image saving testing, @Q doing ES stuff
        newTask.setLocation(this.location);
        try {
            newTask.setImageListId(this.imageList);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(AddTaskActivity.this, "EcecutionException: Failure to set images", Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(AddTaskActivity.this, "InterruptedException: Failure to set images", Toast.LENGTH_LONG).show();
        }
        newTask.setOwnerUsername(currentUser.getUsername());
        newTask.setOwnerId(currentUser.getId());
        newTask.setEditCount(1);


        // TODO:in file here

        Log.i("*** name", newTask.getTaskTitle());
        Log.i("*** desc", newTask.getDescription());
        Log.i("*** maximum bid", Double.toString(newTask.getMaximumBid()));
        Log.i("*** category", newTask.getCategory());


        if (valid) {

            ElasticsearchController.AddTask addTask
                    = new ElasticsearchController.AddTask();
            addTask.execute(this.newTask);

            String completed = new String();
            try {
                completed = addTask.get();
                Log.i("Testing", completed);
            } catch (Exception e) {
                Log.i("Error", e.toString());
            }

            UserList cacheList = this.fileIO.loadFromFile(getApplicationContext());

            if (completed == "NoNetworkError") {
                // add task to current user's myTasks list
                //currentUser.setEditCount(currentUser.getEditCount() + 1);

                cacheList.delUser(this.currentUser);
                currentUser.addTask(newTask);
                cacheList.addUser(this.currentUser);

                this.fileIO.saveInFile(getApplicationContext(), cacheList);

                ElasticsearchController.UpdateUser updateUser
                        = new ElasticsearchController.UpdateUser();
                updateUser.execute(currentUser);

                Intent intent = new Intent(getApplicationContext(), MyTaskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            } else {
                //save for later when connection is there
            }
        } else {
            //Toast invalid
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);
        if (resultCode == RESULT_OK) {
            if (requestCode == this.CAMERA_ADD_IMAGE || requestCode == this.GALLERY_ADD_IMAGE) {
                // get image
                Image image;
                Uri selectedImage = returnedIntent.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                // check img size
                if (this.sizeOf(bitmap) < R.integer.IMAGE_MAX_BYTE_SIZE) {
                    // store
                    image = new Image(bitmap);
                    Log.i("ImageSize", String.valueOf(this.sizeOf(bitmap)));
                    imageList.addImage(image);

                    Toast.makeText(AddTaskActivity.this, "Image added successfully!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    int quality = 5;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] bitmapdata;
                    while (quality > 0){
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
                        bitmapdata = bos.toByteArray();
                        Log.i("ImageSizeInLoop", String.valueOf(new BigInteger(bitmapdata).intValue()));
                        if (new BigInteger(bitmapdata).intValue() < R.integer.IMAGE_MAX_BYTE_SIZE){
                            Log.i("ImageSizeInLoop", String.valueOf(new BigInteger(bitmapdata).intValue()));
                            image = new Image(bitmap);
                            imageList.addImage(image);
                            break;
                        }
                        quality = quality - 5;
                    }
                    Toast.makeText(AddTaskActivity.this, "Image size too large! (<65536bytes)",
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
        if (this.imageList.getSize() == 0) {
            Toast.makeText(AddTaskActivity.this, "No imageList to show! Please add image!",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(getApplicationContext(), EditImageSlideActivity.class);

            Bundle b = new Bundle();

            //b.putParcelableArrayList(EditImageSlideActivity.IMAGES_KEY, this.imageList.getImages());
            b.putString(EditImageSlideActivity.RESULT_KEY, RESULT_CODE);
            i.putExtras(b);

            // send image list by putting it in current user singleton
            CurrentUserSingleton.setImageList(imageList);
            startActivityForResult(i, EDIT_IMAGES_REQUEST_CODE);
        }
    }


    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(AddTaskActivity.this, nextClass);
            startActivity(i);
        }
    }
}
