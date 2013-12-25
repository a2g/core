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

import com.github.a2g.core.interfaces.SceneAPI;




public class Animation {
	private double durationInSeconds;
	private String textualId;
	private ImageCollection framesCollection;
	private SceneObject parent;
	private boolean wasSetAsInitialAnimation;
	private boolean wasSetAsCurrentAnimation;
	private SceneAPI.Special specialAnimationThisWasSetTo;


	public Animation(String textualId, SceneObject owningSceneObject) {
		this.durationInSeconds = 1.0;
		this.parent = owningSceneObject;
		this.textualId = textualId;
		framesCollection = new com.github.a2g.core.objectmodel.ImageCollection();
		wasSetAsInitialAnimation = false;
		wasSetAsCurrentAnimation = false;
		specialAnimationThisWasSetTo = null;
	}

	public ImageCollection getFrames() {
		return framesCollection;
	}

	public String getTextualId() {
		return textualId;
	}

	public SceneObject	getObject() {
		return parent;
	}

	public void setTextualId(String textualId) {
		this.textualId = textualId;
	}

	public ImageCollection getFrameCollection() {
		return framesCollection;
	}

	public Image getDefaultFrame() {
		assert(framesCollection.getCount()
				!= 0);
		if (framesCollection.getCount() == 0) {
			return null;
		}
		com.github.a2g.core.objectmodel.Image frame = framesCollection.at(
				0);

		return frame;
	}

	public int getLength() {
		return framesCollection.getCount();
	}

	public int getLastFrame() {
		return framesCollection.getCount()
				- 1;
	}

	public void setAsSpecialAnimation(SceneAPI.Special special) {
		specialAnimationThisWasSetTo = special;
		if (parent != null) {
			parent.setSpecialAnimation(special,
					textualId);
		}
	}

	public void setAsCurrentAnimationAndSetFrame(int i) {
		parent.setCurrentAnimation(textualId);
		parent.setCurrentFrame(i);
	}

	public void setAsCurrentAnimation() {
		this.wasSetAsCurrentAnimation = true;
		if (parent != null) {
			parent.setCurrentAnimation(textualId);
		}
	}

	public void setAsInitialAnimation() {
		this.wasSetAsInitialAnimation = true;
		if (parent != null)
		{
			parent.setInitialAnimation(	this.textualId);
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
		boolean wasSet = specialAnimationThisWasSetTo
				!= null;

		return wasSet;
	}

	public SceneAPI.Special getDesignatedSpecialAnimation() {
		return specialAnimationThisWasSetTo;
	}

	public void setAsCurrentAnimationAndSetToFrameWithoutBaseMiddleMovement(
			int lastFrame)
	{
		if (parent != null)
		{
			parent.setCurrentAnimationAndSetFrameWithoutBaseMiddleMovement(this.textualId, lastFrame);
		}
	}

	public void setAsCurrentAnimationAndSetToLastFrameWithoutBaseMiddleMovement()
	{
		if (parent != null)
		{
			int lastFrame = this.getLength() - 1;
			parent.setCurrentAnimationAndSetFrameWithoutBaseMiddleMovement(this.textualId, lastFrame);
		}
	}



	public void setAsCurrentAnimationAndSetToFirstFrameWithoutBaseMiddleMovement() {
		if (parent != null)
		{
			parent.setCurrentAnimationAndSetFrameWithoutBaseMiddleMovement(this.textualId,0);
		}

	}

	public void setDurationSecs(double seconds) {
		this.durationInSeconds = seconds;
	}

	public double getDurationSecs() {
		return durationInSeconds;
	}


};
