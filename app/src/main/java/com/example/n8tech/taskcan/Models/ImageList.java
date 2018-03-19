package com.example.n8tech.taskcan.Models;

import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * ImageList contains an iterable ArrayList of Images.
 *
 * @author CMPUT301W18T06
 * @see Image
 */

public class ImageList implements Iterable<Image>{
    private ArrayList<Image> images;

    /** Creates a new instance of ImageList, creates a new ArrayList */
    public ImageList() {
        this.images = new ArrayList<>();
    }

    /** @param image the Image object to be added */
    public void addImage(Image image) {
        images.add(image);
    }

    /**
     * Replaces the image at the specified index with a new image.
     * @param position index of task to be replaced
     * @param image new Image object to replace current Image at the index
     */
    public void replace(int position, Image image) {
        this.images.set(position, image);
    }

    /** @return position integer representing Image position in the list */
    public Image getImage(int position) {
        return this.images.get(position);
    }

    /** @return the ArrayList of images */
    public ArrayList<Image> getImages() {
        return this.images;
    }

    @NonNull
    @Override
    /** Creates an ImageList iterator. */
    public Iterator<Image> iterator() {
        return new ImagesIterator();
    }

    /**
     * Iterator over ImageList
     * @throws UnsupportedOperationException If remove() method is called.
     */
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
