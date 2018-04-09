package com.example.n8tech.taskcan.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.BidList;
import com.example.n8tech.taskcan.Models.BiddedTask;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.R;
import com.example.n8tech.taskcan.Views.ViewBidsActivity;
import com.example.n8tech.taskcan.Views.ViewOtherUserProfileActivity;

import java.util.Locale;

/**
 * TaskViewRecyclerAdapter represents a suitable view for task lists.
 *
 * Used in ViewBids activity. Has accept/decline buttons
 *
 * https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
 * https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 *
 * @author CMPUT301W18T06
 */


public class TaskBidsViewRecyclerAdapter extends RecyclerView.Adapter<TaskBidsViewRecyclerAdapter.ViewHolder> {
    private BidList bidList;
    private Task task;
    private Bid acceptedBid;
    private User currentUser;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView bidUsername;            // TODO should be a button
        public TextView bidAmount;
        public Button acceptButton;
        public Button declineButton;
        public Button cancelButton;             // TODO make a cancel button thats invisible until a bid is accepted.
        private final Context context;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            bidUsername = (TextView) view.findViewById(R.id.bid_list_user);
            bidAmount = (TextView) view.findViewById(R.id.bid_list_amount);
            acceptButton = (Button) view.findViewById(R.id.accept_button);
            declineButton = (Button) view.findViewById(R.id.decline_button);
            cancelButton = (Button) view.findViewById(R.id.cancel_button);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskBidsViewRecyclerAdapter(BidList myBidList, Task task) {
        this.bidList = myBidList;
        this.task = task;
    }

    // return the accepted bidder's username
    public Bid getAcceptedBid(){
        return this.acceptedBid;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public TaskBidsViewRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_bid_list, parent, false);

        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    // onClick from https://stackoverflow.com/questions/24471109/recyclerview-onclick
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        this.currentUser = CurrentUserSingleton.getUser();
        final Bid currentBid = bidList.getBid(position);

        // if there is an accepted bid, set buttons accordingly
        if (task.getAcceptedBid() != null) {
            holder.acceptButton.setVisibility(View.INVISIBLE);
            holder.declineButton.setVisibility(View.INVISIBLE);
            holder.cancelButton.setVisibility(View.VISIBLE);
        }

        holder.bidUsername.setText(currentBid.getBidUsername());
        holder.bidAmount.setText(String.format(Locale.CANADA,"$%.2f", currentBid.getBidAmount()));


        holder.bidUsername.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(holder.context, ViewOtherUserProfileActivity.class);
                intent.putExtra("userId", currentBid.getBidId());
                v.getContext().startActivity(intent);
            }
        });

        holder.acceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Hide all bids but the current position
                task.setAcceptedBid(bidList.getBid(position));
                task.setProviderUsername(bidList.getBid(position).getBidUsername());
                task.setProviderId(bidList.getBid(position).getBidId());
                task.setStatus("Assigned");

                BidList acceptedBidList = new BidList();
                acceptedBidList.addBid(task.getAcceptedBid());
                String providerUsername = task.getProviderUsername();
                Log.i("accepted bid from", providerUsername);


                // display only accepted bid
                bidList = acceptedBidList;
                task.setAcceptedBidList(acceptedBidList);

                // remove the accepted bid from task's bid list
                // as of UC 07.02.01 and Kens forum post
                task.getBidList().removeBid(task.getAcceptedBid());
                notifyDataSetChanged();

                holder.acceptButton.setVisibility(View.INVISIBLE);
                holder.declineButton.setVisibility(View.INVISIBLE);
                holder.cancelButton.setVisibility(View.VISIBLE);

                // load every user thats bid on it, remove that task from their mybid list
                User losingUser;
                Bid losingBid;

                for(int i = 0; i < task.getBidList().getSize(); i++) {
                    losingBid = task.getBidList().getBid(i);
                    // load the user of that bid, if its not the winning one
                    if (!losingBid.getBidId().equals(task.getProviderId())) {

                        ElasticsearchController.GetUser getUser
                                = new ElasticsearchController.GetUser();
                        getUser.execute(losingBid.getBidId());
                        losingUser = new User();

                        try {
                            losingUser = getUser.get();
                            Log.i("removing from bidlist:", losingUser.getUsername());
                        } catch (Exception e) {
                            Log.i("Error", e.toString());
                        }

                        Log.i("losing bid un", losingBid.getBidUsername());
                        Log.i("losing user un:", losingUser.getUsername());
                        Log.i("winning user un:", providerUsername);

                        losingUser.removeBidTask(task.getId());

                        ElasticsearchController.UpdateUser updateUser
                                = new ElasticsearchController.UpdateUser();
                        updateUser.execute(losingUser);
                    }
                }

                ElasticsearchController.UpdateTask updateTask = new ElasticsearchController.UpdateTask();
                updateTask.execute(task);

                ElasticsearchController.UpdateUser updateUser
                        = new ElasticsearchController.UpdateUser();
                updateUser.execute(currentUser);

            }


        });

        holder.declineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // get user of deleted bid
                ElasticsearchController.GetUser getUser
                        = new ElasticsearchController.GetUser();
                getUser.execute(bidList.getBid(position).getBidId());

                // remove the bid from the task and from the screen
                task.getBidList().removeBid(bidList.getBid(position));
                bidList.removeBid(bidList.getBid(position));

                if (task.getBidList().getSize() > 0 ) {
                    task.setStatus("Bidded");
                } else {
                    task.setStatus("Requested");
                }

                User userWithDeletedBid = new User();
                try {
                    userWithDeletedBid = getUser.get();
                    Log.i("removing from bidlist:", userWithDeletedBid.getUsername());
                } catch (Exception e) {
                    Log.i("Error", e.toString());
                }

                userWithDeletedBid.removeBidTask(task.getId());

                ElasticsearchController.UpdateTask updateTask = new ElasticsearchController.UpdateTask();
                updateTask.execute(task);

                ElasticsearchController.UpdateUser updateUser
                        = new ElasticsearchController.UpdateUser();
                updateUser.execute(userWithDeletedBid);

                ElasticsearchController.UpdateUser updateCurrentUser
                        = new ElasticsearchController.UpdateUser();
                updateCurrentUser.execute(currentUser);

                notifyDataSetChanged();
            }
        });

        holder.cancelButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // user cancelled the assigned bid, show old bids again
                // clear accepted bid from task

                String oldProviderId = task.getProviderId();
                bidList = task.getBidList();
                task.clearAssignedProvider();

                if (task.getBidList().getSize() > 0 ) {
                    task.setStatus("Bidded");
                } else {
                    task.setStatus("Requested");
                }


                User userWithReinstatedBid;
                Bid losingBid;

                for(int i = 0; i < task.getBidList().getSize(); i++) {
                    losingBid = task.getBidList().getBid(i);

                    // load the user of that bid, if its not the winning one

                    ElasticsearchController.GetUser getUser
                            = new ElasticsearchController.GetUser();

                    getUser.execute(losingBid.getBidId());
                    userWithReinstatedBid = new User();

                    try {
                        userWithReinstatedBid = getUser.get();
                        Log.i("removing from bidlist:", userWithReinstatedBid.getUsername());
                    } catch (Exception e) {
                        Log.i("Error", e.toString());
                    }

                    BiddedTask newBiddedTask = new BiddedTask();

                    if (!losingBid.getBidId().equals(oldProviderId)) {

                        newBiddedTask.makeBiddedTask(task, losingBid);
                        userWithReinstatedBid.addBidTask(newBiddedTask);

                    } else {
                        int index = userWithReinstatedBid.getBidTaskList().getIndexOfBiddedTask(task);
                        newBiddedTask.makeBiddedTask(task, losingBid);
                        userWithReinstatedBid.getBidTaskList().replaceAtIndex(index, newBiddedTask);
                    }
                    ElasticsearchController.UpdateUser updateUser
                            = new ElasticsearchController.UpdateUser();
                    updateUser.execute(userWithReinstatedBid);
                }

                ElasticsearchController.UpdateTask updateTask = new ElasticsearchController.UpdateTask();
                updateTask.execute(task);

                ElasticsearchController.UpdateUser updateUser
                        = new ElasticsearchController.UpdateUser();
                updateUser.execute(currentUser);

                holder.acceptButton.setVisibility(View.VISIBLE);
                holder.declineButton.setVisibility(View.VISIBLE);
                holder.cancelButton.setVisibility(View.INVISIBLE);

                notifyDataSetChanged();


            }

        }));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.i("TestingAdapter", String.valueOf(bidList.getSize()));
        return bidList.getSize();
    }

    //public void refresh(BidList newBidList) {
     //   this.bidList = newBidList.copy();
       // this.notifyDataSetChanged();
    //}
}

