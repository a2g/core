/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import com.github.a2g.core.authoredroom.InternalAPI;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


public class DialogTreePresenter {
    private EventBus bus;
    private InternalAPI api;
    private DialogTree theDialogTree;
    private DialogTreePanel view;
    private boolean isInDialogTreeMode;
    private short dialogTreeTalker;
		
    public DialogTreePresenter(final AcceptsOneWidget panel, EventBus bus, InternalAPI api) {
        this.bus = bus;
        this.api = api;
        this.theDialogTree = new DialogTree();
        this.view = new DialogTreePanel();
        panel.setWidget(view);
        this.dialogTreeTalker = 0;
        
        this.api = api;
       
        this.isInDialogTreeMode = false;
    }
	  
    public void clear() {
        theDialogTree.clear();
        view.update(theDialogTree, bus);
    }

    public void setInDialogTreeMode(boolean isInDialogTree) {
        this.isInDialogTreeMode = isInDialogTree;
        view.setVisible(isInDialogTreeMode);
        api.getVerbsGui().setVisible(
                !isInDialogTreeMode);
        api.getInventoryGui().setVisible(
                !isInDialogTreeMode);
        api.getCommandLineGui().setVisible(
                !isInDialogTreeMode);
    }
    
    public void addBranch(int subBranchId, String lineOfDialog) {
        setInDialogTreeMode(true);
        theDialogTree.addSubBranch(subBranchId, lineOfDialog);
        view.update(theDialogTree, bus);
    }

    public boolean isInDialogTreeMode() {
        return this.isInDialogTreeMode;
    }
	
    public void setDialogTreeTalker(short personWhoSpeaksTheChosenDialog) {
        this.dialogTreeTalker = personWhoSpeaksTheChosenDialog;

    }

    public short getDialogTreeTalker() {
        return this.dialogTreeTalker;
    }

}
