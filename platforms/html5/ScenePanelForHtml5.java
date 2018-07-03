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

package com.github.a2g.core.platforms.html5;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.TextMetrics;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.internal.ICommandLinePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IPackagedImage;
import com.github.a2g.core.interfaces.internal.IScenePanelFromScenePresenter;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.internal.ImagePanelAPI;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.platforms.html4.dependencies.ImageForHtml4;
import com.github.a2g.core.platforms.html4.dependencies.PackagedImageForHtml4;
import com.github.a2g.core.platforms.html4.mouse.ImageMouseClickHandler;
import com.github.a2g.core.platforms.html4.mouse.SceneObjectMouseOverHandler;
import com.github.a2g.core.platforms.html5.dependencies.CanvasEtcHtml5;
import com.github.a2g.core.platforms.html5.dependencies.FontCallsHtml5;
import com.github.a2g.core.platforms.html5.mouse.SceneMouseClickHandler;
import com.github.a2g.core.platforms.html5.mouse.SceneMouseOverHandler;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectI;
import com.github.a2g.core.primitive.SpeechBubble;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.touch.client.Point;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;

//commandLineAndVerbsAndInventory = new VerticalPanel();

public class ScenePanelForHtml5 
extends VerticalPanel 
implements ImagePanelAPI, 
IScenePanelFromScenePresenter {
	// private static final Logger HTML5CANVAS =
	// Logger.getLogger(LogNames.HTML5CANVAS);

	// private EventBus bus;
	private AbsolutePanel abs;
	private int cameraOffsetX;
	private int cameraOffsetY;

	private IScenePresenterFromScenePanel toScene; 
	private Map<Integer, PointI> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllVisibleImages;
	private boolean speechVisible;
	private ColorEnum speechColor;
	private SpeechBubble speechRectAndLeaderLine;
	private CanvasEtcHtml5 canvasEtcHtml5;

	public ScenePanelForHtml5(EventBus bus, IScenePresenterFromScenePanel toScene,
			ICommandLinePresenterFromSceneMouseOver toCommandLine) {
		// this.bus = bus;
		this.getElement().setId("cwAbsolutePanel");
		this.addStyleName("absolutePanel");
		this.cameraOffsetX = 0;
		this.cameraOffsetY = 0;
		this.abs = new AbsolutePanel();
		this.toScene = toScene;
		// this.toCommandLine = toCommandLine;
		this.mapOfPointsByImage = new TreeMap<Integer, PointI>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllVisibleImages = new LinkedList<Image>();
		canvasEtcHtml5 = new CanvasEtcHtml5("");
		canvasEtcHtml5.addMouseMoveHandler(new SceneMouseOverHandler(this, bus, toScene, toCommandLine));
		canvasEtcHtml5.addClickHandler(new SceneMouseClickHandler(bus, canvasEtcHtml5));
		
		this.add(abs);
	}

	void putPoint(ImageForHtml4 image, int x, int y) {
		mapOfPointsByImage.put(hash(image), new PointI(x, y));
	}

	@Override
	public Image createNewImageAndAddHandlers(LoadHandler lh, IPackagedImage imageResource,
			IScenePresenterFromSceneMouseOver api, EventBus bus, int x, int y, String objectTextualId,
			short objectCode) {
		com.google.gwt.user.client.ui.Image image = Image.getImageFromResource((PackagedImageForHtml4) imageResource,
				lh);

		ImageForHtml4 imageAndPos = new ImageForHtml4(image, this, new PointI(x, y));

		// add gwt mouse handlers
		imageAndPos.getNativeImage()
				.addMouseMoveHandler(new SceneObjectMouseOverHandler(bus, api, objectTextualId, objectCode));

		imageAndPos.getNativeImage().addClickHandler(new ImageMouseClickHandler(bus, this.abs));
		image.setVisible(false);
		return imageAndPos;
	}

	@Override
	public void setImageVisible(Image image, boolean visible) {
		UIObject.setVisible(((ImageForHtml4) image).getNativeImage().getElement(), visible);

		boolean isIn = listOfVisibleHashCodes.contains(hash(image));
		if (visible && !isIn) {
			listOfVisibleHashCodes.add(hash(image));
			triggerPaint();
		} else if (!visible & isIn) {
			listOfVisibleHashCodes.remove(hash(image));
			triggerPaint();
		}
	}

	@Override
	public void add(Image image, int x, int y) {
		this.abs.add(((ImageForHtml4) image).getNativeImage(), x, y);

		listOfAllVisibleImages.add(image);
		putPoint((ImageForHtml4) image, x, y);
		triggerPaint();
	}

	@Override
	public void insert(Image image, int x, int y, int before) {
		this.abs.insert(((ImageForHtml4) image).getNativeImage(), x - cameraOffsetX, y - cameraOffsetY, before);

		listOfAllVisibleImages.add(before, image);
		putPoint((ImageForHtml4) image, x, y);
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		this.abs.remove(((ImageForHtml4) image).getNativeImage());
		listOfAllVisibleImages.remove(((ImageForHtml4) image).getNativeImage());
		mapOfPointsByImage.remove(hash(image));
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void setThingPosition(Image image, int left, int top, double scale) {
		this.abs.setWidgetPosition(((ImageForHtml4) image).getNativeImage(), left - cameraOffsetX, top - cameraOffsetY);
		if (mapOfPointsByImage.containsKey(hash(image))) {
			putPoint((ImageForHtml4) image, left, top);
			triggerPaint();
		}
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
		canvasEtcHtml5.setScenePixelSize(width, height, this);
	}

	@Override
	public void setCameraOffset(int x, int y) {
		this.cameraOffsetX = x;
		this.cameraOffsetY = y;
	}

	public void triggerPaint() {
		paint();
	}

	Integer hash(Image image) {
		int ocode = ((ImageForHtml4) image).getNativeImage().hashCode();
		return new Integer(ocode);
	}

	static public RectI getRectGivenSpeechAndMaxRect(String speech, RectI maxRect, Context2d ctxt) {
		TextMetrics tm = ctxt.measureText(speech);
		double width = tm.getWidth();
		double startX = maxRect.getCenter().getX() - width / 2;

		RectI retVal = new RectI((int) startX, maxRect.getTop(), (int) width, maxRect.getHeight());
		return retVal;
	}

	public void paint() {
		//canvasEtcHtml5.getContextB().clearRect(0,0, this.getWidth(), this.getHeight());
		Iterator<Image> iter = listOfAllVisibleImages.iterator();
		while (iter.hasNext()) {
			Image image = iter.next();
			if (listOfVisibleHashCodes.contains(hash(image))) {
				PointI p = mapOfPointsByImage.get(hash(image));
				int leftTopPlusX = p.getX();
				int leftTopPlusY = p.getY();
				ImageElement imageElement = (ImageElement) (((ImageForHtml4) image).getNativeImage().getElement()
						.cast());
				if(image.getScale()==1)
				{
					canvasEtcHtml5.drawAtXY(leftTopPlusX, leftTopPlusY, imageElement);
				}
				else
				{
				
					// img - the specified image to be drawn. This method does
					// nothing if img is null.
					// sx1 - the x coordinate of the first corner of the source
					// rectangle.
					// sy1 - the y coordinate of the first corner of the source
					// rectangle.
					// sx2 - the x coordinate of the second corner of the source
					// rectangle.
					// sy2 - the y coordinate of the second corner of the source
					// rectangle.

					// dx1 - the x coordinate of the first corner of the destination
					// rectangle.
					// dy1 - the y coordinate of the first corner of the destination
					// rectangle.
					// dx2 - the x coordinate of the second corner of the
					// destination rectangle.
					// dy2 - the y coordinate of the second corner of the
					// destination rectangle.

					// source coords: these are correct. don't change.
					int sx = 0;
					int sy = 0;
					int sw = getImageWidth(image);
					int sh = getImageHeight(image);

					// these are also correct, the real question
					// lies in what is leftTopPlusY
					// these are set with SetThingPosition
					int dx = (int) (leftTopPlusX) - cameraOffsetX;
					int dy = (int) (leftTopPlusY) - cameraOffsetY;
					int dw = (int) (sw * image.getScale());
					int dh = (int) (sh * image.getScale());
					
					
					canvasEtcHtml5.drawAtXYScaled(imageElement, sx,sy,sw,sh,dx,dy,dw,dh);
				}
			}
		}

		if (speechVisible) {
			boolean isDiagnosticsDisplayed = false;
		    canvasEtcHtml5.drawSpeech(speechRectAndLeaderLine, speechColor, isDiagnosticsDisplayed);
		}

		// update the front canvas
		canvasEtcHtml5.copyBackBufferToFront();

	}

	public int getWidth() {
		return canvasEtcHtml5.getCoordinateSpaceWidth();
	}

	public int getHeight() {
		return canvasEtcHtml5.getCoordinateSpaceHeight();
	}

	public String getObjectUnderMouse(int x, int y) {

		// System.out.println("----------------");
		if (toScene != null) {
			int count = toScene.getSceneObjectCount();
			for (int i = count - 1; i >= 0; i--) {
				String otid = toScene.getOtidByIndex(i);
				if (toScene.getVisibleByOtid(otid)) 	{
					String atid = toScene.getAtidOfCurrentAnimationByOtid(otid);
					int frame = toScene.getCurrentFrameByOtid(otid);
					RectI rect = toScene.getBoundingRectByFrameAndAtid(frame, atid);
					int obx = (int) toScene.getXByOtid(otid);
					int oby = (int) toScene.getYByOtid(otid);

					int adjX = x - obx + cameraOffsetX;
					int adjY = y - oby + cameraOffsetY;

					if (rect.contains(adjX, adjY)) {
						return otid;
					}
				}
			}
		} else {
			return "ERROR!";
		}

		return "";
	}

	@Override
	public void setStateOfPopup(boolean isVisible, ColorEnum talkingColor, SpeechBubble c,
			TalkPerformer sayAction) {

		this.speechVisible = isVisible;
		this.speechColor = talkingColor;
		this.speechRectAndLeaderLine = c;
		this.triggerPaint();

	}

	@Override
	public void onSceneEntry(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetScale(Image image) {
		// not sure how this will be implemented.
	}

	@Override
	public Point measureTextWidthAndHeight(String text) {
		FontCallsHtml5 fm = new FontCallsHtml5(this.canvasEtcHtml5.getContextB());
		return fm.measureTextWidthAndHeight(text);
	}

	@Override
	public void setFontNameAndHeight(String fontName, int fontHeight) {
		FontCallsHtml5 fm = new FontCallsHtml5(this.canvasEtcHtml5.getContextB());
		fm.setFontNameAndHeight(fontName, fontHeight);
	}

}
