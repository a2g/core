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
import com.github.a2g.core.objectmodel.Animation;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.action.ChainedAction;

public class MoveWhilstAnimatingAction extends ChainedAction
{
	private SceneObject obj;// set in constructor

	private double endX;// set via setters
	private double endY;// set via setters
	private double startX;// set via setters
	private double startY;// set via setters
	private int animatingDelay;// set via setters
	private double screenCoordsPerSecond;// set via setters
	private boolean isParallel;// set via setters

	private Animation anim; // set in runGameAction
	private int framesInAnim;// set in runGameAction
	private int framesPlayedDuringWalk;// set in runGameAction
	private boolean isBackwards;
	private boolean holdLastFrame;

	private boolean isStopped;
	
	public MoveWhilstAnimatingAction(BaseAction parent, short objId, double endX, double endY, boolean isLinear)
	{
		super(parent, parent.getApi(), isLinear);
		this.obj = getApi().getObject(objId);
		this.animatingDelay = 0;
		assert(obj!=null);
		this.screenCoordsPerSecond = obj.getScreenCoordsPerSecond();
		
		this.endX = endX;// - getApi().getSceneGui().getCameraX();
		this.endY = endY;// - getApi().getSceneGui().getCameraY();
		this.startX = 0.0;
		this.startY = 0.0;
		this.isParallel = false;
		this.isBackwards = false;
		this.holdLastFrame = true;
		this.isStopped = false;
	}
	
	public void setHoldLastFrame(boolean holdLastFrame)
	{
		this.holdLastFrame = holdLastFrame;
	}
	SceneObject getObject(){return this.obj;}
	double getEndX(){ return endX;}
	double getEndY(){ return endY;}
	double getStartX(){ return startX;}
	double getStartY(){ return startY;}

	void setNonBlocking(boolean isParallel){ this.isParallel = isParallel;}
	void setAnimatingDelay(int delay){ this.animatingDelay = delay;}

	@Override
	public void runGameAction()
	{

		this.anim = this.obj.getAnimations().at(obj.getCurrentAnimation());
		startX = getObject().getBaseMiddleX();
		startY = getObject().getBaseMiddleY();

		// distance and time calculations
		double diffX = this.startX - this.endX;
		System.out.println(" walkto-start " + startX + " " + startY +" to "+endX + " " + endY);
		double diffY = this.startY - this.endY;
		double diffXSquared = diffX * diffX;
		double diffYSquared = diffY * diffY;
		double dist = Math.sqrt(
				diffXSquared + diffYSquared);

		this.framesPlayedDuringWalk = (int) (dist* 40 + animatingDelay);

		if (this.anim != null) {
			this.framesInAnim = this.anim.getLength();
		} else {
			this.framesInAnim = 0;
		}
		double seconds = dist / screenCoordsPerSecond;
		this.run((int)(seconds*1000.0));

	}

	@Override
	protected void onUpdateGameAction(double progress) {
		if(isStopped)
			return;
		if(progress>0 && progress <1)
		{

			progress = progress*1.0;
		}
		double x = this.startX
				+ progress
				* (this.endX
						- this.startX);
		double y = this.startY
				+ progress
				* (this.endY
						- this.startY);

		System.out.println(" walkto-mid " + x + " " + y );

		if(getApi().isInANoGoZone(new PointF(x,y)))
		{
			// if in a nogozone in the middle of moving
			// then the delta(inx,y) may be small enough
			// for us to tell they have moved between two 
			// gate points. And if so, then we can fire
			getApi().fireOnMovementBeyondAGateIfRelevant(new PointF(x,y));
			
			// and we make sure we only do this once
			// we don't keep letting the animation try all the points
			// on the line to it's target, because
			// some of these will also be behind some other gate.
			isStopped = true;
			return;
		}

		this.obj.setBaseMiddleX(x);
		this.obj.setBaseMiddleY(y);

		int frameToSetTo =0;
		if(this.framesInAnim >1)
		{
			double framesPlayedSoFar = framesPlayedDuringWalk * (isBackwards? (1 - progress)
					: progress);
			frameToSetTo = (int)framesPlayedSoFar % this.framesInAnim;

		}
		if (obj != null) {
			obj.setCurrentAnimation(anim.getTextualId());
			obj.setCurrentFrame(frameToSetTo);
		}


	}

	@Override // method in animation
	protected void onCompleteGameAction()
	{
		if(!getApi().isInANoGoZone(new PointF(endX,endY)))
		{
			onUpdateGameAction(1.0);
			this.obj.setBaseMiddleX(endX);
			this.obj.setBaseMiddleY(endY);
		}
		if (holdLastFrame==false) {
			if (this.obj != null)
			{

				obj.setToInitialAnimationWithoutChangingFrame();
			}
		}
	}

	@Override
	public boolean isParallel() {

		return isParallel;
	}


}
