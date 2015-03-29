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
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromScrollAction;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;

public class ScrollCameraXAction extends ChainedAction {
	private IScenePresenterFromScrollAction scene;
	private String obj;// set in constructor

	private double endX;// set via setters
	private double startX;// set via setters
	private boolean isParallel;// set via setters
	private double duration;

	public ScrollCameraXAction(BaseAction parent, double endX, double duration,
			boolean isLinear) {
		super(parent, isLinear);
		this.isParallel = false;
		this.endX = endX;
		this.duration = duration;
	}

	String getObject() {
		return this.obj;
	}

	double getEndX() {
		return endX;
	}

	void setNonBlocking(boolean isParallel) {
		this.isParallel = isParallel;
	}

	@Override
	public void runGameAction() {
		startX = scene.getCameraX();

		this.run((int) (duration * 1000));
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		double x = this.startX + progress * (this.endX - this.startX);
		scene.setCameraX(x);
	}

	@Override
	// method in animation
	protected void onCompleteGameAction() {
		scene.setCameraX(endX);
	}

	@Override
	public boolean isParallel() {
		return isParallel;
	}

	public void setScene(IScenePresenterFromScrollAction scene) {
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
