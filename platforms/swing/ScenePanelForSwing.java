/*
 `* Copyright 2012 Anthony Cassidy
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
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.objectmodel.PointFWithNeighbours;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.internal.ICommandLinePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IScenePanelFromScenePresenter;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.platform.IPlatformPackagedImage;
import com.github.a2g.core.interfaces.internal.IImagePanel;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.LogNames;
import com.github.a2g.core.primitive.PointI;
import com.google.gwt.touch.client.Point;
import com.github.a2g.core.primitive.RectI;
import com.github.a2g.core.primitive.SpeechBubble;
import com.github.a2g.core.primitive.RectF;
import com.github.a2g.core.platforms.swing.dependencies.ImageForSwing;
import com.github.a2g.core.platforms.swing.dependencies.CanvasEtcSwing;
import com.github.a2g.core.platforms.swing.dependencies.PlatformFontCallsSwing;
import com.github.a2g.core.platforms.swing.dependencies.PlatformPackagedImageForSwing;
import com.github.a2g.core.platforms.swing.mouse.SceneMouseClickHandler;
import com.github.a2g.core.platforms.swing.mouse.SceneMouseOverHandler;
import com.google.gwt.event.shared.EventBus;

@SuppressWarnings("serial")
public class ScenePanelForSwing extends JPanel implements IScenePanelFromScenePresenter, IImagePanel, ActionListener {
	private static final Logger IMAGE_DUMP = Logger.getLogger(LogNames.IMAGE_DUMP.toString());

	int width;
	int height;
	int tally;
	int cameraOffsetX;
	int cameraOffsetY;
	boolean isRenderDiagnostics;
	IScenePresenterFromScenePanel toScene;
	ICommandLinePresenterFromSceneMouseOver toCommandLine;

	private Map<Integer, PointI> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllVisibleImages;

	private boolean isSpeechVisible;
	CanvasEtcSwing speechCanvas;
	int fontHeight;
	String fontName;

	public ScenePanelForSwing(EventBus bus, IScenePresenterFromScenePanel toScene,
			ICommandLinePresenterFromSceneMouseOver toCommandLine) {
		isRenderDiagnostics = false;

		this.fontHeight = 26;
		this.fontName = "arial";
		this.toScene = toScene;
		this.toCommandLine = toCommandLine;
		this.mapOfPointsByImage = new TreeMap<Integer, PointI>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllVisibleImages = new LinkedList<Image>();
		this.width = 200;
		this.height = 200;
		this.setBounds(0, 0, 320, 200);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.setDoubleBuffered(true);
		this.speechCanvas = new CanvasEtcSwing();

		cameraOffsetX = 0;
		cameraOffsetY = 0;
		tally++;

		super.addMouseListener(new SceneMouseClickHandler(bus, toScene));

		super.addMouseMotionListener(new SceneMouseOverHandler(this, bus, toScene, toCommandLine));

		InputMap im = getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "onEnter");

		am.put("onEnter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isRenderDiagnostics = !isRenderDiagnostics;
				// this is only hit with a setfocus in paint, ie:
				// public void paint(Graphics g)
				// {
				// this.requestFocus();
				ListIterator<Image> im = listOfAllVisibleImages.listIterator();

				while (im.hasNext()) {
					Image i = im.next();
					IMAGE_DUMP.log(Level.FINE, "image" + i.getAtid());
				}
			}
		});
	}

	public Image createNewImageAndAdddHandlers(LoadHandler lh, IPlatformPackagedImage imageResource, EventBus bus, int x, int y,
			String objectTextualId, short objectId) {

		java.awt.Image img = ((PlatformPackagedImageForSwing) imageResource).unpack();

		ImageForSwing imageAndPos = new ImageForSwing(img, objectTextualId, this, new PointI(x, y));

		return imageAndPos;
	}

	@Override
	public void setScenePixelSize(int width, int height) {
		this.width = width;
		this.height = height;
		speechCanvas.setScenePixelSize(width, height);
		super.setSize(width, height);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.width, this.height);
	}

	void putPoint(ImageForSwing image, int x, int y) {
		mapOfPointsByImage.put(hash(image), new PointI(x, y));
	}

	@Override
	public void setThingPosition(Image image, int x, int y, double scale) {
		if (mapOfPointsByImage.containsKey(hash(image))) {
			putPoint((ImageForSwing) image, x, y);
			triggerPaint();
		}

	}

	@Override
	public void setImageVisible(Image image, boolean visible) {
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
	public void insert(Image image, int x, int y, int before) {
		listOfAllVisibleImages.add(before, image);
		putPoint((ImageForSwing) image, x, y);
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		listOfAllVisibleImages.remove(((ImageForSwing) image).getNativeImage());
		mapOfPointsByImage.remove(hash(image));
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void add(Image image, int x, int y) {
		listOfAllVisibleImages.add(image);
		putPoint((ImageForSwing) image, x, y);
		triggerPaint();
	}

	@Override
	public void clear() {
		listOfAllVisibleImages.clear();
		mapOfPointsByImage.clear();
		listOfVisibleHashCodes.clear();
		this.isSpeechVisible = false; 
	}

	public void triggerPaint() {
		repaint();
		tally++;
	}

	Integer hash(Image image) {
		int ocode = ((ImageForSwing) image).getNativeImage().hashCode();
		return new Integer(ocode);
	}

	@Override
	public void paint(Graphics g) {
		render(g);
	}

	void render(Graphics g) {
		// this.requestFocus();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, width, height);
		Iterator<Image> iter = listOfAllVisibleImages.iterator();
		while (iter.hasNext()) {
			Image image = iter.next();
			if (listOfVisibleHashCodes.contains(hash(image))) {
				PointI p = mapOfPointsByImage.get(hash(image));
				int leftTopPlusX = p.getX();
				int leftTopPlusY = p.getY();

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
				int sx1 = 0;
				int sy1 = 0;
				int sx2 = getImageWidth(image);
				int sy2 = getImageHeight(image);

				// these are also correct, the real question
				// lies in what is leftTopPlusY
				// these are set with SetThingPosition
				int dx1 = (int) (leftTopPlusX) - cameraOffsetX;
				int dy1 = (int) (leftTopPlusY) - cameraOffsetY;
				int dx2 = (int) (dx1 + sx2 * image.getScale());
				int dy2 = (int) (dy1 + sy2 * image.getScale());
				g.drawImage(((ImageForSwing) image).getNativeImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
			}
		}
		// System.out.println("printed with tally " + tally +" draws "+ draws);
		tally = 0;

		// connect all the points in a big line
		List<RectF> obstacles = toScene.getObstacles();
		List<Point> points = toScene.getBoundaryPoints();
		List<Point> helpers = toScene.getHelperPoints();
		Point centre = toScene.getBoundaryPointsCentre();
		List<Point> path = toScene.getLastPath();
		Iterator<RectF> bubbles = toScene.getHeadRectangles();

		if (isRenderDiagnostics && points.size() > 0) {
			g.setColor(new Color(255, 0, 0));

			int size = points.size();
			Point lastPt = points.get(size - 1);

			// draw boundary points
			for (int i = 0; i < size; i++) {
				Point newPt = points.get(i);
				drawLine(newPt, lastPt, g);
				lastPt = newPt;
			}

			// draw obstacles
			if (true) {
				g.setColor(new Color(0, 0, 255));
				for (int i = 0; i < obstacles.size(); i++) {
					RectF rect = obstacles.get(i);
					drawLine(rect.getTopLeft(), rect.getTopRight(), g);
					drawLine(rect.getTopRight(), rect.getBottomRight(), g);
					drawLine(rect.getBottomRight(), rect.getBottomLeft(), g);
					drawLine(rect.getBottomLeft(), rect.getTopLeft(), g);
				}

				g.setColor(new Color(0, 255, 255));
				while (bubbles.hasNext()) {
					RectF rect = bubbles.next();
					drawLine(rect.getTopLeft(), rect.getTopRight(), g);
					drawLine(rect.getTopRight(), rect.getBottomRight(), g);
					drawLine(rect.getBottomRight(), rect.getBottomLeft(), g);
					drawLine(rect.getBottomLeft(), rect.getTopLeft(), g);
				}
			}

			// draw network
			g.setColor(Color.red);
			// Point rawStart = new Point(0,.1);
			// Point rawEnd = new Point(.9,.9);

			List<PointFWithNeighbours> verts = toScene.getLastNetworkOfConcaveVertices();
			if (verts != null) {
				for (int i = 0; i < verts.size(); i++) {
					PointFWithNeighbours a = verts.get(i);

					Iterator<PointFWithNeighbours> it = a.getNeighbours();
					while (it.hasNext()) {
						PointFWithNeighbours b = it.next();
						drawLine(a, b, g);
					}
				}
			}

			// draw solution
			g.setColor(new Color(0, 255, 0));
			if (path != null) {
				Point first = path.get(0);
				for (int i = 1; i < path.size(); i++) {
					Point second = path.get(i);
					drawLine(first, second, g);
					first = second;
				}
			}

			g.drawOval((int) (centre.getX() * width), (int) (centre.getY() * height), 3, 3);

			// draw helper points.
			g.setColor(new Color(255, 200, 200));
			for (int i = 0; i < helpers.size(); i++) {
				g.drawOval((int) (helpers.get(i).getX() * width), (int) (helpers.get(i).getY() * height), 3, 3);

			}
		}

		if (isSpeechVisible && speechCanvas.getImage() != null) {
			g.drawImage(speechCanvas.getImage(), 0, 0, this);
		}

	}

	void drawLine(Point newPt, Point lastPt, Graphics g) {
		int width = getWidth();
		int height = getHeight();
		double newX = newPt.getX() * width;
		double newY = newPt.getY() * height;
		double lastX = lastPt.getX() * width;
		double lastY = lastPt.getY() * height;
		g.drawLine((int) newX, (int) newY, (int) lastX, (int) lastY);
	}

	public String getObjectUnderMouse(int x, int y) {
		// System.out.println( "----------------");
		int count = toScene.getSceneObjectCount();
		for (int i = count - 1; i >= 0; i--) {
			String otid = toScene.getOtidByIndex(i);
			if (toScene.getVisibleByOtid(otid)) {
				// System.out.println(ob.getTextualId() + ob.getDrawingOrder());
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

		return "";
	}

	@Override
	public int getImageWidth(Image image) {
		int width = ((ImageForSwing) image).getNativeImage().getWidth(this);
		return width;
	}

	@Override
	public int getImageHeight(Image image) {
		int height = ((ImageForSwing) image).getNativeImage().getHeight(this);
		return height;
	}

	@Override
	public Image createNewImageAndAddHandlers(final LoadHandler lh, IPlatformPackagedImage imageResource,
			IScenePresenterFromSceneMouseOver api, EventBus bus, int x, int y, String objectTextualId,
			short objectCode) {
		java.awt.Image img = ((PlatformPackagedImageForSwing) imageResource).unpack();

		ImageForSwing imageAndPos = new ImageForSwing(img, objectTextualId, this, new PointI(x, y));
		return imageAndPos;
	}

	@Override
	public void setCameraOffset(int x, int y) {
		this.cameraOffsetX = x;
		this.cameraOffsetY = y;
	}

	public PointI getTopLeft() {
		return new PointI(0, 0);
		// return new
		// Point(this.getLocationOnScreen().x,this.getLocationOnScreen().y );
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void setIsDiagnosticsDisplay(boolean isDisplayed) {
		isRenderDiagnostics = isDisplayed;
	}

	public boolean getIsDiagnosticsDisplayed() {
		return isRenderDiagnostics;
	}

	public void saveToJPEG(String filename) {
		int w = this.getWidth();
		int h = this.getHeight();
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		render(g);
		try {
			// OutputStream out = new FileOutputStream(filename + ".jpeg");
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// encoder.encode(bi);
			// out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void onSceneEntry(String name) {
		// String path = "c:/";
		this.saveToJPEG("c:/" + name);
	}

	@Override
	public void resetScale(Image image) {
		// TODO Auto-generated method stub

	}

	@Override
	public Point measureTextWidthAndHeight(String text) {
		PlatformFontCallsSwing fm = new PlatformFontCallsSwing(speechCanvas.getGraphics());
		return fm.measureTextWidthAndHeight(text);
	}

	private void setFontNameAndHeight(String name, int height) {
		PlatformFontCallsSwing fm = new PlatformFontCallsSwing(speechCanvas.getGraphics());
		fm.setFontNameAndHeight(name, height);
	}

	@Override
	public void incrementFont() {
		fontHeight++;
		setFontNameAndHeight(fontName, fontHeight);
	}

	@Override
	public void decrementFont() {
		fontHeight--;
		setFontNameAndHeight(fontName, fontHeight);
	}

	@Override
	public void setTitleCard(String titlecard) {
		this.isSpeechVisible = (titlecard.length() > 0); 
 
		String[] splitByNewline = titlecard.split("\n");
		ArrayList<SpeechBubble> pages = SpeechBubble.calculateWordWrappedPages(new PointI(width, height),
				splitByNewline, this, new RectI(318, 178, 4, 4));
		pages.get(0).rectBubble = new RectI(0, 0, width, height);
		pages.get(0).yPoints = new int[0];
		this.speechCanvas.draw(pages.get(0), ColorEnum.Red, ColorEnum.Black, new PointI(width, height),
				this.getIsDiagnosticsDisplayed());
	}

	@Override
	public void setStateOfSpeech(boolean isVisible, ColorEnum speechColor, ColorEnum backgroundColor,
			SpeechBubble rectAndLeaderLine, TalkPerformer sayAction) {
		this.isSpeechVisible = isVisible;
		 if(isVisible==false)
		 {
			 if(isVisible){};
		 }

		if (rectAndLeaderLine == null)
			return;

		if (rectAndLeaderLine.rectBubble.getWidth() <= 0)
			return;

		this.speechCanvas.draw(rectAndLeaderLine, speechColor, ColorEnum.White, new PointI(width, height),
				this.getIsDiagnosticsDisplayed());

	}

}
