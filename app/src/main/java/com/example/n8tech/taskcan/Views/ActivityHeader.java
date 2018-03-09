package com.example.n8tech.taskcan.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;

import com.example.n8tech.taskcan.R;


/**
 * Header that inititalizes the Application Bar and Navigation Drawer
 * for Activities which require their functionality.
 */

public abstract class ActivityHeader extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    protected abstract int getLayoutResourceId();
    protected abstract String getActivityTitle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        Toolbar mainToolbar = findViewById(R.id.menu_toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(getActivityTitle());
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_menu_home: {
                                Intent goToHome = new Intent(ActivityHeader.this,SearchActivity.class);
                                startActivityForResult(goToHome, 1);

                                break;
                            }
                            case R.id.nav_menu_my_tasks: {
                                Intent goToTasks = new Intent(ActivityHeader.this, MyTaskActivity.class);
                                startActivityForResult(goToTasks, 1);

                                break;
                            }
                            case R.id.nav_menu_my_bids: {
                                Intent goToBids = new Intent(ActivityHeader.this, MyBidActivity.class);
                                startActivityForResult(goToBids, 1);

                                break;
                            }
                            case R.id.nav_menu_my_profile: {
                                Intent goToProfile = new Intent(ActivityHeader.this, ViewProfileActivity.class);
                                startActivityForResult(goToProfile, 1);

                                break;
                            }
                            case R.id.nav_menu_sign_out: {
                                Intent goToSignIn = new Intent(ActivityHeader.this,SignInActivity.class);
                                startActivityForResult(goToSignIn, 1);

                                // Need to add sign out functionality

                                break;
                            }
                        }
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return false;
                    }
                });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}