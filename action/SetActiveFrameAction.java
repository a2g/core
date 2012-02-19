/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;


public class SetActiveFrameAction extends BaseAction {
    private int objId;
    private int frame;

    public SetActiveFrameAction(BaseAction parent, int objId,
            int frame) {
        super(parent, parent.getApi());
        this.objId = objId;
        this.frame = frame;
    }

    @Override
    public void runGameAction() {
        super.run(1);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {
        getApi().getObject(this.objId).setCurrentFrame(
                this.frame);

    }

    @Override
    public boolean isParallel() {

        return false;
    }

}
