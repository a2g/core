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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.a2g.core.primitive.LogNames;


public class SceneObjectCollection {
	private static final Logger ADDING_ANIM_TO_SOC_MAP = Logger.getLogger(LogNames.ADDANIMATION.toString());
	private static final Logger ADD_SCENEOBJECT = Logger.getLogger(LogNames.ADD_SCENEOBJECT.toString());

	private List<String> theOtids;
	private List<Short> theOCodes;
	private ArrayList<SceneObject> list;
	private TreeMap<String, Animation> theAtidMap;

	public SceneObjectCollection() {
		theOtids = new LinkedList<String>();
		theOCodes = new LinkedList<Short>();
		list = new ArrayList<SceneObject>();
		this.theAtidMap = new TreeMap<String, Animation>();
	}

	public void clear()
	{
		theOtids.clear();
		theOCodes.clear();
		list.clear();
		theAtidMap.clear();
	}

	public void addSceneObject(SceneObject sceneObject) 
	{
		ADD_SCENEOBJECT.log(Level.FINE, "added " + sceneObject.getOtid());
		list.add(sceneObject);
		
		Collections.sort(list, new Comparator<SceneObject>() {
			@Override
			public int compare(SceneObject o1, SceneObject o2) {
				return o1.getDrawingOrder() - o2.getDrawingOrder();
			}
		});
		
		theOtids.clear();
		theOCodes.clear();
		for (int i = 0; i < list.size(); i++) {
			theOtids.add(list.get(i).getOtid());
			theOCodes.add(list.get(i).getOCode());
		}
		assert(theOtids.size()==list.size());
		assert(theOCodes.size()==list.size());

	}

	public SceneObject getByIndex(int index) throws NoSuchElementException {
		if (index == -1)
			return null;
		if (index >= list.size())
			throw new NoSuchElementException();
		return list.get(index);
	}


	public SceneObject getByOtid(String otid) {
		int i = this.theOtids.indexOf(otid);
		if(i==-1)
		{
			// this actually fails in the following case. but I think its the trail
			throw new NoSuchElementException("'"+otid+"' not present in theOtids of size "+theOtids.size()); 
			/*
			this actually fails in 
			at com.github.a2g.core.objectmodel.SceneObjectCollection.getByOtid(SceneObjectCollection.java:86)
			at com.github.a2g.core.objectmodel.ScenePresenter.getObjectByOtid(ScenePresenter.java:86)
			at com.github.a2g.core.objectmodel.AllActionMethods.setToInitialAnimationWithoutChangingFrameByOtid(AllActionMethods.java:282)
			at com.github.a2g.core.action.performer.MovePerformer.onCompleteForMover(MovePerformer.java:168)
			at com.github.a2g.core.action.performer.WalkSinglePerformer.onCompleteActionAndCheckForGateExit(WalkSinglePerformer.java:107)
			at com.github.a2g.core.action.performer.WalkMultiPerformer.onCompleteActionAndCheckForGateExit(WalkMultiPerformer.java:154)
			at com.github.a2g.core.action.WalkMaybeSwitchAction.onCompleteActionAndCheckForGateExit(WalkMaybeSwitchAction.java:78)
			at com.github.a2g.core.action.BaseAction.onComplete(BaseAction.java:100)
			*/
		}
		return this.getByIndex(i);
	}

	public int getIndexByOtid(String otid) {
		int index = this.theOtids.indexOf(otid);
		return index;
	}

	public int getIndexByOCode(short ocode) {
		int i = this.theOCodes.indexOf(ocode);
		return i;
	}
	public SceneObject getByOCode(Short ocode) {
		int i = this.theOCodes.indexOf(ocode);
		if(i==-1)
		{
			return null;//"ScenePresenter::getOtidByCode recd bad ocode " + ocode;
		}
		return this.getByIndex(i);
	}

	public int getCount() {
		return list.size();
	}

	public Animation getAnimationByAtid(String atid) {
		Animation anim = this.theAtidMap.get(atid);

		if (anim == null) {
			// first param is name, second is parent;
			anim = new Animation("", null);
			this.theAtidMap.put(atid, anim);
			ADDING_ANIM_TO_SOC_MAP.log(Level.FINE, "getAnimationBy <" +atid +">");
			throw new NoSuchElementException("Animation getAnimationByAtid");

		}
		return anim;
	}

	public void addAnimation(String atid, Animation destAnimation) {
		if (theAtidMap.get(atid) == null) {
			// System.out.println("ScenePresenter::added " + animTextualId);

			ADDING_ANIM_TO_SOC_MAP.log(Level.FINE, "addAnimation " +atid);
			this.theAtidMap.put(atid, destAnimation);
		}
	}



}
