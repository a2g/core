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
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.interfaces.performer.IMovePerformer;
import com.github.a2g.core.interfaces.performer.IScalePerformer;
import com.github.a2g.core.interfaces.performer.ISwitchPerformer;
import com.github.a2g.core.interfaces.performer.IWalkPerformer;
import com.github.a2g.core.primitive.PointF;

public class WalkMaybeSwitchAction extends ChainEndAction
{
	IMovePerformer mover;
	ISwitchPerformer switcher;
	IWalkPerformer walker;
	IScalePerformer scaler;

	public WalkMaybeSwitchAction(BaseAction parent, IMovePerformer m, IWalkPerformer w, ISwitchPerformer s, IScalePerformer sc) {
		super(parent);
		walker = w;
		switcher = s;
		mover = m;
		scaler = sc;
		mover.setToInitialAtEndForMover(true);// only ChainableAction::walkAndSwitch sets setToInitialAtEnd(false);
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory)
	{
		mover.setSceneForMover(scene);
		walker.setSceneForWalk(scene);
		switcher.setSceneForSwitch(scene);
		scaler.setSceneForScaler(scene);
	}

	@Override
	public void runGameAction() {
		switcher.runForSwitch( );
		double duration = mover.runForMover();
		walker.runForWalk(mover.getStartPtForMover(), mover.getEndPtForMover());
		scaler.runForScaler();
		this.run((int) (duration * 1000.0));
	}



	@Override
	protected void onUpdateGameAction(double progress) {
		PointF pt = mover.onUpdateCalculateForMover(progress);
		switcher.onUpdateForSwitch(progress);
		// in this case the previous line could have switched scenes.
		// or it could have run in to the no-go-zone, in which case
		// we don't want mover updating it to a new position.
		// or else it may access objects which are not there.
		// In both the above cases isStoppedForSwitch is true.
		if(switcher.isStoppedForSwitch())
		{
			this.cancel();// process onComplete immediately
			return;//prevent mover from executing.
		}
		mover.onUpdateCalculateForMover(progress, pt);
		scaler.onUpdateForScaler(progress);
	}

	@Override
	protected boolean onCompleteGameAction() {
		onUpdateGameAction(1.0);
		// the next line is crucial because the previous line
		// might have just switched scenes.
		// If it has stopped? we still do mover.onCompleteForMover..
		// which kist sets to initial. It doesn't update position.
		// If scene has exited do we not do mover.onCompleteForMover
		// because the Otids referred to in mover and switcher
		// refer to objects in a scene that we've exited from.
		boolean isExited = switcher.isExitedThruGate();
		if(!isExited)
		{
			mover.onCompleteForMover();
			scaler.onCompleteForScaler();
			isExited = switcher.onCompleteForSwitch();
		}
		return isExited;
	}

	void setEndX(double endX) {
		mover.setEndXForMover(endX);
		switcher.setEndXForSwitch(endX);
	}

	void setEndY(double endY) {
		mover.setEndYForMover(endY);
		switcher.setEndYForSwitch(endY);
	}
}
