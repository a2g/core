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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.gwt.factory.GWTImage;
import com.github.a2g.core.gwt.factory.GWTPackagedImage;
import com.github.a2g.core.gwt.mouse.InventoryItemMouseClickHandler;
import com.github.a2g.core.gwt.mouse.InventoryItemMouseOverHandler;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.MouseToInventoryPresenterAPI;
import com.github.a2g.core.interfaces.PackagedImageAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.res.UserInterfaceDecoration;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class InventoryPanel
extends AbsolutePanel
implements
ImagePanelAPI
, InventoryPanelAPI
{
	final com.google.gwt.user.client.ui.Image imgLeft;
	final com.google.gwt.user.client.ui.Image imgRight;
	InternalAPI api;
	final MouseToInventoryPresenterAPI mouseToPresenter;

	public InventoryPanel(InternalAPI api, EventBus bus, final MouseToInventoryPresenterAPI mouseToPresenter, ColorEnum fore, ColorEnum back)
	{
		this.mouseToPresenter = mouseToPresenter;
		this.api = api;
		ImageResource resLeft = UserInterfaceDecoration.getLeftArrow();
		ImageResource resRight = UserInterfaceDecoration.getRightArrow();

		DOM.setStyleAttribute(getElement(), "color", fore.toString());
		DOM.setStyleAttribute(getElement(), "backgroundColor", back.toString());


		imgLeft = new com.google.gwt.user.client.ui.Image(resLeft.getSafeUri());
		imgRight = new com.google.gwt.user.client.ui.Image(resRight.getSafeUri());
		imgLeft.addClickHandler(
				new ClickHandler()
				{	@Override
					public void onClick(ClickEvent event)
				{
					mouseToPresenter.setMouseOver(.05, .5);
					mouseToPresenter.doClick();
				}
				});
		imgRight.addClickHandler(
				new ClickHandler()
				{	@Override
					public void onClick(ClickEvent event)
				{
					mouseToPresenter.setMouseOver(.95, .5);
					mouseToPresenter.doClick();
				}
				});
		this.add(imgLeft, 0, 0);
		this.add(imgRight, 100, 0);
	}

	@Override
	public void updateInventory(Inventory inventory)
	{
	}

	final com.google.gwt.user.client.ui.Image getImageFromResource(
			GWTPackagedImage imageResource, LoadHandler lh)
	{
		{
			final com.google.gwt.user.client.ui.Image image = imageResource
					.unpack();
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
				new InventoryItemMouseClickHandler(this, mouseToPresenter));

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

	@Override
	public void setLeftArrowVisible(boolean visible) {
		imgLeft.setVisible(visible);
	}

	@Override
	public void setRightArrowVisible(boolean visible) {
		imgRight.setVisible(visible);

	}
}
