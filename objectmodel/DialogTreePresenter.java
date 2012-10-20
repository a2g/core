/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.a2g.core.objectmodel;


import com.github.a2g.bridge.panel.DialogTreePanel;
import com.github.a2g.bridge.thing.AcceptsOneThing;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.google.gwt.event.shared.EventBus;


public class DialogTreePresenter {
    private EventBus bus;
    private InternalAPI api;
    private DialogTree theDialogTree;
    private DialogTreePanel view;
    private boolean isInDialogTreeMode;
    private short dialogTreeTalker;
		
    public DialogTreePresenter(final AcceptsOneThing panel, EventBus bus, InternalAPI api) {
        this.bus = bus;
        this.api = api;
        this.theDialogTree = new DialogTree();
        this.view = new DialogTreePanel();
        panel.setThing(view);
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
