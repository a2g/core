/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.Animation;
import com.github.a2g.core.action.BaseAction;


public class SetActiveAnimationAction extends BaseAction {
    private int animId;
    public SetActiveAnimationAction(BaseAction parent, int animId) {
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
        Animation a = getApi().getAnimation(
                this.animId);

        if (a != null) {
            a.setAsCurrentAnimation();
        }
    }

    @Override
    public boolean isParallel() {

        return false;
    }
}
