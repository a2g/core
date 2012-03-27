/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import com.github.a2g.core.authoredroom.IAmTheMainAPI;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


public class ChoicesPresenter {
    private EventBus bus;
    private IAmTheMainAPI api;
    private Choices theChoices;
    private ChoicesPanel view;
    private boolean isInChoicesMode;
    private int choiceTalker;
		
    public ChoicesPresenter(final AcceptsOneWidget panel, EventBus bus, IAmTheMainAPI api) {
        this.bus = bus;
        this.api = api;
        this.theChoices = new Choices();
        this.view = new ChoicesPanel();
        panel.setWidget(view);
        this.choiceTalker = 0;
        
        this.api = api;
       
        this.isInChoicesMode = false;
    }
	  
    public void clear() {
        theChoices.clear();
        view.update(theChoices, bus);
    }

    public void setInChoicesMode(boolean isInChoices) {
        this.isInChoicesMode = isInChoices;
        view.setVisible(isInChoicesMode);
        api.getVerbsGui().setVisible(
                !isInChoicesMode);
        api.getInventoryGui().setVisible(
                !isInChoicesMode);
        api.getCommandLineGui().setVisible(
                !isInChoicesMode);
    }
    
    public void addChoice(int place, String text) {
        setInChoicesMode(true);
        theChoices.addChoice(place, text);
        view.update(theChoices, bus);
    }

    public boolean isInChoicesMode() {
        return this.isInChoicesMode;
    }
	
    public void setChoiceTalker(int personWhoSpeaksTheChosenChoice) {
        this.choiceTalker = personWhoSpeaksTheChosenChoice;

    }

    public int getChoiceTalker() {
        return this.choiceTalker;
    }

}
