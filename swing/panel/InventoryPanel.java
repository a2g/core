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


import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.objectmodel.Image;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.objectmodel.DialogTree;
import com.github.a2g.core.objectmodel.Inventory;
import com.github.a2g.core.objectmodel.InventoryItem;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.swing.factory.SwingImage;
import com.github.a2g.core.swing.factory.SwingPackagedImage;
import com.github.a2g.core.swing.mouse.DialogTreeMouseClickHandler;
import com.github.a2g.core.swing.mouse.DialogTreeMouseOutHandler;
import com.github.a2g.core.swing.mouse.DialogTreeMouseOverHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Grid;

@SuppressWarnings({ "unused", "serial" })
public class InventoryPanel 
extends JPanel 
implements ImageObserver, com.github.a2g.core.interfaces.ImagePanelAPI, InventoryPanelAPI
{
	GridLayout gridLayout;
	InternalAPI api;
	
    public InventoryPanel(InternalAPI api) 
    {
    	this.api = api;
    	this.add(new Label("Inventory"));
    	gridLayout = new GridLayout();
    	gridLayout.setColumns(4);
    	gridLayout.setRows(1);
    	setLayout(gridLayout);
    
    }

    @Override
	public void updateInventory(Inventory inventory) {
    
        // destroy old
    	gridLayout = new GridLayout();
    	gridLayout.setColumns(4);
    	gridLayout.setRows(1);
    	setLayout(gridLayout);
    
    	double ratioOfWidthToHeight = 2;
    	int count = inventory.items().getCount();
    	double halfCount = (count
    			/ ratioOfWidthToHeight);
    	double height = Math.sqrt(halfCount);
    	double width = height
    			* ratioOfWidthToHeight; // make it square

    	int integerForColumns = (int) (height + .5);
    	int integerForRows = (int) (width + .5);
    	if(integerForColumns>0 && integerForRows>0)
    	{
    		gridLayout.setColumns(integerForColumns);
    		gridLayout.setRows(integerForRows);


    		int j = 0;

    		for (int i = 0; i < inventory.items().getCount() && j < count; i++) 
    		{
    			InventoryItem item = inventory.items().at(
    					i);

    			if (item.isVisible()) {
    				int row = j / gridLayout.getColumns();
    				int column = j
    						% gridLayout.getColumns();

    				if (row < gridLayout.getRows()
    						&& column < gridLayout.getColumns()) 
    				{
    					item.getDisplayName();

    					//this.setThing(row, column, item.getImage().getNativeImage());
    				}
    				j++;
    			}
    		}
    	}
    }

    @Override
	public Image createNewImageAndAdddHandlers(
			com.github.a2g.core.interfaces.PackagedImageAPI imageResource,
			LoadHandler lh, EventBus bus, String objectTextualId,
			int objectCode, int i, int j)
    {
		java.awt.Image img = ((SwingPackagedImage)imageResource).unpack();
		
		Image imageAndPos = new SwingImage(img, objectTextualId, this, new Point(-1, -1));
		
		// to fire image loading done.
		lh.onLoad(null);
		
		return imageAndPos;
	}

	@Override
	public void setThingPosition(Image image, int i, int j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setImageVisible(Image image, boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Image image, int x, int y, int before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Image image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Image image, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public int getImageWidth(Image image) {
		return ((SwingImage)image).getNativeImage().getWidth(this);
	}

	@Override
	public int getImageHeight(Image image) {
		return ((SwingImage)image).getNativeImage().getHeight(this);
	}

	

	

}
    
