package com.example.n8tech.taskcan.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.n8tech.taskcan.Controller.ElasticsearchController;
import com.example.n8tech.taskcan.Controller.TaskBidsViewRecyclerAdapter;
import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.BidList;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;

/**
 * ViewBidsActivity allows the current user to view bids on a task.
 *
 * @author CMPUT301W18T06
 */
public class ViewBidsActivity extends ActivityHeader {
    private BidList bidList = new BidList();
    private Task task;
    private User currentUser;
    private int currentTaskIndex;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currentUser = CurrentUserSingleton.getUser();

        Bundle extras = getIntent().getExtras();
        currentTaskIndex = extras.getInt("taskIndex");
        task = this.currentUser.getMyTaskList().getTaskAtIndex(currentTaskIndex);

        recyclerView = findViewById(R.id.ViewBidsRecyclerView);


        for (Bid bid : task.getBidList()){
            this.bidList.addBid(bid);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setHasFixedSize(true);

        ElasticsearchController.UpdateTask updateTask
                = new ElasticsearchController.UpdateTask();
        updateTask.execute(this.task);

        ElasticsearchController.UpdateUser updateUser
                = new ElasticsearchController.UpdateUser();
        updateUser.execute(currentUser);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        final TaskBidsViewRecyclerAdapter mAdapter;

        if (task.getAcceptedBid() == null){
            // if no bid accepted yet, display all bids history
            mAdapter = new TaskBidsViewRecyclerAdapter(bidList, task);

        } else {
            // if accepted bid, set list to display only the accepted one.
            Log.i("**accepted bid username", task.getAcceptedBid().getBidUsername());
            mAdapter = new TaskBidsViewRecyclerAdapter(task.getAcceptedBidList(), task);
        }
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_view_bids;
    }

    @Override
    protected String getActivityTitle() {
        return "View Bids";
    }

    @Override
    protected <T> void navigationView_itemOnClick(Class<T> nextClass) {
        if (!this.getClass().equals(nextClass)) {
            Intent i = new Intent(ViewBidsActivity.this, nextClass);
            startActivity(i);
        }
    }
}
