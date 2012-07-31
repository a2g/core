/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.BaseDialogTreeAction;


/* ! This inherits from BaseDialogTreeAction because it is valid to be used as 
 *  the last action in a chain that is returned from SceneAPI.onDialogTree().
 *  You can use it in all places where you would use a GameAction
 */
public class SwitchToAction extends BaseDialogTreeAction {
    private String scene;

    public SwitchToAction(BaseAction parent, String e) {
        super(parent, parent.getApi());
        this.scene = e;
    }

    @Override
    public void runGameAction() {

        super.run(1);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {

        getApi().switchToScene(this.scene);

    }

    @Override
    public boolean isParallel() {

        return false;
    }

}
