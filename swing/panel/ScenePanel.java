
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


package com.github.a2g.core.swing.panel;


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
import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.PackagedImageAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.swing.factory.SwingImage;
import com.github.a2g.core.swing.factory.SwingPackagedImage;
import com.github.a2g.core.swing.mouse.SceneMouseClickHandler;
import com.github.a2g.core.swing.mouse.SceneMouseOverHandler;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.objectmodel.SceneObjectCollection;
import com.google.gwt.event.shared.EventBus;


@SuppressWarnings("serial")
public class ScenePanel
extends JPanel
implements ScenePanelAPI
, ImagePanelAPI
, ActionListener
{
	int width;
	int height;
	int tally;
	InternalAPI api;

	private Map<Integer,Point> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllVisibleImages;

	public ScenePanel(EventBus bus, InternalAPI api)
	{
		this.api = api;
		this.mapOfPointsByImage = new TreeMap<Integer, Point>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllVisibleImages = new LinkedList<Image>();
		this.width = 200;
		this.height = 200;
		this.setBounds(0,0,320,200);
		this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		this.setDoubleBuffered(true);
		tally++;

		super.addMouseListener
		(
				new SceneMouseClickHandler(bus,api)
				);

		super.addMouseMotionListener
		(
				new SceneMouseOverHandler(this, bus,api)
				);
	}




	public Image createNewImageAndAdddHandlers(
			LoadHandler lh,
			PackagedImageAPI imageResource,
			InternalAPI api,
			EventBus bus,
			int x,
			int y,
			String objectTextualId,
			short objectId)
	{

		java.awt.Image img = ((SwingPackagedImage)imageResource).unpack();

		SwingImage imageAndPos = new SwingImage(img, objectTextualId, this, new Point(x, y));

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

	void putPoint(SwingImage image, int x,int y)
	{
		mapOfPointsByImage.put(image.getNativeImage().hashCode(), new Point(x,y));
	}

	@Override
	public void setThingPosition(Image image, int x, int y) {
		if(mapOfPointsByImage.containsKey(((SwingImage)image).getNativeImage().hashCode()))
		{
			putPoint((SwingImage)image, x,y);
			triggerPaint();
		}

	}

	@Override
	public void setImageVisible(Image image, boolean visible)
	{
		boolean isIn = listOfVisibleHashCodes.contains(((SwingImage)image).getNativeImage().hashCode());
		if(visible && !isIn)
		{
			listOfVisibleHashCodes.add(((SwingImage)image).getNativeImage().hashCode());
			triggerPaint();
		}
		else if(!visible & isIn)
		{
			listOfVisibleHashCodes.remove(new Integer(((SwingImage)image).getNativeImage().hashCode()));
			triggerPaint();
		}
	}

	@Override
	public void insert(Image image, int x, int y, int before) {
		listOfAllVisibleImages.add(before,image);
		putPoint((SwingImage)image, x,y);
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		listOfAllVisibleImages.remove(((SwingImage)image).getNativeImage());
		mapOfPointsByImage.remove(((SwingImage)image).getNativeImage().hashCode());
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void add(Image image, int x, int y) {
		listOfAllVisibleImages.add(image);
		putPoint((SwingImage)image, x,y);
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
		Iterator<Image> iter = listOfAllVisibleImages.iterator();
		while(iter.hasNext())
		{
			Image image = iter.next();
			if(listOfVisibleHashCodes.contains(((SwingImage)image).getNativeImage().hashCode()))
			{
				Point p = mapOfPointsByImage.get(((SwingImage)image).getNativeImage().hashCode());
				int x = p.getX();
				//x+= image.getBoundingRect().getLeft();
				int y = p.getY();
				//y+= image.getBoundingRect().getTop();
				g.drawImage(((SwingImage)image).getNativeImage(),x,y,this);
			}
		}
		//System.out.println("printed with tally " + tally +" draws "+ draws);
		tally=0;

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
						int adjX = x - ob.getX();
						int adjY = y - ob.getY();

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
	public int getImageWidth(Image image) {
		int width =  ((SwingImage)image).getNativeImage().getWidth(this);
		return width;
	}

	@Override
	public int getImageHeight(Image image) 
	{
		int height = ((SwingImage)image).getNativeImage().getHeight(this);
		return height;
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public Image createNewImageAndAddHandlers(final LoadHandler lh,
			PackagedImageAPI imageResource, InternalAPI api, EventBus bus,
			int x, int y, String objectTextualId, short objectCode)
	{
		java.awt.Image img = ((SwingPackagedImage)imageResource).unpack();

		SwingImage imageAndPos = new SwingImage(img, objectTextualId, this, new Point(x, y));

		// to fire image loading done.
		// only gwt is asynch, we are swing which synchronous
		Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{

			@Override
			public void run() {
				lh.onLoad(null);
			}

		};
		timer.schedule(task, 1);


		return imageAndPos;
	}














}
