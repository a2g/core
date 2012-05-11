package com.github.a2g.core;


import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


public class VerbsPresenter {

    private Verbs theVerbs;
    private VerbsPanel view;

    public VerbsPresenter(final AcceptsOneWidget panel, EventBus bus, IAmHostingTheMasterPresenter parent) {
        this.theVerbs = new Verbs();
        this.view = new VerbsPanel(theVerbs,
                bus);
        
        panel.setWidget(view);
    }

    public void setVisible(boolean isVisible) {
        view.setVisible(isVisible);
		
    }
}