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

package com.github.a2g.core.platforms.html4.dependencies;

import com.github.a2g.core.interfaces.internal.ImagePanelAPI;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.primitive.Point;

public class ImageForHtml4 extends Image {
	private final com.google.gwt.user.client.ui.Image image;
	private Point originalDimensions;
	public ImageForHtml4(final com.google.gwt.user.client.ui.Image image,
			ImagePanelAPI panel, Point offset) {
		super(panel, offset, "fromHtml4");
		this.image = image;
		this.originalDimensions = null;
		panel.setImageVisible(this, false);
	}

	public com.google.gwt.user.client.ui.Image getNativeImage() {
		return image;
	}
	
	public Point getOriginalDimensions()
	{
		return originalDimensions;
	}
	
	public void setOriginalDimensions(Point point)
	{
		originalDimensions = point;
	}

}
