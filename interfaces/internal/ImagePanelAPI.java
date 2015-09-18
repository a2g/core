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

import com.github.a2g.core.objectmodel.Image;

public interface ImagePanelAPI {
	public void setImageVisible(Image image, boolean visible);

	public void add(Image image, int x, int y);

	public void insert(Image image, int x, int y, int before);

	public void remove(Image image);

	public void setThingPosition(Image image, int left, int top);

	public int getImageHeight(Image image);

	public int getImageWidth(Image image);
}
