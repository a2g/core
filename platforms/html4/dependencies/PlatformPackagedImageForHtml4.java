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

import com.github.a2g.core.interfaces.nongame.platform.IPlatformPackagedImage;

public class PlatformPackagedImageForHtml4 implements IPlatformPackagedImage {
	com.google.gwt.resources.client.ImageResource img;

	public PlatformPackagedImageForHtml4(
			com.google.gwt.resources.client.ImageResource img) {
		this.img = img;
	}

	public com.google.gwt.resources.client.ImageResource getNative() {
		return img;
	}

	public com.google.gwt.user.client.ui.Image unpack() {

		return new com.google.gwt.user.client.ui.Image(this.img);
	}
}