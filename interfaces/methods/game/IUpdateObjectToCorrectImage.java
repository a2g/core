package com.github.a2g.core.interfaces.methods.game;
/**
 * This exists because of:
 * - @ref SceneObject::IncrementFrameWithWraparound 
 * 
 * because that is the only methods that don't update the image
 * that the SceneObject needs to show. If not using those two methods
 * then do not need to use this method.
 * 
 * @author Admin
 *
 */
public interface IUpdateObjectToCorrectImage {
	void updateObjectToCorrectImage(short ocode);
}
