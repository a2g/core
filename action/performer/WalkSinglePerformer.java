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
 
import com.github.a2g.core.interfaces.nongame.performer.IMovePerformer;
import com.github.a2g.core.interfaces.nongame.performer.IScalePerformer;
import com.github.a2g.core.interfaces.nongame.performer.ISwitchPerformer;
import com.github.a2g.core.interfaces.nongame.performer.IWalkPerformer;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromActions;
import com.google.gwt.touch.client.Point;

/**
 * 
 * This only uses a walker and a mover NOT a switcher.
 * As a result there is no collision detection
 * So a plain-old walk, (eg in an action) can
 * end up running over the boundary.
 */
public class WalkSinglePerformer  
{
	IMovePerformer mover;
	ISwitchPerformer switcher;
	IWalkPerformer walker;
	IScalePerformer scaler; 

	public WalkSinglePerformer(IMovePerformer m, IWalkPerformer w, ISwitchPerformer s, IScalePerformer sc) {
		walker = w;
		switcher = s;
		mover = m;
		scaler = sc;
		mover.setToInitialAtEndForMover(true);// only ChainableAction::walkAndSwitch sets setToInitialAtEnd(false);
	}
 
	public void setScene(IScenePresenterFromActions scene)
	{
		scaler.setSceneForScaler(scene);
		mover.setSceneForMover(scene);
		walker.setSceneForWalker(scene);
		if(switcher!=null)
			switcher.setSceneForSwitch(scene);
	}
 
	public double getRunningDuration() {
		if(switcher!=null)
			switcher.runForSwitch( );
		scaler.runForScaler();
		double duration = mover.getRunningDurationForMover();
		walker.runForWalker(mover.getStartPtForMover(), mover.getEndPtForMover());
		return duration;
	}

	public void onUpdateGameAction(double progress) {
		Point pt = mover.onUpdateCalculateForMover(progress);
		if(switcher!=null)
		{
			switcher.onUpdateForSwitch(progress);
			// in this case the previous line could have switched scenes.
			// or it could have run in to the no-go-zone, in which case
			// we don't want mover updating it to a new position.
			// or else it may access objects which are not there.
			// In both the above cases isStoppedForSwitch is true.
			if(switcher.isInANoGoZone()||switcher.isExitedThruGate())
			{ 
				return;//prevent mover from executing.
			}
			
			
		}
		
		scaler.onUpdateForScaler(progress);
		mover.onUpdateCalculateForMover(progress, pt);
		walker.runForWalker(mover.getStartPtForMover(), mover.getEndPtForMover());
	}


	public boolean onCompleteActionAndCheckForGateExit() {
		// we need to add an ifStoppedForSwitch here, because
		// cancel doesn't cancel straight away, but it calls
		// onComplete...
		if(switcher!=null && switcher.isExitedThruGate())
			return true;
		onUpdateGameAction(1.0);

		// the next line is crucial because the previous line
		// might have just switched scenes.
		// If it has stopped? we still do mover.onCompleteForMover..
		// which kist sets to initial. It doesn't update position.
		// If scene has exited do we not do mover.onCompleteForMover
		// because the Otids referred to in mover and switcher
		// refer to objects in a scene that we've exited from.
		if(switcher!=null && !switcher.isInANoGoZone())
			scaler.onCompleteForScaler();
		mover.onCompleteForMover();
		boolean isExited = switcher!=null? switcher.onCompleteForSwitch() : false; 
		return isExited;

	}

	public void setToInitialAtEnd(boolean isSetToInitialAtEnd) {
		mover.setToInitialAtEndForMover(isSetToInitialAtEnd);
	}

	public void setEndScale(double endScale) {
		scaler.setEndScaleForScaler(endScale);
	}

	public void setStartScale(double startScale) {
		scaler.setStartScaleForScaler(startScale);
	}

	public void setStartX(double startX) {
		mover.setStartXForMover(startX);
		if(switcher!=null)
			switcher.setStartXForSwitcher(startX);
	}

	public void setStartY(double startY) {
		mover.setStartYForMover(startY);
		if(switcher!=null)
			switcher.setStartYForSwitcher(startY);
	}
	
	void setEndX(double endX) {
		mover.setEndXForMover(endX);
		if(switcher!=null)
			switcher.setEndXForSwitch(endX);
	}

	void setEndY(double endY) {
		mover.setEndYForMover(endY);
		if(switcher!=null)
			switcher.setEndYForSwitch(endY);
	}

	public boolean isCancelNeededDueToGateOrNoGoZone() {
		return (switcher!=null &&( switcher.isExitedThruGate() || switcher.isInANoGoZone()));
	}

	public void inchBackIfInNoGoZone() {
		if(switcher!=null && switcher.isInANoGoZone())
		{
			mover.inchBack();
		}
		
	}
	
}
