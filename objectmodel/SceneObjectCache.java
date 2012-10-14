/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.objectmodel;



public class SceneObjectCache {	
    private SceneObjectCollection sceneObjectCollection;
    private String name;

    public SceneObjectCache(String name) {
    	this.name = name;
        this.sceneObjectCollection = new SceneObjectCollection();
    }

	public void addToAppropriateAnimation(int prefix, Image imageAndPos, String objectTextualId, String animationTextualId, short objectCode, int objPlusAnimCode, int width, int height)
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
