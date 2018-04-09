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
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.common.mvel2.util.NullType;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * ViewTaskonMapsActivity implements Maps to display the geolocation of a task.
 *
 * @see EditTaskMapActivity
 * @author CMPUT301W18T06
 */
public class ViewTaskOnMapsActivity extends ActivityHeader implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Task task;
    private User currentUser;
    private int currentTaskIndex;
    private Task currentTask;
    private EditText searchRadius;
    private TextView radiusDescr;
    private Button refreshRadius;
    private double radius = 5.0;
    private String radiusEntry;
    private final String searchQuery = "";
    private static final int LOCATION_REQUEST_CODE = 101;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng currentLocation;
    private TaskList resultTaskList;
    private Map<Marker, Task> markerMap = new HashMap<Marker, Task>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        searchRadius = findViewById(R.id.view_task_map_radius_entry);
        searchRadius.setVisibility(View.INVISIBLE);

        radiusDescr = findViewById(R.id.view_task_on_map_radius_descr);
        radiusDescr.setVisibility(View.INVISIBLE);

        refreshRadius = findViewById(R.id.view_task_on_map_refresh_button);
        refreshRadius.setVisibility(View.INVISIBLE);

        this.currentUser = CurrentUserSingleton.getUser();

        Type taskType = new TypeToken<Task>(){}.getType();
        Intent intent = getIntent();
        Gson gson = new Gson();

        task = gson.fromJson(intent.getStringExtra("currentTask"), taskType);         // change this to the right task from the search
        if (task == null) {
            task = new Task();
        }

    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(ViewTaskOnMapsActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_task_on_maps;
    }

    @Override
    protected String getActivityTitle() {
        return "Task Finder";
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (task.getLocation() != null) {
            LatLng taskMarker = task.getLocation();
            mMap.addMarker(new MarkerOptions().position(taskMarker).title(task.getTaskTitle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taskMarker, 13.0f));
        } else {
            // in the case that we just opened the map, go to current position and mark tasks in 5km radius
            //TaskList resultTaskList = new TaskList();

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
                return;
            }

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0f));

                        searchRadius.setVisibility(View.VISIBLE);
                        radiusDescr.setVisibility(View.VISIBLE);
                        refreshRadius.setVisibility(View.VISIBLE);
                    }

                    ElasticsearchController.SearchLocation searchLocation
                            = new ElasticsearchController.SearchLocation(currentLocation, radius * 1000);
                    searchLocation.execute(searchQuery);

                    try {
                        resultTaskList = searchLocation.get();
                        for (Task task : resultTaskList){
                            LatLng taskMarker = task.getLocation();
                            Marker marker = mMap.addMarker(new MarkerOptions().position(taskMarker).title(task.getTaskTitle()));
                            markerMap.put(marker, task);
                        }
                    } catch (Exception e) {
                        Log.i("Error", String.valueOf(e));
                    }

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                        @Override
                        public void onInfoWindowClick(Marker currentMarker) {
                            // TODO Auto-generated method stub
                            currentTask = markerMap.get(currentMarker);
                            if(currentUser.getId().equals(currentTask.getOwnerId())) {
                                Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
                                Gson gson = new Gson();
                                intent.putExtra("currentTask", gson.toJson(currentTask));
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), ViewTaskActivity.class);
                                Gson gson = new Gson();
                                intent.putExtra("currentTask", gson.toJson(currentTask));
                                startActivity(intent);
                            }
                        }
                    });
                }
            });
        }
    }

    public void viewMapRefreshClick(View v) {
        radiusEntry = searchRadius.getText().toString();

        if (!radiusEntry.isEmpty()){
            try
            {
                radius = Double.parseDouble(radiusEntry);
            } catch (Exception e1) {
                // not double
                e1.printStackTrace();
            }

            mMap.clear();

            ElasticsearchController.SearchLocation searchLocation
                    = new ElasticsearchController.SearchLocation(currentLocation, radius * 1000);
            searchLocation.execute(searchQuery);

            try {
                resultTaskList = searchLocation.get();
                Log.i("Size", String.valueOf(resultTaskList.getSize()));
                for (Task task : resultTaskList){
                    LatLng taskMarker = task.getLocation();
                    Marker marker = mMap.addMarker(new MarkerOptions().position(taskMarker).title(task.getTaskTitle()));
                    markerMap.put(marker, task);
                }
            } catch (Exception e) {
                Log.i("Error", String.valueOf(e));
            }

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                @Override
                public void onInfoWindowClick(Marker currentMarker) {
                    // TODO Auto-generated method stub
                    currentTask = markerMap.get(currentMarker);
                    if(currentUser.getId().equals(currentTask.getOwnerId())) {
                        Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
                        Gson gson = new Gson();
                        intent.putExtra("currentTask", gson.toJson(currentTask));
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ViewTaskActivity.class);
                        Gson gson = new Gson();
                        intent.putExtra("currentTask", gson.toJson(currentTask));
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
