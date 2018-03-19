package com.example.n8tech.taskcan.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ahuang on 2018-03-19.
 */

public class Image {
    private ArrayList<Integer> image;
    public Image() {
        this.image = new ArrayList<>();
    }
    public void setImagePixel(int position, int value) {
        this.image.set(position, value);
    }
    public void setImagePixel(int value) {
        this.image.add(value);
    }
    public void setImage(ArrayList<Integer> image) {
        this.image = image;
    }
    public ArrayList<Integer> getImageArrayList() {
        return this.image;
    }
    public int[] getImageArray() {
        int[] image = new int[this.image.size()];
        for (int i=0; i < image.length; i++) {
            image[i] = this.image.get(i);
        }
        return image;
    }

}
