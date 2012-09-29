package com.github.a2g.core.objectmodel;


import com.google.gwt.event.shared.EventBus;
import com.github.a2g.bridge.AcceptsOneThing;
import com.github.a2g.bridge.panel.VerbsPanel;


public class VerbsPresenter {

    private Verbs theVerbs;
    private VerbsPanel view;

    public VerbsPresenter(final AcceptsOneThing panel, EventBus bus, MasterPresenterHostAPI parent) {
        this.theVerbs = new Verbs();
        this.view = new VerbsPanel(theVerbs,
                bus);
        
        panel.setThing(view);
    }

    public void setVisible(boolean isVisible) {
        view.setVisible(isVisible);
		
    }

	public void clear() {
		// doesn't change.
		
	}
	
	public Verbs getVerbsModel()
	{
		return theVerbs;
	}
	
}
