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

import java.util.ArrayList;
import java.util.TreeMap;

import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.InventoryPresenterCallbackAPI;
import com.github.a2g.core.interfaces.MouseToInventoryPresenterAPI;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;
import com.google.gwt.event.shared.EventBus;


public class InventoryPresenter 
implements MouseToInventoryPresenterAPI
{

    private Inventory theInventory;
    private InventoryPanelAPI view;
    EventBus eventBus;
    private TreeMap<Integer, InventoryItem> theInventoryItemMap;
    InventoryPresenterCallbackAPI api;
	private int width;
	private int height;
	ArrayList<Rect> rectsForSlots;
	ArrayList<InventoryItem> itemsForSlots;
    int offset;
	private Rect leftArrowRect;
	private Rect rightArrowRect;
	private int mousePosX;
	private int mousePosY;
	private final int WIDTH_OF_LEFT_ARROW = 20;
	private final int WIDTH_OF_RIGHT_ARROW = 20;
    public InventoryPresenter(final HostingPanelAPI panel, EventBus bus, InventoryPresenterCallbackAPI api) 
    {
    	rectsForSlots = new ArrayList<Rect>();
    	itemsForSlots =  new ArrayList<InventoryItem>();
        this.offset = 0;
    	this.eventBus = bus;
        this.theInventory = new Inventory();
        this.api = api;
        this.view = api.getFactory().createInventoryPanel(this);
        panel.setThing(view);
        this.theInventoryItemMap = new TreeMap<Integer, InventoryItem>();
        // give it a default size - helps out the unit tests
        final int DEFAULT_INVENTORY_IMAGE_SIZE = 20;
        setSizeOfSingleInventoryImage(DEFAULT_INVENTORY_IMAGE_SIZE,DEFAULT_INVENTORY_IMAGE_SIZE);
    }

    public Inventory getInventory() {
        return theInventory;
    }

    public boolean addInventory(String objectTextualId, int objectCode, boolean initiallyVisible, Image image) {
        boolean isCarrying = api.getValue(
                "CARRYING_"
                        + objectTextualId.toUpperCase())
                                > 0;
        InventoryItem item = new InventoryItem(
                this.eventBus, objectTextualId,
                image, objectCode, isCarrying);

        item.setVisible(initiallyVisible);
        this.theInventoryItemMap.put(
                objectCode, item);
        this.theInventory.items().add(item);
        this.updateInventory();
        return true;
    } 
	
    public InventoryItem getInventoryItem(int i) 
    {
        InventoryItem inv = this.theInventoryItemMap.get(i);

        return inv;
    }

    private boolean isRightArrowVisible()
    {
		int number = offset+getNumberOfVisibleItems()-this.rectsForSlots.size();	
		return(number > 0);
    }

	private void clickRightArrow() {
		if(isRightArrowVisible())
		{
			offset-=2;
			this.updateInventory();
		}
	}

	private boolean isLeftArrowVisible()
	{
		int number = offset+getNumberOfVisibleItems()-this.rectsForSlots.size();
		return (number < 0 );
	}
	private void clickLeftArrow() {
		if(isLeftArrowVisible())
		{
			offset+=2;
			this.updateInventory();
		}
		
	}
	
	int getNumberOfVisibleItems()
	{
		int count = 0;
		InventoryItemCollection inv = this.getInventory().items();
		for(int i=0;i<inv.getCount();i++)
    	{
    		InventoryItem item = inv.at(i);
    		if(item.isVisible())
    			count++;
    	}	
		return count;
	}
	
	void hideImage(Image image)
	{
		if(image!=null)// null is valid in the case of unit test
		{
			image.setVisible(false, new Point(-width,-height));
		}
	}
			
    public void updateInventory() 
    {
    	
    	view.setLeftArrowVisible(isLeftArrowVisible());
    	
    	view.setRightArrowVisible(isRightArrowVisible());
    	
    	itemsForSlots.clear();
    	for(int i = 0;i<rectsForSlots.size();i++)
    	{
    		itemsForSlots.add(null);
    	}
    	int currentSlot = offset; 
    	InventoryItemCollection inv = this.getInventory().items();
    	for(int i=0;i<inv.getCount();i++)
    	{
    		InventoryItem item = inv.at(i);
    		Image image = item.getImage();
    		if(item.isVisible())
    		{
    			if(0<=currentSlot&& currentSlot<this.rectsForSlots.size())
    			{
    				itemsForSlots.set(currentSlot, item);
    				if(image!=null)// null is valid in the case of unit test
    				{
    					Rect rect = rectsForSlots.get(currentSlot);
    					image.setVisible(true, new Point(rect.getLeft(), rect.getTop()));
    				}
    			}
    			else
    			{
    				hideImage(image);
    			}
    			currentSlot++;
    		}
    		else
    		{
    			hideImage(image);
    		}
    	}
    }

    public void setVisible(boolean isVisible) {
        view.setVisible(isVisible);
    }

	public void clear() {
		theInventoryItemMap.clear();
		theInventory = new Inventory();
		
	}

	public InventoryPanelAPI getView() {
		return view;
	}

	
	public void setSizeOfSingleInventoryImage(int w, int h)
	{
		int l = this.WIDTH_OF_LEFT_ARROW;
		int r = this.WIDTH_OF_RIGHT_ARROW;
		this.width = 2*w+l+r;
		this.height = 2*h;
		this.rectsForSlots.clear();
		rectsForSlots.add(new  Rect(l   ,0, w, h));
		rectsForSlots.add(new  Rect(l   ,h, w, h));
		rectsForSlots.add(new  Rect(l+w ,0, w, h));
		rectsForSlots.add(new  Rect(l+w ,h, w, h));
		leftArrowRect = new  Rect(0     ,0 ,l, h*2);
		rightArrowRect = new Rect(l+2*w ,0 ,r, h*2);
	
		view.setPixelSize(width, height);
	}

	InventoryItem getItemForRect(int i)
	{
		InventoryItem item = itemsForSlots.get(i);
		return item;
	}
	
	@Override
	public void setMouseOver(double d, double e) 
	{
		mousePosX = (int)(d*width);
		mousePosY = (int)(e*height);
		
		for(int i=0;i<rectsForSlots.size();i++)
		{
			if(rectsForSlots.get(i).contains(mousePosX, mousePosY))
			{
				InventoryItem inv = getItemForRect(i);
				if(inv!=null)
				{
					api.onMouseOverInventory(inv.getDisplayName(), inv.getTextualId(), inv.getCode());
					return;
				}
			}
		}
		api.onMouseOverInventory("", "", 0);
	}

	@Override
	public void doClick() 
	{
		if(this.leftArrowRect.contains(mousePosX, mousePosY))
		{
			clickLeftArrow();
		}
		else if(rightArrowRect.contains(mousePosX, mousePosY))
		{
			clickRightArrow();
		}
		else
		{
			api.onClickInventory();
		}
		
			
	}


}
