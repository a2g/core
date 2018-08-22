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


package com.github.a2g.core.platforms.swing;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JPanel;

import com.github.a2g.core.objectmodel.Image;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.interfaces.nongame.IImagePanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformInventoryPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformPackagedImage;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromInventoryPanel;
import com.github.a2g.core.objectmodel.Inventory;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.platforms.swing.dependencies.ImageForSwing;
import com.github.a2g.core.platforms.swing.dependencies.PlatformPackagedImageForSwing;
import com.github.a2g.core.platforms.swing.mouse.InventoryMouseClickHandler;
import com.github.a2g.core.platforms.swing.mouse.InventoryMouseOverHandler;
import com.google.gwt.event.shared.EventBus;

@SuppressWarnings("serial")
public class InventoryPanelForSwing
extends JPanel
implements IImagePanel
, IPlatformInventoryPanel
, ActionListener
{
	class Structure
	{
		public Image image;
		int x;
		int y;
		boolean visible;
	}

	int tally;
	int width;
	int height;

	private Map<Integer,PointI> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllAvailableImages;
	private boolean isLeftArrowVisible;
	private boolean isRightArrowVisible;
	private ColorEnum fore;
	//private ColorEnum back;

 

	public InventoryPanelForSwing(EventBus bus, IInventoryPresenterFromInventoryPanel api2, ColorEnum fore, ColorEnum back)
	{
		this.mapOfPointsByImage = new TreeMap<Integer, PointI>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllAvailableImages = new LinkedList<Image>();
		this.width = 200;
		this.height = 200;
		this.isLeftArrowVisible = false;
		this.isRightArrowVisible = false;
		this.fore = fore;
		//this.back = back;
		this.setDoubleBuffered(true);
		tally++;

		this.setForeground(new Color(fore.r, fore.g, fore.b));
		this.setBackground(new Color(back.r, back.g, back.b));

		super.addMouseListener
		(
				new InventoryMouseClickHandler(this, api2)
				);

		super.addMouseMotionListener
		(
				new InventoryMouseOverHandler(this, api2)
				);

		 
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(width,height);
	}

	@Override
	public void setThingPosition(Image image, int x, int y, double scale) {
		if(mapOfPointsByImage.containsKey(((ImageForSwing)image).getNativeImage().hashCode()))
		{
			mapOfPointsByImage.put(((ImageForSwing)image).getNativeImage().hashCode(), new PointI(x,y));
			triggerPaint();
		}
	}

	@Override
	public void setImageVisible(Image image, boolean visible)
	{
		boolean isIn = listOfVisibleHashCodes.contains(((ImageForSwing)image).getNativeImage().hashCode());
		if(visible && !isIn)
		{
			listOfVisibleHashCodes.add(((ImageForSwing)image).getNativeImage().hashCode());
			triggerPaint();
		}
		else if(!visible & isIn)
		{
			listOfVisibleHashCodes.remove(new Integer(((ImageForSwing)image).getNativeImage().hashCode()));
			triggerPaint();
		}
	}

	@Override
	public void insert(Image image, int x, int y, int before) {
		listOfAllAvailableImages.add(before,image);
		mapOfPointsByImage.put(((ImageForSwing)image).getNativeImage().hashCode(), new PointI(x,y));
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		listOfAllAvailableImages.remove(((ImageForSwing)image).getNativeImage());
		mapOfPointsByImage.remove(((ImageForSwing)image).getNativeImage().hashCode());
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void add(Image image, int x, int y) {
		listOfAllAvailableImages.add(image);
		mapOfPointsByImage.put(((ImageForSwing)image).getNativeImage().hashCode(), new PointI(x,y));
		triggerPaint();
	}

	@Override
	public void clear() {
		listOfAllAvailableImages.clear();
		mapOfPointsByImage.clear();
		listOfVisibleHashCodes.clear();
	}

	public void triggerPaint()
	{
		repaint();
		tally++;
	}

	@Override
	public void paint(Graphics g)
	{
		Rectangle r = g.getClipBounds();
		g.clearRect(0, 0, r.width,r.height);
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, width, height);
		Iterator<Image> iter = listOfAllAvailableImages.iterator();
		while(iter.hasNext())
		{
			Image image = iter.next();
			if(listOfVisibleHashCodes.contains(((ImageForSwing)image).getNativeImage().hashCode()))
			{
				PointI p = mapOfPointsByImage.get(((ImageForSwing)image).getNativeImage().hashCode());
				g.drawImage(((ImageForSwing)image).getNativeImage(),p.getX(),p.getY(),this);
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
			 
			g.setColor(new Color(fore.r, fore.g, fore.b));
			g.fillPolygon(xPoints,yPoints,3 );
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
			 
			g.setColor(new Color(fore.r, fore.g, fore.b));
			g.fillPolygon(xPoints,yPoints,3 ); 
		}

		tally=0;
	}

	@Override
	public int getImageWidth(Image image) {
		return ((ImageForSwing)image).getNativeImage().getWidth(this);
	}

	@Override
	public int getImageHeight(Image image) {
		return ((ImageForSwing)image).getNativeImage().getHeight(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		return;
	}


	// this is the one that gets called.
	@Override
	public Image createNewImageAndAddHandlers(IPlatformPackagedImage imageResource,
			LoadHandler lh, EventBus bus, String objectTextualId,
			int objectCode, int x, int y)
	{
		java.awt.Image img = ((PlatformPackagedImageForSwing)imageResource).unpack();

		ImageForSwing imageAndPos = new ImageForSwing(img, objectTextualId, this, new PointI(0,0));

		return imageAndPos;
	}

	@Override
	public void updateInventory(Inventory inventory) {
		// this gets visited every startScene
		triggerPaint();

	}

	@Override
	public void setLeftArrowVisible(boolean visible) {
		isLeftArrowVisible = visible;
		triggerPaint();
	}

	@Override
	public void setRightArrowVisible(boolean visible) {
		isRightArrowVisible = visible;
		triggerPaint();
	}

	@Override
	public void setDimensionsOfPanel(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void resetScale(Image image) {
		// TODO Auto-generated method stub
		
	}
}