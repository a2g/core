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

package com.github.a2g.core.action.performer;

import com.github.a2g.core.interfaces.IScenePresenterFromSwitchPerformer;
import com.github.a2g.core.interfaces.performer.ISwitchPerformer;
import com.github.a2g.core.primitive.PointF;

public class SwitchPerformer implements ISwitchPerformer
{
	private IScenePresenterFromSwitchPerformer scene;

	private short ocode;

	private double endX;// set via setters
	private double endY;// set via setters
	private double startX;// set via setters
	private double startY;// set via setters


	private boolean isStopped;
	private boolean isExitedThruGate;

	public SwitchPerformer(short ocode) {

		this.ocode = ocode;
		this.endX = Double.NaN;
		this.endY = Double.NaN;
		this.startX = 0.0;
		this.startY = 0.0;
		this.isStopped = false;
		this.isExitedThruGate = false;
	}


	@Override
	public void setEndX(double endX) {
		this.endX = endX;
	}

	@Override
	public void setEndY(double endY) {
		this.endY = endY;
	}

	double getEndX() {
		return endX;
	}

	double getEndY() {
		return endY;
	}

	double getStartX() {
		return startX;
	}

	double getStartY() {
		return startY;
	}


	@Override
	public void run( ) {
		String otid = scene.getOtidByCode(ocode);
		startX = scene.getBaseMiddleXByOtid(otid);
		startY = scene.getBaseMiddleYByOtid(otid);

		if (endX == Double.NaN)
			endX = startX;
		if (endY == Double.NaN)
			endY = startY;
    }

	@Override
	public void onUpdate(double progress) {
		if (isStopped)
			return;
		if (progress > 0 && progress < 1) {

			progress = progress * 1.0;
		}
		double x = this.startX + progress * (this.endX - this.startX);
		double y = this.startY + progress * (this.endY - this.startY);


		if (scene.isInANoGoZone(new PointF(x, y))) {

			// and we make sure we only do this once
			// we don't keep letting the animation try all the points
			// on the line to it's target, because
			// some of these will also be behind some other gate.
			isStopped = true;
			
			// if in a nogozone in the middle of moving
			// then the delta(inx,y) may be small enough
			// for us to tell they have moved between two
			// gate points. And if so, then we can fire
			isExitedThruGate = scene.doSwitchBeyondGateIfSetUp(new PointF(x, y));

		}

	}

	@Override
	public boolean onComplete() { 
		
		if (scene.isInANoGoZone(new PointF(endX, endY))) {
			if(!isStopped)
			{
				isStopped = true;
				isExitedThruGate = scene.doSwitchBeyondGateIfSetUp(new PointF(endX, endY));
			}
		}
		return isExitedThruGate;
	}

	@Override
	public void setScene(IScenePresenterFromSwitchPerformer scene) {
		this.scene = scene;
	}

	@Override
	public boolean isStopped() {
		return isStopped;
	}

}
