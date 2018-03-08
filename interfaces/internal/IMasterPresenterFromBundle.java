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

package com.github.a2g.core.interfaces.internal;

import com.google.gwt.event.dom.client.LoadHandler;

public interface IMasterPresenterFromBundle {

	/*
	 * ! If the same textualIds have already been used in a previous call to
	 * this method method, then the ImageResource is added to the list of
	 * animation frames for the animation specified by the ids.
	 * 
	 * @param drawingOrder
	 * 
	 * @param x specifies the horizontal offset (in pixels) used to position the
	 * image in its default location in the scene.
	 * 
	 * @param y specifies the vertical offset (in pixels) used to position the
	 * image in its default location in the scene.
	 * 
	 * @param otid this specifies which object to add the image to
	 * (see @ref otids).
	 * 
	 * @param atid this specifies which animation of the object
	 * to add the image to (see @ref atids).
	 * 
	 * @param ocode this code is registered as the code for the object
	 * (unless the texturalIdForObject has been used before). (see @ref Codes).
	 * 
	 * @param atid this code is registered as the code for the
	 * animation (unless the textualIdForAnimation has been used before).
	 * 
	 * @param imageResource the image to add.
	 * 
	 * @return true on success, otherwise false.
	 */
	public boolean addImageForEitherInventoryOrScene(LoadHandler lh, int drawingOrder,
			int x, int y, int w, int h, String otid,
			String atid, short ocode, String objectPlusAnimCode,
			IPackagedImage imageResource);

	public void setScenePixelSize(int width, int height);

	public void setInventoryImageSize(int width, int height);

	public boolean queueMP3ForASoundObject(String name, String location);

};
