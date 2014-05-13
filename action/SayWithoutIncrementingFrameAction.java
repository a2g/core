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
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.action.ChainedAction;



public class SayWithoutIncrementingFrameAction extends ChainedAction {
	private String speech;
	private ColorEnum color;
	private boolean isParallel;
	private Animation anim;
	private boolean isHoldLastFrame;

	public SayWithoutIncrementingFrameAction(BaseAction parent, String animCode, String speech) {
		super(parent, parent.getApi(), true);
		this.speech = speech;
		isParallel = false;
		isHoldLastFrame = false;
		anim  = getApi().getAnimation(animCode);
		SceneObject object = anim.getObject();
		color = (object!=null)? object.getTalkingColor() : null;
		
	}

	@Override
	public void runGameAction() {
		int delay = 0;
		int duration = (this.speech.length()
				* (2 + delay))
				* 40;

		this.getApi().setStateOfPopup(true, .1, .1, color, speech,this);

		this.run(duration);
		if (anim != null) {
			anim.setAsCurrentAnimation();
		}
	}

	@Override
	protected void onUpdateGameAction(double progress) {}

	@Override
	protected void onCompleteGameAction() {
		getApi().setStateOfPopup( false, .1,.1, color, "",null);
		SceneObject animsParent = anim.getObject();
		if(!isHoldLastFrame )
		{
			if (animsParent != null)
			{
				String s = animsParent.getInitialAnimation();
				animsParent.setCurrentAnimation(s);
			}
		}
	}

	@Override
	public boolean isParallel() {

		return isParallel;
	}

	public void setNonBlocking(boolean b) {
		isParallel = b;
		
	}

	public void setHoldLastFrame(boolean isHoldLastFrame ) {
		this.isHoldLastFrame = isHoldLastFrame ;
		
	}

}
