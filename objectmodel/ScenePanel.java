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
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.PackagedImageAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.platforms.html4.ImageForHtml4;
import com.github.a2g.core.platforms.html4.PackagedImageForHtml4;
import com.github.a2g.core.platforms.html4.mouse.ImageMouseClickHandler;
import com.github.a2g.core.platforms.html4.mouse.SceneObjectMouseOverHandler;
import com.github.a2g.core.platforms.html4.mouse.SceneObjectTouchMoveHandler;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;


public class ScenePanel
extends AbsolutePanel
implements ImagePanelAPI, ScenePanelAPI
{
	int cameraOffsetX;
	int cameraOffsetY;
	SceneObjectTouchMoveHandler theTouchMoveHandler;
	FlowPanel speechWidget;
	Label label;

	public ScenePanel(EventBus bus, InternalAPI api)
	{
		this.getElement().setId("cwAbsolutePanel");
		//this.addStyleName("absolutePanel");
		this.cameraOffsetX = 0;
		this.cameraOffsetY = 0;
		this.theTouchMoveHandler = new SceneObjectTouchMoveHandler(api);
		
		label = new Label("hello");
		this.speechWidget = new FlowPanel();
		speechWidget.add(label);
		
	}

	@Override
	public Image createNewImageAndAddHandlers
	(
			LoadHandler lh,
			PackagedImageAPI imageResource,
			InternalAPI api,
			EventBus bus,
			int x,
			int y,
			String objectTextualId,
			short objectCode)
	{
	
		com.google.gwt.user.client.ui.Image image = Image.getImageFromResource((PackagedImageForHtml4)imageResource,lh);

		ImageForHtml4 imageAndPos = new ImageForHtml4(image, this, new Point(x, y));

		// add gwt mouse handlers
		imageAndPos.getNativeImage().addMouseMoveHandler
		(
				new SceneObjectMouseOverHandler(bus, api, objectTextualId, objectCode)
				);

		imageAndPos.getNativeImage().addClickHandler
		(
				new ImageMouseClickHandler(bus, this)
				);

		imageAndPos.getNativeImage().addTouchMoveHandler
		(
				theTouchMoveHandler
				);

		return imageAndPos;
	}


	@Override
	public void setImageVisible(Image image, boolean visible)
	{
		super.setVisible(((ImageForHtml4)image).getNativeImage().getElement(), visible);
	}

	@Override
	public void add(Image image, int x, int y)
	{
		super.add(((ImageForHtml4)image).getNativeImage(),x,y);
	}

	@Override
	public void insert(Image image, int x, int y, int before)
	{
		super.insert(((ImageForHtml4)image).getNativeImage(),x-cameraOffsetX,y-cameraOffsetY,before);
	}

	@Override
	public void remove(Image image)
	{
		super.remove(((ImageForHtml4)image).getNativeImage());
	}

	@Override
	public void setThingPosition(Image image, int left, int top)
	{
		super.setWidgetPosition(((ImageForHtml4)image).getNativeImage(), (int)(left-cameraOffsetX*image.getParallaxX()),(int)( top-cameraOffsetY*image.getParallaxY()));
	}

	@Override
	public int getImageHeight(Image image)
	{
		return ((ImageForHtml4)image).getNativeImage().getHeight();
	}

	@Override
	public int getImageWidth(Image image)
	{
		return ((ImageForHtml4)image).getNativeImage().getWidth();
	}

	@Override
	public void setScenePixelSize(int width, int height)
	{
		this.setSize("" + width + "px",
				"" + height + "px");
	}

	@Override
	public void setCameraOffset(int x, int y)
	{
		this.cameraOffsetX = x;
		this.cameraOffsetY = y;
	}

	@Override
	public void setStateOfPopup(boolean visible, int x, int y,
			ColorEnum talkingColor, String speech, BaseAction ba) {
		if(!visible)
		{
			super.remove(speechWidget);
		}
		label.setVisible(visible);
		label.getElement().getStyle().setProperty("color","#ff0000");
		label.getElement().getStyle().setProperty("fontcolor","#00ff00");
		label.getElement().getStyle().setProperty("textcolor","#0000ff");
		if(talkingColor!=null)
		{
			label.getElement().getStyle().setProperty("borderColor",talkingColor.toString());
		}
		label.setText(speech);
		super.add(speechWidget, x,y);
	}

}
