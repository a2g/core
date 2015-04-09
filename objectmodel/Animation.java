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

import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.primitive.RectF;

public class Animation {
	private double durationInSeconds;
	private String atid;
	private ImageCollection framesCollection;
	private SceneObject parent;
	private boolean wasSetAsInitialAnimation;
	private boolean wasSetAsCurrentAnimation;
	private IGameScene.Special specialAnimationThisWasSetTo;
	private RectF rectForMaxSpeechBalloon;

	public Animation(String atid, SceneObject owningSceneObject) {
		this.durationInSeconds = 1.0;
		this.parent = owningSceneObject;
		this.atid = atid;
		framesCollection = new com.github.a2g.core.objectmodel.ImageCollection();
		wasSetAsInitialAnimation = false;
		wasSetAsCurrentAnimation = false;
		specialAnimationThisWasSetTo = null;
		rectForMaxSpeechBalloon = new RectF(.25,.25,.5,.5);
	}

	public SceneObject getObject() {
		return parent;
	}

	public ImageCollection getFrames() {
		return framesCollection;
	}

	public String getAtid() {
		return atid;
	}

	public void setAtid(String atid) {
		this.atid = atid;
	}

	public ImageCollection getFrameCollection() {
		return framesCollection;
	}

	public Image getDefaultFrame() {
		assert (framesCollection.getCount() != 0);
		if (framesCollection.getCount() == 0) {
			return null;
		}
		com.github.a2g.core.objectmodel.Image frame = framesCollection
				.getByIndex(0);

		return frame;
	}

	public int getLength() {
		return framesCollection.getCount();
	}

	public int getLastFrame() {
		return framesCollection.getCount() - 1;
	}

	public void setAsSpecialAnimation(IGameScene.Special special) {
		specialAnimationThisWasSetTo = special;
		if (parent != null) {
			parent.setSpecialAnimation(special, atid);
		}
	}

	public void setAsCurrentAnimationAndSetFrame(int i) {
		parent.setCurrentAnimation(atid);
		parent.setCurrentFrame(i);
	}

	public void setAsCurrentAnimation() {
		this.wasSetAsCurrentAnimation = true;
		if (parent != null) {
			parent.setCurrentAnimation(atid);
		}
	}

	public void setAsInitialAnimation() {
		this.wasSetAsInitialAnimation = true;
		if (parent != null) {
			parent.setInitialAnimation(this.atid);
		}
	}

	public void setSceneObject(SceneObject parent) {
		this.parent = parent;
	}

	public SceneObject getSceneObject() {
		return parent;
	}

	public boolean getWasSetAsInitialAnimation() {
		return wasSetAsInitialAnimation;
	}

	public boolean getWasSetAsCurrentAnimation() {
		return wasSetAsCurrentAnimation;
	}

	public boolean getWasSetAsSpecialAnimation() {
		boolean wasSet = specialAnimationThisWasSetTo != null;

		return wasSet;
	}

	public IGameScene.Special getDesignatedSpecialAnimation() {
		return specialAnimationThisWasSetTo;
	}

	public void alignBaseMiddleOfOldFrameToFrameOfThisAnimation(int frame) {
		if (parent != null) {
			parent.alignBaseMiddleOfOldFrameToFrameOfNewAnimation(atid, frame);
		}
	}

	public void setDurationSecs(double seconds) {
		this.durationInSeconds = seconds;
	}

	public double getDurationSecs() {
		return durationInSeconds;
	}

	public RectF getMaxSpeechBalloonExtents() {
		return this.rectForMaxSpeechBalloon;
	}

	public void setMaxSpeechBalloonRect(RectF rectF) {
		rectForMaxSpeechBalloon = rectF;
	}

};
