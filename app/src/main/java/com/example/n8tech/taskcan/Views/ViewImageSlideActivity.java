package com.example.n8tech.taskcan.Views;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.n8tech.taskcan.Controller.SlideShowAdapter;
import com.example.n8tech.taskcan.Models.Image;
import com.example.n8tech.taskcan.Models.ImageList;
import com.example.n8tech.taskcan.R;

import me.relex.circleindicator.CircleIndicator;

public class ViewImageSlideActivity extends AppCompatActivity {
    private ImageList slides;
    private ViewPager mPager;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_slide);
        this.slides = new ImageList();
        this.slides.setImages(getIntent().getExtras().<Image>getParcelableArrayList(TaskDetailActivity.IMAGES_KEY));
        this.initialialSlideShow();
    }
    private void initialialSlideShow() {
        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new SlideShowAdapter(getApplicationContext(), slides));
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        new Runnable() {
            public void run() {
                if (currentPage == slides.getSize()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
    }
}
