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

import java.util.Map;
import java.util.TreeMap;

import com.github.a2g.core.interfaces.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.primitive.Rect;
/**
 * 
 * @author Admin
 *
 */
public class SceneObject {
	private String initialAnimationId;
	private Map<String, String> mapOfSpecialAnimations;
	private final String otid;
	private String displayName;
	private AnimationCollection animationCollection;
	private FrameAndAnimation fak;
	private com.github.a2g.core.objectmodel.Image currentImage;
	private boolean visible;
	private final double screenPixelWidth;
	private final double screenPixelHeight;
	private double rawY; // needed for moving image around.
	private double rawX;
	private int numberPrefix;
	private short ocode;
	private double screenCoordsPerSecond;
	private double scale;

	public SceneObject(String otid, int screenWidth, int screenHeight) {
		this.currentImage = null;
		this.otid = otid;
		this.displayName = otid;
		this.animationCollection = new AnimationCollection();
		this.fak = new FrameAndAnimation(this.otid);
		this.visible = true;
		this.screenPixelWidth = screenWidth;
		this.screenPixelHeight = screenHeight;
		this.mapOfSpecialAnimations = new TreeMap<String, String>();
		this.numberPrefix = 0;
		this.initialAnimationId = otid + "_INITIAL";
		this.scale = 1;

		this.setBaseMiddleX(0);
		this.setBaseMiddleY(0);

		this.screenCoordsPerSecond = .3;
	}

	public void setNumberPrefix(int number) {
		this.numberPrefix = number;
	}

	public int getNumberPrefix() {
		return this.numberPrefix;
	}

	public String getOtid() {
		return this.otid;
	}

	public AnimationCollection getAnimations() {
		return this.animationCollection;
	}

	public int getCurrentFrame() {
		int i = this.fak.getCurrentFrame();

		return i;
	}

	public void setCurrentFrame(int i) {
		this.fak.setCurrentFrame(i);
		updateToCorrectImage();
	}

	public void incrementFrameWithWraparound() {
		Animation anim = getAnimations().getByAtid(
				this.fak.getCurrentAnimationAtid());
		final int animLength = anim.getFrames().getCount();
		if (animLength == 0) {
			// Log::NoSingleImage(QString("Here in IncrementFrame"));
			return;
		}

		int i = this.fak.getCurrentFrame() + 1;

		if (i >= animLength) {
			this.fak.setCurrentFrame(0);
		} else {
			this.fak.setCurrentFrame(i);
		}
		// do no update image here - since it is a heavy operation, we do it
		// once per tick.
	}

	public void decrementFrameWithWraparound() {
		Animation anim = getAnimations().getByAtid(
				this.fak.getCurrentAnimationAtid());

		int i = this.fak.getCurrentFrame() - 1;

		if (i < 0) {
			this.fak.setCurrentFrame(anim.getFrames().getCount() - 1);
		} else {
			this.fak.setCurrentFrame(i);
		}
		// do no update image here - since it is a heavy operation, we do it
		// once per tick.
	}

	public void updateCurrentImage() {
		if (currentImage != null) {
			currentImage.setLeftTop(getRawLeftTop());
		}
	}

	public void updateToCorrectImage() {
		// 1. do this only when the this.currentImage != img
		String currentAnim = fak.getCurrentAnimationAtid();
		Animation anim = this.animationCollection.getByAtid(currentAnim);

		// if animation is set to something bad, then set it to back to initial
		if (anim == null) {
			fak.setCurrentAnimationAtid(this.initialAnimationId);
			anim = this.animationCollection.getByAtid(fak
					.getCurrentAnimationAtid());
		}

		if (anim == null) {
			anim = this.animationCollection.getByIndex(0);
			fak.setCurrentAnimationAtid(anim.getAtid());
		}

		if (anim != null) {

			int effectiveFrame = fak.getCurrentFrame();
			// if we use -17 to indicate last frame (
			if (fak.getCurrentFrame() == -17) {
				// set both to last frame
				fak.setCurrentFrame(anim.getLength() - 1);
				effectiveFrame = anim.getLength() - 1;
			}
			// if the frame overflows...
			else if (fak.getCurrentFrame() >= anim.getLength()) {
				// ...set it to last frame
				effectiveFrame = anim.getLength() - 1;
			}

			com.github.a2g.core.objectmodel.Image current = anim
					.getFrameCollection().getByIndex(effectiveFrame);

			// yes current can equal null in some weird cases where I place
			// breakpoints...
			if (current != null) {
				if (this.currentImage != null) {
					this.currentImage.setVisible(false, getRawLeftTop());
				}
				this.currentImage = current;
			}
		}
		// 2, but do this always
		if (this.currentImage != null) {
			this.currentImage.setScale(scale);
			this.currentImage.setVisible(this.visible, getRawLeftTop());
		}
	}
 
	public void setVisible(boolean visible) {
		// we always do this, we don't even check if visible!=this.visible
		this.visible = visible;
		updateToCorrectImage();
	}

	public boolean isVisible() {
		return this.visible;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	Point getRawLeftTop() {
		return new Point((int) this.rawX, (int) this.rawY);
	}

	static double worldToScreenX(double intX, double screenSpan,
			int lowerBound, int upperBound, double scale) {
		double rectSpan = (upperBound - lowerBound)*scale;
		double doubleX = (intX + .5 * rectSpan + lowerBound) / screenSpan;
		return doubleX;
	}

	static double worldToScreenY(double intY, double screenSpan,
			int lowerBound, int upperBound, double scale) {
		double rectSpan = (upperBound - lowerBound)*scale;
		double doubleY = (intY + rectSpan + lowerBound) / screenSpan;
		return doubleY;
	}

	static double screenToWorldX(double doubleX, double screenSpan,
			int lowerBound, int upperBound, double scale) {
		double rectSpan = (upperBound - lowerBound)*scale;
		double rawX = doubleX * screenSpan - .5 * rectSpan - lowerBound;
		return rawX;
	}

	static double screenToWorldY(double doubleY, double screenSpan,
			int lowerBound, int upperBound, double scale) {
		double rectSpan = (upperBound - lowerBound)*scale;
		double rawY = doubleY * screenSpan - rectSpan - lowerBound;
		return rawY;
	}

	public void setX(double rawX) {
		this.rawX = rawX;
		if (currentImage != null) {
			this.currentImage.setLeftTop(getRawLeftTop());
		}
	}

	public void setY(double rawY) {
		this.rawY = rawY;
		if (currentImage != null) {
			this.currentImage.setLeftTop(getRawLeftTop());
		}
	}

	public double getX() {
		return this.rawX;
	}

	public double getY() {
		return this.rawY;
	}

	public void setBaseMiddleX(double baseMiddleX) {
		double rawX = screenToWorldX(baseMiddleX, screenPixelWidth,
				getCurrentBoundingRect().getLeft(), getCurrentBoundingRect()
				.getRight(),scale);
		setX(rawX);
	}

	public void setBaseMiddleY(double baseMiddleY) {
		double rawY = screenToWorldY(baseMiddleY, screenPixelHeight,
				getCurrentBoundingRect().getTop(), getCurrentBoundingRect()
				.getBottom(),scale);
		setY(rawY);
	}

	public double getBaseMiddleX() {
		double bmx = worldToScreenX(rawX, screenPixelWidth,
				getCurrentBoundingRect().getLeft(), getCurrentBoundingRect()
				.getRight(),scale);
		
		return bmx;
	}

	public double getBaseMiddleY() {
		double bmy = worldToScreenY(rawY, screenPixelHeight,
				getCurrentBoundingRect().getTop(), getCurrentBoundingRect()
				.getBottom(),scale);

		return bmy;
	}

	public Rect getCurrentBoundingRect() {
		if (currentImage != null) {
			return currentImage.getBoundingRectPreScaling();
		}
		return new Rect(0, 0, 0, 0);
	}

	void setSpecialAnimation(WalkDirection type, String atid) {
		this.mapOfSpecialAnimations.put(type.toString(), atid);
	}

	public String getSpecialAnimation(WalkDirection type) {
		String toReturn = this.mapOfSpecialAnimations.get(type.toString());
		if(toReturn==null)
			return this.getInitialAnimation();// more reliable fallback
		return toReturn;
	}

	public String getCurrentAnimation() {
		String atid = this.fak.getCurrentAnimationAtid();

		return atid;
	}

	public void setCurrentAnimation(String atid) {
		this.fak.setCurrentAnimationAtid(atid);
		updateToCorrectImage();
	}

	public String getInitialAnimation() {
		return initialAnimationId;
	}

	public void setInitialAnimation(String initialAnimation) {
		this.initialAnimationId = initialAnimation;
	}

	public void setOCode(short ocode) {
		this.ocode = ocode;
	}

	public short getOCode() {
		return ocode;

	}

	public void setParallaxX(double x) {
		for (int a = 0; a < animationCollection.getCount(); a++) {
			Animation anim = animationCollection.getByIndex(a);
			for (int i = 0; i < anim.getLength(); i++) {
				anim.getFrames().getByIndex(i).setParallaxX(x);
			}
		}
	}

	PointF getBaseMiddleXY() {
		double left = this.getX();
		double right = this.getY();
		Rect r = this.getCurrentBoundingRect();
		double averageXPos = left + (r.getLeft() + r.getRight()) / 2.0;
		double lowerYPos = right + r.getBottom();
		double x = averageXPos / screenPixelWidth;
		double y = lowerYPos / screenPixelHeight;
		return new PointF(x, y);
	}

	public Point getMouthLocation() {
		double left = this.getX();
		double top = this.getY();
		Rect r = this.getCurrentBoundingRect();
		double x = left + (r.getLeft() + r.getRight()) / 2.0;
		double y = top + r.getTop();

		return new Point((int)x,(int)y);
	}

	public void alignBaseMiddleOfOldFrameToFrameOfNewAnimation(String atid,
			int frame) {
		PointF h = getBaseMiddleXY();

		setCurrentAnimationAndFrame(atid, frame);

		this.updateToCorrectImage();

		// then change position
		setBaseMiddleX(h.getX());
		setBaseMiddleY(h.getY());

	}

	public double getScreenCoordsPerSecond() {
		return screenCoordsPerSecond;
	}

	public void setScreenCoordsPerSecond(double coordsPerSecond) {
		this.screenCoordsPerSecond = coordsPerSecond;
	}

	public void setToInitialAnimationWithoutChangingFrame() {
		// todo: should really check whether initial animation is null
		this.setCurrentAnimation(this.getInitialAnimation());
	}

	public void setCurrentAnimationAndFrame(String atid, int frame) {
		this.fak.setCurrentAnimationAtid(atid);
		this.fak.setCurrentFrame(frame);

		this.updateToCorrectImage();
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

};
