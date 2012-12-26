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
import com.github.a2g.core.interfaces.TitleCardPanelAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TitleCardPanel 
extends SimplePanel 
implements TitleCardPanelAPI
{

	Label label;
	InternalAPI api;
	
    public TitleCardPanel(final InternalAPI api, ColorEnum fore, ColorEnum back) {
    	this.api = api;
    	VerticalPanel layout = new VerticalPanel();
    	{
    		label = new Label();
    		DOM.setStyleAttribute(label.getElement(), "color",fore.toString());
    		DOM.setStyleAttribute(label.getElement(), "BackgroundColor",back.toString());
    		
    		label.setText("Loading...");

    		layout.add(label);
    	}
    	
    	this.add(layout);
    }
      
	@Override
	public void setColor(ColorEnum color) {
		if(color!=null)
		{
			DOM.setStyleAttribute(label.getElement(), "color",color.toString());
			DOM.setStyleAttribute(label.getElement(), "borderColor",color.toString());
		}
		
	}

	@Override
	public void setText(String text) {
		label.setText(text);
		
	}
	
	@Override
	public void setScenePixelSize(int width, int height)
	{
		this.setSize("" + width + "px",
			"" + height + "px");
	}
}
    
    

