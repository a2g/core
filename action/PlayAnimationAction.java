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
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromPlayAction;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.action.ChainedAction;

public class PlayAnimationAction extends ChainedAction {
	private String atid;
	private String otid;
	private boolean isBackwards;
	private boolean holdLastFrame;
	private boolean isNonBlocking;
	private IScenePresenterFromPlayAction scene;

	public PlayAnimationAction(BaseAction parent, String atid, boolean isLinear) {
		super(parent, isLinear);
		this.isBackwards = false;
		this.holdLastFrame = false;
		this.isNonBlocking = false;
		this.atid = atid;
	}

	@Override
	public void runGameAction() {
		otid = scene.getOtidOfAtid(atid);
		int durationInMilliseconds = (int) (scene.getDurationByAtid(atid) * 1000);

		this.run(durationInMilliseconds);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		int lastFrame = scene.getNumberOfFramesByAtid(atid) - 1;
		double frame = isBackwards ? (1 - progress) * lastFrame : progress
				* lastFrame;

		scene.setCurrentFrameByOtid(otid, (int) frame);
		scene.setAsACurrentAnimationByAtid(atid);
		scene.setCurrentFrameByOtid(otid, (int) frame);
	}

	@Override
	protected void onCompleteGameAction() {

		System.out.println("ActionRunner::done " + atid + " is this length: "
				+ scene.getNumberOfFramesByAtid(atid));

		onUpdateGameAction(1.0);

		if (!this.holdLastFrame) {
			scene.setToInitialAnimationWithoutChangingFrameByOtid(otid);
		}
	}

	public void setBackwards(boolean isBackwards) {
		this.isBackwards = isBackwards;
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

	public void setScene(IScenePresenterFromPlayAction scene) {
		this.scene = scene;
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) {
		setScene(scene);

	}

}
