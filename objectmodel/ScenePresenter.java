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


import java.util.TreeMap;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.PackagedImageAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.interfaces.ScenePresenterAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;
import com.google.gwt.event.dom.client.LoadHandler;


public class ScenePresenter implements ScenePresenterAPI{
	private int width;
	private int height;
	private Scene scene;
	private ScenePanelAPI view;
	private double cameraX;
	private double cameraY;
	private TreeMap<String, Animation> theATIDMap;

	public ScenePresenter(final HostingPanelAPI panel, InternalAPI api)
	{
		this.cameraX=0.0;
		this.cameraY=0.0;
		this.setWidth(320);
		this.setHeight(180);
		this.scene = new Scene();
		this.view = api.getFactory().createScenePanel();
		panel.setThing(view);
		view.setVisible(true);

		this.theATIDMap = new TreeMap<String, Animation>();
	}
	
	
	public SceneObject getObject(short ocode) {
		return this.scene.objectCollection().getByOCode(ocode);
	}
	public SceneObject getObject(String OTEXT) {
		return this.scene.objectCollection().getByOTEXT(OTEXT);
	}
	public Animation getAnimation(String code) {
		Animation anim = this.theATIDMap.get(
				code);

		if (anim == null) {
			// first param is name, second is parent;
			anim = new Animation("", null);
			this.theATIDMap.put(code,
					anim);
		}
		return anim;
	}

	public ScenePanelAPI getView() {
		return view;
	}

	public void setView(ScenePanelAPI view) {
		this.view = view;
	}

	// start go in panel

	public void setPixelSize(int width, int height) {

		this.view.setScenePixelSize(width,height);
	}

	public Point getSizeOfSceneArea() {
		// int width = this.sceneArea.
		return new Point(this.getWidth(),
				this.getHeight());
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}
	// end go in panel.

	public void clear() {
		view.clear();
	}


	public Scene getModel() {
		return scene;
	}

	public void reset()
	{
		this.scene = new Scene();
	}

	public double getCameraY()
	{
		return this.cameraY;
	}

	public double getCameraX()
	{
		return this.cameraX;
	}

	public void setCameraX(double x)
	{
		this.cameraX = x;
		offsetAllImagesInScene();
	}

	public void setCameraY(double y)
	{
		this.cameraY = y;
		offsetAllImagesInScene();
	}
	private void offsetAllImagesInScene()
	{
		double camX = cameraX*this.width;
		double camY = cameraY*this.height;
		view.setCameraOffset((int)camX,(int)camY);
		for(int i=0;i<this.scene.objectCollection().count();i++)
		{
			this.scene.objectCollection().getByIndex(i).updateCurrentImage();
		}
	}

	@Override
	public Image createNewImageAndAddHandlers(LoadHandler lh,
			PackagedImageAPI imageResource, InternalAPI api,
			com.google.gwt.event.shared.EventBus bus, int x, int y,
			String objectTextualId, short objectCode) {
		
		return this.view.createNewImageAndAddHandlers(lh, imageResource, api, bus, x, y, objectTextualId, objectCode);
	}
	
	@Override
	public void setScenePixelSize(int width, int height) {
		this.view.setScenePixelSize(width, height);		
	}

	@Override
	public void setVisible(boolean b) {
		this.view.setVisible(b);
		
	}

	@Override
	public void setCameraOffset(int x, int y) {
		this.view.setCameraOffset(x, y);
		
	}

	@Override
	public void setStateOfPopup(boolean visible, int x, int y,
			ColorEnum talkingColor, String speech, BaseAction ba) {
		this.view.setStateOfPopup(visible, x, y, talkingColor, speech, ba);
		
	}

	@Override
	public int getSceneObjectCount() {
		return this.getModel().objectCollection().count();
	}

	@Override
	public void setVisibleByIndex(int index, boolean visible) {
		getModel().objectCollection().getByIndex(index).setVisible(visible);
	}

	@Override
	public boolean getVisibleByIndex(int index) {
		return getModel().objectCollection().getByIndex(index).isVisible();
	}

	@Override
	public String getCurrentAnimationByOTEXT(String otext) {
		return this.getObject(otext).getCurrentAnimation();
	}

	@Override
	public String getDisplayNameByOTEXT(String otext) {
		return this.getObject(otext).getDisplayName();
	}

	@Override
	public short getCodeByOTEXT(String otext) {
		return this.getObject(otext).getCode();
	}

	@Override
	public int getCurrentFrameByIndex(int i) {
		return this.getModel().objectCollection().getByIndex(i).getCurrentFrame();
	}

	@Override
	public double getXByIndex(int i) {
		return this.getModel().objectCollection().getByIndex(i).getY();
	}

	@Override
	public double getYByIndex(int i) {
		return this.getModel().objectCollection().getByIndex(i).getY();
	}

	@Override
	public Rect getBoundingRectByATIDAndFrame(String atid, int frame) {
		return this.getAnimation(atid).getDefaultFrame().getBoundingRect();
	}

	@Override
	public String getCurrentAnimationByIndex(int i) {
		return this.getModel().objectCollection().getByIndex(i).getCurrentAnimation();
	}

	@Override
	public String getOTEXTByIndex(int i) {
		return this.getModel().objectCollection().getByIndex(i).getTextualId();
	}


	public void addSceneObject(SceneObject destObject) {
		this.scene.objectCollection().add(destObject);
	}

	public void addAnimation(String animTextualId, Animation destAnimation) {
		if(theATIDMap.get(animTextualId)==null)
		{	
			//System.out.println("ScenePresenter::added " + animTextualId);
			this.theATIDMap.put(animTextualId, destAnimation);
		}
	}

}


;
