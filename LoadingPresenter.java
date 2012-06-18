/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core;


import com.github.a2g.core.authoredroom.InternalAPI;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


public class LoadingPresenter {
    private EventBus bus;
    private InternalAPI api;
    private LoadingPanel view;
    int current;
    int total;
    private String name;	
    public LoadingPresenter(final AcceptsOneWidget panel, EventBus bus, InternalAPI api) {
    	this.name = "";
        this.bus = bus;
        this.api = api;
        this.view = new LoadingPanel();
        panel.setWidget(view);
        this.api = api;
        this.current = 0;
        this.total = 0;
    }
    
    void incrementProgress()
    {
    	current = current +1;
    	view.update(current, total, name);
    }

    void setTotal(int total)
    {
    	this.total = total;
    	this.current = 0;
    	view.update(current, total, name);
    }

	public void setName(String string) {
		name = string;
	}

	public void clear() {
		name = "";
		total = 1;
		current =1;
		
	}

}
