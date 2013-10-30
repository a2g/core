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


import com.github.a2g.core.gwt.factory.GWTImage;
import com.github.a2g.core.gwt.factory.GWTPackagedImage;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.gwt.mouse.ImageMouseClickHandler;
import com.github.a2g.core.gwt.mouse.SceneObjectMouseOverHandler;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.PackagedImageAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.primitive.Point;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;


public class ScenePanel
extends AbsolutePanel
implements ImagePanelAPI, ScenePanelAPI
{
	int cameraOffsetX;
	int cameraOffsetY;

	public ScenePanel(EventBus bus, InternalAPI api)
	{
		this.getElement().setId("cwAbsolutePanel");
		this.addStyleName("absolutePanel");
		this.cameraOffsetX = 0;
		this.cameraOffsetY = 0;
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
		com.google.gwt.user.client.ui.Image image = Image.getImageFromResource((GWTPackagedImage)imageResource,lh);

		GWTImage imageAndPos = new GWTImage(image, this, new Point(x, y));

		// add gwt mouse handlers
		imageAndPos.getNativeImage().addMouseMoveHandler
		(
				new SceneObjectMouseOverHandler(bus, api, objectTextualId, objectCode)
				);

		imageAndPos.getNativeImage().addClickHandler
		(
				new ImageMouseClickHandler(bus, this)
				);

		return imageAndPos;
	}


	@Override
	public void setImageVisible(Image image, boolean visible)
	{
		super.setVisible(((GWTImage)image).getNativeImage().getElement(), visible);
	}

	@Override
	public void add(Image image, int x, int y)
	{
		super.add(((GWTImage)image).getNativeImage(),x,y);
	}

	@Override
	public void insert(Image image, int x, int y, int before)
	{
		super.insert(((GWTImage)image).getNativeImage(),x-cameraOffsetX,y-cameraOffsetY,before);
	}

	@Override
	public void remove(Image image)
	{
		super.remove(((GWTImage)image).getNativeImage());
	}

	@Override
	public void setThingPosition(Image image, int left, int top)
	{
		super.setWidgetPosition(((GWTImage)image).getNativeImage(), left-cameraOffsetX, top-cameraOffsetY);
	}

	@Override
	public int getImageHeight(Image image)
	{
		return ((GWTImage)image).getNativeImage().getHeight();
	}

	@Override
	public int getImageWidth(Image image)
	{
		return ((GWTImage)image).getNativeImage().getWidth();
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

}
