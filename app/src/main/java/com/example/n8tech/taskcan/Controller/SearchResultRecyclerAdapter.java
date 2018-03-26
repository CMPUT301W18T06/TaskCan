package com.example.n8tech.taskcan.Controller;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.R;
import com.example.n8tech.taskcan.Views.TaskDetailActivity;

/**
 * TaskViewRecyclerAdapter represents a suitable view for task lists.
 *
 * https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
 * https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 *
 * @author CMPUT301W18T06
 */


public class SearchResultRecyclerAdapter extends RecyclerView.Adapter<SearchResultRecyclerAdapter.ViewHolder> {
    private TaskList taskList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView taskTitle;
        public TextView taskOwnerName;
        public TextView taskStatus;
        public TextView taskBid;

        public ViewHolder(View view) {
            super(view);
            taskTitle = view.findViewById(R.id.task_view_title);
            taskOwnerName = view.findViewById(R.id.task_view_bidder_name);
            taskStatus = view.findViewById(R.id.task_view_status);
            taskBid = view.findViewById(R.id.task_view_current_bid);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchResultRecyclerAdapter(TaskList myTaskList) {
        this.taskList = myTaskList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchResultRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
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
        final Task currentTask = taskList.getTaskAtIndex(position);
        holder.taskTitle.setText(currentTask.getTaskTitle());
        holder.taskStatus.setText(currentTask.getStatus());

        // if a current bid set to that, else set to "None"
        String currentBidText;
        holder.taskOwnerName.setText(currentTask.getOwnerUsername());
        currentBidText = String.valueOf(currentTask.getCurrentBid());

        if(currentBidText.equals("-1.0")) {
            holder.taskBid.setText("No Bids");
        }
        else {
            holder.taskBid.setText(currentBidText);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TestingAdapterClick", String.valueOf(position));
                Intent intent = new Intent(view.getContext(), TaskDetailActivity.class);
                intent.putExtra("taskIndex", position);
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
}

