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
import com.github.a2g.core.action.performer.SwitchPerformer;
import com.github.a2g.core.action.performer.WalkPerformer;
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.interfaces.performer.IMovePerformer;
import com.github.a2g.core.interfaces.performer.ISwitchPerformer;
import com.github.a2g.core.interfaces.performer.IWalkPerformer;
import com.github.a2g.core.primitive.PointF;

public class WalkMaybeSwitchAction extends ChainEndAction
{
	IMovePerformer mover;
	ISwitchPerformer switcher;
	IWalkPerformer walker;

	public WalkMaybeSwitchAction(BaseAction parent, IMovePerformer m, IWalkPerformer w, ISwitchPerformer s) {
		super(parent);
		walker = w;
		switcher = s;
		mover = m;
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
	}

	@Override
	public void runGameAction() {
		switcher.runForSwitch( );
		double duration = mover.runForMover();
		walker.runForWalk(mover.getStartPtForMover(), mover.getEndPtForMover());
		this.run((int) (duration * 1000.0));
	}



	@Override
	protected void onUpdateGameAction(double progress) {
		PointF pt = mover.onUpdateCalculateForMover(progress);
		switcher.onUpdateForSwitch(progress);
		if(switcher.isStoppedForSwitch()) 
			return;
		mover.onUpdateCalculateForMover(progress, pt);
	}

	@Override
	protected boolean onCompleteGameAction() { 
		onUpdateGameAction(1.0);
		if(!switcher.isStoppedForSwitch())//<- this line is crucial or man will slide in initial pos. Not sure why, I think its in the scenario where switcher starts destroying the scene first?
		{
			mover.onCompleteForMover();
		}
		boolean isExited = switcher.onCompleteForSwitch();
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
