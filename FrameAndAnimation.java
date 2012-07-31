/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;

import com.github.a2g.core.authoredscene.SceneAPI;





public class FrameAndAnimation {
    private int currentFrame;
    private String currentAnimationTextualId;
    public FrameAndAnimation(String owner) {
        this.currentFrame = 0;
        this.currentAnimationTextualId = SceneAPI.INITIAL;
    }

    public void setCurrentFrame(int frame) {
        this.currentFrame = frame;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentAnimationTextualId(String animationTextualId) {
        this.currentAnimationTextualId = animationTextualId;
    }

    public String getCurrentAnimationTextualId() {
        return this.currentAnimationTextualId;
    }

}


;
