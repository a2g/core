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

package com.github.a2g.core.platforms.html5.panel;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.action.SayAction;
import com.github.a2g.core.interfaces.ICommandLinePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.IPackagedImage;
import com.github.a2g.core.interfaces.IScenePanelFromScenePresenter;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.platforms.html4.ImageForHtml4;
import com.github.a2g.core.platforms.html4.PackagedImageForHtml4;
import com.github.a2g.core.platforms.html4.mouse.ImageMouseClickHandler;
import com.github.a2g.core.platforms.html4.mouse.SceneObjectMouseOverHandler;
import com.github.a2g.core.platforms.html5.mouse.SceneMouseClickHandler;
import com.github.a2g.core.platforms.html5.mouse.SceneMouseOverHandler;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;

//commandLineAndVerbsAndInventory = new VerticalPanel();

public class ScenePanelForHtml5
extends VerticalPanel
implements 
ImagePanelAPI
, IScenePanelFromScenePresenter
{
	//private EventBus bus;
	private AbsolutePanel abs;
	private int cameraOffsetX;
	private int cameraOffsetY;

	private IScenePresenterFromScenePanel toScene;
	//private ICommandLineFromSceneMouseOver toCommandLine;

	private Canvas canvas;
	private Canvas backBuffer;
	@SuppressWarnings("unused")
	private final CssColor redrawColor = CssColor.make("rgba(255,0,0,0.6)");
	private Context2d context;
	private Context2d backBufferContext;
	private Map<Integer,Point> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllVisibleImages;

	public ScenePanelForHtml5(EventBus bus, IScenePresenterFromScenePanel toScene, ICommandLinePresenterFromSceneMouseOver toCommandLine)
	{
		//this.bus = bus;
		this.getElement().setId("cwAbsolutePanel");
		this.addStyleName("absolutePanel");
		this.cameraOffsetX = 0;
		this.cameraOffsetY = 0;
		this.abs = new AbsolutePanel();
		this.toScene = toScene;
		//this.toCommandLine = toCommandLine;
		this.mapOfPointsByImage = new TreeMap<Integer, Point>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllVisibleImages = new LinkedList<Image>();
		canvas = Canvas.createIfSupported();
		backBuffer = Canvas.createIfSupported();
		if (canvas == null) {
			// RootPanel.get(holderId).add(new Label(upgradeMessage));
			return;
		}
		canvas.addMouseMoveHandler(new SceneMouseOverHandler(this, bus, toScene, toCommandLine));
		canvas.addClickHandler(new SceneMouseClickHandler(bus, canvas));
		this.add(canvas);
		this.add(abs);
	}


	void putPoint(ImageForHtml4 image, int x,int y)
	{
		mapOfPointsByImage.put(hash(image), new Point(x,y));
	}


	@Override
	public Image createNewImageAndAddHandlers
	(
			LoadHandler lh,
			IPackagedImage imageResource,
			IScenePresenterFromSceneMouseOver api,
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
				new ImageMouseClickHandler(bus, this.abs)
				);
		image.setVisible(false);
		return imageAndPos;
	}


	@Override
	public void setImageVisible(Image image, boolean visible)
	{
		UIObject.setVisible(((ImageForHtml4)image).getNativeImage().getElement(), visible);

		boolean isIn = listOfVisibleHashCodes.contains(hash(image));
		if(visible && !isIn)
		{
			listOfVisibleHashCodes.add(hash(image));
			triggerPaint();
		}
		else if(!visible & isIn)
		{
			listOfVisibleHashCodes.remove(hash(image));
			triggerPaint();
		}
	}

	@Override
	public void add(Image image, int x, int y)
	{
		this.abs.add(((ImageForHtml4) image).getNativeImage(), x, y);

		listOfAllVisibleImages.add(image);
		putPoint((ImageForHtml4)image, x,y);
		triggerPaint();
	}

	@Override
	public void insert(Image image, int x, int y, int before)
	{
		this.abs.insert(((ImageForHtml4)image).getNativeImage(),x-cameraOffsetX,y-cameraOffsetY,before);

		listOfAllVisibleImages.add(before,image);
		putPoint((ImageForHtml4)image, x,y);
		triggerPaint();
	}

	@Override
	public void remove(Image image)
	{
		this.abs.remove(((ImageForHtml4)image).getNativeImage());
		listOfAllVisibleImages.remove(((ImageForHtml4)image).getNativeImage());
		mapOfPointsByImage.remove(hash(image));
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void setThingPosition(Image image, int left, int top)
	{
		this.abs.setWidgetPosition(((ImageForHtml4)image).getNativeImage(), left-cameraOffsetX, top-cameraOffsetY);
		if(mapOfPointsByImage.containsKey(hash(image)))
		{
			putPoint((ImageForHtml4)image, left,top);
			triggerPaint();
		}
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
		this.remove(canvas);

		this.setSize("" + width + "px",
				"" + height + "px");
		canvas.setSize("" + width + "px",
				"" + height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height	);
		backBuffer.setCoordinateSpaceWidth(width);
		backBuffer.setCoordinateSpaceHeight(height);

		this.add(canvas);

		context = canvas.getContext2d();
		backBufferContext = backBuffer.getContext2d();

	}

	@Override
	public void setCameraOffset(int x, int y)
	{
		this.cameraOffsetX = x;
		this.cameraOffsetY = y;
	}

	public void triggerPaint()
	{
		paint();
	}

	Integer hash(Image image)
	{
		int code= ((ImageForHtml4)image).getNativeImage().hashCode();
		return new Integer(code);
	}

	public void paint()
	{
		Iterator<Image> iter = listOfAllVisibleImages.iterator();
		while(iter.hasNext())
		{
			Image image = iter.next();
			if(listOfVisibleHashCodes.contains(hash(image)))
			{
				Point p = mapOfPointsByImage.get(hash(image));
				int x = p.getX();
				int y = p.getY();

				backBufferContext.save();
				backBufferContext.translate(x, y);
				//backBufferContext.rotate(rot);
				ImageElement imageElement = (ImageElement)( ((ImageForHtml4)image).getNativeImage().getElement().cast());
				backBufferContext.drawImage(imageElement, 0, 0);
				backBufferContext.restore();
			}
		}
		// System.out.println("printed with tally " + tally +" draws "+ draws);

		// update the front canvas
		context.drawImage(backBufferContext.getCanvas(), 0, 0);
	}

	public int getWidth()
	{
		return canvas.getCoordinateSpaceWidth();
	}

	public int getHeight()
	{
		return canvas.getCoordinateSpaceHeight();
	}


	public String getObjectUnderMouse(int x, int y)
	{
		//System.out.println("----------------");
		int count = toScene.getSceneObjectCount();
		for(int i = 0;i<count;i++)
		{
			String otid = toScene.getOtidByIndex(i);
			if(toScene.getVisibleByOtid(otid))
			{
				String atid = toScene.getAtidOfCurrentAnimationByOtid(otid);
				int frame = toScene.getCurrentFrameByOtid(otid);
				Rect rect = toScene.getBoundingRectByFrameAndAtid(frame,atid);
				//System.out.println(ob.getTextualId() + ob.getNumberPrefix());
				
				int adjX = x - (int)toScene.getXByOtid(otid) + cameraOffsetX;
				int adjY = y - (int)toScene.getYByOtid(otid) + cameraOffsetY;

				if(rect.contains(adjX, adjY))
				{
					return otid;
				}
			}
		}

		return "";
	}


	@Override
	public void setStateOfPopup(boolean isVisible, double x, double y,
			ColorEnum talkingColor, String speech, SayAction sayAction) {
	// maybe use the dom to create speech bubble graphic and render it.
	}


	



}
