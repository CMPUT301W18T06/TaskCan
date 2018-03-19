package com.example.n8tech.taskcan.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;

/**
 * ViewBidsActivity allows the current user to view bids on a task.
 *
 * @author CMPUT301W18T06
 */
public class ViewBidsActivity extends ActivityHeader {
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.currentUser = CurrentUserSingleton.getUser();

    }


    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(ViewBidsActivity.this, nextClass);
            startActivity(i);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_bids;
    }

    @Override
    protected String getActivityTitle() {
        return "View Bids";
    }
}
