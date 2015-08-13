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
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.github.a2g.core.objectmodel.Image;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.ICommandLinePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.IPackagedImage;
import com.github.a2g.core.interfaces.IScenePanelFromScenePresenter;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.LogNames;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.platforms.java.ImageForJava;
import com.github.a2g.core.platforms.java.PackagedImageForJava;
import com.github.a2g.core.platforms.java.mouse.SceneMouseClickHandler;
import com.github.a2g.core.platforms.java.mouse.SceneMouseOverHandler;
import com.google.gwt.event.shared.EventBus;


@SuppressWarnings("serial")
public class ScenePanelForJava
extends JPanel
implements IScenePanelFromScenePresenter
, ImagePanelAPI
, ActionListener
{
	private static final Logger IMAGE_DUMP = Logger.getLogger(LogNames.IMAGE_DUMP);
	
	int width;
	int height;
	int tally;
	int cameraOffsetX;
	int cameraOffsetY;
	boolean isRenderBoundary;
	IScenePresenterFromScenePanel toScene;
	ICommandLinePresenterFromSceneMouseOver toCommandLine;

	private Map<Integer,Point> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllVisibleImages;
	private PopupPanelForJava speechPopup;

	public ScenePanelForJava(EventBus bus, IScenePresenterFromScenePanel toScene, ICommandLinePresenterFromSceneMouseOver toCommandLine)
	{
		isRenderBoundary = true;
		this.speechPopup = new PopupPanelForJava(toScene);
		this.toScene = toScene;
		this.toCommandLine = toCommandLine;
		this.mapOfPointsByImage = new TreeMap<Integer, Point>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllVisibleImages = new LinkedList<Image>();
		this.width = 200;
		this.height = 200;
		this.setBounds(0,0,320,200);
		this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		this.setDoubleBuffered(true);
		cameraOffsetX=0;
		cameraOffsetY=0;
		tally++;

		super.addMouseListener
		(
				new SceneMouseClickHandler(bus,toScene)
				);

		super.addMouseMotionListener
		(
			new SceneMouseOverHandler(this, bus, toScene, toCommandLine)
		);
		
		InputMap im = getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "onEnter");

        am.put("onEnter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	isRenderBoundary = !isRenderBoundary;
            	// this is only hit with a setfocus in paint, ie:
            	// public void paint(Graphics g)
            	//{
            	//  this.requestFocus();
            	ListIterator<Image> im = listOfAllVisibleImages.listIterator();
            	 
                while(im.hasNext())
                {
                	Image i = im.next();
                	IMAGE_DUMP.log(Level.FINE, "image" +i.getAtid());
                }
            }
        });
	}




	public Image createNewImageAndAdddHandlers(
			LoadHandler lh,
			IPackagedImage imageResource,
			EventBus bus,
			int x,
			int y,
			String objectTextualId,
			short objectId)
	{

		java.awt.Image img = ((PackagedImageForJava)imageResource).unpack();

		ImageForJava imageAndPos = new ImageForJava(img, objectTextualId, this, new Point(x, y));

		// to fire image loading done.
		lh.onLoad(null);

		return imageAndPos;
	}


	@Override
	public void setScenePixelSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		super.setSize(width, height);
	}


	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(this.width,this.height);
	}

	void putPoint(ImageForJava image, int x,int y)
	{
		mapOfPointsByImage.put(hash(image), new Point(x,y));
	}

	@Override
	public void setThingPosition(Image image, int x, int y) {
		if(mapOfPointsByImage.containsKey(hash(image)))
		{
			putPoint((ImageForJava)image, x,y);
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
		putPoint((ImageForJava)image, x,y);
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		listOfAllVisibleImages.remove(((ImageForJava)image).getNativeImage());
		mapOfPointsByImage.remove(hash(image));
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void add(Image image, int x, int y) {
		listOfAllVisibleImages.add(image);
		putPoint((ImageForJava)image, x,y);
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

	Integer hash(Image image)
	{
		int ocode= ((ImageForJava)image).getNativeImage().hashCode();
		return new Integer(ocode);
	}

	@Override
	public void paint(Graphics g)
	{
		//this.requestFocus();
		g.clearRect(0, 0, width, height);
		Iterator<Image> iter = listOfAllVisibleImages.iterator();
		while(iter.hasNext())
		{
			Image image = iter.next();
			if(listOfVisibleHashCodes.contains(hash(image)))
			{
				Point p = mapOfPointsByImage.get(hash(image));
				int x = p.getX();
				int y = p.getY();

				g.drawImage(((ImageForJava)image).getNativeImage(),(int)(x-cameraOffsetX*image.getParallaxX()),(int)(y-cameraOffsetY*image.getParallaxY()),this);
			}
		}
		//System.out.println("printed with tally " + tally +" draws "+ draws);
		tally=0;
		
		if(isRenderBoundary)
		{
			g.setColor(new Color(255,0,0));
			int size = toScene.getBoundaryPoints().size();
			
			// gate vs point: gate adds 2 valid to array, point adds 1 dummy, then 1 valid
			// ...so last point is always real. doesn't need checking.
			double lastX = toScene.getBoundaryPoints().get(size-1).getX()*width;
			double lastY = toScene.getBoundaryPoints().get(size-1).getY()*height;
			double maxX = lastX;
			double minX = lastX;
			double maxY = lastY;
			double minY = lastY;
			for(int i=0; i<size; i++)
			{
				double newX = toScene.getBoundaryPoints().get(i).getX()*width;
				double newY = toScene.getBoundaryPoints().get(i).getY()*height;
				
				
				if(newX<0)
					continue;
				maxX = Math.max(maxX, newX);
				maxY = Math.max(maxY, newY);
				
				minX = Math.min(minX, newX);
				minY = Math.min(minY, newY);
				g.drawLine((int)newX, (int)newY, (int)lastX, (int)lastY);
				lastX = newX;
				lastY = newY;
			}
			
			PointF c = toScene.getBoundaryPointsCentre();
			g.drawOval((int)(c.getX()*width), (int)(c.getY()*height), 3, 3);
			
			// if the green center is better, then replace the impl
			// of getBoundaryPointsCentre with max/min maths from here
			g.setColor(new Color(0,255,0));
			double x2 = maxX/2 + minX/2;
			double y2 = maxY/2 + minY/2;
			g.drawOval((int)x2, (int)y2, 4, 4);
			
		}

	}

	public String getObjectUnderMouse(int x, int y)
	{
		//System.out.println( "----------------");
		int count = toScene.getSceneObjectCount();
		for(int i = count-1;i>=0;i--)
		{
			String otid = toScene.getOtidByIndex(i);
			if(toScene.getVisibleByOtid(otid))
			{
				//System.out.println(ob.getTextualId() + ob.getNumberPrefix());
				int frame = toScene.getCurrentFrameByOtid(otid);
				String atid = toScene.getAtidOfCurrentAnimationByOtid(otid);
				Rect rect = toScene.getBoundingRectByFrameAndAtid(frame,atid);
				int obx = (int)toScene.getXByOtid(otid);
				int oby = (int)toScene.getYByOtid(otid);

				int adjX = x - obx + cameraOffsetX;
				int adjY = y - oby + cameraOffsetY;

				if(rect.contains(adjX, adjY))
				{
					return otid;
				}
			}
		}

		return "";
	}

	@Override
	public int getImageWidth(Image image) {
		int width =  ((ImageForJava)image).getNativeImage().getWidth(this);
		return width;
	}

	@Override
	public int getImageHeight(Image image)
	{
		int height = ((ImageForJava)image).getNativeImage().getHeight(this);
		return height;
	}


	@Override
	public Image createNewImageAndAddHandlers(final LoadHandler lh,
			IPackagedImage imageResource, IScenePresenterFromSceneMouseOver api, EventBus bus,
			int x, int y, String objectTextualId, short objectCode)
	{
		java.awt.Image img = ((PackagedImageForJava)imageResource).unpack();

		ImageForJava imageAndPos = new ImageForJava(img, objectTextualId, this, new Point(x, y));
		return imageAndPos;
	}

	@Override
	public void setCameraOffset(int x, int y)
	{
		this.cameraOffsetX = x;
		this.cameraOffsetY = y;
	}



	public Point getTopLeft() {
		return new Point(0,0);
		//return new Point(this.getLocationOnScreen().x,this.getLocationOnScreen().y );
	}

	@Override
	public void setStateOfPopup(boolean isVisible, ColorEnum talkingColor,
			String speech, Rect pixels, Point mouth, TalkPerformer sayAction) {
		
		this.speechPopup.setVisible(isVisible);
		this.speechPopup.setColor(talkingColor);
		this.speechPopup.setText(speech);
		this.speechPopup.setPopupPosition(pixels.getLeft(), pixels.getTop());
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}










}
