/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChoicesBaseAction;


/* ! This inherits from ChoicesBaseAction because it is valid to be used as 
 *  the last action in a chain that is returned from onChoice().
 *  You can use it in all places where you would use a GameAction
 */
public class SwitchToAction extends ChoicesBaseAction {
    private String room;

    public SwitchToAction(BaseAction parent, String e) {
        super(parent, parent.getApi());
        this.room = e;
    }

    @Override
    public void runGameAction() {

        super.run(1);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {

        getApi().switchToRoom(this.room);

    }

    @Override
    public boolean isParallel() {

        return false;
    }

}
