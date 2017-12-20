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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.a2g.core.primitive.LogNames;

public class LoadedLoad {
	private static final Logger LOADING_ANIM = Logger.getLogger(LogNames.LOADING_ANIM.toString());

	private SceneObjectCollection sceneObjectCollection;
	private String name;

	public LoadedLoad(String name) {
		this.name = name;
		this.sceneObjectCollection = new SceneObjectCollection();
	}

	public void addToAppropriateAnimation(int prefix, Image imageAndPos,
			String objectTextualId, String animationTextualId,
			short objectCode, String objPlusAnimCode, int screenPixelWidth,
			int screenPixelHeight) {
		// objects and animations
		int index = this.getSceneObjectCollection().getIndexByOtid(
				objectTextualId);
		SceneObject sceneObject = null;
		if (index != -1) {
			sceneObject = getSceneObjectCollection().getByIndex(index);
		}else{
			sceneObject = new SceneObject(objectTextualId, screenPixelWidth,
					screenPixelHeight);
			sceneObject.setDrawingOrder(prefix);
			sceneObject.setOCode(objectCode);

			this.getSceneObjectCollection().add(sceneObject);
		}

		Animation animation = sceneObject.getAnimations().getByAtid(
				objPlusAnimCode);
		if (animation == null) {
			// much simpler if not in the animation map.
			animation = new Animation(objPlusAnimCode, sceneObject);
			sceneObject.getAnimations().add(animation);
			LOADING_ANIM.log(Level.FINE, "added to loader "+objPlusAnimCode);
		}

		animation.getFrameCollection().add(imageAndPos);
		double milliseconds = animation.getFrameCollection().getCount() * 40*4;//as if the animation is at 25fps, but then slowed down 4 times.
		animation.setDurationSecs(milliseconds/1000);
	}

	public SceneObjectCollection getSceneObjectCollection() {
		return sceneObjectCollection;
	}

	public String getName() {
		return name;
	}

}
