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

import com.google.gwt.event.dom.client.LoadHandler;

import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.internal.IPackagedImage;
import com.github.a2g.core.interfaces.internal.IScenePanelFromScenePresenter;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.internal.ImagePanelAPI;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.platforms.html4.dependencies.ImageForHtml4;
import com.github.a2g.core.platforms.html4.dependencies.SceneSpeechBalloonPanelForHtml4;
import com.github.a2g.core.platforms.html4.dependencies.SpeechBalloonCalculatorForHtml4;
import com.github.a2g.core.platforms.html4.mouse.ImageMouseClickHandler;
import com.github.a2g.core.platforms.html4.mouse.SceneObjectMouseOverHandler;
import com.github.a2g.core.platforms.html4.mouse.SceneObjectTouchMoveHandler;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class ScenePanelForHtml4 extends AbsolutePanel implements ImagePanelAPI,
IScenePanelFromScenePresenter {
	int cameraOffsetX;
	int cameraOffsetY;
	SceneObjectTouchMoveHandler theTouchMoveHandler;
	SceneSpeechBalloonPanelForHtml4 speechWidget;

	public ScenePanelForHtml4(EventBus bus, IScenePresenterFromScenePanel api) {
		this.getElement().setId("cwAbsolutePanel");
		// this.addStyleName("absolutePanel");
		this.cameraOffsetX = 0;
		this.cameraOffsetY = 0;
		this.theTouchMoveHandler = new SceneObjectTouchMoveHandler(api);
		this.speechWidget = new SceneSpeechBalloonPanelForHtml4();
	}

	@Override
	public Image createNewImageAndAddHandlers(LoadHandler lh,
			IPackagedImage imageResource,
			IScenePresenterFromSceneMouseOver api, EventBus bus, int x, int y,
			String objectTextualId, short objectCode) {

		com.google.gwt.user.client.ui.Image image = Image.getImageFromResource(
				(PackagedImageForHtml4) imageResource, lh);

		ImageForHtml4 imageAndPos = new ImageForHtml4(image, this, new Point(x,
				y));

		// add gwt mouse handlers
		imageAndPos.getNativeImage().addMouseMoveHandler(
				new SceneObjectMouseOverHandler(bus, api, objectTextualId,
						objectCode));

		imageAndPos.getNativeImage().addClickHandler(
				new ImageMouseClickHandler(bus, this));

		imageAndPos.getNativeImage().addTouchMoveHandler(theTouchMoveHandler);

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
		super.insert(((ImageForHtml4) image).getNativeImage(), x
				- cameraOffsetX, y - cameraOffsetY, before);
	}

	@Override
	public void remove(Image image) {
		super.remove(((ImageForHtml4) image).getNativeImage());
	}

	@Override
	public void setThingPosition(Image image, int left, int top, double scale) 
	{
		ImageForHtml4 image4 = (ImageForHtml4) image;
		super.setWidgetPosition(image4.getNativeImage(),
				(int) (left - cameraOffsetX * image.getParallaxX()),
				(int) (top - cameraOffsetY * image.getParallaxY()));
		double width = getImageWidth(image);
		double height = getImageHeight(image);
		if(scale<1.0)
		{
			if(image4.getOriginalDimensions()!=null)
		
				image4.setOriginalDimensions(new Point(image4.getNativeImage().getWidth(),image4.getNativeImage().getHeight()));
			
			width*=scale;
			height*=scale;
		}
		((ImageForHtml4) image).getNativeImage().setPixelSize((int)width, (int)height);
		
	}

	@Override
	public int getImageHeight(Image image) {
		ImageForHtml4 image4 = (ImageForHtml4) image;
		if(image4.getOriginalDimensions()!=null)
			return image4.getOriginalDimensions().getY();
		return image4.getNativeImage().getHeight();
	}

	@Override
	public int getImageWidth(Image image) {
		ImageForHtml4 image4 = (ImageForHtml4) image;
		if(image4.getOriginalDimensions()!=null)
			return image4.getOriginalDimensions().getX();
		return image4.getNativeImage().getWidth();
	}

	@Override
	public void setScenePixelSize(int width, int height) {
		this.setSize("" + width + "px", "" + height + "px");
	}

	@Override
	public void setCameraOffset(int x, int y) {
		this.cameraOffsetX = x;
		this.cameraOffsetY = y;
	}

	@Override
	public void setStateOfPopup(boolean isVisible,
			ColorEnum talkingColor, String speech, Rect maxBalloonRect, Point mouth, TalkPerformer sayAction) {
		if (!isVisible) {
			super.remove(speechWidget);
		}

		SpeechBalloonCalculatorForHtml4 calc = new SpeechBalloonCalculatorForHtml4(speech, maxBalloonRect, 30, mouth, 38, 3);
		
		Rect rectThatsScaledToFit =  calc.getRectInPixels();
		
		speechWidget.setText(speech);
		speechWidget.setBorderColor(talkingColor);
		
		

		speechWidget.setLeaderLine(calc);

		speechWidget.setVisible(isVisible);


		super.add(speechWidget, rectThatsScaledToFit.getLeft(), rectThatsScaledToFit.getTop());

	}

	@Override
	public void onSceneEntry(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetScale(Image image) {
		ImageForHtml4 image4 = (ImageForHtml4) image;
		if(image4.getOriginalDimensions()!=null)
		{
			Point temp = image4.getOriginalDimensions();
			image4.getNativeImage().setPixelSize(temp.getX(),temp.getY());
			image4.getNativeImage().setWidth("");
			image4.getNativeImage().setHeight(""); 
		}
		image.setScale(1.0);
		
	}

}
