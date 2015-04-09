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
import com.github.a2g.core.action.SayAction;
import com.github.a2g.core.interfaces.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.IPackagedImage;
import com.github.a2g.core.interfaces.IScenePanelFromScenePresenter;
import com.github.a2g.core.platforms.html4.ImageForHtml4;
import com.github.a2g.core.platforms.html4.PackagedImageForHtml4;
import com.github.a2g.core.platforms.html4.mouse.ImageMouseClickHandler;
import com.github.a2g.core.platforms.html4.mouse.SceneObjectMouseOverHandler;
import com.github.a2g.core.platforms.html4.mouse.SceneObjectTouchMoveHandler;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class ScenePanel extends AbsolutePanel implements ImagePanelAPI,
		IScenePanelFromScenePresenter {
	int cameraOffsetX;
	int cameraOffsetY;
	SceneObjectTouchMoveHandler theTouchMoveHandler;
	SpeechBalloon speechWidget;
	
	public ScenePanel(EventBus bus, IScenePresenterFromScenePanel api) {
		this.getElement().setId("cwAbsolutePanel");
		// this.addStyleName("absolutePanel");
		this.cameraOffsetX = 0;
		this.cameraOffsetY = 0;
		this.theTouchMoveHandler = new SceneObjectTouchMoveHandler(api);
		this.speechWidget = new SpeechBalloon();
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
	public void setThingPosition(Image image, int left, int top) {
		super.setWidgetPosition(((ImageForHtml4) image).getNativeImage(),
				(int) (left - cameraOffsetX * image.getParallaxX()),
				(int) (top - cameraOffsetY * image.getParallaxY()));
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
	public void setScenePixelSize(int width, int height) {
		this.setSize("" + width + "px", "" + height + "px");
	}

	@Override
	public void setCameraOffset(int x, int y) {
		this.cameraOffsetX = x;
		this.cameraOffsetY = y;
	}

	public void setStateOfPopup(boolean isVisible, 
			ColorEnum talkingColor, String speech, Rect maxBalloonRect, Point mouth, SayAction sayAction) {
		if (!isVisible) {
			super.remove(speechWidget);
		}
		
		speechWidget.setText(speech);
		
		speechWidget.setVisible(isVisible);
		
		speechWidget.setBorderColor(talkingColor);
		
		speechWidget.setRectInPixels(maxBalloonRect);
		Point bubble = maxBalloonRect.getCenter();
		if(bubble.getY() > mouth.getY())
		{
			if(mouth.getX() > bubble.getX())
			{
				// on the top, pointing right
				speechWidget.setStyleAsFromTopPointingRight();
			}
			else
			{
				// on the top, pointing left
				speechWidget.setStyleAsFromTopPointingLeft();
			}
		}
		else
		{
			if(mouth.getX() > bubble.getX())
			{
				// on the bottom, pointing right 
				speechWidget.setStyleAsFromBottomPointingRight();
			}
			else
			{
				// on the bottom, pointing left
				speechWidget.setStyleAsFromBottomPointingLeft();
			}
		}
		
		super.add(speechWidget, maxBalloonRect.getLeft(),maxBalloonRect.getTop());

	}

}
