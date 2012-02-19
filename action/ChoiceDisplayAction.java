/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChoicesBaseAction;


public class ChoiceDisplayAction extends ChoicesBaseAction {

    private String text;
    private int place;

    public ChoiceDisplayAction(BaseAction parent, String text, int place) {
        super(parent, parent.getApi());
        this.setPlace(place);
        this.setText(text);
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
        getApi().getChoicesGui().addChoice(
                place, text);
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

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
