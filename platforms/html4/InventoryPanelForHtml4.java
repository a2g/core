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
package com.github.a2g.core.platforms.html4;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.interfaces.internal.IInventoryPanelFromInventoryPresenter;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromInventoryPanel;
import com.github.a2g.core.interfaces.platform.IPlatformPackagedImage;
import com.github.a2g.core.interfaces.internal.IImagePanel;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.objectmodel.Inventory;
import com.github.a2g.core.platforms.html4.dependencies.ImageForHtml4;
import com.github.a2g.core.platforms.html4.dependencies.PackagedImageForHtml4;
import com.github.a2g.core.platforms.html4.mouse.InventoryItemMouseClickHandler;
import com.github.a2g.core.platforms.html4.mouse.InventoryItemMouseOverHandler;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;

public class InventoryPanelForHtml4
extends AbsolutePanel
implements
IImagePanel
, IInventoryPanelFromInventoryPresenter
{
	final FlowPanel arrowLeft;
	final FlowPanel arrowRight;
	final IInventoryPresenterFromInventoryPanel mouseToPresenter;

	public InventoryPanelForHtml4(final IInventoryPresenterFromInventoryPanel mouseToPresenter, ColorEnum fore, ColorEnum back, ColorEnum rollover)
	{
		setVisible(true);
		this.mouseToPresenter = mouseToPresenter;

		getElement().getStyle().setProperty("color", fore.toString());
		getElement().getStyle().setProperty("backgroundColor", back.toString());
		getElement().getStyle().setProperty("overflow", "visible");

		arrowLeft = new FlowPanel();
		this.add(arrowLeft);
		arrowLeft.getElement().addClassName("arrowLeft");


		arrowLeft.setHeight("0");
		arrowLeft.setWidth("0");
		arrowLeft.getElement().getStyle().setProperty("borderBottom", "10px solid transparent");
		arrowLeft.getElement().getStyle().setProperty("borderRight", "10px solid "+fore.toString());
		arrowLeft.getElement().getStyle().setProperty("borderTop", "10px solid transparent");
		arrowLeft.getElement().getStyle().setProperty("float", "left");
		arrowLeft.getElement().getStyle().setPosition(Position.RELATIVE);
		arrowLeft.getElement().getStyle().setProperty("top", "50%");
		arrowLeft.getElement().getStyle().setProperty("transform", "translateY(-50%)");

		arrowRight = new FlowPanel();
		this.add(arrowRight);
		arrowRight.getElement().addClassName("arrowRight");


		arrowRight.setHeight("0");
		arrowRight.setWidth("0");
		arrowRight.getElement().getStyle().setProperty("borderBottom", "10px solid transparent");
		arrowRight.getElement().getStyle().setProperty("borderLeft", "10px solid "+fore.toString());
		arrowRight.getElement().getStyle().setProperty("borderTop", "10px solid transparent");
		arrowRight.getElement().getStyle().setProperty("float", "right");

		arrowRight.getElement().getStyle().setPosition(Position.RELATIVE);
		arrowRight.getElement().getStyle().setProperty("top", "50%");
		arrowRight.getElement().getStyle().setProperty("transform", "translateY(-50%)");

		arrowLeft.addDomHandler(
				new ClickHandler()
				{	@Override
					public void onClick(ClickEvent event)
				{
					mouseToPresenter.setMouseOver(.05, .5);
					mouseToPresenter.doClick();
				}
				}, ClickEvent.getType());
		arrowRight.addDomHandler(
				new ClickHandler()
				{	@Override
					public void onClick(ClickEvent event)
				{
					mouseToPresenter.setMouseOver(.95, .5);
					mouseToPresenter.doClick();
				}

				}, ClickEvent.getType());

	}

	@Override
	public void updateInventory(Inventory inventory)
	{
	}


	@Override
	public Image createNewImageAndAdddHandlers(IPlatformPackagedImage imageResource,
			LoadHandler lh, EventBus bus, String otid,
			int ocode, int i, int j) {

		com.google.gwt.user.client.ui.Image image = Image.getImageFromResource(
				(PackagedImageForHtml4) imageResource, lh);

		ImageForHtml4 imageAndPos = new ImageForHtml4(image, this, new PointI(0, 0));

		imageAndPos.getNativeImage().addMouseMoveHandler(
				new InventoryItemMouseOverHandler(bus, mouseToPresenter, otid,
						ocode));

		imageAndPos.getNativeImage().addClickHandler(
				new InventoryItemMouseClickHandler(this, mouseToPresenter));

		return imageAndPos;
	}

	@Override
	public void setImageVisible(Image image, boolean visible) {
		super.setVisible(((ImageForHtml4) image).getNativeImage().getElement(),
				visible);
	}

	@Override
	public void add(Image image, int x, int y) {
		super.add(((ImageForHtml4) image).getNativeImage(), x, y);
	}

	@Override
	public void insert(Image image, int x, int y, int before) {
		super.insert(((ImageForHtml4) image).getNativeImage(), x, y, before);
	}

	@Override
	public void remove(Image image) {
		super.remove(((ImageForHtml4) image).getNativeImage());
	}

	@Override
	public void setThingPosition(Image image, int left, int top, double scale) {
		super.setWidgetPosition(((ImageForHtml4) image).getNativeImage(), left, top);
	}

	@Override
	public int getImageHeight(Image image) {
		return ((ImageForHtml4) image).getNativeImage().getHeight();
	}

	@Override
	public int getImageWidth(Image image) {
		return ((ImageForHtml4) image).getNativeImage().getWidth();
	}

	@Override
	public void setLeftArrowVisible(boolean visible) {
		arrowLeft.setVisible(visible);
	}

	@Override
	public void setRightArrowVisible(boolean visible) {
		arrowRight.setVisible(visible);

	}

	@Override
	public void setDimensionsOfPanel(int width, int height) {
		this.setPixelSize(width, height);
	}

	@Override
	public void resetScale(Image image) {
		//inventory images aren't scaled - so do nothing.
		
	}
}
