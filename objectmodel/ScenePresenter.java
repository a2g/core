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
import java.util.List;

import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.internal.IBoundaryCalculator;
import com.github.a2g.core.interfaces.internal.IFactory;
import com.github.a2g.core.interfaces.internal.IHostingPanel;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromScenePresenter;
import com.github.a2g.core.interfaces.internal.IScenePanelFromScenePresenter;
import com.github.a2g.core.interfaces.internal.IScenePresenter;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromBoundaryCalculator;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.primitive.RectF;

public class ScenePresenter
implements IScenePresenter
, IScenePresenterFromBoundaryCalculator {
	public static final short DEFAULT_SCENE_OBJECT = -1;
	private int width;
	private int height;
	private Scene scene;
	private IScenePanelFromScenePresenter view;
	private double cameraX;
	private double cameraY;

	
	private String defaultSceneObjectOtid;
	private ColorEnum talkingColorForScene;
	private String sceneTalkerAtid;
	private String sceneAskerAtid;
	private String sceneAnswererAtid;
	private IMasterPresenterFromScenePresenter master;

	private IBoundaryCalculator boundaryCalculator;
	private int arrivalSegment;

	public ScenePresenter(final IHostingPanel panel,
			IMasterPresenterFromScenePresenter master, IFactory factory) {
		this.master = master;
		this.sceneTalkerAtid = "";
		this.sceneAskerAtid = "";
		this.sceneAnswererAtid = "";

		this.cameraX = 0.0;
		this.cameraY = 0.0;
		this.width = 320;
		this.height = 180;
		this.boundaryCalculator = factory.createBoundaryCalculator(this);

		this.scene = new Scene();
		this.view = master.getFactory().createScenePanel(this);
		panel.setThing(view);
		view.setVisible(true);
		this.arrivalSegment = -1;
		defaultSceneObjectOtid = "ScenePresenter::getDefaultSceneObjectOtid was used before it was initialized";

		talkingColorForScene = ColorEnum.Fuchsia;
	}

	private SceneObject getObjectByOCode(short ocode) {
		return this.scene.objectCollection().getByOCode(ocode);
	}

	public SceneObject getObjectByOtid(String otid) {
		return this.scene.objectCollection().getByOtid(otid);
	}

	public Animation getAnimationByAtid(String atid) {
		SceneObjectCollection coll = this.scene.objectCollection();
		return coll.getAnimationByAtid(atid);
	}

	@Override
	public IScenePanelFromScenePresenter getView() {
		return view;
	}

	public void setView(IScenePanelFromScenePresenter view) {
		this.view = view;
	}

	public void setScenePixelSize(int width, int height) {

		this.view.setScenePixelSize(width, height);
	}

	public void clearEverythingExceptView() {

		scene.objectCollection().clear();
		this.sceneTalkerAtid = "";
		this.sceneAskerAtid = "";
		this.sceneAnswererAtid = "";
	}

	public void clearView()
	{
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
		for (int i = 0; i < this.scene.objectCollection().count(); i++) {
			this.scene.objectCollection().getByIndex(i).updateCurrentImage();
		}
	}

	public void addSceneObject(SceneObject destObject) {
		this.scene.objectCollection().add(destObject);
	}

	public void addAnimation(String atid, Animation destAnimation) {
		this.scene.objectCollection().addAnimation(atid,destAnimation);
	}

	public void alignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimationByAtid(
			int frame, String atid) {
		String otid = getOtidByAtid(atid);
		SceneObject o = getObjectByOtid(otid);
		o.alignBaseMiddleOfOldFrameToFrameOfNewAnimation(atid, frame);
	}

	@Override
	public int getSceneObjectCount() {
		return scene.objectCollection().count();
	}

	@Override
	public String getOtidByIndex(int i) {
		return scene.objectCollection().getByIndex(i).getOtid();
	}

	@Override
	public String getAtidOfCurrentAnimationByOtid(String otid) {
		SceneObject o = scene.objectCollection().getByOtid(otid);
		if(o!=null)
		{
			String a = o.getCurrentAnimation();
			return a;
		}
		return "getAtidOfCurrentAnimationByOtid couldn't find otid for <"+otid+">";
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
	public Rect getBoundingRectByFrameAndAtid(int frame, String atid) {
		Rect toReturn = new Rect(0,0,1,1);
		Animation a = getAnimationByAtid(atid);
		if(a!=null)
		{
			ImageCollection fc = a.getFrames();
			Image i = fc.getByIndex(frame);
			if(i!=null)
				toReturn = i.getBoundingRectPreScaling();
			else
				assert(false);
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
		if(ocode==-1)
			return this.getDefaultSceneObjectOtid();
		if(getObjectByOCode(ocode)==null)
			return "ScenePresenter::getOtidByCode recd bad ocode "+ocode;
		return getObjectByOCode(ocode).getOtid();
	}

	public void setStateOfPopup(String atid, boolean isVisible, String speech, TalkPerformer sayAction) {
		Animation a = this.getAnimationByAtid(atid);
		RectF r = a.getMaxSpeechBalloonExtents();
		Rect pixels = new Rect(
				(int)(r.getLeft() * this.getSceneGuiWidth())
				,	(int)(r.getTop() * this.getSceneGuiHeight())
				, (int)(r.getWidth()* this.getSceneGuiWidth())
				, (int)(r.getHeight()* this.getSceneGuiHeight())
				);

		ColorEnum talkingColor = a.getTalkingColor();
		if(talkingColor==null)
		{
			talkingColor = this.talkingColorForScene;
		}
		Point mouth = new Point(0,0);
		if(a.getSceneObject()!=null)
		{
			mouth = a.getSceneObject().getMouthLocation();
		}
		view.setStateOfPopup(isVisible, talkingColor, speech, pixels, mouth, sayAction);
	}

	public String getSceneTalkerAtid() {
		if(!sceneTalkerAtid.isEmpty())
			return sceneTalkerAtid;
		String defaultInitial = this.getObjectByOtid(defaultSceneObjectOtid).getInitialAnimation();
		return defaultInitial;
	}

	public void setSceneTalkerAtid(String sceneTalkerAtid) {
		this.sceneTalkerAtid = sceneTalkerAtid;
	}

	public String getSceneAskerAtid() {
		if(!sceneAskerAtid.isEmpty())
			return sceneAskerAtid;
		String nextBest = this.getSceneTalkerAtid();
		return nextBest;
	}

	public void setSceneAskerAtid(String sceneAskerAtid) {
		this.sceneAskerAtid = sceneAskerAtid;
	}
	public String getSceneAnswererAtid() {
		if(!sceneAnswererAtid.isEmpty())
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

	public int getExistingPrefixIfAvailable(short ocode, int numberPrefix) {
		SceneObject o = this.getObjectByOCode(ocode);
		if(o!=null)
		{
			return o.getDrawingOrder();
		}
		return numberPrefix;
	}

	public void clearBoundaries() {
		boundaryCalculator.clearBoundaries();
	}

	@Override
	public void switchToScene(String foundDest, int arrivalSegment) {
		master.switchToScene(foundDest, arrivalSegment);
	}


	public void addBoundaryGate(double tlx,double tly, double brx,double bry, Object sceneToSwitchTo, int arrivalSegment) {
		boundaryCalculator.addBoundaryGate(sceneToSwitchTo,  arrivalSegment, new PointF(tlx,tly), new PointF(brx,bry));
	}

	public void addBoundaryPoint(double x, double y) {
		boundaryCalculator.addBoundaryPoint(new PointF(x,y));
	}


	public boolean isInANoGoZone(PointF tp) {
		return boundaryCalculator.isInANoGoZone(tp);
	}


	public boolean doSwitchIfBeyondGate(PointF tp)
	{
		return boundaryCalculator.doSwitchIfBeyondGate(tp);
	}

	@Override
	public ArrayList<PointF> getBoundaryPoints() {
		return boundaryCalculator.getGatePoints();
	}

	@Override
	public PointF getBoundaryPointsCentre() {
		return boundaryCalculator.getCentreOfSegments();
	}

	public void addObstacleRect(double x, double y, double right, double bottom) {
		boundaryCalculator.addObstacleRect(x,y,right,bottom);
		
	}

	public List<PointF> findPath(PointF rawStart, PointF rawEnd) {
		return boundaryCalculator.findPath(rawStart, rawEnd);
	}

	@Override
	public ArrayList<RectF> getObstacles() {
		return boundaryCalculator.getObstacles();
	}

	@Override
	public List<PointFWithNeighbours> getLastNetworkOfConcaveVertices() 
	{
		return boundaryCalculator.getLastNetworkOfConcaveVertices();
	}
	
	

	@Override
	public List<PointF> getLastPath() {
		return boundaryCalculator.getLastPath();
	}

	public String getOtidOfDefaultSceneObject() {
		return defaultSceneObjectOtid;
	}

	public void setArrivalSegment(int arrivalSegment) {
		this.arrivalSegment = arrivalSegment;		
	}

	public void repositionDefaultObject() {
		ArrayList<PointF> points = boundaryCalculator.getGatePoints();
		if(arrivalSegment>-1 && arrivalSegment < (points.size()-1))
		{
			PointF a = points.get(arrivalSegment);
			PointF b = points.get(arrivalSegment+1);
			PointF c = boundaryCalculator.getCentreOfSegments();
			PointF mp = new PointF((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);
			// we want v, a vector starting at mp, and heading 10% towards centre.
			PointF v = new PointF((c.getX() -mp.getX())*.1, (c.getY()-mp.getY())*.1);
			PointF result = new PointF(mp.getX()+v.getX(), mp.getY()+v.getY());
			SceneObject o = scene.objectCollection().getByOtid(this.getDefaultSceneObjectOtid());
			o.setBaseMiddleX(result.getX());
			o.setBaseMiddleY(result.getY());
		}
	}

};
