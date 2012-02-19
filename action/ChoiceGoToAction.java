/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChoicesBaseAction;


public class ChoiceGoToAction extends ChoicesBaseAction {

    private int place;

    public ChoiceGoToAction(BaseAction parent, int place) {
        super(parent, parent.getApi());
        this.setPlace(place);
    }

    @Override
    protected void onUpdate(double progress) {}

    @Override
    public void runGameAction() {
        super.run(1);
        getApi().getChoicesGui().setInChoicesMode(
                true);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {
        getApi().executeChoiceOnCurrentRoom(
                getPlace());
    }

    @Override
    public boolean isParallel() {

        return false;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPlace() {
        return place;
    }

}
