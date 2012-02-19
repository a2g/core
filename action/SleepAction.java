/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;


public class SleepAction extends BaseAction {
    private int milliseconds;
    public SleepAction(BaseAction parent, int milliseconds) {
        super(parent, parent.getApi());
        this.milliseconds = milliseconds;
    }

    @Override	
    public void runGameAction() {
        this.run(this.milliseconds);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {}

    @Override
    public boolean isParallel() {

        return false;
    }

}
