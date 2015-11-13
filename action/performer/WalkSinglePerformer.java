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

import com.github.a2g.core.action.performer.MovePerformer;
import com.github.a2g.core.action.performer.ScalePerformer;
import com.github.a2g.core.action.performer.WalkPerformer;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.primitive.PointF;

/**
 * 
 * This only uses a walker and a mover NOT a switcher.
 * As a result there is no collision detection
 * So a plain-old walk, (eg in an action) can
 * end up running over the boundary.
 */
public class WalkSinglePerformer  
{
	MovePerformer mover;
	WalkPerformer walker;
	ScalePerformer scaler;

	public WalkSinglePerformer(short ocode) {
		mover = new MovePerformer(ocode);
		mover.setToInitialAtEndForMover(true);// only ChainableAction::walkAndSwitch sets setToInitialAtEnd(false);
		walker = new WalkPerformer(ocode);
		scaler = new ScalePerformer(ocode);
	}
 
	public void setScene( 
			IScenePresenterFromActions scene)
	{

		scaler.setSceneForScaler(scene);
		mover.setSceneForMover(scene);
		walker.setSceneForWalk(scene);
	}
 
	public double runGameAction() {
		
		scaler.runForScaler();
		double duration = mover.runForMover();
		walker.runForWalk(mover.getStartPtForMover(), mover.getEndPtForMover());
		
		return duration * 1000.0;
	}
 
	 public void onUpdateGameAction(double progress) {
		scaler.onUpdateForScaler(progress);
		PointF pt = mover.onUpdateCalculateForMover(progress);
		mover.onUpdateCalculateForMover(progress, pt);
		walker.runForWalk(mover.getStartPtForMover(), mover.getEndPtForMover());
		
	}

	 
	public boolean onCompleteGameAction() {
		onUpdateGameAction(1.0);
		scaler.onCompleteForScaler();
		mover.onCompleteForMover();
		
		return false;
	}
	
	public void setEndX(double endX) {
		mover.setEndXForMover(endX);
	}

	public void setEndY(double endY) {
		mover.setEndYForMover(endY);
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
}
