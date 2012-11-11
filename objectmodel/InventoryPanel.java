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

import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.gwt.factory.GWTImage;
import com.github.a2g.core.gwt.factory.GWTPackagedImage;
import com.github.a2g.core.gwt.mouse.ImageMouseClickHandler;
import com.github.a2g.core.gwt.mouse.InventoryItemMouseOverHandler;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.PackagedImageAPI;
import com.github.a2g.core.primitive.Point;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class InventoryPanel 
extends AbsolutePanel 
implements ImagePanelAPI,
		InventoryPanelAPI {
	InternalAPI api;

	public InventoryPanel(InternalAPI api) {
		// super(1, 1);
		this.api = api;
	}

	@Override
	public void updateInventory(Inventory inventory) {
		/*
		 * double ratioOfWidthToHeight = 2; int count =
		 * inventory.items().getCount(); double halfCount = (count /
		 * ratioOfWidthToHeight); double height = Math.sqrt(halfCount); double
		 * width = height ratioOfWidthToHeight; // make it square
		 * 
		 * this.resize((int) (height + .5), (int) (width + .5));
		 * 
		 * int j = 0;
		 * 
		 * for (int i = 0; i < inventory.items().getCount() && j < count; i++) {
		 * InventoryItem item = inventory.items().at( i);
		 * 
		 * if (item.isVisible()) { int row = j / getColumnCount(); int column =
		 * j % getColumnCount();
		 * 
		 * if (row < getRowCount() && column < getColumnCount()) {
		 * item.getDisplayName();
		 * 
		 * this.setWidget(row, column, item.getImage().getNativeImage()); } j++;
		 * } }
		 */
	}

	final com.google.gwt.user.client.ui.Image getImageFromResource(
			GWTPackagedImage imageResource, LoadHandler lh) {
		// this.numberOfImagesToLoad++;
		// assert (imageResource != null);
		// if(theLoadedImageMap.containsKey(imageResource.toString()))
		// {
		// final com.google.gwt.user.client.ui.Image image =
		// theLoadedImageMap.get(imageResource.toString());
		// lh.onLoad(null);
		// return image;
		// }
		// else
		{
			final com.google.gwt.user.client.ui.Image image = imageResource
					.unpack();
			// theLoadedImageMap.put(imageResource.toString(), image);
			if (lh != null) {
				image.addLoadHandler(lh);
			}
			return image;
		}
	}

	@Override
	public Image createNewImageAndAdddHandlers(PackagedImageAPI imageResource,
			LoadHandler lh, EventBus bus, String objectTextualId,
			int objectCode, int i, int j) {

		com.google.gwt.user.client.ui.Image image = getImageFromResource(
				(GWTPackagedImage) imageResource, lh);

		GWTImage imageAndPos = new GWTImage(image, this, new Point(0, 0));

		imageAndPos.getNativeImage().addMouseMoveHandler(
				new InventoryItemMouseOverHandler(bus, api, objectTextualId,
						objectCode));

		imageAndPos.getNativeImage().addClickHandler(
				new ImageMouseClickHandler(bus, null));

		return imageAndPos;
	}

	@Override
	public void setImageVisible(Image image, boolean visible) {

		super.setVisible(((GWTImage) image).getNativeImage().getElement(),
				visible);
	}

	@Override
	public void add(Image image, int x, int y) {
		super.add(((GWTImage) image).getNativeImage(), x, y);
	}

	@Override
	public void insert(Image image, int x, int y, int before) {
		super.insert(((GWTImage) image).getNativeImage(), x, y, before);
	}

	@Override
	public void remove(Image image) {
		super.remove(((GWTImage) image).getNativeImage());
	}

	@Override
	public void setThingPosition(Image image, int left, int top) {
		super.setWidgetPosition(((GWTImage) image).getNativeImage(), left, top);
	}

	@Override
	public int getImageHeight(Image image) {
		return ((GWTImage) image).getNativeImage().getHeight();
	}

	@Override
	public int getImageWidth(Image image) {
		return ((GWTImage) image).getNativeImage().getWidth();
	}

}
