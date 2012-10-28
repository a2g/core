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

import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.LoaderPanelAPI;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoaderPanel 
extends SimplePanel 
implements LoaderPanelAPI
{

	Label progress;
	Button reload;
	InternalAPI api;
	
    public LoaderPanel(final InternalAPI api) {
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

	public void setSize(int width, int height)
	{
		this.setSize("" + width + "px",
			"" + height + "px");
	}
}
    
    

