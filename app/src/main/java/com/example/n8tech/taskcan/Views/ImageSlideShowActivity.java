/*
 *  Copyright (c) 2018 Alexander Filbert, Carolyn Binns, Jeanna Somoza, JingMing Huang, Matthew Quigley, Nathanael Belayneh
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.n8tech.taskcan.Views;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.n8tech.taskcan.Controller.SlideShowAdapter;
import com.example.n8tech.taskcan.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * ImageSlideShowActivity handles the implementation of an image slider
 * used for photos related to tasks.
 *
 * @author CMPUT301W18T06
 */

public class ImageSlideShowActivity extends AppCompatActivity {
    private static final String IMAGES_KEY = "ImageSlideShowActivity_IMAGES";
    private int[] images;
    private ArrayList<Integer> slides;
    private ViewPager mPager;
    private int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slide_show);
        this.images = getIntent().getIntArrayExtra(IMAGES_KEY);
        this.initialialSlideShow();
    }

    private void initialialSlideShow() {
        for (int i = 0; i < images.length; i++) {
            this.slides.add(images[i]);
        }
        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new SlideShowAdapter(ImageSlideShowActivity.this, slides));
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
    }
}
