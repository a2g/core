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
import com.google.gwt.touch.client.Point;


public class MovePerformer implements IMovePerformer
{	
	private static final Logger MOVE_PERFORMER = Logger.getLogger(LogNames.MOVE_PERFORMER.toString());
	
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
	final private static double NAN = -1.1234578;
	public MovePerformer(short ocode) 
	{
		this.ocode = ocode;
		this.animatingDelay = 0;
		this.endX = Double.NaN;
		this.endY = Double.NaN;
		this.startX = NAN;
		this.startY = NAN;
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
	public double getRunningDurationForMover() {
		String otid =  scene.getOtidByCode(ocode);
		String atid = scene.getAtidOfCurrentAnimationByOtid(otid);
		if(startX==NAN)
			startX = scene.getBaseMiddleXByOtid(otid);
		if(startY==NAN)
			startY = scene.getBaseMiddleYByOtid(otid);

		if (endX == Double.NaN)
			endX = startX;
		if (endY == Double.NaN)
			endY = startY;

		// distance and time calculations
		double diffX = this.startX - this.endX;
		
		
		//MOVE.log( Level.FINER, "walkto-start {0} {1} to {2} {3}", new Object[]{ startX, startY, endX, endY } );
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
		
		double durationSecs = dist / screenCoordsPerSecond;
		return durationSecs;

	}

	@Override
	public Point onUpdateCalculateForMover(double progress) {
		if (progress > 0 && progress < 1) {

			progress = progress * 1.0;
		}
		double x = this.startX + progress * (this.endX - this.startX);
		double y = this.startY + progress * (this.endY - this.startY);

		//MOVE.log( Level.FINER, "walk to-mid {0} {1}", new Object[]{ x,y} );
		MOVE_PERFORMER.log( Level.FINER, "walk to-mid can add more detail here if in java");		
		return new Point(x,y);
	}
	
	@Override
	public void onUpdateCalculateForMover(double progress, Point pt) {
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
	public Point getStartPtForMover() {
		return new Point(startX,startY);
	}

	@Override
	public Point getEndPtForMover() {
		return new Point(endX,endY);
	}

	@Override
	public void setStartXForMover(double startX) {
		this.startX = startX;
	}
	
	@Override
	public void setStartYForMover(double startY) {
		this.startY = startY;
	}



	public boolean isInitialAtEnd() {
		return setToInitialAtEnd;
	}


}
