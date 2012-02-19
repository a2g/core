/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;


public class SetTalkingAnimationAction extends BaseAction {
    private int animId;

    public SetTalkingAnimationAction(BaseAction parent, int animId) {
        super(parent, parent.getApi());
        this.animId = animId;
    }

    @Override
    public void runGameAction() {
        super.run(1);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {
        getApi().getAnimation(this.animId).setAsTalkingAnimation();
    }

    @Override
    public boolean isParallel() {

        return false;
    }

}
