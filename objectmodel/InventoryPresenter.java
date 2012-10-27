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

import java.util.TreeMap;

import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.MasterPresenterHostAPI;
import com.google.gwt.event.shared.EventBus;


public class InventoryPresenter {

    private Inventory theInventory;
    private InventoryPanelAPI view;
    EventBus eventBus;
    MasterPresenterHostAPI parent;
    private TreeMap<Integer, InventoryItem> theInventoryItemMap;
	  
    public InventoryPresenter(final HostingPanelAPI panel, EventBus bus, MasterPresenterHostAPI parent, InternalAPI api) {
        this.eventBus = bus;
        this.parent = parent;
        this.theInventory = new Inventory();
        this.view = parent.getFactory().createInventoryPanel();
        panel.setThing(view);
        this.theInventoryItemMap = new TreeMap<Integer, InventoryItem>();
    }

    public Inventory getInventory() {
        return theInventory;
    }

    public boolean addInventory(String objectTextualId, int objectCode, Image image) {
        boolean isCarrying = parent.getValue(
                "CARRYING_"
                        + objectTextualId.toUpperCase())
                                > 0;

        InventoryItem item = new InventoryItem(
                this.eventBus, objectTextualId,
                image, objectCode, isCarrying);

        this.theInventoryItemMap.put(
                objectCode, item);
        this.theInventory.items().add(item);

        return true;
    } 
	
    public InventoryItem getInventoryItem(int i) {
        InventoryItem inv = this.theInventoryItemMap.get(
                i);

        return inv;
    }

    public void updateInventory() {
        this.view.updateInventory(
                this.getInventory());
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
}
