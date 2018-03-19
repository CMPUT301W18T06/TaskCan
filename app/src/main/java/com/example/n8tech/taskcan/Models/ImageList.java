package com.example.n8tech.taskcan.Models;

import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by ahuang on 2018-03-19.
 */

public class ImageList implements Iterable<Image>{
    private ArrayList<Image> images;
    public ImageList() {
        this.images = new ArrayList<>();
    }

    public void addImage(Image image) {
        images.add(image);
    }
    public void replace(int position, Image image) {
        this.images.set(position, image);
    }
    public Image getImage(int position) {
        return this.images.get(position);
    }
    public ArrayList<Image> getImages() {
        return this.images;
    }

    @NonNull
    @Override
    public Iterator<Image> iterator() {
        return new ImagesIterator();
    }

    private class ImagesIterator implements Iterator<Image> {

        private int index = 0;

        public boolean hasNext() {
            return (this.index < images.size());
        }

        public Image next() {
            return images.get(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException("not supported yet");
        }
    }
}
