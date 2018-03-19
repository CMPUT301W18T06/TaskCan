package com.example.n8tech.taskcan.Controller;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.n8tech.taskcan.Models.ImageList;
import com.example.n8tech.taskcan.R;

import java.util.ArrayList;

/**
 * SlideShowAdapter represents an image slider to be used for photos.
 *
 * https://www.androidtutorialpoint.com/basics/android-image-slider-tutorial/
 *
 * @author CMPUT301W18T06
 */

public class SlideShowAdapter extends PagerAdapter {
    private ImageList images;
    private LayoutInflater inflater;
    private Context context;

    public SlideShowAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.images = new ImageList();
    }

    public SlideShowAdapter(Context context, ImageList images) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.images = images;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = this.inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.slide);
        myImage.setImageBitmap(this.images.getImage(position).getImage());
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public int getCount() {
        return this.images.getSize();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
