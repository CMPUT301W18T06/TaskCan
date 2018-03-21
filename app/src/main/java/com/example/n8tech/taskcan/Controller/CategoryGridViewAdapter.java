package com.example.n8tech.taskcan.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n8tech.taskcan.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by AlanJ on 2018-03-21.
 * http://www.viralandroid.com/2016/04/android-gridview-with-image-and-text.html
 */

public class CategoryGridViewAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> mCategories;

    public CategoryGridViewAdapter(Context context) {
        this.mContext = context;

        this.mCategories = new ArrayList<>();
        this.mCategories.addAll(Arrays.asList(context.getResources().getStringArray(R.array.categories_array)));
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View categoryGridView;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            categoryGridView = inflater.inflate(R.layout.category_grid_layout, null);
            TextView textViewAndroid = categoryGridView.findViewById(R.id.category_gridview_text);
            ImageView imageViewAndroid = categoryGridView.findViewById(R.id.category_gridview_image);
            textViewAndroid.setText(this.mCategories.get(position));
            imageViewAndroid.setImageResource(gridViewImageId[position]);
        } else {
            categoryGridView = convertView;
        }

        return categoryGridView;
    }

    private void buttonClick(View v, final int position) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, String.valueOf(position),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    int[] gridViewImageId = {
            R.drawable.ic_build_24dp, R.drawable.ic_local_shipping_24dp, R.drawable.ic_pets_24dp, R.drawable.ic_person_24dp,
            R.drawable.ic_landscape_24dp, R.drawable.ic_car_24dp, R.drawable.ic_local_dining_24dp, R.drawable.ic_create_24dp,
            R.drawable.ic_more_horiz_24dp,

    };
}
