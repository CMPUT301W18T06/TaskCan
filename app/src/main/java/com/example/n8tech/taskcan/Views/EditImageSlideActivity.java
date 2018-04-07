package com.example.n8tech.taskcan.Views;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.n8tech.taskcan.Controller.SlideShowAdapter;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Image;
import com.example.n8tech.taskcan.Models.ImageList;
import com.example.n8tech.taskcan.R;

import me.relex.circleindicator.CircleIndicator;

public class EditImageSlideActivity extends AppCompatActivity {
    public final static String IMAGES_KEY = "EditImageSlideActivity_IMAGESKEY";
    public final static String RESULT_KEY = "EditImageSlideActivity_REQUESTKEY";

    private ImageList slides;
    private ViewPager mPager;
    private SlideShowAdapter adapter;
    private String result_code;
    private Integer currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image_slide);
        this.slides = new ImageList();
        //
        //this.slides.setImages(getIntent().getExtras().<Image>getParcelableArrayList(IMAGES_KEY));
        this.slides = CurrentUserSingleton.getImageList();

        this.result_code = getIntent().getExtras().getString(RESULT_KEY);
        this.initializeSlideShow();
    }

    private void initializeSlideShow() {
        this.mPager = findViewById(R.id.pager);
        this.adapter = new SlideShowAdapter(getApplicationContext(), slides);
        this.mPager.setAdapter(this.adapter);
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        new Runnable() {
            public void run() {
                if (currentPage == slides.getSize()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
                Log.i("slide image","-----------------------" + currentPage.toString());
            }
        };
    }

    // TODO always deletes the first image
    public void deleteButtonClick(View view) {
        Log.i("IMAGE SLIDE ACTIVITY --", this.currentPage.toString());
        this.slides.removeImage(this.currentPage);
        this.adapter.notifyDataSetChanged();
        onBackPressed();
    }

    /*@Override
    public void onBackPressed() {
        //Intent i = new Intent();
        //Bundle b = new Bundle();
        //b.putParcelableArrayList(this.result_code, this.slides.getImages());
        //i.putExtras(b);
        //setResult(Activity.RESULT_OK, i);

    }*/
}
