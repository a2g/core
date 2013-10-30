
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


package com.github.a2g.core.canvas.panel;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.GestureStartEvent;
import com.google.gwt.event.dom.client.GestureStartHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.swing.JPanel;

//import com.github.a2g.bridge.Image;
import com.github.a2g.core.objectmodel.Image;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.canvas.factory.Html5Image;
import com.github.a2g.core.gwt.factory.GWTImage;
import com.github.a2g.core.gwt.factory.GWTPackagedImage;
import com.github.a2g.core.gwt.mouse.ImageMouseClickHandler;
import com.github.a2g.core.gwt.mouse.SceneObjectMouseOverHandler;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.PackagedImageAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.objectmodel.SceneObjectCollection;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;


@SuppressWarnings("serial")
public class ScenePanel
extends AbsolutePanel
implements ScenePanelAPI, ImagePanelAPI
{
	int width;
	int height;
	int tally;
	int cameraOffsetX;
	int cameraOffsetY;
	InternalAPI api;
	Canvas canvas;
	Canvas backBuffer;
	final CssColor redrawColor = CssColor.make("rgba(255,255,255,0.6)");
	Context2d context;
	Context2d backBufferContext;

	private Map<Integer,Point> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllVisibleImages;

	public ScenePanel(EventBus bus, InternalAPI api)
	{
		this.api = api;
		this.mapOfPointsByImage = new TreeMap<Integer, Point>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllVisibleImages = new LinkedList<Image>();
		canvas = Canvas.createIfSupported();
		backBuffer = Canvas.createIfSupported();
		if (canvas == null) {
			// RootPanel.get(holderId).add(new Label(upgradeMessage));
			return;
		}

		// init the canvases
		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
		backBuffer.setCoordinateSpaceWidth(width);
		backBuffer.setCoordinateSpaceHeight(height);
		this.add(canvas);
		context = canvas.getContext2d();
		backBufferContext = backBuffer.getContext2d();
	}

	void putPoint(Html5Image image, int x,int y)
	{
		mapOfPointsByImage.put(hash(image), new Point(x,y));
	}

	@Override
	public void setThingPosition(Image image, int x, int y) {
		if(mapOfPointsByImage.containsKey(hash(image)))
		{
			putPoint((Html5Image)image, x,y);
			triggerPaint();
		}

	}

	@Override
	public void setImageVisible(Image image, boolean visible)
	{
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
	public void insert(Image image, int x, int y, int before) {
		listOfAllVisibleImages.add(before,image);
		putPoint((Html5Image)image, x,y);
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		listOfAllVisibleImages.remove(((Html5Image)image).getNativeImage());
		mapOfPointsByImage.remove(hash(image));
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void add(Image image, int x, int y) {
		listOfAllVisibleImages.add(image);
		putPoint((Html5Image)image, x,y);
		triggerPaint();
	}

	@Override
	public void clear() {
		listOfAllVisibleImages.clear();
		mapOfPointsByImage.clear();
		listOfVisibleHashCodes.clear();
	}

	public void triggerPaint()
	{
		paint();
		tally++;
	}

	Integer hash(Image image)
	{
		int code= ((Html5Image)image).getNativeImage().hashCode();
		return new Integer(code);
	}

	public void paint()
	{

	    // update the back canvas
	    backBufferContext.setFillStyle(redrawColor);
	    backBufferContext.fillRect(0, 0, width, height);
	    
	    
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
			    backBufferContext.drawImage(((Html5Image)image).getNativeImage(), 0, 0);
			    backBufferContext.restore();}
		}
		//System.out.println("printed with tally " + tally +" draws "+ draws);
		tally=0;

		
	    // update the front canvas
	    context.drawImage(backBufferContext.getCanvas(), 0, 0);
	}

	public String getObjectUnderMouse(int x, int y)
	{
		//System.out.println("----------------");
		String textualId = "";
		SceneObjectCollection coll = api.getSceneGui().getModel().objectCollection();
		ArrayList<SceneObject> list = coll.getSortedList();
		for(int i = 0;i<list.size();i++)
		{
			SceneObject ob = list.get(i);
			if(ob.isVisible())
			{
				//System.out.println(ob.getTextualId() + ob.getNumberPrefix());
				int frame = ob.getCurrentFrame();
				String anim = ob.getCurrentAnimation();
				if(ob.getAnimations().at(anim)!=null)
				{
					Image img = ob.getAnimations().at(anim).getFrames().at(frame);
					if(img!=null)
					{
						Rect rect = img.getBoundingRect();
						int adjX = x - (int)ob.getX() + cameraOffsetX;
						int adjY = y - (int)ob.getY() + cameraOffsetY;

						if(rect.contains(adjX, adjY))
						{
							textualId = ob.getTextualId();
						}
					}
				}
			}
		}

		return textualId;
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
	public int getImageHeight(Image image) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getImageWidth(Image image) {
		// TODO Auto-generated method stub
		return 0;
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
		short objectId)
	{
		
		com.google.gwt.user.client.ui.Image img = Image.getImageFromResource((GWTPackagedImage)imageResource,lh);

		Html5Image imageAndPos = new Html5Image(img, objectTextualId, this, new Point(x, y));

		// add gwt mouse handlers
		//imageAndPos.getNativeImage().addMouseMoveHandler
		//(
		//		new SceneObjectMouseOverHandler(bus, api, objectTextualId, objectCode)
		//		);

		//imageAndPos.getNativeImage().addClickHandler
		//(
		//		new ImageMouseClickHandler(bus, this)
		//		);
		lh.onLoad(null);
		return imageAndPos;
	}
}















