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


import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.Rect;

public abstract class Image {
	
	private final ImagePanel panel;
	private final Point offset;
	

	public Image(ImagePanel panel, Point offset) 
	{
		this.offset = offset;
		this.panel = panel;
		//panel.setImageVisible(this, false);
	};

	public void addImageToPanel(int before) {
		panel.insert(this, this.offset.getX(), this.offset.getY(), before);
	}

	public void removeImageFromPanel() {
		panel.remove(this);
	}

	public void setTopLeft(Point topLeft) 
	{
		update(topLeft);
	}

	public void setVisible(boolean visible, Point position) 
	{
		panel.setImageVisible(this, visible);
		update(position);
	}

	public Rect getBoundingRect() {
		return new Rect(
				this.offset.getX(), 
				this.offset.getY(),
				panel.getImageWidth(this), 
				panel.getImageHeight(this));
	}

	private void update(Point topLeft) {

		int x = this.offset.getX();
		int y = this.offset.getY();
		panel.setThingPosition(
				this, 
				x + topLeft.getX(), 
				y + topLeft.getY()
		);
	}

	

}
