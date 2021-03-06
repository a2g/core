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

import com.github.a2g.core.interfaces.nongame.IImagePanel;
import com.github.a2g.core.platforms.html4.dependencies.PlatformPackagedImageForHtml4;
import com.github.a2g.core.primitive.PointI;
import com.github.a2g.core.primitive.RectI;
import com.google.gwt.event.dom.client.LoadHandler;

public abstract class Image {

	private final IImagePanel panel;
	private final PointI constOffset;
	private double parallaxX;
	private double parallaxY;
	private double scale;
	String atid;

	public Image(IImagePanel panel, PointI offset, String atid) {
		this.constOffset = offset;
		this.panel = panel;
		this.parallaxX = 1.0;
		this.parallaxY = 1.0;
		this.atid = atid;
		this.scale = 1.0;
	};

	public void addImageToPanel(int before) {
		panel.insert(this, this.constOffset.getX(), this.constOffset.getY(),
				before);
	}

	public void removeImageFromPanel() {
		panel.remove(this);
	}

	public void setLeftTop(PointI leftTop) {
		update(leftTop);
	}
	
	public void setScale(double scale)
	{
		this.scale = scale;
	}

	public double getScale()
	{
		return scale;
	}

	public void setVisible(boolean visible, PointI position) {
		panel.setImageVisible(this, visible);
		update(position);
	}

	public RectI getBoundingRectPreScaling() {
		return new RectI(this.constOffset.getX(), 
						this.constOffset.getY(),
						(int)(panel.getImageWidth(this)), 
						(int)(panel.getImageHeight(this)));
	}

	public void setParallaxX(double x) {
		this.parallaxX = x;
	}

	public void setParallaxY(double y) {
		this.parallaxY = y;
	}

	public double getParallaxX() {
		return parallaxX;
	}

	public double getParallaxY() {
		return this.parallaxY;
	}

	private void update(PointI xAndY) {

		//this should be alright because we deduct the constOffset
		// when calculating x & y
		int constXOffset = (int)(this.constOffset.getX());
		int constYOffset = (int)(this.constOffset.getY());
		panel.setThingPosition(this, constXOffset + xAndY.getX(),
				constYOffset + xAndY.getY(), scale);
	}

	public static final com.google.gwt.user.client.ui.Image getImageFromResource(
			PlatformPackagedImageForHtml4 imageResource, LoadHandler lh) {
		final com.google.gwt.user.client.ui.Image image = new com.google.gwt.user.client.ui.Image(
				imageResource.getNative().getSafeUri());
		if (lh != null) {
			image.addLoadHandler(lh);
		}
		return image;
	}

	public String getAtid() {
		return atid;
	}

	public void resetScale() {
		panel.resetScale(this);
		
	}


}