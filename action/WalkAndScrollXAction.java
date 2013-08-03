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


public class WalkAndScrollXAction
extends WalkToAction
{
	double startCameraX;
	//double startCameraY;

	public WalkAndScrollXAction(BaseAction parent, short objId, double endX, double endY, int delay, boolean isLinear)
	{
		super(parent, objId, endX, endY, delay, isLinear);
		startCameraX = getApi().getSceneGui().getCameraX();
		//startCameraY = getApi().getSceneGui().getCameraY();
	}

	@Override
	public void runGameAction()
	{
		super.runGameAction();
	}

	@Override
	protected void onUpdateGameAction(double progress)
	{
		super.onUpdateGameAction(progress);

		double x = startCameraX
				+ progress
				* ( this.getEndX()- this.getStartX());
		//double y = startCameraY
		//			+ progress
		//		* (this.getEndY()- this.getStartY());

		getApi().getSceneGui().setCameraX(x);
		//getApi().getSceneGui().setCameraY(y);

	}

	@Override // on complete walking
	protected void onCompleteGameAction()
	{

		super.onCompleteGameAction();

		getApi().getSceneGui().setCameraX(startCameraX	+ this.getEndX()- this.getStartX());
		//getApi().getSceneGui().setCameraY(getEndY());


	}
}
