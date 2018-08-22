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

import com.github.a2g.core.objectmodel.Image;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.interfaces.nongame.IImagePanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformInventoryPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformPackagedImage;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromInventoryPanel;
import com.github.a2g.core.objectmodel.Inventory;
import com.github.a2g.core.platforms.html4.dependencies.ImageForHtml4;
import com.github.a2g.core.platforms.html4.dependencies.PlatformPackagedImageForHtml4;
import com.github.a2g.core.platforms.html4.mouse.ImageMouseClickHandler;
import com.github.a2g.core.platforms.html5.dependencies.CanvasEtcHtml5;
import com.github.a2g.core.platforms.html5.mouse.InventoryMouseClickHandler;
import com.github.a2g.core.platforms.html5.mouse.InventoryMouseOverHandler;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectI;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;

 
public class InventoryPanelForHtml5
extends VerticalPanel 
implements IImagePanel
, IPlatformInventoryPanel
{
	class Structure
	{
		public Image image;
		int x;
		int y;
		boolean visible;
	}
	private AbsolutePanel abs;
	int tally;
	int width;
	int height;

	private Map<Integer,PointI> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllAvailableImages;
	private CanvasEtcHtml5 canvasEtcHtml5;
	private IInventoryPresenterFromInventoryPanel toInvPres;
	private boolean isLeftArrowVisible;
	private boolean isRightArrowVisible;
	private ColorEnum fore;
	private ColorEnum back;
	 
	public InventoryPanelForHtml5(EventBus bus, IInventoryPresenterFromInventoryPanel toInvPres, ColorEnum fore, ColorEnum back, ColorEnum rollover)
	{
		this.getElement().setId("cwAbsolutePanel");
		this.addStyleName("absolutePanel");
		
		this.mapOfPointsByImage = new TreeMap<Integer, PointI>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllAvailableImages = new LinkedList<Image>();
		this.width = 400;
		this.height = 200;
		this.toInvPres = toInvPres;
		this.fore = fore;
		this.back = back;
		tally++;
		
 
		canvasEtcHtml5 = new CanvasEtcHtml5("");
		canvasEtcHtml5.addMouseMoveHandler(new InventoryMouseOverHandler(this, bus, toInvPres));
		canvasEtcHtml5.addClickHandler(new InventoryMouseClickHandler(bus, canvasEtcHtml5));

		this.abs = new AbsolutePanel(); 
		this.add(abs);
	 
	}
 
	Integer hash(Image image) {
		int ocode = ((ImageForHtml4) image).getNativeImage().hashCode();
		return new Integer(ocode);
	}
	
	void putPoint(ImageForHtml4 image, int x, int y) {
		mapOfPointsByImage.put(hash(image), new PointI(x, y));
	}

	
	@Override
	public void setThingPosition(Image image, int left, int top, double scale) {
		this.abs.setWidgetPosition(((ImageForHtml4) image).getNativeImage(), left , top  );
		if (mapOfPointsByImage.containsKey(hash(image))) {
			putPoint((ImageForHtml4) image, left, top);
			triggerPaint();
		}
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
		triggerPaint();
	}

	@Override
	public void insert(Image image, int x, int y, int before) {
		this.abs.insert(((ImageForHtml4) image).getNativeImage(), x  , y  , before);

		listOfAllAvailableImages.add(before, image);
		putPoint((ImageForHtml4) image, x, y);
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		this.abs.remove(((ImageForHtml4) image).getNativeImage());
		listOfAllAvailableImages.remove(((ImageForHtml4) image).getNativeImage());
		mapOfPointsByImage.remove(hash(image));
		setImageVisible(image, false);
		triggerPaint();
	}
	@Override
	public void add(Image image, int x, int y) {
		this.abs.add(((ImageForHtml4) image).getNativeImage(), x, y);

		listOfAllAvailableImages.add(image);
		putPoint((ImageForHtml4) image, x, y);
		triggerPaint();
	}

	@Override
	public void clear() {
		listOfAllAvailableImages.clear();
		mapOfPointsByImage.clear();
		listOfVisibleHashCodes.clear();
	}

	public void triggerPaint() {
		paint();
	}
	
	public void paint()
	{
		// red background
		canvasEtcHtml5.getContextB().setFillStyle("red");
		canvasEtcHtml5.getContextB().fillRect(0, 0, width, height);
		
		// blue rectangles
		for(RectI r : toInvPres.getRects())
		{
			//String style = fillColor.toString().toLowerCase();
			canvasEtcHtml5.getContextB().beginPath();
			canvasEtcHtml5.getContextB().setFillStyle("blue");
			canvasEtcHtml5.getContextB().fillRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight());
			canvasEtcHtml5.getContextB().closePath();
		}
		
		// now actual inventory images.
		Iterator<Image> iter = listOfAllAvailableImages.iterator();
		while(iter.hasNext())
		{
			Image image = iter.next();
			
			if(listOfVisibleHashCodes.contains(hash(image)))
			{
				PointI p = mapOfPointsByImage.get(hash(image));
				ImageElement imageElement = (ImageElement) (((ImageForHtml4) image).getNativeImage().getElement().cast());
				canvasEtcHtml5.drawAtXY(p.getX(), p.getY(),  imageElement );
			}
		}
		


		if(isLeftArrowVisible)
		{

			int[] xPoints = new int[3];
			int[] yPoints = new int[3];
			xPoints[0]=(int)(.05*width);
			xPoints[1]=(int)(.25*width);
			xPoints[2]=(int)(.25*width);
			
			yPoints[0]=(int)(.50*height);
			yPoints[1]=(int)(.01*height);
			yPoints[2]=(int)(.09*height);
			 
			canvasEtcHtml5.drawFilledTriangle(xPoints,yPoints,3, fore, back,1);
		}
		if(isRightArrowVisible)
		{
			int[] xPoints = new int[3];
			int[] yPoints = new int[3];
			xPoints[0]=(int)(.95*width);
			xPoints[1]=(int)(.75*width);
			xPoints[2]=(int)(.75*width);
			
			yPoints[0]=(int)(.50*height);
			yPoints[1]=(int)(.01*height);
			yPoints[2]=(int)(.09*height);
			 
			canvasEtcHtml5.drawFilledTriangle(xPoints,yPoints,3, fore, back,1);
			 
		}

		tally=0;
		// update the front canvas
		canvasEtcHtml5.copyBackBufferToFront();

	}

	@Override
	public int getImageHeight(Image image) {
		return ((ImageForHtml4) image).getNativeImage().getHeight();
	}

	@Override
	public int getImageWidth(Image image) {
		return ((ImageForHtml4) image).getNativeImage().getWidth();
	}

	// this is the one that gets called.
	@Override
	public Image createNewImageAndAddHandlers(IPlatformPackagedImage imageResource,
			LoadHandler lh, EventBus bus, String objectTextualId,
			int objectCode, int x, int y)
	{
		com.google.gwt.user.client.ui.Image image = Image.getImageFromResource((PlatformPackagedImageForHtml4) imageResource,
				lh);

		ImageForHtml4 imageAndPos = new ImageForHtml4(image, this, new PointI(x, y));

		// add gwt mouse handlers
		imageAndPos.getNativeImage()
				.addMouseMoveHandler(new InventoryMouseOverHandler(this, bus, toInvPres));

		imageAndPos.getNativeImage().addClickHandler(new ImageMouseClickHandler(bus, this.abs));
		image.setVisible(false);
		return imageAndPos;
	}
	
	

	@Override
	public void updateInventory(Inventory inventory) {
		// this gets visited every startScene
		triggerPaint();

	}

	@Override
	public void setLeftArrowVisible(boolean visible) {
		//imgLeft.setVisible(visible, new PointI(0,0));
		this.isLeftArrowVisible = visible;
		triggerPaint();
	}

	@Override
	public void setRightArrowVisible(boolean visible) {
		//imgRight.setVisible(visible, new PointI(50,0));
		this.isRightArrowVisible = visible;
		triggerPaint();
	}

	@Override
	public void setDimensionsOfPanel(int width, int height) {
		this.width = width;
		this.height = height;
		canvasEtcHtml5.setScenePixelSize(width, height, this);
	}

	@Override
	public void resetScale(Image image) {
		// TODO Auto-generated method stub
		
	}
}