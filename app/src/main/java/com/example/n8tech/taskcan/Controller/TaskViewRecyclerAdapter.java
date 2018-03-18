package com.example.n8tech.taskcan.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
 * https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */

public class TaskViewRecyclerAdapter extends RecyclerView.Adapter<TaskViewRecyclerAdapter.ViewHolder> {
    private TaskList taskList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView taskTitle;
        public TextView taskBid;

        public ViewHolder(View view) {
            super(view);
            taskTitle = view.findViewById(R.id.task_view_title);
            taskBid = view.findViewById(R.id.task_view_current_bid);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskViewRecyclerAdapter(TaskList myTaskList) {
        this.taskList = myTaskList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TaskViewRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_view_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Task currentTask = taskList.getTaskAtIndex(position);
        holder.taskTitle.setText(currentTask.getTaskTitle());
        holder.taskBid.setText(String.valueOf(currentTask.getMaximumBid()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return taskList.getSize();
    }
}

