package com.github.a2g.core;

import com.github.a2g.core.authoredscene.InternalAPI;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoadingPanel extends SimplePanel {

	Label progress;
	Button reload;
	InternalAPI api;
	
    LoadingPanel(final InternalAPI api) {
    	this.api = api;
    	VerticalPanel layout = new VerticalPanel();
    	{
    		progress = new Label();
    		DOM.setStyleAttribute(
    				progress.getElement(), "color",
    		"Red");
    		progress.setText("Loading...");

    		layout.add(progress);
    	}
    	{
    		reload = new Button("Reload");
    		layout.add(reload);
    		addHandler(api);
    	}
    	this.add(layout);
    }
    
    void addHandler(final InternalAPI api)
    {
    	reload.addClickHandler
		(
			new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event) {
					api.restartReloading();
				}
			}
		);
    }

    public void update(int current, int total, String name) {
    	progress.setText(" "+current+"/"+total+ " " + name);
    }
}
    
    

