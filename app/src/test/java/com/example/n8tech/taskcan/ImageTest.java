package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.Image;
import android.graphics.Bitmap;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Unit testing for Image class.
 *
 * @see Image
 * @author CMPUT301W18T06
 */

public class ImageTest {
    private Bitmap bitmap;
    private int[] image_array;

    public ImageTest(){

    }

    @Test
    public void testImageConstructors(){
        bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        image_array = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.setPixels(image_array, 0, 100,0,0,100,100);
        Arrays.fill(image_array, 100);
        Image image1 = new Image(bitmap);
        Image image2 = new Image(image_array, 100, 100);
        assertEquals(image1, image2);
    }

    @Test
    public void testRecreateRecycledBitmap(){
        int[] image_array = new int[100 * 100];
        Arrays.fill(image_array, 50);
        Image image1 = new Image(image_array, 100, 100);
        image1.recreateRecycledBitmap();
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        bitmap.getPixels(image_array, 0, 100, 0,0, 100, 100);
        Image image2 = new Image(bitmap);
        assertEquals(image1, image2);
    }



}
