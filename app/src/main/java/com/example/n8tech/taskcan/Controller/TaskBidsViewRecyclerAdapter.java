package com.example.n8tech.taskcan.Controller;

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
import com.example.n8tech.taskcan.R;
import com.example.n8tech.taskcan.Views.ViewBidsActivity;

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
    private Bid acceptedBid;
    private int currentSelectedPosition = RecyclerView.NO_POSITION;



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

        public ViewHolder(View view) {
            super(view);
            bidUsername = view.findViewById(R.id.bid_list_user);
            bidAmount = view.findViewById(R.id.bid_list_amount);
            acceptButton = view.findViewById(R.id.accept_button);
            declineButton = view.findViewById(R.id.decline_button);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskBidsViewRecyclerAdapter(BidList myBidList) {
        this.bidList = myBidList;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Bid currentBid = bidList.getBid(position);
        holder.bidUsername.setText(currentBid.getBidUsername());
        holder.bidAmount.setText(String.valueOf(currentBid.getBidAmount()));

        holder.bidUsername.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // TODO: Username Click

            }
        });

        holder.acceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO: Accept button click
                // delete all but the current position
                acceptedBid = bidList.getBid(position);

                BidList newBidList = new BidList();
                newBidList.addBid(acceptedBid);
                bidList = newBidList;

                // add accepted bid back to list
                //bidList.addBid(acceptedBid);

                // TODO make accepted & decline bid invisible, make a cancel button visible

                //holder.button.setVisibility(View.INVISIBLE);
                notifyDataSetChanged();

                //finish();            // TODO stop this activity, goes back to details
            }
        });


        holder.declineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bidList.removeBid(bidList.getBid(position));
                // TODO update elastic search
                // or just reupload task?
                notifyDataSetChanged();
            }
        });

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

