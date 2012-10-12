package com.github.a2g.core.objectmodel;


import java.util.TreeMap;

import com.github.a2g.bridge.image.Image;
import com.github.a2g.bridge.panel.InventoryPanel;
import com.github.a2g.bridge.thing.AcceptsOneThing;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.google.gwt.event.shared.EventBus;


public class InventoryPresenter {

    private Inventory theInventory;
    private InventoryPanel view;
    EventBus eventBus;
    MasterPresenterHostAPI parent;
    private TreeMap<Integer, InventoryItem> theInventoryItemMap;
	  
    public InventoryPresenter(final AcceptsOneThing panel, EventBus bus, MasterPresenterHostAPI parent, InternalAPI api) {
        this.eventBus = bus;
        this.parent = parent;
        this.theInventory = new Inventory();
        this.view = new InventoryPanel(api);
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

	public InventoryPanel getView() {
		return view;
	}
}
