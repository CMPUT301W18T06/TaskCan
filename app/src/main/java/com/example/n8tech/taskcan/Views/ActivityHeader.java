package com.example.n8tech.taskcan.Views;

import android.app.Activity;
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

import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.google.gson.Gson;


/**
 * Header that initializes the Application Bar and Navigation Drawer
 * for Activities which require their functionality.
 */

//TODO: refactor class name to something that makes more sense in english because EditProfileActivity is NOT a ActivityHeader
public abstract class ActivityHeader extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    protected String currentUser;
    protected abstract int getLayoutResourceId();
    protected abstract String getActivityTitle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        this.mDrawerLayout = findViewById(R.id.drawer_layout);

        this.initializeToolBar();
        this.initializeNavagation();
    }

    private void initializeNavagation() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_menu_home: {
                                navigationView_itemOnClick(SearchActivity.class);
                                break;
                            }
                            case R.id.nav_menu_my_tasks: {
                                navigationView_itemOnClick(MyTaskActivity.class);
                                break;
                            }
                            case R.id.nav_menu_my_bids: {
                                navigationView_itemOnClick(MyBidActivity.class);
                                break;
                            }
                            case R.id.nav_menu_my_profile: {
                                navigationView_itemOnClick(ViewProfileActivity.class);
                                break;
                            }
                            case R.id.nav_menu_sign_out: {
                                CurrentUserSingleton.resetCurrentUser();

                                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);

                                //navigationView_itemOnClick(SignInActivity.class);
                                //TODO: Need to add sign out functionality

                                break;
                            }
                        }
                        mDrawerLayout.closeDrawers();

                        //TODO: Add code here to update the UI based on the item selected
                        //TODO: For example, swap UI fragments here
                        return false;
                    }
                });
    }
//    private <T> void navigationView_itemOnClick(Class<T> nextClass) {
//        if (!this.getClass().equals(nextClass)) {
//            Intent i = new Intent(ActivityHeader.this, nextClass);
//            i.putExtra(SignInActivity.USER_MESSAGE, currentUser);
//            startActivityForResult(i, 1);
//        }
//    }
    protected abstract <T> void navigationView_itemOnClick(Class<T> nextClass);

    private void initializeToolBar() {
        Toolbar mainToolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(getActivityTitle());
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

}
