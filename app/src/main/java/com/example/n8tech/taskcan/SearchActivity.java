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

package com.example.n8tech.taskcan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
=======
import android.text.Html;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
>>>>>>> Alex

public class SearchActivity extends AppCompatActivity {

    public static final String SEARCH_MESSAGE = "com.example.n8tech.taskcan.SEARCH_MESSAGE";
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar mainToolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Home");
<<<<<<< HEAD

        Intent intent = getIntent();
        String userMsg = intent.getStringExtra(SignInActivity.USER_MESSAGE);

        searchField = findViewById(R.id.search_activity_search_field);
        Button searchButton = findViewById(R.id.search_activity_search_button);
        Button browseButton = findViewById(R.id.search_activity_browse_button);
        Button mapButton = findViewById(R.id.search_activity_map_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchField.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra(SEARCH_MESSAGE, searchText);
                startActivity(intent);
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewCategoryActivity.class);
                startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewTaskOnMapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_list, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
=======
        mainToolbar.setTitle(Html.fromHtml("<font color='#FFFFFFF'>Home </font>"));
>>>>>>> Alex
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_list, menu);
        return true;
    }


}
