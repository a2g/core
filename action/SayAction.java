/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.Animation;
import com.github.a2g.core.RoomObject;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.authoredroom.ColorEnum;
import com.github.a2g.core.authoredroom.IAmTheMainAPI;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;


public class SayAction extends BaseAction {
    private String speech;
    private PopupPanel popup;
    private int objId;
    private Animation anim;
    private RoomObject object;

    public SayAction(BaseAction parent, int objId, String speech) {
        super(parent, parent.getApi());
        this.objId = objId;
        this.speech = speech;
        this.popup = null;

        IAmTheMainAPI api = getApi();

        this.object = api.getObject(this.objId);

        if (object != null) {
            String talkingAnimKeyword = object.getTalkingAnimation();

            anim = object.animations().at(
                    talkingAnimKeyword);
        }

    }

    @Override
    public void runGameAction() {
        int delay = 0;
        int duration = (this.speech.length()
                * (2 + delay))
                        * 40;

        this.popup = new PopupPanel();
        this.popup.setTitle(this.speech);
        Label label = new Label(this.speech);

        this.popup.setWidget(label);
        
        ColorEnum color = ColorEnum.Blue;

        if (object != null
                && object.getTalkingColor()
                        != null) {
            color = object.getTalkingColor();
        }
        DOM.setStyleAttribute(
                label.getElement(), "color",
                color.toString());
        DOM.setStyleAttribute(
                popup.getElement(),
                "borderColor",
                color.toString());
        
        if (this.anim != null) {
            this.anim.getObject().setCurrentAnimation(
                    this.anim.getKeyword());
            this.anim.getObject().setVisible(
                    true);
        }

        this.popup.setPopupPosition(20, 20);
        this.popup.show();

        this.run(duration);
    }

    @Override
    protected void onUpdateGameAction(double progress) {
        if (this.anim != null) {
            double frame = progress
                    * this.anim.getLength();

            this.anim.getObject().setCurrentFrame(
                    (int) frame);
        }
    }

    @Override
    protected void onCompleteGameAction() {
        if (this.anim != null) {
            this.anim.getObject().setCurrentAnimation(
                    this.anim.getObject().getHomeAnimation());
        }

        if (this.popup != null) {
            this.popup.hide();
        }
    }

    @Override
    public boolean isParallel() {

        return false;
    }

}
