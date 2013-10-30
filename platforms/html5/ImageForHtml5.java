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

package com.github.a2g.core.platforms.html5;

import com.github.a2g.core.interfaces.ImagePanelAPI;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.primitive.Point;
import com.google.gwt.dom.client.ImageElement;

public class ImageForHtml5 extends Image
{
	private final ImageElement imageElement;
	private String objectId;// backpointer

	public ImageForHtml5(final com.google.gwt.user.client.ui.Image image,
			String objectId, ImagePanelAPI panel, Point offset)  
	{
		super(panel,offset);
		this.imageElement = (ImageElement) image.getElement().cast();
		this.objectId = objectId;
		panel.setImageVisible(this, false);
	}

	public ImageElement getNativeImage()
	{
		return imageElement;
	}

	public String getObjectId()
	{
		return objectId;
	}
}
