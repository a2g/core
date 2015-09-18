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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.a2g.core.interfaces.internal.IScenePresenterFromMovePerformer;
import com.github.a2g.core.interfaces.performer.IMovePerformer;
import com.github.a2g.core.primitive.LogNames;
import com.github.a2g.core.primitive.PointF;


public class MovePerformer implements IMovePerformer
{	
	private static final Logger MOVE = Logger.getLogger(LogNames.MOVE);
	
	private IScenePresenterFromMovePerformer scene;

	private short ocode;// set in constructor

	private double endX;// set via setters
	private double endY;// set via setters
	private double startX;// set via setters
	private double startY;// set via setters
	private int animatingDelay;// set via setters
	private boolean isBackwards; // via setters
	private boolean setToInitialAtEnd; // via setters

	private int framesPlayedDuringMove;// set in runGameAction
	
	public MovePerformer(short ocode) 
	{
		this.ocode = ocode;
		this.animatingDelay = 0;
		this.endX = Double.NaN;
		this.endY = Double.NaN;
		this.startX = 0.0;
		this.startY = 0.0;
		this.isBackwards = false;
		this.setToInitialAtEnd = false;
	}

	 

	@Override
	public void setEndXForMover(double endX) {
		this.endX = endX;
	}

	@Override
	public void setEndYForMover(double endY) {
		this.endY = endY;
	}

	@Override
	public void setToInitialAtEndForMover(boolean setToInitialAtEnd) {
		this.setToInitialAtEnd = setToInitialAtEnd;
	}
  
	public void setAnimatingDelay(int delay) {
		this.animatingDelay = delay;
	}
	
	public void setIsBackwards(boolean isBackwards)
	{
		this.isBackwards = isBackwards;
	}
	

	@Override
	public double runForMover() {
		String otid =  scene.getOtidByCode(ocode);
		
		String atid = scene.getAtidOfCurrentAnimationByOtid(otid);
		startX = scene.getBaseMiddleXByOtid(otid);
		startY = scene.getBaseMiddleYByOtid(otid);

		if (endX == Double.NaN)
			endX = startX;
		if (endY == Double.NaN)
			endY = startY;

		// distance and time calculations
		double diffX = this.startX - this.endX;
		
		
		MOVE.log( Level.FINER, "walkto-start {0} {1} to {2} {3}", new Object[]{ startX, startY, endX, endY } );
		double diffY = this.startY - this.endY;
		double diffXSquared = diffX * diffX;
		double diffYSquared = diffY * diffY;
		double dist = Math.sqrt(diffXSquared + diffYSquared);

		this.framesPlayedDuringMove = (int) (dist * 40 + animatingDelay);
		double screenCoordsPerSecond = scene.getScreenCoordsPerSecondByOtid(otid);
		
		

		// we do this here - not in update()
		// because walker needs a chance to override this...
		// and walker doesn't get called every update.
		// Also, if we called change animation every update
		// then we could get weird results, as frame number
		// is clamped when we go from a long to a short animation.
		scene.setCurrentAnimationByAtid(atid);
		
		double duration = dist / screenCoordsPerSecond;
		return duration;

	}

	@Override
	public PointF onUpdateCalculateForMover(double progress) {
		if (progress > 0 && progress < 1) {

			progress = progress * 1.0;
		}
		double x = this.startX + progress * (this.endX - this.startX);
		double y = this.startY + progress * (this.endY - this.startY);

		MOVE.log( Level.FINER, "walkto-mid {0} {1}", new Object[]{ x,y} );
		
		return new PointF(x,y);
	}
	
	@Override
	public void onUpdateCalculateForMover(double progress, PointF pt) {
		String otid = scene.getOtidByCode(ocode);
		String atid = scene.getAtidOfCurrentAnimationByOtid(otid);
		
		int framesInAnim = (atid != null) ? scene.getNumberOfFramesByAtid(atid) : 0;
		 
		scene.setBaseMiddleXByOtid(otid, pt.getX());
		scene.setBaseMiddleYByOtid(otid, pt.getY());

		int frameToSetTo = 0;
		if (framesInAnim > 1) {
			double framesPlayedSoFar = framesPlayedDuringMove
					* (isBackwards ? (1 - progress) : progress);
			frameToSetTo = (int) framesPlayedSoFar % framesInAnim;

		}

		scene.setCurrentFrameByOtid(otid, frameToSetTo);
	}

	@Override
	public void onCompleteForMover() {
		// just set to initial if needed
		if (setToInitialAtEnd == true) {
			String otid = scene.getOtidByCode(ocode);
			if (otid != null) {
				scene.setToInitialAnimationWithoutChangingFrameByOtid(otid);
			}
		}
	}

	@Override
	public void setSceneForMover(IScenePresenterFromMovePerformer scene) {
		this.scene = scene;
	}

	@Override
	public PointF getStartPtForMover() {
		return new PointF(startX,startY);
	}

	@Override
	public PointF getEndPtForMover() {
		return new PointF(endX,endY);
	}

}
