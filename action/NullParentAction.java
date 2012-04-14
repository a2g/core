/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.authoredroom.IAmTheMainAPI;


public class NullParentAction extends BaseAction {
    public NullParentAction() {
        super(null, null);
    }

    public void setApi(IAmTheMainAPI api) {
        super.setApi(api);
    }

    @Override
    public void runGameAction() {
        super.run(1); // the delayed execution trick
    }

    @Override
    protected void onCompleteGameAction() {}

    @Override
    protected void onUpdateGameAction(double progress) {}

    public void setParent(BaseAction parent) {
        this.parent = parent;
    }

    @Override
    public boolean isParallel() {

        return false;
    }

}