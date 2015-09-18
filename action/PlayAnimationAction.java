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
import com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromPlayAction;
import com.github.a2g.core.interfaces.internal.ITitleCardPresenterFromActions;
import com.github.a2g.core.action.ChainableAction;

public class PlayAnimationAction extends ChainableAction {
	private String atid;
	private String otid;
	private boolean isBackwards;
	private boolean holdLastFrame;
	private IScenePresenterFromPlayAction scene;

	public PlayAnimationAction(BaseAction parent, String atid) {
		super(parent);
		this.isBackwards = false;
		this.holdLastFrame = false;
		this.atid = atid;
	}

	@Override
	public void runGameAction() {
		otid = scene.getOtidByAtid(atid);
		int durationInMilliseconds = (int) (scene.getDurationByAtid(atid) * 1000);

		this.run(durationInMilliseconds);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		int lastFrame = scene.getNumberOfFramesByAtid(atid) - 1;
		double frame = isBackwards ? (1 - progress) * lastFrame : progress
				* lastFrame;

		scene.setCurrentFrameByOtid(otid, (int) frame);
		scene.setCurrentAnimationByAtid(atid);
		scene.setCurrentFrameByOtid(otid, (int) frame);
	}

	@Override
	protected boolean onCompleteGameAction() {

		onUpdateGameAction(1.0);

		if (!this.holdLastFrame) {
			scene.setToInitialAnimationWithoutChangingFrameByOtid(otid);
		}
		return false;
	}

	public void setBackwards(boolean isBackwards) {
		this.isBackwards = isBackwards;
	}

	public void setHoldLastFrame(boolean holdLastFrame) {
		this.holdLastFrame = holdLastFrame;
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
