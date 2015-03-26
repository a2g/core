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
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromMoveAction;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.action.ChainedAction;

public class MoveWhilstAnimatingAction extends ChainedAction {
	IScenePresenterFromMoveAction scene;

	private short ocode;
	private String otid;// set in constructor
	private String atid; // set in runGameAction

	private double endX;// set via setters
	private double endY;// set via setters
	private double startX;// set via setters
	private double startY;// set via setters
	private int animatingDelay;// set via setters
	private double screenCoordsPerSecond;// set via setters
	private boolean isParallel;// set via setters

	private int framesInAnim;// set in runGameAction
	private int framesPlayedDuringWalk;// set in runGameAction
	private boolean isBackwards;
	private boolean holdLastFrame;

	private boolean isStopped;
	public static final short DEFAULT_WALK_OBJECT = -1;

	public MoveWhilstAnimatingAction(BaseAction parent, short ocode,
			boolean isLinear) {

		super(parent, isLinear);
		this.ocode = ocode;
		this.animatingDelay = 0;
		this.screenCoordsPerSecond = scene.getScreenCoordsPerSecondByOtid(otid);
		this.endX = Double.NaN;
		this.endY = Double.NaN;
		this.startX = 0.0;
		this.startY = 0.0;
		this.isParallel = false;
		this.isBackwards = false;
		this.holdLastFrame = true;
		this.isStopped = false;
	}

	String getOtid() {
		return otid;
	}

	void setEndX(double endX) {
		this.endX = endX;
	}

	void setEndY(double endY) {
		this.endY = endY;
	}

	public void setHoldLastFrame(boolean holdLastFrame) {
		this.holdLastFrame = holdLastFrame;
	}

	String getObject() {
		return this.otid;
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

	void setNonBlocking(boolean isParallel) {
		this.isParallel = isParallel;
	}

	void setAnimatingDelay(int delay) {
		this.animatingDelay = delay;
	}

	@Override
	public void runGameAction() {
		if (ocode == -1) {
			otid = scene.getOtidOfDefaultWalkObject();
		} else {
			otid = scene.getOtidByCode(ocode);
		}
		atid = scene.getAtidOfCurrentAnimationByOtid(otid);
		startX = scene.getBaseMiddleXByOtid(otid);
		startY = scene.getBaseMiddleYByOtid(otid);

		if (endX == Double.NaN)
			endX = startX;
		if (endY == Double.NaN)
			endY = startY;

		// distance and time calculations
		double diffX = this.startX - this.endX;
		System.out.println(" walkto-start " + startX + " " + startY + " to "
				+ endX + " " + endY);
		double diffY = this.startY - this.endY;
		double diffXSquared = diffX * diffX;
		double diffYSquared = diffY * diffY;
		double dist = Math.sqrt(diffXSquared + diffYSquared);

		this.framesPlayedDuringWalk = (int) (dist * 40 + animatingDelay);

		if (this.atid != null) {
			this.framesInAnim = scene.getNumberOfFramesByAtid(atid);
		} else {
			this.framesInAnim = 0;
		}
		double seconds = dist / screenCoordsPerSecond;
		this.run((int) (seconds * 1000.0));

	}

	@Override
	protected void onUpdateGameAction(double progress) {
		if (isStopped)
			return;
		if (progress > 0 && progress < 1) {

			progress = progress * 1.0;
		}
		double x = this.startX + progress * (this.endX - this.startX);
		double y = this.startY + progress * (this.endY - this.startY);

		System.out.println(" walkto-mid " + x + " " + y);

		if (scene.isInANoGoZone(new PointF(x, y))) {
			// if in a nogozone in the middle of moving
			// then the delta(inx,y) may be small enough
			// for us to tell they have moved between two
			// gate points. And if so, then we can fire
			scene.fireOnMovementBeyondAGateIfRelevant(new PointF(x, y));

			// and we make sure we only do this once
			// we don't keep letting the animation try all the points
			// on the line to it's target, because
			// some of these will also be behind some other gate.
			isStopped = true;
			return;
		}

		scene.setBaseMiddleXByOtid(otid, x);
		scene.setBaseMiddleYByOtid(otid, y);

		int frameToSetTo = 0;
		if (this.framesInAnim > 1) {
			double framesPlayedSoFar = framesPlayedDuringWalk
					* (isBackwards ? (1 - progress) : progress);
			frameToSetTo = (int) framesPlayedSoFar % this.framesInAnim;

		}

		scene.setAsACurrentAnimationByAtid(atid);
		scene.setCurrentFrameByOtid(otid, frameToSetTo);
	}

	@Override
	// method in animation
	protected void onCompleteGameAction() {
		if (!scene.isInANoGoZone(new PointF(endX, endY))) {
			onUpdateGameAction(1.0);
			scene.setBaseMiddleXByOtid(otid, endX);
			scene.setBaseMiddleYByOtid(otid, endY);
		}
		if (holdLastFrame == false) {
			if (this.otid != null) {

				scene.setToInitialAnimationWithoutChangingFrameByOtid(otid);
			}
		}
	}

	@Override
	public boolean isParallel() {

		return isParallel;
	}

	@Override
	public void setAll(IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard,
			IInventoryPresenterFromActions inventory) {
		setScene(scene);

	}

	public void setScene(IScenePresenterFromMoveAction scene) {
		this.scene = scene;
	}

}
