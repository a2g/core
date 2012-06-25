/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.DialogTreeBaseAction;


public class DialogTreeDisplayAction extends DialogTreeBaseAction {

    private String text;
    private int branchId;

    public DialogTreeDisplayAction(BaseAction parent, String text, int branchId) {
        super(parent, parent.getApi());
        this.setBranchId(branchId);
        this.setText(text);
    }

    @Override
    protected void onUpdate(double progress) {}

    @Override
    public void runGameAction() {
        super.run(1);
        getApi().getDialogTreeGui().setInDialogTreeMode(
                true);
    }

    @Override
    protected void onUpdateGameAction(double progress) {}

    @Override
    protected void onCompleteGameAction() {
        getApi().getDialogTreeGui().addBranch(
                branchId, text);
    }

    @Override
    public boolean isParallel() {

        return false;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
