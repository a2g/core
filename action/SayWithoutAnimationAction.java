/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;


public class SayWithoutAnimationAction extends BaseAction {
    private String speech;
    private PopupPanel popup;

    public SayWithoutAnimationAction(BaseAction parent, int objId, String speech) {
        super(parent, parent.getApi());

        this.speech = speech;
        this.popup = null;
    }

    @Override
    public void runGameAction() {
        int delay = 0;
        int duration = (this.speech.length()
                * (2 + delay))
                        * 40;

        this.popup = new PopupPanel();
        this.popup.setTitle(this.speech);
        this.popup.setWidget(
                new Label(this.speech));

        this.popup.setPopupPosition(20, 20);
        this.popup.show();

        this.run(duration);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {
        if (this.popup != null) {
            this.popup.hide();
        }
    }

    @Override
    public boolean isParallel() {

        return false;
    }

}