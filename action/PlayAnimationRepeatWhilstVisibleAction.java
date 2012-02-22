/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.Animation;
import com.github.a2g.core.action.BaseAction;


public class PlayAnimationRepeatWhilstVisibleAction extends BaseAction {
    private Animation anim;

    public PlayAnimationRepeatWhilstVisibleAction(BaseAction parent, int animId) {
        super(parent, parent.getApi());
        this.anim = getApi().getAnimation(
                animId);
    }

    @Override
    public void runGameAction() {
        int duration = (this.anim.getLength()
                + 1)
                        * 40
                        * 10;
        String s = this.anim.getTextualId();

        this.anim.getObject().setCurrentAnimation(
                s);
        this.anim.getObject().setVisible(true);
        this.run(duration);
    }

    @Override
    protected void onUpdateGameAction(double progress) {
        double frame = progress
                * this.anim.getLength();

        this.anim.getObject().setCurrentFrame(
                (int) frame);
    }

    @Override
    protected void onCompleteGameAction() {}

    @Override
    public boolean isParallel() {

        return false;
    }

}
