package com.example.n8tech.taskcan.Models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;

import io.searchbox.annotations.JestId;

/**
 * Image contains an ArrayList of integers representing pixel values
 * and methods related to drawing images.
 *
 * @author CMPUT301W18T06
 */

public class Image implements Parcelable {
    private Bitmap image;
    private int width;
    private int height;

    private int[] image_array;

    @JestId
    private String id;
    /**
     * Creates an instance of Image, creating a new ArrayList.
     */
    public Image(Bitmap bitmap) {
        this.image = bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.image_array = new int[this.width * this.height];
        bitmap.getPixels(this.image_array, 0, this.width, 0, 0, this.width, this.height);
    }

    public Image(int[] image_array, int x, int y) {
        this.image_array = image_array;
        this.width = x;
        this.height = y;
        this.image = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        this.image.setPixels(image_array, 0, x, 0, 0, x, y);
    }

    public void recreateRecycledBitmap() {
        this.image = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        this.image.setPixels(this.image_array, 0, this.width, 0, 0, width, height);
    }


    protected Image(Parcel in) {
        image = in.readParcelable(Bitmap.class.getClassLoader());
        width = in.readInt();
        height = in.readInt();
        image_array = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(image, flags);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeIntArray(image_array);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    /** @return an ArrayList of integers representing pixel values */
    public Bitmap getImageBitmap() {
        return this.image;
    }

    public int[] getImageArray() { return this.image_array; }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
