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

import java.awt.Dimension;
import java.awt.GridLayout;

import com.github.a2g.core.interfaces.DialogTreePanelAPI;
import com.github.a2g.core.objectmodel.DialogTree;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.swing.mouse.DialogTreeMouseClickHandler;
import com.github.a2g.core.swing.mouse.DialogTreeMouseOutHandler;
import com.github.a2g.core.swing.mouse.DialogTreeMouseOverHandler;
import com.google.gwt.event.shared.EventBus;
import java.awt.Label;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DialogTreePanel 
extends JPanel 
implements DialogTreePanelAPI
{
	ColorEnum fore;
	ColorEnum back;
	ColorEnum roll;
	private int width;
	private int height;
	public DialogTreePanel(EventBus bus, ColorEnum fore, ColorEnum back, ColorEnum roll) 
	{
		GridLayout grid = new GridLayout();
    	this.setLayout(grid);
    	grid.setRows(4);
    	grid.setColumns(1); 

    	this.fore = fore;
    	this.back = back;
    	this.roll = roll;
	   	this.setBackground(back.css);
    	this.setForeground(fore.css);
    }

	 @Override
	 public void setVisible(boolean isVisible)
	 {
		 super.setVisible(isVisible);
	 }
	 
    @Override
	public Dimension getPreferredSize()
    {
    	return new Dimension(width,height);
    }
    
    @Override
	public void setPixelSize(int width,int height)
    {
    	this.width = width;
    	this.height = height;
    	super.setSize(width, height);
    }
    
    @Override
	public void update(DialogTree dialogTree, final EventBus bus) {
    	// destroy old
    	this.removeAll();
    	
    	for (int i = 0; i < dialogTree.getSubBranchIds().size(); i++) 
    	{
    		int subBranchId = dialogTree.getSubBranchIds().get(i).intValue();
    		String lineOfDialog = dialogTree.getLinesOfDialog().get(i);
    		
    		Label label = new Label(lineOfDialog);
    		label.addMouseListener(
					new DialogTreeMouseOverHandler(label, roll.css)
					);
			label.addMouseListener(
					new DialogTreeMouseOutHandler(label, fore.css)
					);

			label.addMouseListener(
					new DialogTreeMouseClickHandler(
							bus, label, subBranchId));

    		add(label);
		}	
    
    	validate();
    	repaint();
    }



}
