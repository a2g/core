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
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.interfaces.nongame.presenter.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromScrollAction;

public class ScrollCameraYAction extends ChainableAction {
	private IScenePresenterFromScrollAction scene;
	private String obj;// set in constructor

	private double endY;// set via setters
	private double startY;// set via setters
	private double duration;

	public ScrollCameraYAction(BaseAction parent, double endY, double duration) {
		super(parent);
		this.endY = endY;
		this.duration = duration;
	}

	String getObject() {
		return this.obj;
	}

	double getEndY() {
		return endY;
	}

	@Override
	public void runGameAction() {
		startY = scene.getCameraY();

		this.run((int) (duration * 1000));
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		double x = this.startY + progress * (this.endY - this.startY);
		scene.setCameraY(x);
	}

	@Override
	// method in animation
	protected boolean onCompleteActionAndCheckForGateExit() {
		scene.setCameraY(endY);
		return false;
	}



	public void setScene(IScenePresenterFromScrollAction scene) {
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
