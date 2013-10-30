
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

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

//import com.github.a2g.bridge.Image;
import com.github.a2g.core.objectmodel.Image;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.PackagedImageAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.platforms.html4.PackagedImageForHtml4;
import com.github.a2g.core.platforms.html5.ImageForHtml5;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.objectmodel.SceneObjectCollection;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;


public class ScenePanelForHtml5
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

	public ScenePanelForHtml5(EventBus bus, InternalAPI api)
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

	void putPoint(ImageForHtml5 image, int x,int y)
	{
		mapOfPointsByImage.put(hash(image), new Point(x,y));
	}

	@Override
	public void setThingPosition(Image image, int x, int y) {
		if(mapOfPointsByImage.containsKey(hash(image)))
		{
			putPoint((ImageForHtml5)image, x,y);
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
		putPoint((ImageForHtml5)image, x,y);
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		listOfAllVisibleImages.remove(((ImageForHtml5)image).getNativeImage());
		mapOfPointsByImage.remove(hash(image));
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void add(Image image, int x, int y) {
		listOfAllVisibleImages.add(image);
		putPoint((ImageForHtml5)image, x,y);
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
		int code= ((ImageForHtml5)image).getNativeImage().hashCode();
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
			    backBufferContext.drawImage(((ImageForHtml5)image).getNativeImage(), 0, 0);
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
		
		com.google.gwt.user.client.ui.Image img = Image.getImageFromResource((PackagedImageForHtml4)imageResource,lh);

		ImageForHtml5 imageAndPos = new ImageForHtml5(img, objectTextualId, this, new Point(x, y));

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















