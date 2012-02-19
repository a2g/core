/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.RoomObject;
import com.github.a2g.core.action.BaseAction;


public class WaitForFrameAction extends BaseAction {
    private int frame;
    private RoomObject object;

    public WaitForFrameAction(BaseAction parent, int objectId, int frame) {
        super(parent, parent.getApi());
        this.frame = frame;
        this.object = getApi().getObject(
                objectId);
    }

    @Override	
    public void runGameAction() {
        // we achieve the variable execution time, by using a max value here..
        String name = object.currentAnimation();
        int count = object.animations().at(name).getFrames().count();
        int milliseconds = count * 150;

        this.run(milliseconds);
    }

    @Override
    protected void onUpdateGameAction(double progress) {
        // ..then cancelling the animation when it reaches the desired condition.
        if (object.getCurrentFrame() == frame) {
            cancel();
        }
    }

    @Override
    protected void onCompleteGameAction() {}

    @Override
    public boolean isParallel() {
        return false;
    }

}
