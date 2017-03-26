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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.objectmodel.PointFWithNeighbours;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.internal.ICommandLinePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IPackagedImage;
import com.github.a2g.core.interfaces.internal.IScenePanelFromScenePresenter;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.internal.ImagePanelAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.LogNames;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.primitive.RectF;
import com.github.a2g.core.platforms.java.ImageForJava;
import com.github.a2g.core.platforms.java.PackagedImageForJava;
import com.github.a2g.core.platforms.java.mouse.SceneMouseClickHandler;
import com.github.a2g.core.platforms.java.mouse.SceneMouseOverHandler;
import com.google.gwt.event.shared.EventBus;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


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
	boolean isRenderDiagnostics;
	IScenePresenterFromScenePanel toScene;
	ICommandLinePresenterFromSceneMouseOver toCommandLine;

	private Map<Integer,Point> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllVisibleImages; 

	private boolean speechVisible;
	private ColorEnum speechColor;
	private String speechText;
	private Rect speechRect;

	private BufferedImage bufferedImage;

	public ScenePanelForJava(EventBus bus, IScenePresenterFromScenePanel toScene, ICommandLinePresenterFromSceneMouseOver toCommandLine)
	{
		isRenderDiagnostics = false; 
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
		this.speechVisible = false;
		this.speechColor = ColorEnum.Navy;
		this.speechText = "";
		this.speechRect = null;

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
				isRenderDiagnostics = !isRenderDiagnostics;
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
		//lh.onLoad(null);

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
		render(g);
	}
	
	void render(Graphics g)
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
				int leftTopPlusX = p.getX();
				int leftTopPlusY = p.getY();


				// img - the specified image to be drawn. This method does nothing if img is null.
				// sx1 - the x coordinate of the first corner of the source rectangle.
				// sy1 - the y coordinate of the first corner of the source rectangle.
				// sx2 - the x coordinate of the second corner of the source rectangle.
				// sy2 - the y coordinate of the second corner of the source rectangle.

				// dx1 - the x coordinate of the first corner of the destination rectangle.
				// dy1 - the y coordinate of the first corner of the destination rectangle.
				// dx2 - the x coordinate of the second corner of the destination rectangle.
				// dy2 - the y coordinate of the second corner of the destination rectangle.

				// source coords: these are correct. don't change.
				int sx1 = 0;
				int sy1 = 0;
				int sx2 = getImageWidth(image);
				int sy2 = getImageHeight(image);

				// these are also correct, the real question
				// lies in what is leftTopPlusY
				// these are set with SetThingPosition
				int dx1 = (int)(leftTopPlusX)-cameraOffsetX;
				int dy1 = (int)(leftTopPlusY)-cameraOffsetY;
				int dx2 = (int)(dx1 + sx2*image.getScale());
				int dy2 = (int)(dy1 + sy2*image.getScale());
				g.drawImage(((ImageForJava)image).getNativeImage(),  dx1,  dy1,  dx2,  dy2,  sx1,  sy1,  sx2,  sy2, this);
			}
		}
		//System.out.println("printed with tally " + tally +" draws "+ draws);
		tally=0;

		// connect all the points in a big line
		List<RectF> obstacles = toScene.getObstacles();
		List<PointF> points = toScene.getBoundaryPoints();
		List<PointF> helpers = toScene.getHelperPoints();
		PointF centre = toScene.getBoundaryPointsCentre();
		List<PointF> path = toScene.getLastPath();
		Iterator<RectF> bubbles = toScene.getSpeechRects();


		if(isRenderDiagnostics && points.size()>0)
		{
			g.setColor(new Color(255,0,0));


			int size = points.size();
			PointF lastPt = points.get(size-1);

			// draw boundary points
			for(int i=0; i<size; i++)
			{
				PointF newPt = points.get(i);
				drawLine(newPt, lastPt, g);
				lastPt = newPt;
			}

			// draw obstacles
			if(true)
			{
				g.setColor(new Color(0,0,255));
				for(int i=0;i<obstacles.size();i++)
				{
					RectF rect = obstacles.get(i);
					drawLine(rect.getTopLeft(), rect.getTopRight(), g);
					drawLine(rect.getTopRight(), rect.getBottomRight(), g);
					drawLine(rect.getBottomRight(), rect.getBottomLeft(), g);
					drawLine(rect.getBottomLeft(), rect.getTopLeft(), g);
				} 

				g.setColor(new Color(0,255,255));
				while(bubbles.hasNext())
				{
					RectF rect = bubbles.next();
					drawLine(rect.getTopLeft(), rect.getTopRight(), g);
					drawLine(rect.getTopRight(), rect.getBottomRight(), g);
					drawLine(rect.getBottomRight(), rect.getBottomLeft(), g);
					drawLine(rect.getBottomLeft(), rect.getTopLeft(), g);
				} 
			}

			// draw network
			g.setColor(Color.red);
			//PointF rawStart = new PointF(0,.1); 
			//PointF rawEnd = new PointF(.9,.9);

			List<PointFWithNeighbours> verts = toScene.getLastNetworkOfConcaveVertices();
			if(verts!=null)
			{
				for(int i=0;i<verts.size();i++)
				{
					PointFWithNeighbours a = verts.get(i); 

					Iterator<PointFWithNeighbours> it = a.getNeighbours();
					while(it.hasNext())
					{
						PointFWithNeighbours b = it.next();
						drawLine(a,b,g);
					}
				}
			}

			// draw solution
			g.setColor(new Color(0,255,0));
			if(path!=null)
			{
				PointF first = path.get(0);
				for(int i=1;i<path.size();i++)
				{
					PointF second = path.get(i);
					drawLine(first,second,g);
					first = second;
				}
			}


			g.drawOval((int)(centre.getX()*width), (int)(centre.getY()*height), 3, 3);
			
			// draw helper points.
			g.setColor(new Color(255,200,200));
			for(int i=0;i<helpers.size();i++)
			{
				g.drawOval((int)(helpers.get(i).getX()*width), (int)(helpers.get(i).getY()*height), 3, 3);
				
			}
		}

		if(speechVisible && bufferedImage!=null)
		{	
			g.drawImage(bufferedImage, speechRect.getLeft(), speechRect.getTop(), this);

		}


	}
	void drawLine(PointF newPt, PointF lastPt, Graphics g)
	{
		int width = getWidth();
		int height = getHeight();
		double newX = newPt.getX()*width;
		double newY = newPt.getY()*height;
		double lastX = lastPt.getX()*width;
		double lastY = lastPt.getY()*height;
		g.drawLine((int)newX, (int)newY, (int)lastX, (int)lastY);
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
				//System.out.println(ob.getTextualId() + ob.getDrawingOrder());
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
			String speech, Rect pixels, Point mouth, TalkPerformer sayAction) 
	{
		this.speechVisible = isVisible;
		this.speechColor = talkingColor;
		this.speechText = speech;
		this.speechRect = pixels;

		updateSpeechImage();

	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	void updateSpeechImage()
	{
		if(speechRect.getWidth()<=0)
			return;
		bufferedImage = new BufferedImage(
				speechRect.getWidth(),
				speechRect.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D imageGraphics = bufferedImage.createGraphics();
		GradientPaint gp = new GradientPaint(
				20f,
				20f,
				Color.white,
				380f,
				280f,
				Color.white);
		imageGraphics.setPaint(gp);
		imageGraphics.fillRect(0, 0, speechRect.getWidth(), speechRect.getHeight());

		String html = "<html><body style='padding: 4px;"
		//+"height: "+speechRect.getHeight()+"px;"
        +"width: "+(speechRect.getWidth()*.7)+"px; '>"
		+ speechText;
		JLabel textLabel = new JLabel(html);
		//Dimension size = textLabel.getPreferredSize();
		Dimension size = new Dimension(speechRect.getWidth(), speechRect.getHeight() );
		textLabel.setSize(size);

		Dimension d = new Dimension(speechRect.getWidth(), speechRect.getHeight() );
		if(d.width>0)
		{
			BufferedImage bi = new BufferedImage(
					d.width,
					d.height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.createGraphics();
			g.setColor(new Color(255, 255, 255, 128));//white, semi-transparent

			g.fillRoundRect(
					0,
					0,
					this.speechRect.getWidth(),
					this.speechRect.getHeight(),
					10,
					10);
			g.setColor(Color.black);
			textLabel.paint(g);
			Graphics g2 = bufferedImage.getGraphics();
			//Rect r = speechRect;
			//g2.drawImage(bi, r.getLeft(), r.getTop(), r.getRight(), r.getBottom(), this);
			g2.drawImage(bi, 0, 0, this);
			//g2.setColor(Color.white);
			//g2.fillRect(speechRect.getLeft(), speechRect.getTop(), speechRect.getWidth()-1, speechRect.getHeight());
			g2.setColor(new Color(speechColor.r, speechColor.g, speechColor.b));
			g2.drawRect(0+1, 0+1, speechRect.getWidth()-3, speechRect.getHeight()-3);
			g2.drawRect(0, 0, speechRect.getWidth()-1, speechRect.getHeight()-1);
			
			//ImageIcon ii = new ImageIcon(bufferedImage);
			//JLabel imageLabel = new JLabel(ii);
			//this.add(imageLabel);
		}
		/*
		g.setColor(Color.white);
		g.fillRect(speechRect.getLeft(), speechRect.getTop(), speechRect.getWidth()-1, speechRect.getHeight());
		g.setColor(new Color(speechColor.r, speechColor.g, speechColor.b));
		g.drawRect(speechRect.getLeft()+1, speechRect.getTop()+1, speechRect.getWidth()-3, speechRect.getHeight()-2);
		g.drawRect(speechRect.getLeft(), speechRect.getTop(), speechRect.getWidth()-1, speechRect.getHeight());
		g.setFont(new Font("Arial",Font.BOLD,12));
		g.drawString(speechText, speechRect.getLeft()+4, speechRect.getTop()+speechRect.getHeight()/2);
		//g.getFontMetrics()
		 */
	}



	public void setIsDiagnosticsDisplay(boolean isDisplayed)
	{
		isRenderDiagnostics = isDisplayed;
	}
	
	public boolean getIsDiagnosticsDisplay()
	{
		return isRenderDiagnostics;
	}

	public void saveToJPEG(String filename) {
		int w = this.getWidth();
	    int h = this.getHeight();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    render(g);
	    try {
	        OutputStream out = new FileOutputStream(filename+".jpeg");
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	        encoder.encode(bi);
	        out.close();
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}

	@Override
	public void onSceneEntry(String name) {
		//String path = "c:/";
		this.saveToJPEG("c:/"+name);
	}





}
