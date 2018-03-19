package com.example.n8tech.taskcan.Controller;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * SlideShowAdapter represents an image slider to be used for photos.
 *
 * https://www.androidtutorialpoint.com/basics/android-image-slider-tutorial/
 *
 * @author CMPUT301W18T06
 */

public class SlideShowAdapter extends PagerAdapter {
    private ArrayList<Integer> slides;
    private LayoutInflater inflater;
    private Context context;

    public SlideShowAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.slides = new ArrayList<>();
    }

    public SlideShowAdapter(Context context, ArrayList<Integer> slides) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.slides = slides;
    }

    @Override
    public int getCount() {
        return this.slides.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
