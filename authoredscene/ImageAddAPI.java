/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredscene;

import com.github.a2g.bridge.LoadHandler;
import com.github.a2g.bridge.ImageResource;


public interface ImageAddAPI {
       
    /*!
	If the same textualId had already been used in a previous call to this method method, then the call will fail, and return false.
	@param textualIdForInventory this specifies which inventory item the image represents (see @ref TextualIds).
	@param codeForInventory this code is registered as the code for the inventory item  (see @ref Codes).
	@param imageResource the image to add.

	@return true on success, otherwise false.
     */
    public boolean addImageForAnInventoryItem(LoadHandler lh, String textualIdForInventory, int codeForInventory, ImageResource imageResource);
    
    /*!
	If the same textualIds have already been used in a previous call to this method method, then the ImageResource is added to the
	list of animation frames for the animation specified by the ids.
	@param numberPrefix
	@param x specifies the horizontal offset (in pixels) used to position the image in its default location in the scene.
	@param y specifies the vertical offset (in pixels) used to position the image in its default location in the scene.
	@param textualIdForObject this specifies which object to add the image to (see @ref TextualIds).
	@param textualIdForAnimation this specifies which animation of the object to add the image to  (see @ref TextualIds).
	@param codeForObject this code is registered as the code for the object (unless the texturalIdForObject has been used before). (see @ref Codes).
	@param codeForAnimation this code is registered as the code for the animation (unless the textualIdForAnimation has been used before). (see @ref Codes).
	@param imageResource the image to add.

	@return true on success, otherwise false.
     */
    public boolean addImageForASceneObject(LoadHandler lh, int numberPrefix, int x, int y, String textualIdForObject, String textualIdForAnimation, short codeForObject, int codeForAnimation, ImageResource imageResource);
    
};



