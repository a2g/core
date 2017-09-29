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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.TextMetrics;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadHandler;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.internal.ICommandLinePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IPackagedImage;
import com.github.a2g.core.interfaces.internal.IScenePanelFromScenePresenter;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.internal.ImagePanelAPI;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.platforms.html4.PackagedImageForHtml4;
import com.github.a2g.core.platforms.html4.dependencies.ImageForHtml4;
import com.github.a2g.core.platforms.html4.dependencies.SpeechBalloonCalculatorForHtml4;
import com.github.a2g.core.platforms.html4.mouse.ImageMouseClickHandler;
import com.github.a2g.core.platforms.html4.mouse.SceneObjectMouseOverHandler;
import com.github.a2g.core.platforms.html5.dependencies.ContextRealHtml5;
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

public class ScenePanelForHtml5 extends VerticalPanel implements ImagePanelAPI, IScenePanelFromScenePresenter {
	// private static final Logger HTML5CANVAS =
	// Logger.getLogger(LogNames.HTML5CANVAS);

	// private EventBus bus;
	private AbsolutePanel abs;
	private int cameraOffsetX;
	private int cameraOffsetY;

	private IScenePresenterFromScenePanel toScene;
	// private ICommandLineFromSceneMouseOver toCommandLine;

	@SuppressWarnings("unused")
	private final CssColor redrawColor = CssColor.make("rgba(255,0,0,0.6)");
	private Map<Integer, Point> mapOfPointsByImage;
	private LinkedList<Integer> listOfVisibleHashCodes;
	private LinkedList<Image> listOfAllVisibleImages;
	private boolean speechVisible;
	private ColorEnum speechColor;
	private String speechText;
	private Rect speechMaxRect;
	private ContextRealHtml5 canvas;

	public ScenePanelForHtml5(EventBus bus, IScenePresenterFromScenePanel toScene,
			ICommandLinePresenterFromSceneMouseOver toCommandLine) {
		// this.bus = bus;
		this.getElement().setId("cwAbsolutePanel");
		this.addStyleName("absolutePanel");
		this.cameraOffsetX = 0;
		this.cameraOffsetY = 0;
		this.abs = new AbsolutePanel();
		this.toScene = toScene;
		// this.toCommandLine = toCommandLine;
		this.mapOfPointsByImage = new TreeMap<Integer, Point>();
		this.listOfVisibleHashCodes = new LinkedList<Integer>();
		this.listOfAllVisibleImages = new LinkedList<Image>();
		canvas = new ContextRealHtml5("");
		canvas.addMouseMoveHandler(new SceneMouseOverHandler(this, bus, toScene, toCommandLine));
		canvas.addClickHandler(new SceneMouseClickHandler(bus, canvas));
		
		this.add(abs);
	}

	void putPoint(ImageForHtml4 image, int x, int y) {
		mapOfPointsByImage.put(hash(image), new Point(x, y));
	}

	@Override
	public Image createNewImageAndAddHandlers(LoadHandler lh, IPackagedImage imageResource,
			IScenePresenterFromSceneMouseOver api, EventBus bus, int x, int y, String objectTextualId,
			short objectCode) {
		com.google.gwt.user.client.ui.Image image = Image.getImageFromResource((PackagedImageForHtml4) imageResource,
				lh);

		ImageForHtml4 imageAndPos = new ImageForHtml4(image, this, new Point(x, y));

		// add gwt mouse handlers
		imageAndPos.getNativeImage()
				.addMouseMoveHandler(new SceneObjectMouseOverHandler(bus, api, objectTextualId, objectCode));

		imageAndPos.getNativeImage().addClickHandler(new ImageMouseClickHandler(bus, this.abs));
		image.setVisible(false);
		return imageAndPos;
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
	}

	@Override
	public void add(Image image, int x, int y) {
		this.abs.add(((ImageForHtml4) image).getNativeImage(), x, y);

		listOfAllVisibleImages.add(image);
		putPoint((ImageForHtml4) image, x, y);
		triggerPaint();
	}

	@Override
	public void insert(Image image, int x, int y, int before) {
		this.abs.insert(((ImageForHtml4) image).getNativeImage(), x - cameraOffsetX, y - cameraOffsetY, before);

		listOfAllVisibleImages.add(before, image);
		putPoint((ImageForHtml4) image, x, y);
		triggerPaint();
	}

	@Override
	public void remove(Image image) {
		this.abs.remove(((ImageForHtml4) image).getNativeImage());
		listOfAllVisibleImages.remove(((ImageForHtml4) image).getNativeImage());
		mapOfPointsByImage.remove(hash(image));
		setImageVisible(image, false);
		triggerPaint();
	}

	@Override
	public void setThingPosition(Image image, int left, int top, double scale) {
		this.abs.setWidgetPosition(((ImageForHtml4) image).getNativeImage(), left - cameraOffsetX, top - cameraOffsetY);
		if (mapOfPointsByImage.containsKey(hash(image))) {
			putPoint((ImageForHtml4) image, left, top);
			triggerPaint();
		}
	}

	@Override
	public int getImageHeight(Image image) {
		return ((ImageForHtml4) image).getNativeImage().getHeight();
	}

	@Override
	public int getImageWidth(Image image) {
		return ((ImageForHtml4) image).getNativeImage().getWidth();
	}

	@Override
	public void setScenePixelSize(int width, int height) {

		this.setSize("" + width + "px", "" + height + "px");

	}

	@Override
	public void setCameraOffset(int x, int y) {
		this.cameraOffsetX = x;
		this.cameraOffsetY = y;
	}

	public void triggerPaint() {
		paint();
	}

	Integer hash(Image image) {
		int ocode = ((ImageForHtml4) image).getNativeImage().hashCode();
		return new Integer(ocode);
	}

	static public Rect getRectGivenSpeechAndMaxRect(String speech, Rect maxRect, Context2d ctxt) {
		TextMetrics tm = ctxt.measureText(speech);
		double width = tm.getWidth();
		double startX = maxRect.getCenter().getX() - width / 2;

		Rect retVal = new Rect((int) startX, maxRect.getTop(), (int) width, maxRect.getHeight());
		return retVal;
	}

	public void paint() {
		Iterator<Image> iter = listOfAllVisibleImages.iterator();
		while (iter.hasNext()) {
			Image image = iter.next();
			if (listOfVisibleHashCodes.contains(hash(image))) {
				Point p = mapOfPointsByImage.get(hash(image));
				int x = p.getX();
				int y = p.getY();

				
				ImageElement imageElement = (ImageElement) (((ImageForHtml4) image).getNativeImage().getElement()
						.cast());
				canvas.translateAndDraw(x,y,imageElement);
			
			}
		}

		if (speechVisible) {
			canvas.setFillStyle(ColorEnum.White.toString());
			canvas.setFont("16px \"Times New Roman\"");
			Rect r = speechMaxRect;// getRectGivenSpeechAndMaxRect(this.speechText,
									// this.speechMaxRect, backBufferContext);
			canvas.fillRect(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight());
			canvas.setFillStyle(this.speechColor.name());
			canvas.fillText(this.speechText, r.getLeft(), r.getTop(), r.getWidth());

			int x = r.getLeft();
			int y = r.getTop();
			int w = r.getWidth();
			int h = r.getHeight();
			int lineSpacing = 3;
			int fontHeight = 10;

			canvas.setLineWidth(1);
			canvas.setStrokeStyle("black");
			canvas.strokeRect(x, y, w, h);

			// Paint text
			ArrayList<String> lines = SpeechBalloonCalculatorForHtml4.splitLines(
					canvas, 
					w,
					"arial", 
					speechText);
			// Block of text height
			int both = lines.size() * (fontHeight + lineSpacing);
			if (both >= h) {
				// We won't be able to wrap the text inside the area
				// the area is too small. We should inform the user
				// about this in a meaningful way
			} else {
				// We determine the y of the first line
				int ly = (h - both) / 2 + y + lineSpacing * lines.size();
				double lx = 0;
				for (int j = 0; j < lines.size(); ++j, ly += fontHeight + lineSpacing) {
					// We continue to centralize the lines
					String line = lines.get(j);
					lx = (x + w) / 2 - (canvas.measureTextWidth(line)) / 2;
					// DEBUG
					// console.log("ctx2d.fillText('"+ lines[j] +"', "+ lx +", "
					// + ly + ")");
					canvas.fillText(line, lx, ly);
				}
			}
		}

		// update the front canvas
		canvas.copyBackBufferToFront();

		/*
		 * 
		 * 
		 * for (var j = 0, ly; j w) { if(j==0) { //ctx2d.translate((x - w) ,ly +
		 * h); ctx2d.save(); ctx2d.translate(x,y + h);
		 * ctx2d.rotate(-0.5*Math.PI);
		 * 
		 * }
		 * 
		 * // We continue to centralize the lines 320,90,35,100" ly = (w -
		 * (parseInt(fh) +parseInt(spl)))/2 + ((parseInt(fh) +parseInt(spl)) *
		 * (j)) ; lx = (h - ctx2d.measureText(lines[j]).width)/2;
		 * 
		 * // DEBUG console.log("ctx2d.fillText('"+ lines[j] +"', "+ lx +", " +
		 * ly + ")"); ctx2d.textBaseline = "middle";
		 * 
		 * ctx2d.fillStyle = "white";
		 * 
		 * 
		 * ctx2d.fillText(lines[j], lx,ly);
		 * 
		 * } else { lx = x+w/2-ctx2d.measureText(lines[j]).width/2;
		 * 
		 * console.log("ctx2d.fillText('"+ lines[j] +"', "+ lx +", " + ly +
		 * ")"); ctx2d.textBaseline = "middle"; ctx2d.fillText(lines[j], lx,
		 * ly); ly+=parseInt(fh)+parseInt(spl); }
		 * 
		 */
	}

	public int getWidth() {
		return canvas.getCoordinateSpaceWidth();
	}

	public int getHeight() {
		return canvas.getCoordinateSpaceHeight();
	}

	public String getObjectUnderMouse(int x, int y) {

		// System.out.println("----------------");
		if (toScene != null) {
			int count = toScene.getSceneObjectCount();
			for (int i = 0; i < count; i++) {
				String otid = toScene.getOtidByIndex(i);
				if (toScene.getVisibleByOtid(otid)) {
					String atid = toScene.getAtidOfCurrentAnimationByOtid(otid);
					int frame = toScene.getCurrentFrameByOtid(otid);
					Rect rect = toScene.getBoundingRectByFrameAndAtid(frame, atid);
					// System.out.println(ob.getTextualId() +
					// ob.getDrawingOrder());

					int adjX = x - (int) toScene.getXByOtid(otid) + cameraOffsetX;
					int adjY = y - (int) toScene.getYByOtid(otid) + cameraOffsetY;

					if (rect.contains(adjX, adjY)) {
						return otid;
					}
				}
			}
		} else {
			return "ERROR!";
		}

		return "";
	}

	@Override
	public void setStateOfPopup(boolean isVisible, ColorEnum talkingColor, String speech, Rect maxBalloonRect,
			Point mouth, TalkPerformer sayAction) {

		this.speechVisible = isVisible;
		this.speechColor = talkingColor;
		this.speechText = speech;
		this.speechMaxRect = maxBalloonRect;

	}

	@Override
	public void onSceneEntry(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetScale(Image image) {
		// not sure how this will be implemented.
	}

}
