/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.sceneobject.Animation;
import com.github.a2g.core.sceneobject.SceneObject;


public class PlayAnimationAction extends BaseAction {
    private int delay;
    private Animation anim;
    private SceneObject animsParent;
    private boolean isBackwards;
    private boolean holdLastFrame;
    private boolean isNonBlocking;

    public PlayAnimationAction(BaseAction parent, int animId) {
        super(parent, parent.getApi());
        this.anim = getApi().getAnimation(
                animId);
        this.animsParent = anim.getObject();
        this.delay = 0;
        this.isBackwards = false;
        this.holdLastFrame = false;
        this.isNonBlocking = false;
    }

    @Override
    public void runGameAction() {
        int duration = (this.anim.getLength()
                + 1)
                        * (40 + this.delay);
        String s = this.anim.getTextualId();

        if (animsParent != null) {
            animsParent.setCurrentAnimation(s);
            animsParent.setVisible(true);
        }
        this.run(duration);
    }

    @Override
    protected void onUpdateGameAction(double progress) {
        int length = this.anim.getLength();
        double frame = isBackwards
                ? (1 - progress) * length
                : progress * length;

        if (animsParent != null) {
            animsParent.setCurrentFrame(
                    (int) frame);
        }
    }

    @Override
    protected void onCompleteGameAction() {
        
        if (!this.holdLastFrame) {
            if (this.anim != null) {
                SceneObject o = anim.getObject();

                if (o != null) {
                    String s = o.getHomeAnimation();

                    o.setCurrentAnimation(s);
                }
            }
        }
    }

    public void setBackwards(boolean isBackwards) {
        this.isBackwards = isBackwards;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setHoldLastFrame(boolean holdLastFrame) {
        this.holdLastFrame = holdLastFrame;
    }

    public void setNonBlocking(boolean isNonBlocking) {
        this.isNonBlocking = isNonBlocking;
    }

    @Override
    public boolean isParallel() {

        return isNonBlocking;
    }

}
