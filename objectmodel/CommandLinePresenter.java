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



import com.github.a2g.core.event.ExecuteCommandEvent;
import com.github.a2g.core.event.ExecuteCommandEventHandlerAPI;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.event.SetRolloverEventHandlerAPI;
import com.github.a2g.core.interfaces.CommandLineCallbackAPI;
import com.github.a2g.core.interfaces.CommandLinePanelAPI;
import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.SceneAPI;
import com.google.gwt.event.shared.EventBus;



public class CommandLinePresenter 
        implements ExecuteCommandEventHandlerAPI, SetRolloverEventHandlerAPI {

    private SceneAPI callbacks;
    private CommandLineCallbackAPI api;
    private CommandLinePanelAPI view;
    private CommandLine model;
    
    public CommandLinePresenter(final HostingPanelAPI panel, EventBus bus, CommandLineCallbackAPI api) {
        this.model = new CommandLine(api);
        this.api = api;
        this.view = api.getFactory().createCommandLinePanel();
        panel.setThing(view);
        
        bus.addHandler(
                ExecuteCommandEvent.TYPE, this);
        bus.addHandler(SetRolloverEvent.TYPE,
                this);
    }
   
    public void setVisible(boolean isVisible) {
        model.setVisible(isVisible);
        view.setVisible(isVisible);
    }
    
    public void setMouseable(boolean mouseable) {
        model.setMouseable(mouseable);
        updateImage();
    }

    @Override
    public void onSetMouseOver(String displayName, String textualId, int code) {
        if (api.isInDialogTreeMode()) {
            return;
        }
        model.setMouseOver(displayName, textualId,
                code);

        updateImage();

    }

    @Override
    public boolean onClick(double x, double y) {
    	
        if (isOkToExecute()) {
        	System.out.println("ONEXECUTECOMMAND::execute  " + model.getSentence().getDisplayName());
            this.execute(x, y);
            return true;
        }
        
        System.out.println("ONEXECUTECOMMAND::nextbestthing  " + model.getSentence().getDisplayName());
        this.doNextBestThingToExecute();
        
        return false;
    }
    
    //
    //
    //
    //
    //
    
    
    private void clear() {
        model.clear();
        updateImage();
    }
    
    
    private void doNextBestThingToExecute() {	
        model.doNextBestThingToExecute();
        updateImage();
    }
    
    private boolean isOkToExecute() {
        boolean isOkToExecute = model.isOkToExecute();

        return isOkToExecute;
    }


    private void updateImage() {
        Sentence sentence = getSentence();

        sentence.setBBB(
                new SentenceUnit("", "", -1));
        sentence.setAAA(
                new SentenceUnit("", "", -1));
        String displayName = sentence.getDisplayName();

        view.setText(displayName+" ");
    }
    
    private  Sentence getSentence() {
        Sentence sentence = model.getSentence();

        return sentence;
    }
    private void execute(double x, double y) {
        // if its incomplete, the clear everything..
        if (!model.isOkToExecute()) {
            clear();
            return;
        }

        if (this.api != null) {
            int verbAsCode = model.getSentence().getVerbAsCode();
            SentenceUnit sentenceA = model.getSentence().getAAA();
            SentenceUnit sentenceB = model.getSentence().getBBB();
            // clamp the mouse pointer to within the viewport coords
            if(x>1.0) x=1.0;
            if(x<0.0) x=0.0;
            if(y>1.0) y=1.0;
            if(y<0.0) y=0.0;
            
            api.doCommand( verbAsCode, getSentence().getVerbAsVerbEnumeration(), sentenceA,
                    sentenceB, x, y);
           
        }

        clear();
    }
    
}
