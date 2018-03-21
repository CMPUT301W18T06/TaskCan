package com.example.n8tech.taskcan.Controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.n8tech.taskcan.R;
import com.example.n8tech.taskcan.Views.ViewCategoryActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by AlanJ on 2018-03-21.
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
        Button button;
        if (convertView == null) {
            button = new Button(this.mContext);
            button.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            button = (Button) convertView;
        }
        button.setText(this.mCategories.get(position));
        this.buttonClick(button, position);
        return button;
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
}
