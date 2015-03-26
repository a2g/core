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
import com.github.a2g.core.action.ChainedAction;
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromWaitAction;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;

public class WaitForFrameAction extends ChainedAction {
	private IScenePresenterFromWaitAction scene;
	private int frame;
	private short ocode;
	private String otid;

	public WaitForFrameAction(BaseAction parent, short ocode, int frame) {
		super(parent, true);
		this.frame = frame;
		this.ocode = ocode;
	}

	@Override
	public void runGameAction() {
		// we achieve the variable execution time, by using a max value here..
		this.otid = scene.getOtidByCode(ocode);
		String atid = scene.getAtidOfCurrentAnimationByOtid(otid);
		int count = scene.getNumberOfFramesByAtid(atid);
		int milliseconds = count * 1000;

		this.run(milliseconds);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		// ..then cancelling the animation when it reaches the desired
		// condition.
		if (scene.getCurrentFrameByOtid(otid) == frame) {
			cancel();
		}
	}

	@Override
	protected void onCompleteGameAction() {
	}

	@Override
	public boolean isParallel() {
		return false;
	}

	public IScenePresenterFromWaitAction getScene() {
		return scene;
	}

	public void setScene(IScenePresenterFromWaitAction scene) {
		this.scene = scene;
	}

	@Override
	public void setAll(IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard,
			IInventoryPresenterFromActions inventory) {
		setScene(scene);

	}

}
