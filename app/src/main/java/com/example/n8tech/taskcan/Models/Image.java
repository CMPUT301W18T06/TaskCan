package com.example.n8tech.taskcan.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Image contains an ArrayList of integers representing pixel values
 * and methods related to drawing images.
 *
 * @author CMPUT301W18T06
 */

public class Image {
    private ArrayList<Integer> image;

    /**
     * Creates an instance of Image, creating a new ArrayList.
     */
    public Image() {
        this.image = new ArrayList<>();
    }

    /** @param value integer representing the pixel value */
    public void setImagePixel(int value) {
        this.image.add(value);
    }

    /** @param image an ArrayList of integers */
    public void setImage(ArrayList<Integer> image) {
        this.image = image;
    }

    /** @return an ArrayList of integers representing pixel values */
    public ArrayList<Integer> getImageArrayList() {
        return this.image;
    }

    /** @return an Array of integers representing pixel values */
    public int[] getImageArray() {
        int[] image = new int[this.image.size()];
        for (int i=0; i < image.length; i++) {
            image[i] = this.image.get(i);
        }
        return image;
    }

}
