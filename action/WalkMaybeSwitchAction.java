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
import com.github.a2g.core.action.performer.WalkMultiPerformer;
import com.github.a2g.core.interfaces.nongame.presenter.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromActions;


/**
 * 
 * This is the workhorse. Used for most
 * interactive walk to commands.
 * It triggers a path find, that occurs in WalkMulti
 */
public class WalkMaybeSwitchAction extends ChainEndAction
{
	WalkMultiPerformer multiWalker;

	public WalkMaybeSwitchAction(BaseAction parent, short ocode) {
		super(parent);
		multiWalker = new WalkMultiPerformer(ocode, true);
		multiWalker.setToInitialAtEnd(true);// only ChainableAction::walkAndSwitch sets setToInitialAtEnd(false);
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			IInventoryPresenterFromActions inventory)
	{
		multiWalker.setScene(scene);
	}

	@Override
	public void runGameAction() {
		double duration = multiWalker.getRunningDuration();
		this.run((int) (duration * 1000.0));
	}



	@Override
	protected void onUpdateGameAction(double progress) {
		
		multiWalker.onUpdateGameAction(progress);
		// in this case the previous line could have switched scenes.
		// or it could have run in to the no-go-zone, in which case
		// we don't want any more updates. So we cancel immediately.
		// This triggers onComplete
		if(multiWalker.isCancelNeededDueToGateOrNoGoZone())
		{
			this.cancel();// process onComplete immediately
		}
	}

	@Override
	protected boolean onCompleteActionAndCheckForGateExit() {
		
		boolean isExited = multiWalker.onCompleteActionAndCheckForGateExit();
		return isExited;
	}

	void setEndX(double endX) {
		multiWalker.setEndX(endX);
	}

	void setEndY(double endY) {
		multiWalker.setEndY(endY);
	}
}
