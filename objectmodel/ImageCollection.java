/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.objectmodel;


import com.github.a2g.bridge.image.Image;

import java.util.Vector;


public class ImageCollection {
    private Vector<Image> theVector;

    ImageCollection() {
        theVector = new Vector<Image>();
    }

    public com.github.a2g.bridge.image.Image at(int index) {
        if (index < 0
                || index >= theVector.size()) {
            return null;
        }
        return theVector.get(index);
    }

    public void add(Image image) {
        theVector.add(image);
    }

    public int count() {
        return theVector.size();
    }

}
