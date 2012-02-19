/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;


public class SetInventoryVisibleAction extends BaseAction {

    private int invId;
    private boolean isVisible;

    public SetInventoryVisibleAction(BaseAction parent, int invId, boolean isVisible) {
        super(parent, parent.getApi());
        this.invId = invId;
        this.isVisible = isVisible;
    }

    @Override
    public void runGameAction() {

        run(1);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {
        getApi().getInventoryItem(this.invId).setVisible(
                this.isVisible);

    }

    @Override
    public boolean isParallel() {

        return false;
    }

}
