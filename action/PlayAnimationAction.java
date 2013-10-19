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
import com.github.a2g.core.action.ChainedAction;


public class PlayAnimationAction extends ChainedAction {
	private int delay;
	private Animation anim;
	private SceneObject animsParent;
	private boolean isBackwards;
	private boolean holdLastFrame;
	private boolean isNonBlocking;

	public PlayAnimationAction(BaseAction parent, String  animCode, boolean isLinear) {
		super(parent, parent.getApi(), isLinear);
		this.anim = getApi().getAnimation(animCode);
		this.animsParent = anim.getObject();
		this.delay = 0;
		this.isBackwards = false;
		this.holdLastFrame = false;
		this.isNonBlocking = false;
	}

	@Override
	public void runGameAction() {
		int duration = (this.anim.getLength()+ 1)
				* (40 + this.delay);
		
		if (animsParent != null) {
			animsParent.setVisible(true);
		}
		this.run(duration);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		int lastFrame = this.anim.getLength()-1;
		double frame = isBackwards
				? (1 - progress) * lastFrame
						: progress * lastFrame;

				if (animsParent != null) {
					animsParent.setCurrentFrame(
							(int) frame);

					animsParent.setCurrentAnimation(anim.getTextualId());
					animsParent.setCurrentFrame(
							(int) frame);
				}
	}

	@Override
	protected void onCompleteGameAction() {
		onUpdateGameAction(1.0);
		if (!this.holdLastFrame) {
			if (this.animsParent != null) 
			{
				String s = animsParent.getInitialAnimation();
				animsParent.setCurrentAnimation(s);
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
