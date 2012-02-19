/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.authoredroom.IAmARoom;
import com.github.a2g.core.authoredroom.IAmTheMainAPI;
import com.github.a2g.core.event.ExecuteCommandEvent;
import com.github.a2g.core.event.ExecuteCommandEventHandler;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.event.SetRolloverEventHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.UIObject;


public class CommandLinePresenter 
        implements ExecuteCommandEventHandler, SetRolloverEventHandler {

    private IAmARoom callbacks;
    private IAmTheMainAPI api;
    private CommandLinePanel view;
    private CommandLine model;
    
    public CommandLinePresenter(final AcceptsOneWidget panel, EventBus bus, IAmTheMainAPI api) {
        this.model = new CommandLine(api);
        this.callbacks = null;
       
        this.api = api;
        this.view = new CommandLinePanel();
        panel.setWidget(view);
        
        bus.addHandler(
                ExecuteCommandEvent.TYPE, this);
        bus.addHandler(SetRolloverEvent.TYPE,
                this);
    }

    public void setCallbacks(IAmARoom callback) {
        this.callbacks = callback;
    }

    public void updateImage() {
        Sentence sentence = getSentence();

        sentence.setBBB(
                new SentenceUnit("", "", -1));
        sentence.setAAA(
                new SentenceUnit("", "", -1));
        String displayName = sentence.getDisplayName();

        view.setText(displayName);
    }

    public Sentence getSentence() {
        Sentence sentence = model.getSentence();

        return sentence;
    }

    public void onRightClick() {
        clear();
    }

    public void execute(double x, double y) {
        // if its incomplete, the clear everything..
        if (!model.isOkToExecute()) {
            clear();
            return;
        }

        if (this.callbacks != null) {
            int verbAsCode = model.getSentence().getVerbAsCode();
            SentenceUnit sentenceA = model.getSentence().getAAA();
            SentenceUnit sentenceB = model.getSentence().getBBB();
            BaseAction a = this.callbacks.onCommandLineExecute(
                    verbAsCode, sentenceA,
                    sentenceB, x, y);

            api.executeActionBaseOrChoiceActionBaseAndProcessReturnedInteger(
                    a);
            api.setLastCommand(x, y,
                    getSentence().getVerbAsVerbEnumeration(),
                    sentenceA.getKeyword(),
                    sentenceB.getKeyword());
        }

        clear();
    }
    
    public void setVisible(boolean isVisible) {
        model.setVisible(isVisible);
        UIObject.setVisible(view.getElement(),
                isVisible);
    }
    
    public void doNextBestThingToExecute() {
        model.doNextBestThingToExecute();
        updateImage();
    }
    
    public boolean isOkToExecute() {
        boolean isOkToExecute = model.isOkToExecute();

        return isOkToExecute;
    }
  
    public void clear() {
        model.clear();
        updateImage();
    }
    
    public void setMouseable(boolean mouseable) {
        model.setMouseable(mouseable);
        updateImage();
    }

    @Override
    public void onSetRollover(String displayName, String keyword, int code) {
        if (api.getChoicesGui().isInChoicesMode()) {
            return;
        }
        model.setRollover(displayName, keyword,
                code);

        updateImage();

    }

    @Override
    public void onExecuteCommand(double x, double y) {
        if (isOkToExecute()) {
            this.execute(x, y);
        } else {
            this.doNextBestThingToExecute();
        }
    }

}
