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
import com.github.a2g.core.action.ChainedAction;


public class AlignBaseMiddleAction extends ChainedAction {
	private String animId;
	private int frame;
	public AlignBaseMiddleAction(BaseAction parent, String animationCode, int frame) {
		super(parent, parent.getApi(), true);
		this.animId = animationCode;
		this.frame = frame;
	}

	@Override
	public void runGameAction() {
		super.run(1);
	}

	@Override
	protected void onUpdateGameAction(double progress) {}

	@Override
	protected void onCompleteGameAction() {
		Animation a = getApi().getAnimation(
				this.animId);

		if (a != null) {
			a.alignBaseMiddleOfOldFrameToFrameOfThisAnimation(frame);
		}
	}

	@Override
	public boolean isParallel() {

		return false;
	}
}