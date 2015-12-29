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

import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.RectF;

public class Animation {
	private double durationInSeconds;
	private String atid;
	private ImageCollection framesCollection;
	private SceneObject ownerObject;
	private RectF rectForMaxSpeechBalloon;

	private ColorEnum talkingColor;

	public Animation(String atid, SceneObject ownerSceneObject) {
		this.framesCollection = new com.github.a2g.core.objectmodel.ImageCollection();
		this.durationInSeconds = 1.0;
		this.ownerObject = ownerSceneObject;
		this.atid = atid;
		this.rectForMaxSpeechBalloon = new RectF(0,.25,1.0,.75);
		//give it  random color, to force us to commit, and not rely on default :)
		this.talkingColor = ColorEnum.values()[(int)(Math.random()*ColorEnum.values().length)];
	}

	public SceneObject getObject() {
		return ownerObject;
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

	public int getLength() {
		return framesCollection.getCount();
	}

	public int getLastFrame() {
		return framesCollection.getCount() - 1;
	}


	public void alignBaseMiddleOfOldFrameToFrameOfThisAnimation(int frame) {
		if (ownerObject != null) {
			ownerObject.alignBaseMiddleOfOldFrameToFrameOfNewAnimation(atid, frame);
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

	public ColorEnum getTalkingColor() {
		return talkingColor;
	}

	public void setTalkingColor(ColorEnum color) {
		this.talkingColor = color;
	}

	public void setSceneObject(SceneObject parent) {
		this.ownerObject = parent;
	}

	public SceneObject getSceneObject() {
		return ownerObject;
	}



};
