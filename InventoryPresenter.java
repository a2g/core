package com.github.a2g.core;


import java.util.TreeMap;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;


public class InventoryPresenter {

    private Inventory theInventory;
    private InventoryPanel view;
    EventBus eventBus;
    IAmHostingTheMasterPresenter parent;
    private TreeMap<Integer, InventoryItem> theInventoryItemMap;
	  
    public InventoryPresenter(final AcceptsOneWidget panel, EventBus bus, IAmHostingTheMasterPresenter parent) {
        this.eventBus = bus;
        this.parent = parent;
        this.theInventory = new Inventory();
        this.view = new InventoryPanel();
        panel.setWidget(view);
        this.theInventoryItemMap = new TreeMap<Integer, InventoryItem>();
    }

    public Inventory getInventory() {
        return theInventory;
    }

    public boolean addInventory(String objectTextualId, int objectCode, com.google.gwt.user.client.ui.Image image) {
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
}
