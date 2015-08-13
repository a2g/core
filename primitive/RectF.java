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

package com.github.a2g.core.primitive;

public class RectF {
	private double left;
	private double top;
	private double right;
	private double bottom;

	public RectF(double x, double y, double width, double height) {
		this.left = x;
		this.top = y;
		this.right = x + width;
		this.bottom = y + height;
		if (bottom < 0) {
			this.bottom = y + height;
		}
	}

	public boolean contains(double x, double y) {
		if (x >= left && x <= right) {
			if (y >= top && y <= bottom) {
				return true;
			}
		}
		return false;
	}

	public double getLeft() {
		return left;
	}

	public double getRight() {
		return right;
	}

	public double getBottom() {
		if (bottom >= 0) {
			return bottom;
		}
		return bottom;
	}

	public double getTop() {
		if (top >= 0) {
			return top;
		}
		return top;
	}

	public double getWidth() {
		return getRight() - getLeft();
	}

	public double getHeight() {
		return getBottom() - getTop();
	}

	public PointF getCenter() {
		return new PointF(left+(getWidth()/2),top+(getHeight()/2));
	}

}
