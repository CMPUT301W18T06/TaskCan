package com.example.n8tech.taskcan;

import com.example.n8tech.taskcan.Models.Image;
import android.graphics.Bitmap;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing for Image class.
 *
 * @see Image
 * @author CMPUT301W18T06
 */

public class ImageTest {

    public ImageTest(){

    }

    @Test
    public void testImageConstructors(){

        int[] image_array = new int[100 * 100];
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Image image1 = new Image(bitmap);
        bitmap.getPixels(image_array, 0, 100, 0,0, 100, 100);
        Image image2 = new Image(image_array, 100, 100);
        assertEquals(image1, image2);
    }

    @Test
    public void testRecreateRecycledBitmap(){

    }
}
