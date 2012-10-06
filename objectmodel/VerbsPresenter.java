package com.github.a2g.core.objectmodel;


import com.google.gwt.event.shared.EventBus;
import com.github.a2g.bridge.panel.VerbsPanel;
import com.github.a2g.bridge.thing.AcceptsOneThing;
import com.github.a2g.core.authoredscene.InternalAPI;


public class VerbsPresenter {

    private Verbs theVerbs;
    private VerbsPanel view;

    public VerbsPresenter(final AcceptsOneThing panel, EventBus bus, MasterPresenterHostAPI parent, InternalAPI api) {
        this.theVerbs = new Verbs();
        this.view = new VerbsPanel(theVerbs,
                bus, api);
        
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
