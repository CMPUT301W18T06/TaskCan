package com.example.n8tech.taskcan.Controller;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.BidList;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.example.n8tech.taskcan.Views.ViewTaskActivity;
import com.google.gson.Gson;

import java.util.Locale;

/**
 * TaskViewRecyclerAdapter represents a suitable view for task lists.
 *
 * https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
 * https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 *
 * @author CMPUT301W18T06
 */


public class BidViewRecyclerAdapter extends RecyclerView.Adapter<BidViewRecyclerAdapter.ViewHolder> {
    private TaskList taskList;
    private User currentUser;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView taskTitle;
        public TextView taskBidderName;
        public TextView taskStatus;
        public TextView taskCurrentBid;
        public TextView taskMyBid;
        public ImageView taskThumbnail;

        public ViewHolder(View view) {
            super(view);
            taskTitle = view.findViewById(R.id.task_view_title);
            taskBidderName = view.findViewById(R.id.task_view_bidder_name);
            taskStatus = view.findViewById(R.id.task_view_status);
            taskCurrentBid = view.findViewById(R.id.task_view_current_bid);
            taskMyBid = view.findViewById(R.id.task_view_my_bid);
            taskThumbnail = view.findViewById(R.id.task_view_thumbnail);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BidViewRecyclerAdapter(TaskList myTaskList) {
        this.taskList = myTaskList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BidViewRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        this.currentUser = CurrentUserSingleton.getUser();

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_view_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    // onClick from https://stackoverflow.com/questions/24471109/recyclerview-onclick
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        BidList currentBidList;
        final Task currentTask = taskList.getTaskAtIndex(position);
        holder.taskTitle.setText(currentTask.getTaskTitle());
        holder.taskStatus.setText(currentTask.getStatus());

        // if a current bid set to that, else set to "None"
        String currentBidText;
        String myBidText;
        if (currentTask.getCurrentBid() == -1){
            holder.taskBidderName.setText("No Bids");
            currentBidText = "None";
            myBidText = "";
        }else{
            currentBidList = currentTask.getBidList();
            for (Bid bid : currentBidList){
                if (bid.getBidAmount() == currentTask.getCurrentBid()){
                    holder.taskBidderName.setText(currentTask.getOwnerUsername());
                    break;
                }
            }
            currentBidText = String.format(Locale.CANADA,"$%.2f", currentTask.getCurrentBid());
            myBidText = String.format(Locale.CANADA,"$%.2f", currentTask.getBidById(currentUser.getId()));
        }
        holder.taskCurrentBid.setText(currentBidText);
        holder.taskMyBid.setText(myBidText);

        try {
            holder.taskThumbnail.setImageBitmap(currentTask.getImageList().getImage(0).getImageBitmap());
        } catch (Exception e){
            Log.i("ThumbnailError", "Could not load image");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewTaskActivity.class);
                Gson gson = new Gson();
                intent.putExtra("currentTask", gson.toJson(currentTask));
                view.getContext().startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.i("TestingAdapter", String.valueOf(taskList.getSize()));
        return taskList.getSize();
    }

    public void refresh(TaskList newTaskList) {
        this.taskList = newTaskList.copy();
        this.notifyDataSetChanged();
    }
}

