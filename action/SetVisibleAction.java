/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.RoomObject;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.authoredroom.IAmTheMainAPI;


public class SetVisibleAction extends BaseAction {
    private short objId;
    private boolean isVisible;

    public SetVisibleAction(BaseAction parent, short objId, boolean isVisible) {
        super(parent, parent.getApi());
        this.objId = objId;
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
        IAmTheMainAPI i = getApi();
        RoomObject o = i.getObject(this.objId);

        if (o != null) {
            o.setVisible(this.isVisible);
        } else {
            assert(o != null);
        }
    }

    @Override
    public boolean isParallel() {

        return false;
    }
}
