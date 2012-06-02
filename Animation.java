/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import com.github.a2g.core.authoredroom.RoomBase;


public class Animation {
    private String textualId;
    private com.github.a2g.core.ImageCollection imageAndPosCollection;
    private RoomObject parent;
    private boolean wasSetAsHomeAnimation;
    private boolean wasSetAsTalkingAnimation;
    private boolean wasSetAsCurrentAnimation;
    private RoomBase.Special specialAnimationThisWasSetTo;

    public Animation(String textualId, RoomObject owningRoomObject) {
        this.parent = owningRoomObject;
        this.textualId = textualId;
        imageAndPosCollection = new com.github.a2g.core.ImageCollection();
        wasSetAsHomeAnimation = false;
        wasSetAsTalkingAnimation = false;
        wasSetAsCurrentAnimation = false;
        specialAnimationThisWasSetTo = null;
    }

    public com.github.a2g.core.ImageCollection getFrames() {
        return imageAndPosCollection;
    }

    public String getTextualId() {
        return textualId;
    }

    public RoomObject	getObject() {
        return parent;
    }

    public void setTextualId(String textualId) {
        this.textualId = textualId;
    }

    public com.github.a2g.core.ImageCollection getImageAndPosCollection() {
        return imageAndPosCollection;
    }

    public com.github.a2g.core.Image getDefaultFrame() {
        assert(imageAndPosCollection.count()
                != 0);
        if (imageAndPosCollection.count() == 0) {
            return null;
        }
        com.github.a2g.core.Image frame = imageAndPosCollection.at(
                0);

        return frame;
    }

    public int getLength() { 
        return imageAndPosCollection.count(); 
    }

    public int getLastFrame() {
        return imageAndPosCollection.count()
                - 1;
    }

    public void preloadAnimation() {}

    public void playAnimation(int delay) {}

    public void playAnimation() {}

    public void playAnimationNonBlocking(int delay) {}

    public void playAnimationNonBlocking() {}

    public void playAnimationHoldLastFrame(int delay) {}

    public void playAnimationHoldLastFrame() {}

    public void playAnimationHoldLastFrameNonBlocking(int delay) {}

    public void playAnimationHoldLastFrameNonBlocking() {}

    public void playAnimationRepeatWhilstVisible(int delay) {}

    public void playAnimationRepeatWhilstVisible() {}

    public void playAnimationBackwards(int delay) {}

    public void playAnimationBackwards() {}

    public void playAnimationBackwardsHoldLastFrame(int delay) {}

    public void playAnimationBackwardsHoldLastFrame() {}

    public void setAsSpecialAnimation(RoomBase.Special special) {
        specialAnimationThisWasSetTo = special;
        if (parent != null) {
            parent.setSpecialAnimation(special,
                    textualId);
        }
    }

    public void setAsCurrentAnimationAndSetFrame(int i) {
        parent.setCurrentFrame(i);
        parent.setCurrentAnimation(textualId);
    }

    public void setAsCurrentAnimation() {
        this.wasSetAsCurrentAnimation = true;
        if (parent != null) {
            parent.setCurrentAnimation(textualId);
        }
    }

    public void setAsTalkingAnimation() {
        this.wasSetAsTalkingAnimation = true;
        if (parent != null) {
            parent.setTalkingAnimation( this.textualId);
        }
    }

    public void setAsHomeAnimation() {
        this.wasSetAsHomeAnimation = true;
        if (parent != null) {
            parent.setHomeAnimation(
                    this.textualId);
        }
    }

    public void setRoomObject(RoomObject parent) {
        this.parent = parent;
    }

    public RoomObject getRoomObject() {
        return parent;
    }

    public boolean getWasSetAsHomeAnimation() {
        return wasSetAsHomeAnimation;
    }

    public boolean getWasSetAsTalkingAnimation() {
        return wasSetAsTalkingAnimation;
    }

    public boolean getWasSetAsCurrentAnimation() {
        return wasSetAsCurrentAnimation;
    }

    public boolean getWasSetAsSpecialAnimation() {
        boolean wasSet = specialAnimationThisWasSetTo
                != null;

        return wasSet;
    }

    public RoomBase.Special getDesignatedSpecialAnimation() {
        return specialAnimationThisWasSetTo;
    }
}


;
