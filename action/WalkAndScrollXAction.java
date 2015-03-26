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
import com.github.a2g.core.interfaces.IScenePresenterFromWalkScrollAction;

public class WalkAndScrollXAction extends WalkToAction {
	private IScenePresenterFromWalkScrollAction scene;
	double startCameraX;

	// double startCameraY;

	public WalkAndScrollXAction(BaseAction parent, short ocode, double endX,
			double endY, int delay, boolean isLinear) {
		super(parent, ocode, endX, endY, delay, isLinear);
		startCameraX = scene.getCameraX();
	}

	@Override
	public void runGameAction() {
		super.runGameAction();
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		super.onUpdateGameAction(progress);

		double x = startCameraX + progress
				* (this.getEndX() - this.getStartX());

		scene.setCameraX(x);

	}

	@Override
	// on complete walking
	protected void onCompleteGameAction() {

		super.onCompleteGameAction();

		scene.setCameraX(startCameraX + this.getEndX() - this.getStartX());
		;

	}

	public void setScene(IScenePresenterFromWalkScrollAction scene) {
		this.scene = scene;
	}
}
