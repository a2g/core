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



public class LoadedLoad 
{	
    private SceneObjectCollection sceneObjectCollection;
    private String name;

    public LoadedLoad(String name) {
    	this.name = name;
        this.sceneObjectCollection = new SceneObjectCollection();
    }

	public void addToAppropriateAnimation(int prefix, Image imageAndPos, String objectTextualId, String animationTextualId, short objectCode, String objPlusAnimCode, int width, int height)
	{
		// objects and animations
		SceneObject sceneObject = this.getSceneObjectCollection().at(
				objectTextualId);

		if (sceneObject == null) {
			sceneObject = new SceneObject(
					objectTextualId,
					width,
					height);
			sceneObject.setNumberPrefix(prefix);
			sceneObject.setCode(objectCode);
			
	
			this.getSceneObjectCollection().add(	sceneObject);
		}

		Animation animation  = sceneObject.getAnimations().at(animationTextualId);
		if (animation == null) {
			// much simpler if not in the animation map. 
			animation = new Animation(
					animationTextualId,
					sceneObject);
			animation.setCode(objPlusAnimCode);
			sceneObject.getAnimations().add(animation);
		} 
		
		animation.getImageAndPosCollection().add(imageAndPos);
	}

	public SceneObjectCollection getSceneObjectCollection() {
		return sceneObjectCollection;
	}

	public String getName() {
		return name;
	}
	
	
}
