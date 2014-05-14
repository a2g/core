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
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.action.ChainedAction;


public class WaitForFrameAction extends ChainedAction {
	private int frame;
	private SceneObject object;

	public WaitForFrameAction(BaseAction parent, short objectId, int frame) {
		super(parent, parent.getApi(), true);
		this.frame = frame;
		this.object = getApi().getObject(
				objectId);
	}

	@Override
	public void runGameAction() {
		// we achieve the variable execution time, by using a max value here..
		String name = object.getCurrentAnimation();
		int count = object.getAnimations().at(name).getFrames().getCount();
		int milliseconds = count * 1000;

		this.run(milliseconds);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		// ..then cancelling the animation when it reaches the desired condition.
		if (object.getCurrentFrame() == frame) {
			cancel();
		}
	}

	@Override
	protected void onCompleteGameAction() {}

	@Override
	public boolean isParallel() {
		return false;
	}

}
