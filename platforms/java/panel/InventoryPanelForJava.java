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


package com.github.a2g.core.platforms.java.panel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.github.a2g.core.interfaces.IInventoryPanelFromInventoryPresenter;
import com.github.a2g.core.objectmodel.Image;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.IInventoryPresenterFromInventoryPanel;
import com.github.a2g.core.interfaces.IPackagedImage;
import com.github.a2g.core.objectmodel.Inventory;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.platforms.java.ImageForJava;
import com.github.a2g.core.platforms.java.PackagedImageForJava;
import com.github.a2g.core.platforms.java.mouse.InventoryMouseClickHandler;
import com.github.a2g.core.platforms.java.mouse.InventoryMouseOverHandler;
import com.google.gwt.event.shared.EventBus;

@SuppressWarnings("serial")
public class InventoryPanelForJava
extends JPanel
implements ImagePanelAPI
, IInventoryPanelFromInventoryPresenter
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

	private Map<Integer,Point> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllVisibleImages;

	ImageForJava imgLeft;
	ImageForJava imgRight;

	public InventoryPanelForJava(EventBus bus, IInventoryPresenterFromInventoryPanel api2, ColorEnum fore, ColorEnum back)
	{
		this.mapOfPointsByImage = new TreeMap<Integer, Point>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllVisibleImages = new LinkedList<Image>();
		this.width = 200;
		this.height = 200;
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

		try
		{
			java.awt.Image rawLeft = ImageIO.read(new File("E:\\a2g_core\\res\\leftArrow.png"));
			java.awt.Image rawRight = ImageIO.read(new File("E:\\a2g_core\\res\\leftArrow.png"));

			imgLeft = new ImageForJava(rawLeft, "", this, new Point(0,0));
			imgRight = new ImageForJava(rawRight, "", this, new Point(0,0));

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(width,height);
	}

	@Override
	public void setThingPosition(Image image, int x, int y) {
		if(mapOfPointsByImage.containsKey(((ImageForJava)image).getNativeImage().hashCode()))
		{
			mapOfPointsByImage.put(((ImageForJava)image).getNativeImage().hashCode(), new Point(x,y));
			triggerPaint();
		}
	}

	@Override
	public void setImageVisible(Image image, boolean visible)
	{
		boolean isIn = listOfVisibleHashCodes.contains(((ImageForJava)image).getNativeImage().hashCode());
		if(visible && !isIn)
		{
			listOfVisibleHashCodes.add(((ImageForJava)image).getNativeImage().hashCode());
			triggerPaint();
		}
		else if(!visible & isIn)
		{
			listOfVisibleHashCodes.remove(new Integer(((ImageForJava)image).getNativeImage().hashCode()));
			triggerPaint();
		}
	}

	@Override
	public void insert(Image image, int x, int y, int before) {
		listOfAllVisibleImages.add(before,image);
		mapOfPointsByImage.put(((ImageForJava)image).getNativeImage().hashCode(), new Point(x,y));
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		listOfAllVisibleImages.remove(((ImageForJava)image).getNativeImage());
		mapOfPointsByImage.remove(((ImageForJava)image).getNativeImage().hashCode());
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void add(Image image, int x, int y) {
		listOfAllVisibleImages.add(image);
		mapOfPointsByImage.put(((ImageForJava)image).getNativeImage().hashCode(), new Point(x,y));
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
		repaint();
		tally++;
	}

	@Override
	public void paint(Graphics g)
	{
		g.clearRect(0, 0, width, height);
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, width, height);
		Iterator<Image> iter = listOfAllVisibleImages.iterator();
		while(iter.hasNext())
		{
			Image image = iter.next();
			if(listOfVisibleHashCodes.contains(((ImageForJava)image).getNativeImage().hashCode()))
			{
				Point p = mapOfPointsByImage.get(((ImageForJava)image).getNativeImage().hashCode());
				g.drawImage(((ImageForJava)image).getNativeImage(),p.getX(),p.getY(),this);
			}
		}

		if(listOfVisibleHashCodes.contains(imgLeft.getNativeImage().hashCode()))
		{
			g.drawImage(imgLeft.getNativeImage(),0,0,this);
		}
		if(listOfVisibleHashCodes.contains(imgRight.getNativeImage().hashCode()))
		{
			g.drawImage(imgRight.getNativeImage(),84,0,this);
		}

		//System.out.println("printed with tally " + tally +" draws "+ draws);
		tally=0;
	}

	@Override
	public int getImageWidth(Image image) {
		return ((ImageForJava)image).getNativeImage().getWidth(this);
	}

	@Override
	public int getImageHeight(Image image) {
		return ((ImageForJava)image).getNativeImage().getHeight(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		return;
	}


	// this is the one that gets called.
	@Override
	public Image createNewImageAndAdddHandlers(IPackagedImage imageResource,
			LoadHandler lh, EventBus bus, String objectTextualId,
			int objectCode, int x, int y)
	{
		java.awt.Image img = ((PackagedImageForJava)imageResource).unpack();

		ImageForJava imageAndPos = new ImageForJava(img, objectTextualId, this, new Point(0,0));

		// to fire image loading done.
		// only gwt is asynch, we are swing which synchronous
		lh.onLoad(null);

		return imageAndPos;
	}

	@Override
	public void updateInventory(Inventory inventory) {
		// this gets visited every startScene
		triggerPaint();

	}

	@Override
	public void setScenePixelSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		super.setSize(width, height);
	}

	@Override
	public void setLeftArrowVisible(boolean visible) {
		imgLeft.setVisible(visible, new Point(0,0));
		triggerPaint();
	}

	@Override
	public void setRightArrowVisible(boolean visible) {
		imgRight.setVisible(visible, new Point(50,0));
		triggerPaint();
	}

	@Override
	public void setInventoryImageSize(int width, int height) {
		
	}
}