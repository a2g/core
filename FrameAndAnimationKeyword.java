/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import com.github.a2g.core.authoredroom.RoomBase;


public class FrameAndAnimationKeyword {
    private int currentFrame;
    private String currentAnimationKeyword;
    public FrameAndAnimationKeyword(String owner) {
        this.currentFrame = 0;
        this.currentAnimationKeyword = RoomBase.INITIAL;
    }

    public void setCurrentFrame(int frame) {
        this.currentFrame = frame;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentAnimationKeyword(String animationKeyword) {
        this.currentAnimationKeyword = animationKeyword;
    }

    public String getCurrentAnimationKeyword() {
        return this.currentAnimationKeyword;
    }

}


;
