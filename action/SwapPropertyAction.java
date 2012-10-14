/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.objectmodel.SceneObject;


public class SwapPropertyAction extends BaseAction {

    private short objId1;
    private short objId2;
    private SwapType type;

    public SwapPropertyAction(BaseAction parent, short objId1, short objId2, SwapType type) {
        super(parent, parent.getApi());
        this.objId1 = objId1;
        ;
        this.objId2 = objId2;
        this.type = type;
    }

    @Override
    public void runGameAction() {
        super.run(1);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {
        SceneObject a = getApi().getObject(
                this.objId1);
        SceneObject b = getApi().getObject(
                this.objId2);

        switch (this.type) {
        case Visibility:
            boolean temp = a.isVisible();

            a.setVisible(b.isVisible());
            b.setVisible(temp);

        }

    }

    @Override
    public boolean isParallel() {

        return false;
    }

}
