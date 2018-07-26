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
import com.github.a2g.core.interfaces.nongame.presenter.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromActions;
import com.google.gwt.touch.client.Point;

public class MoveMaybeSwitchWhilstAnimatingAction extends ChainEndAction{

	MovePerformer mover;
	SwitchPerformer switcher;

	public MoveMaybeSwitchWhilstAnimatingAction(BaseAction parent, short ocode) {
		super(parent);
		mover = new MovePerformer(ocode );
		switcher = new SwitchPerformer(ocode );
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			IInventoryPresenterFromActions inventory)
	{
		mover.setSceneForMover(scene);
		switcher.setSceneForSwitch(scene);
	}

	@Override
	public void runGameAction() {
		switcher.runForSwitch();
		double duration = mover.getRunningDurationForMover();

		this.run((int) (duration * 1000.0));

	}



	@Override
	protected void onUpdateGameAction(double progress) {
		Point pt = mover.onUpdateCalculateForMover(progress);
		switcher.onUpdateForSwitch(progress);
		if(switcher.isInANoGoZone())
			return;
		mover.onUpdateCalculateForMover(progress, pt);
	}

	@Override
	// method in animation
	protected boolean onCompleteActionAndCheckForGateExit() {
		onUpdateGameAction(1.0);
		mover.onCompleteForMover();
		boolean isExited = switcher.onCompleteForSwitch();
		return isExited;
	}







}
