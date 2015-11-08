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
import com.github.a2g.core.action.performer.SuperWalkPerformer;
import com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.internal.ITitleCardPresenterFromActions;

/**
 * 
 * This only uses a walker and a mover NOT a switcher.
 * As a result there is no collision detection
 * So a plain-old walk, (eg in an action) can
 * end up running over the boundary.
 */
public class WalkAction extends ChainableAction
{
	SuperWalkPerformer mover;
	 

	public WalkAction(BaseAction parent, short ocode) {
		super(parent);
		mover = new SuperWalkPerformer(ocode);
		mover.setToInitialAtEnd(true);// only ChainableAction::walkAndSwitch sets setToInitialAtEnd(false);
		
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory)
	{
		mover.setScene(scene); 
	}

	@Override
	public void runGameAction() {
		double duration = mover.runGameAction();
		this.run((int) (duration));
	}

	@Override
	protected void onUpdateGameAction(double progress) 
	{	
		mover.onUpdateGameAction(progress);
	}

	@Override
	// method in animation
	protected boolean onCompleteGameAction() {
		return mover.onCompleteGameAction();
	}
	void setEndX(double endX) {
		mover.setEndX(endX);
	}

	void setEndY(double endY) {
		mover.setEndY(endY);
	}
 

	public void setToInitialAtEnd(boolean isSetToInitialAtEnd) {
		mover.setToInitialAtEnd(isSetToInitialAtEnd); 
	}

	public void setEndScale(double endScale) {
		mover.setEndScale(endScale);
	}

	public void setStartScale(double startScale) {
		mover.setStartScale(startScale);
	}
}
