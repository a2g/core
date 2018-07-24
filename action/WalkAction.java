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
import com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;

/**
 * 
 * This only uses a walker and a mover NOT a switcher.
 * It is used in cut scenes.
 * 
 */
public class WalkAction extends ChainableAction
{
	WalkMultiPerformer multi;
	
	public WalkAction(BaseAction parent, short ocode) {
		super(parent);
		multi = new WalkMultiPerformer(ocode,false);
		multi.setToInitialAtEnd(true);// only ChainableAction::walkAndSwitch sets setToInitialAtEnd(false);
		
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			IInventoryPresenterFromActions inventory)
	{
		multi.setScene(scene); 
	}

	@Override
	public void runGameAction() {
		double durationSecs = multi.getRunningDuration();
		this.run((int) (durationSecs*1000.0));
	}

	@Override
	protected void onUpdateGameAction(double progress) 
	{	
		multi.onUpdateGameAction(progress);
	}

	@Override
	// method in animation
	protected boolean onCompleteActionAndCheckForGateExit() {
		return multi.onCompleteActionAndCheckForGateExit();
	}
	void setEndX(double endX) {
		multi.setEndX(endX);
	}

	void setEndY(double endY) {
		multi.setEndY(endY);
	}
 

	public void setToInitialAtEnd(boolean isSetToInitialAtEnd) {
		multi.setToInitialAtEnd(isSetToInitialAtEnd); 
	}

	public void setEndScale(double endScale) {
		multi.setEndScale(endScale);
	}

	public void setStartScale(double startScale) {
		multi.setStartScale(startScale);
	}
}
