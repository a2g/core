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


import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;

import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.VerbsPanelAPI;
import com.github.a2g.core.objectmodel.Verbs;
import com.github.a2g.core.objectmodel.VerbsPresenter;
import com.google.gwt.event.shared.EventBus;



@SuppressWarnings("serial")
public class VerbsPanel 
extends JPanel implements VerbsPanelAPI
{
	InternalAPI api;
    public VerbsPanel(EventBus bus, InternalAPI api) 
    {
    	this.api =  api;
    	   
    	
  		/* label.addMouseListener(
        new VerbMouseOverHandler(
                bus, sentenceText,
                code, i));
label.addMouseListener(
        new VerbMouseClickHandler(bus, api));

		 */
    }
    
    public void setVerbs(Verbs v)
    {
    	GridLayout grid = new GridLayout();
    	this.setLayout(grid);
    	this.add(new Label("Verbs"));
    	
    	grid.setRows(4);
    	grid.setColumns(3); 
    	
    	for (int i = 0; i < (grid.getColumns()
    			* grid.getRows()); i++) 
    	{
    		String buttonText = v.items().get(i).getButtonText();
    		Label label = new Label(
    				buttonText);

    		grid.addLayoutComponent("label"+i, label);
    	}
    }

}
