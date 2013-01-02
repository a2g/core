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


package com.github.a2g.core.swing.panel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;

import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.VerbsPanelAPI;
import com.github.a2g.core.objectmodel.Verb;
import com.github.a2g.core.objectmodel.Verbs;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.swing.mouse.VerbMouseClickHandler;
import com.github.a2g.core.swing.mouse.VerbMouseOverHandler;
import com.google.gwt.event.shared.EventBus;



@SuppressWarnings("serial")
public class VerbsPanel 
extends JPanel implements VerbsPanelAPI
{
	EventBus bus;
	InternalAPI api;
	Verbs verbs;
	GridLayout grid;
    public VerbsPanel(EventBus bus, InternalAPI api, ColorEnum fore, ColorEnum back) 
    {
    	this.bus = bus;
    	this.api =  api;
    	
    	this.setForeground(new Color(fore.css[0], fore.css[1], fore.css[2]));	
   		this.setBackground(new Color(back.css[0], back.css[1], back.css[2]));
    	grid = new GridLayout();
    	grid.setVgap(0);
    	grid.setHgap(0);
    	this.setLayout(grid);
    }
    
	@Override
	public Dimension	getPreferredSize()
	{	
		return new Dimension(160,80);
	}
    
    @Override
	public void setVerbs(Verbs verbs)
    {
    	this.verbs = verbs;
    	update();
    }

    public void update()
    {
    	this.removeAll();
    	//this.add(new Label("Verbs"));
    	
    	int numberOfRows = verbs.getNumberOfRows();
    	int numberOfColumns = verbs.getNumberOfColumns();
    	grid.setRows(numberOfRows);
    	grid.setColumns(numberOfColumns); 
    	
    	for (int i = 0; i < (grid.getColumns()
    			* grid.getRows()); i++) 
    	{
    		if(i<verbs.items().size())
    		{
    			Verb v = verbs.items().get(i);
    			int code = v.getCode();
    			String textualId = v.gettextualId();
    			Label label = new Label(textualId);

    			
    			
    			label.addMouseListener
    			(
    					new VerbMouseOverHandler( bus, v.getdisplayText(), textualId, code)
    					);
    			label.addMouseListener
    			(
    					new VerbMouseClickHandler(bus, api)
    					);

    			this.add(label);
    		}
    	}
    }

}
