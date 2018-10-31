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
import com.github.a2g.core.interfaces.nongame.presenter.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromWaitAction;

public class WaitForFrameAction extends BaseAction {
	private IScenePresenterFromWaitAction scene;
	private int frame;
	private short ocode;
	private String otid;

	public WaitForFrameAction(short ocode, int frame) {
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
	protected boolean onCompleteActionAndCheckForGateExit() {
		return false;
	}



	public IScenePresenterFromWaitAction getScene() {
		return scene;
	}

	public void setScene(IScenePresenterFromWaitAction scene) {
		this.scene = scene;
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			IInventoryPresenterFromActions inventory) {
		setScene(scene);

	}

}
