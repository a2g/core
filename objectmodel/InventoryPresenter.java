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
import java.util.Collection;
import java.util.TreeMap;

import com.github.a2g.core.interfaces.nongame.IHostingPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformInventoryPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenter;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromInventoryPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromInventory;
import com.github.a2g.core.primitive.GuiConstants;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectI;
import com.google.gwt.event.shared.EventBus;

public class InventoryPresenter implements
IInventoryPresenterFromInventoryPanel, IInventoryPresenter {

	private Inventory theInventory;
	private IPlatformInventoryPanel view;
	EventBus eventBus;
	private TreeMap<Integer, InventoryItem> mapOfInventoryByICode;
	IMasterPresenterFromInventory callback;
	private int width;
	private int height;
	ArrayList<RectI> rectsForSlots;
	ArrayList<InventoryItem> itemsForSlots;
	int netRightArrowClicks;
	private RectI leftArrowRect;
	private RectI rightArrowRect;
	private int mousePosX;
	private int mousePosY;
	private int arrowWidth;

	public InventoryPresenter(final IHostingPanel panel, EventBus bus,
			IMasterPresenterFromInventory api) {
		rectsForSlots = new ArrayList<RectI>();
		itemsForSlots = new ArrayList<InventoryItem>();
		this.netRightArrowClicks = 0;
		this.eventBus = bus;
		this.theInventory = new Inventory();
		this.callback = api;
		this.arrowWidth =0;
		this.view = api.getFactory().createInventoryPanel(this,
				GuiConstants.TEXT_NORMAL,
				GuiConstants.BACKGROUND_FILL,
				GuiConstants.TEXT_HIGHLIGHT);

		panel.setThing(view);
		this.mapOfInventoryByICode = new TreeMap<Integer, InventoryItem>();
	}

	public Inventory getInventory() {
		return theInventory;
	}

	public boolean addInventory(String objectTextualId, int objectCode,
			boolean initiallyVisible, Image image) {
		boolean isCarrying = callback.getValue("CARRYING_"
				+ objectTextualId.toUpperCase()) > 0;
				InventoryItem item = new InventoryItem(this.eventBus, objectTextualId,
						image, objectCode, isCarrying);

				item.setVisible(initiallyVisible);
				this.mapOfInventoryByICode.put(objectCode, item);
				this.theInventory.items().add(item);
				this.updateInventory();
				return true;
	}

	public InventoryItem getInventoryItemByICode(int i) {
		InventoryItem inv = this.mapOfInventoryByICode.get(i);

		return inv;
	}

	boolean isRightArrowVisible() {
		return rectsForSlots.size() + netRightArrowClicks < getNumberOfVisibleItems();
	}

	boolean isLeftArrowVisible() {
		return netRightArrowClicks > 0 && getNumberOfVisibleItems() > 0;
	}

	private void clickRightArrow() {
		if (isRightArrowVisible()) {
			netRightArrowClicks += 2;
			this.updateInventory();
		}
	}

	private void clickLeftArrow() {
		if (isLeftArrowVisible()) {
			netRightArrowClicks -= 2;
			this.updateInventory();
		}

	}

	int getNumberOfVisibleItems() {
		int count = 0;
		InventoryItemCollection inv = this.getInventory().items();
		for (int i = 0; i < inv.getCount(); i++) {
			InventoryItem item = inv.getByIndex(i);
			if (item.isVisible())
				count++;
		}
		return count;
	}

	void hideImage(Image image) {
		if (image != null)// null is valid in the case of unit test
		{
			image.setVisible(false, new PointI(-width, -height));
		}
	}

	public void updateInventory() {
		view.setLeftArrowVisible(isLeftArrowVisible());
		view.setRightArrowVisible(isRightArrowVisible());

		itemsForSlots.clear();
		for (int i = 0; i < rectsForSlots.size(); i++) {
			itemsForSlots.add(null);
		}
		int currentSlot = 0;
		InventoryItemCollection inv = this.getInventory().items();
		int howManyVisibleInventoriesToSkip = this.netRightArrowClicks;
		for (int i = 0; i < inv.getCount(); i++) {
			InventoryItem item = inv.getByIndex(i);
			Image image = item.getImage();
			if (item.isVisible()) {
				howManyVisibleInventoriesToSkip--;
				if (howManyVisibleInventoriesToSkip < 0
						&& currentSlot < this.rectsForSlots.size()) {
					itemsForSlots.set(currentSlot, item);
					if (image != null)// null is valid in the case of unit test
					{
						RectI rect = rectsForSlots.get(currentSlot);
						image.setVisible(true,
								new PointI(rect.getLeft(), rect.getTop()));
					}
					currentSlot++;
				} else {
					hideImage(image);
				}
			} else {
				hideImage(image);
			}
		}
	}




	public void clear() {
		mapOfInventoryByICode.clear();
		theInventory = new Inventory();
		view.clear();
	}

	public IPlatformInventoryPanel getView() {
		return view;
	}



	public void setScenePixelSize(int sceneWidth , int sceneHeight ) 
	{
		this.width = sceneWidth/2;
		this.height = sceneHeight/2;
		int destinationSlotWidth= 64;
		int destinationSlotHeight = 32;
		switch(width)
		{
		case 320: destinationSlotWidth = 128; destinationSlotHeight=64; break;
		}

		this.arrowWidth = (width-2*destinationSlotWidth)/2;

		{
			// some temp variablesfor easy of reading
			int la = arrowWidth;
			int ra = arrowWidth;
			int w = destinationSlotWidth;
			int h = destinationSlotHeight;

			this.rectsForSlots.clear();
			rectsForSlots.add(new RectI(la, 0, w, h));
			rectsForSlots.add(new RectI(la, h, w, h));
			rectsForSlots.add(new RectI(la + w, 0, w, h));
			rectsForSlots.add(new RectI(la + w, h, w, h));
			leftArrowRect = new RectI(0, 0, la, h * 2);
			rightArrowRect = new RectI(la + 2 * w, 0, ra, h * 2);
		}
		view.setDimensionsOfPanel(width, height);
	}

	InventoryItem getItemForRect(int i) {
		InventoryItem item = itemsForSlots.get(i);
		return item;
	}

	@Override
	public void setMouseOver(double d, double e) {
		mousePosX = (int) (d * width);
		mousePosY = (int) (e * height);

		for (int i = 0; i < rectsForSlots.size(); i++) {
			if (rectsForSlots.get(i).contains(mousePosX, mousePosY)) {
				InventoryItem inv = getItemForRect(i);
				if (inv != null) {
					callback.onMouseOverVerbsOrInventory(inv.getDisplayName(),
							inv.getItid(), inv.getICode());
					return;
				}
			}
		}
		callback.onMouseOverVerbsOrInventory("", "", 0);
	}

	@Override
	public void doClick() {
		if (this.leftArrowRect.contains(mousePosX, mousePosY)) {
			clickLeftArrow();
		} else if (rightArrowRect.contains(mousePosX, mousePosY)) {
			clickRightArrow();
		} else {
			callback.onClickVerbsOrInventory();
		}

	}

	public int getWidth() {
		return width;
	}

	@Override
	public void doClick(double x, double y) {
		this.setMouseOver(x, y);
		this.doClick();
	}

	@Override
	public String getDisplayNameByItid(String itid) {
		return theInventory.items().getByItid(itid).getDisplayName();
	}

	public void hideAllInventory() {
		InventoryItemCollection inv = this.getInventory().items();
		for (int i = 0; i < inv.getCount(); i++) {
			InventoryItem item = inv.getByIndex(i);
			item.setVisible(false);
		}
	}

	@Override
	public Collection<RectI> getRects() {
		return rectsForSlots;
	}

	public void setSizeOfSingleInventoryImage(int width2, int height2) {
		//view.setSizeOfSingleInventoryImage(width2, height2); 
		
	}

}
