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

package com.github.a2g.core.objectmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.nongame.IBoundaryCalculator;
import com.github.a2g.core.interfaces.nongame.IFactory;
import com.github.a2g.core.interfaces.nongame.IHostingPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformScenePanel;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromScenePresenter;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenter;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromBoundaryCalculator;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.LogNames;
import com.google.gwt.touch.client.Point;
import com.github.a2g.core.primitive.RectI;
import com.github.a2g.core.primitive.SpeechBubble;
import com.github.a2g.core.primitive.RectF;

public class ScenePresenter implements IScenePresenter,
		IScenePresenterFromBoundaryCalculator {
	public static final short DEFAULT_SCENE_OBJECT = -1;
	private static final Logger SET_STATE_OF_POPUP = Logger.getLogger(LogNames.SET_STATE_OF_POPUP.toString());

	private int width;
	private int height;
	private Scene scene;
	private IPlatformScenePanel view;
	private double cameraX;
	private double cameraY;

	private String defaultSceneObjectOtid;
	private String sceneTalkerAtid;
	private String sceneAskerAtid;
	private String sceneAnswererAtid;
	private IMasterPresenterFromScenePresenter master;

	private IBoundaryCalculator boundaryCalculator;
	private int entrySegment;
	private Vector<RectF> rectangles;
	private ArrayList<Point> helperPoints;

	public ScenePresenter(final IHostingPanel panel,
			IMasterPresenterFromScenePresenter master, IFactory factory) {
		this.master = master;
		this.sceneTalkerAtid = "";
		this.sceneAskerAtid = "";
		this.sceneAnswererAtid = "";

		this.cameraX = 0.0;
		this.cameraY = 0.0;
		this.width = 320;
		//this.height = 180;
		this.boundaryCalculator = new BoundaryCalculator(this);

		this.scene = new Scene();
		this.view = master.getFactory().createScenePanel(this);
		panel.setThing(view);
		view.setVisible(true);
		this.entrySegment = -1;
		defaultSceneObjectOtid = "ScenePresenter::getDefaultSceneObjectOtid was used before it was initialized";

		rectangles = new Vector<RectF>(3);
		this.addHelperRectangle(new RectF(0,0,1.0,1.0));// this is for getSpeechRectUsingContingencies, it relies on there always being a zero element
		this.helperPoints = new ArrayList<Point>();
	}

	private SceneObject getObjectByOCode(short ocode) {
		SceneObject toReturn =  this.scene.objectCollection().getByOCode(ocode);
		if(toReturn== null)
		{
			return null; //"ScenePresenter::getOtidByCode recd bad ocode " + ocode;
		}
		return toReturn;
	}

	public SceneObject getObjectByOtid(String otid) {
		return this.scene.objectCollection().getByOtid(otid);
	}

	public Animation getAnimationByAtid(String atid) {
		SceneObjectCollection coll = this.scene.objectCollection();
		return coll.getAnimationByAtid(atid);
	}

	@Override
	public IPlatformScenePanel getView() {
		return view;
	}

	public void setView(IPlatformScenePanel view) {
		this.view = view;
	}

	public void setScenePixelSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.view.setScenePixelSize(width, height);
	}

	public void clearEverythingExceptView() {

		scene.objectCollection().clear();
		rectangles.clear();
		this.sceneTalkerAtid = "";
		this.sceneAskerAtid = "";
		this.sceneAnswererAtid = "";
		this.getView().setTitleCard("");
	}

	public void clearView() {
		view.clear();
	}

	public Scene getModel() {
		return scene;
	}

	public void reset() {
		this.scene = new Scene();
	}

	@Override
	public double getCameraY() {
		return this.cameraY;
	}

	@Override
	public double getCameraX() {
		return this.cameraX;
	}

	@Override
	public void setCameraX(double x) {
		this.cameraX = x;
		offsetAllImagesInScene();
	}

	public void setCameraY(double y) {
		this.cameraY = y;
		offsetAllImagesInScene();
	}

	private void offsetAllImagesInScene() {
		double camX = cameraX * this.width;
		double camY = cameraY * this.height;
		view.setCameraOffset((int) camX, (int) camY);
		for (int i = 0; i < this.scene.objectCollection().getCount(); i++) {
			this.scene.objectCollection().getByIndex(i).updateCurrentImage();
		}
	}

	public void addSceneObject(SceneObject destObject) {
		this.scene.objectCollection().addSceneObject(destObject);
	}

	public void addAnimation(String atid, Animation destAnimation) {
		this.scene.objectCollection().addAnimation(atid, destAnimation);
	}

	public void alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimationByAtid(
			int frame, String atid) {
		String otid = getOtidByAtid(atid);
		SceneObject o = getObjectByOtid(otid);
		o.alignBaseMiddleOfOldFrameToFrameOfNewAnimation(atid, frame);
	}

	@Override
	public int getSceneObjectCount() {
		return scene.objectCollection().getCount();
	}

	@Override
	public String getOtidByIndex(int i) {
		return scene.objectCollection().getByIndex(i).getOtid();
	}

	@Override
	public String getAtidOfCurrentAnimationByOtid(String otid) {
		SceneObject o = scene.objectCollection().getByOtid(otid);
		if (o != null) {
			String a = o.getCurrentAnimation();
			return a;
		}
		return "getAtidOfCurrentAnimationByOtid couldn't find otid for <"
				+ otid + ">";
	}

	public void setOtidOfDefaultSceneObject(String otid) {
		setDefaultSceneObjectOtid(otid);
	}

	@Override
	public boolean getVisibleByOtid(String otid) {
		return scene.objectCollection().getByOtid(otid).isVisible();
	}

	@Override
	public int getCurrentFrameByOtid(String otid) {
		return scene.objectCollection().getByOtid(otid).getCurrentFrame();
	}

	@Override
	public int getSceneGuiWidth() {
		return this.width;
	}

	@Override
	public int getSceneGuiHeight() {
		return this.height;
	}

	@Override
	public String getDisplayNameByOtid(String otid) {
		return this.scene.objectCollection().getByOtid(otid).getDisplayName();
	}

	@Override
	public RectI getBoundingRectByFrameAndAtid(int frame, String atid) {
		RectI toReturn = new RectI(0, 0, 1, 1);
		Animation a = getAnimationByAtid(atid);
		if (a != null) {
			ImageCollection fc = a.getFrames();
			if(frame>=fc.getCount())
				frame=fc.getCount()-1;// just like in SceneObject::updateObjectToCorrectImage
			Image i = fc.getByIndex(frame);
			if (i != null)
				toReturn = i.getBoundingRectPreScaling();
			else
				assert (false);
		}
		return toReturn;
	}

	@Override
	public double getXByOtid(String otid) {
		return this.scene.objectCollection().getByOtid(otid).getX();
	}

	@Override
	public double getYByOtid(String otid) {
		return this.scene.objectCollection().getByOtid(otid).getY();
	}

	@Override
	public short getCodeByOtid(String otid) {
		return getObjectByOtid(otid).getOCode();
	}

	public String getOtidByCode(short ocode) {
		if (ocode == DEFAULT_SCENE_OBJECT)
			return this.getDefaultSceneObjectOtid();
		if (getObjectByOCode(ocode) == null)
			return "ScenePresenter::getOtidByCode recd bad ocode " + ocode;
		return getObjectByOCode(ocode).getOtid();
	}

	public void setStateOfPopup(boolean isVisible, SpeechBubble speechBubble, TalkPerformer sayAction) 
	{
		SET_STATE_OF_POPUP.fine("SETSTATEOFPOPUP " + (isVisible? speechBubble.toString() : "NOT VISIBLE"));
		ColorEnum talkingColor = speechBubble!=null? this.getTalkingColorUsingContingencies(speechBubble.atid) : null;
		
		view.setStateOfSpeech(isVisible, talkingColor, null, speechBubble, sayAction);
	}

	public String getSceneTalkerAtid() {
		if (!sceneTalkerAtid.isEmpty())
			return sceneTalkerAtid;
		String defaultInitial = this.getObjectByOtid(defaultSceneObjectOtid)
				.getInitialAnimation();
		return defaultInitial;
	}

	public void setSceneTalkerAtid(String sceneTalkerAtid) {
		this.sceneTalkerAtid = sceneTalkerAtid;
	}

	public String getSceneDialoggerAtid() {
		if (!sceneAskerAtid.isEmpty())
			return sceneAskerAtid;
		String nextBest = this.getSceneTalkerAtid();
		return nextBest;
	}

	public void setSceneAskerAtid(String sceneAskerAtid) {
		this.sceneAskerAtid = sceneAskerAtid;
	}

	public String getSceneDialoggeeAtid() {
		if (!sceneAnswererAtid.isEmpty())
			return sceneAnswererAtid;
		String nextBest = this.getSceneTalkerAtid();
		return nextBest;
	}

	public void setSceneAnswererAtid(String sceneAnswererAtid) {
		this.sceneAnswererAtid = sceneAnswererAtid;
	}

	public String getDefaultSceneObjectOtid() {
		return defaultSceneObjectOtid;
	}

	public void setDefaultSceneObjectOtid(String otid) {
		this.defaultSceneObjectOtid = otid;
		SceneObject o = this.getObjectByOtid(otid);
		if(o!=null)
			o.setUseBaseMiddleForAnimation(true);
	}

	public String getOtidByAtid(String atid) {
		String toReturn = "";
		Animation a = getAnimationByAtid(atid);
		if (a != null) {
			SceneObject o = a.getObject();
			if (o != null)
				toReturn = o.getOtid();
		}
		return toReturn;
	}

	public int getExistingPrefixIfAvailable(short ocode, int defaultDrawingOrder) 
	{
		int index = this.scene.objectCollection().getIndexByOCode(ocode);
		if(index==-1)
		{
			return defaultDrawingOrder;
		}
		SceneObject o = this.scene.objectCollection().getByIndex(index);
		if(o==null)
		{
			return defaultDrawingOrder;
		}
		
		return o.getDrawingOrder();
	}

	public void clearBoundaries() {
		boundaryCalculator.clearBoundaries();
	}

	@Override
	public void switchToScene(String foundDest, int entrySegment) {
		master.switchToScene(foundDest, entrySegment);
	}

	public void addEdgeSpanToPerimeter(double tlx, double tly, double brx, double bry,
			Object sceneToSwitchTo, int entrySegment) {
		boundaryCalculator.addEdgeSpanToPerimeter(sceneToSwitchTo, entrySegment,
				new Point(tlx, tly), new Point(brx, bry));
	}

	public void addBoundaryPoint(double x, double y) {
		boundaryCalculator.addBoundaryPoint(new Point(x, y));
	}

	public boolean isInANoGoZone(Point tp) {
		return boundaryCalculator.isInANoGoZone(tp);
	}

	public boolean doSwitchIfBeyondGate(Point tp) {
		return boundaryCalculator.doSwitchIfBeyondGate(tp);
	}

	@Override
	public ArrayList<Point> getBoundaryPoints() {
		return boundaryCalculator.getGatePoints();
	}

	@Override
	public Point getBoundaryPointsCentre() {
		return boundaryCalculator.getCentreOfSegments();
	}

	public void addEdgeRectangle(double x, double y, double right, double bottom) {
		boundaryCalculator.addEdgeRectangle(x, y, right, bottom);

	}

	public List<Point> findPath(Point rawStart, Point rawEnd) {
		return boundaryCalculator.findPath(rawStart, rawEnd);
	}

	@Override
	public ArrayList<RectF> getObstacles() {
		return boundaryCalculator.getObstacles();
	}
	
	@Override
	public ArrayList<Point> getHelperPoints() {
		return helperPoints;
	}
	

	@Override
	public List<PointFWithNeighbours> getLastNetworkOfConcaveVertices() {
		return boundaryCalculator.getLastNetworkOfConcaveVertices();
	}

	@Override
	public List<Point> getLastPath() {
		return boundaryCalculator.getLastPath();
	}

	public String getOtidOfDefaultSceneObject() {
		return defaultSceneObjectOtid;
	}

	public void setEntrySegment(int entrySegment) {
		this.entrySegment = entrySegment;
	}

	public void repositionDefaultObject() {
		SceneObject o = scene.objectCollection().getByOtid(
				this.getDefaultSceneObjectOtid());
		
		ArrayList<Point> points = boundaryCalculator.getGatePoints();
		if (entrySegment > -1 && entrySegment < (points.size() - 1)) {
			Point a = points.get(entrySegment);
			Point b = points.get(entrySegment + 1);
			Point c = boundaryCalculator.getCentreOfSegments();
			Point mp = new Point((a.getX() + b.getX()) / 2,
					(a.getY() + b.getY()) / 2);
			// we want v, a vector starting at mp, and heading 10% towards
			// centre.
			Point v = new Point((c.getX() - mp.getX()) * .1,
					(c.getY() - mp.getY()) * .1);
			Point result = new Point(mp.getX() + v.getX(), mp.getY()
					+ v.getY());
			
			o.setBaseMiddleX(result.getX());
			o.setBaseMiddleY(result.getY());
		}
		else
		{
			Point c = boundaryCalculator.getCentreOfSegments();
			o.setBaseMiddleX(c.getX());
			o.setBaseMiddleY(c.getY());
		}
	}

	@Override
	public Iterator<RectF> getHeadRectangles() {
		return rectangles.iterator();
	}

	
	public int addHelperRectangle(RectF rectF) {
		rectangles.add(rectF);
		return rectangles.size() - 1;
	}
	
	public RectF getHelperRectangle(int index) {
		return rectangles.get(index);
	}
	
	public int addHelperPoint(Point pointF) {
		helperPoints.add(pointF);
		return helperPoints.size() - 1;
	}

	public short getOCodeByAtid(String atid) {
		short toReturn = -1;
		Animation a = getAnimationByAtid(atid);
		if (a != null) {
			SceneObject o = a.getObject();
			if (o != null)
				toReturn = o.getOCode();
		}
		;
		return toReturn;
	}

	public void resetScaleOnAllImages() {
		for(int i=0;i<scene.objectCollection().getCount();i++)
		{
			SceneObject o = scene.objectCollection().getByIndex(i);
			o.setScale(1.0);
			for(int j=0;j<o.getAnimations().getCount();j++)
			{
				Animation a = o.getAnimations().getByIndex(j);
				for(int k=0;k<a.getFrames().getCount();k++)
				{
					Image im = a.getFrames().getByIndex(k);
					im.resetScale();
				}
			}
			
		}
	}
	
 
	RectF getHeadRectangleUsingContingencies(String atid)
	{
		Animation a = this.getAnimationByAtid(atid);
		
		// 1. prefer rect from animation..
		int headRectIndex = a.getHeadRectangleIndex();
		
		// 2. if none, then choose sceneObject speech rect
		if (headRectIndex == -1) 
		{
			headRectIndex = a.getSceneObject().getHeadRectangleIndex();
			
			// 3. if still none, then choose the first speech rect
			if (headRectIndex == -1) 
			{
				return a.getSceneObject().calculateHeadRect();
			}
		}
		RectF r = this.rectangles.get(headRectIndex);
		
		return r;
	}

	ColorEnum getTalkingColorUsingContingencies(String atid)
	{
		if(atid!=null)
		{
			Animation a = this.getAnimationByAtid(atid);

			if(a!=null)
			{
				// 1. prefer talking color from animation..
				ColorEnum talkingColor = a.getTalkingColor();
				if(talkingColor!=null)
					return talkingColor;

				// 2. if none, then choose sceneObject talking color
				talkingColor = a.getSceneObject().getTalkingColor();
				if(talkingColor!=null)
					return talkingColor;
			}
		}

		// 3. if still none, then choose random
		return ColorEnum.values()[(int) (Math.random() * ColorEnum.values().length)];
	}

	public Point measureTextWidthAndHeight(String text) {
		return this.getView().measureTextWidthAndHeight(text);
	}

	public void setTitleCard(String titlecard) {
		this.getView().setTitleCard(titlecard);
	}

	public double getPopupDisplayDuration() {
		return 1;
	}

	@Override
	public IBoundaryCalculator getBoundaryCalculator() {
		return this.boundaryCalculator;
	}

	public void clearDisplayNames() {
		
		for(int i=0;i<scene.objectCollection().getCount();i++)
		{
			SceneObject o = scene.objectCollection().getByIndex(i);
			if(!o.getDisplayName().endsWith("_"))
				o.setDisplayName("");
		}
	}

};
