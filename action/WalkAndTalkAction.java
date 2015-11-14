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
import com.github.a2g.core.action.performer.MovePerformer;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.action.performer.TalkPerformer.NonIncrementing;
import com.github.a2g.core.action.performer.WalkPerformer;
import com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromMoveAction;
import com.github.a2g.core.interfaces.internal.ITitleCardPresenterFromActions;
import com.github.a2g.core.primitive.PointF;

/**
 * 
 * This only uses a walk,talk and move NOT switch.
 * As a result there is no collision detection
 * So a walk-talk (eg in an action), can
 * end up running over the boundary.
 */
public class WalkAndTalkAction extends ChainableAction{

	MovePerformer mover;
	WalkPerformer walker;
	TalkPerformer talker;
	public WalkAndTalkAction(BaseAction parent, short ocode, String speech) {
		super(parent);
		mover = new MovePerformer(ocode);
		mover.setToInitialAtEndForMover(true);// only ChainableAction::walkAndSwitch sets setToInitialAtEnd(false)
		walker = new WalkPerformer(ocode);
		talker = new TalkPerformer(TalkPerformer.SCENE_TALKER, speech);
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory)
	{
		walker.setSceneForWalker(scene);
		mover.setSceneForMover(scene);
		talker.setScene(scene);
		talker.setMaster(master);
	}



	@Override
	public void runGameAction() {
		// it is important run goes first because run
		// sets some non-walking animation..
		// but we want the walk animation set.
		talker.run();
		double duration = mover.getRunningDurationForMover();
		walker.runForWalker(mover.getStartPtForMover(), mover.getEndPtForMover());

		this.run((int) (duration * 1000.0));

	}

	@Override
	protected void onUpdateGameAction(double progress) {
		PointF pt = mover.onUpdateCalculateForMover(progress);
		mover.onUpdateCalculateForMover(progress, pt);
		//talker.onUpdate(progress);

	}

	@Override
	// method in animation
	protected boolean onCompleteActionAndCheckForGateExit() {
		onUpdateGameAction(1.0);
		talker.onComplete();
		mover.onCompleteForMover();
		return false;
	}




	void setEndX(double endX) {
		mover.setEndXForMover(endX);
	}

	void setEndY(double endY) {
		mover.setEndYForMover(endY);
	}

	public void setScene(IScenePresenterFromMoveAction scene) {
		mover.setSceneForMover(scene);
	}


	public void setNonIncrementing(NonIncrementing value) {
		talker.setNonIncrementing(value);
	}

}
