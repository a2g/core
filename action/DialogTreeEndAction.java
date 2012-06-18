/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.DialogTreeBaseAction;


public class DialogTreeEndAction extends DialogTreeBaseAction {

    public DialogTreeEndAction(BaseAction parent) {
        super(parent, parent.getApi());
    }

    @Override
    protected void onUpdate(double progress) {}

    @Override
    public void runGameAction() {
        super.run(1);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {
        getApi().getChoicesGui().setInChoicesMode(
                false);
    }

    @Override
    public boolean isParallel() {

        return false;
    }

}
