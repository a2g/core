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

package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.objectmodel.Animation;
import com.github.a2g.core.objectmodel.SceneObject;


public class PlayAnimationAction extends BaseAction {
	private int delay;
	private Animation anim;
	private SceneObject animsParent;
	private boolean isBackwards;
	private boolean holdLastFrame;
	private boolean isNonBlocking;

	public PlayAnimationAction(BaseAction parent, String  animCode) {
		super(parent, parent.getApi());
		this.anim = getApi().getAnimation(animCode);
		this.animsParent = anim.getObject();
		this.delay = 0;
		this.isBackwards = false;
		this.holdLastFrame = false;
		this.isNonBlocking = false;
	}

	@Override
	public void runGameAction() {
		int duration = (this.anim.getLength()
				+ 1)
				* (40 + this.delay);
		String s = this.anim.getTextualId();

		if (animsParent != null) {
			animsParent.setCurrentAnimation(s);
			animsParent.setVisible(true);
		}
		this.run(duration);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		int length = this.anim.getLength();
		double frame = isBackwards
				? (1 - progress) * length
						: progress * length;

				if (animsParent != null) {
					animsParent.setCurrentFrame(
							(int) frame);
				}
	}

	@Override
	protected void onCompleteGameAction() {

		if (!this.holdLastFrame) {
			if (this.anim != null) {
				SceneObject o = anim.getObject();

				if (o != null) {
					String s = o.getHomeAnimation();

					o.setCurrentAnimation(s);
				}
			}
		}
	}

	public void setBackwards(boolean isBackwards) {
		this.isBackwards = isBackwards;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setHoldLastFrame(boolean holdLastFrame) {
		this.holdLastFrame = holdLastFrame;
	}

	public void setNonBlocking(boolean isNonBlocking) {
		this.isNonBlocking = isNonBlocking;
	}

	@Override
	public boolean isParallel() {

		return isNonBlocking;
	}

}
