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

package com.github.a2g.core.platforms.java;

import com.github.a2g.core.interfaces.internal.ImagePanelAPI;
import com.github.a2g.core.objectmodel.Image;
import com.github.a2g.core.primitive.Point;

public class ImageForJava extends Image {
	private final java.awt.Image image;
	private String objectId;

	public ImageForJava(final java.awt.Image image, String objectId,
			ImagePanelAPI panel, Point offset) {
		super(panel, offset, objectId);
		this.image = image;
		this.objectId = objectId;
		panel.setImageVisible(this, false);
	}

	public java.awt.Image getNativeImage() {
		return image;
	}

	public String getObjectId() {
		return objectId;
	}
}
