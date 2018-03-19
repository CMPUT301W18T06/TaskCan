package com.example.n8tech.taskcan.Models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Image contains an ArrayList of integers representing pixel values
 * and methods related to drawing images.
 *
 * @author CMPUT301W18T06
 */

public class Image implements Parcelable {
    private Bitmap image;
    private int[] image_array;
    /**
     * Creates an instance of Image, creating a new ArrayList.
     */
    public Image(Bitmap bitmap) {
        this.image = bitmap;
        int x = bitmap.getWidth();
        int y = bitmap.getHeight();
        this.image_array = new int[x * y];
        bitmap.getPixels(this.image_array, 0, x, 0, 0, x, y);
    }

    protected Image(Parcel in) {
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
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

    /** @param image an ArrayList of integers */
    public void setImage(Bitmap image) {
        this.image = image;
        int x = this.image.getWidth();
        int y = this.image.getHeight();
        this.image_array = new int[x * y];
        this.image.getPixels(this.image_array, 0, x, 0, 0, x, y);
    }

    /** @return an ArrayList of integers representing pixel values */
    public Bitmap getImage() {
        return this.image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(image, i);
    }
}
