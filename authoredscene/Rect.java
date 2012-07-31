/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredscene;


public class Rect {
    private int left;
    private int top;
    private int right;
    private int bottom;

    public Rect(int x, int y, int width, int height) {
        this.left = x;
        this.top = y;
        this.right = x + width;
        this.bottom = y + height;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }

}
