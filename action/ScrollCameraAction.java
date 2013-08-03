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

public class ScrollCameraAction extends ChainedAction
{
	private SceneObject obj;// set in constructor

	private double endX;// set via setters
	private double endY;// set via setters
	private double startX;// set via setters
	private double startY;// set via setters
	private boolean isParallel;// set via setters
	private int duration;

	public ScrollCameraAction(BaseAction parent, double endX, double endY, int duration, boolean isLinear)
	{
		super(parent, parent.getApi(), isLinear);
		this.isParallel = false;
		this.endX = endX;
		this.endY = endY;
		this.startX = 0.0;
		this.startY = 0.0;
		this.duration = duration;
	}

	SceneObject getObject(){return this.obj;}
	double getEndX(){ return endX;}
	double getEndY(){ return endY;}

	void setNonBlocking(boolean isParallel){ this.isParallel = isParallel;}

	@Override
	public void runGameAction()
	{
		startX = getApi().getSceneGui().getCameraX();
		startY = getApi().getSceneGui().getCameraY();

		this.run(duration);
	}

	@Override
	protected void onUpdateGameAction(double progress)
	{
		double x = this.startX
				+ progress
				* (this.endX
						- this.startX);
		double y = this.startY
				+ progress
				* (this.endY
						- this.startY);

		getApi().getSceneGui().setCameraX(x);
		getApi().getSceneGui().setCameraY(y);
	}

	@Override // method in animation
	protected void onCompleteGameAction()
	{
		getApi().getSceneGui().setCameraX(endX);
		getApi().getSceneGui().setCameraY(endY);
	}

	@Override
	public boolean isParallel()
	{
		return isParallel;
	}
}
