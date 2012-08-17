package com.github.a2g.core.objectmodel;


import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


public class VerbsPresenter {

    private Verbs theVerbs;
    private VerbsPanel view;

    public VerbsPresenter(final AcceptsOneWidget panel, EventBus bus, MasterPresenterHostAPI parent) {
        this.theVerbs = new Verbs();
        this.view = new VerbsPanel(theVerbs,
                bus);
        
        panel.setWidget(view);
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
