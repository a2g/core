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
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.action.performer.TalkPerformer.NonIncrementing;

public class DialogTreeTalkAction extends DialogChainableAction {
	TalkPerformer performer;
	private boolean isParallel;
	 
	 

	public DialogTreeTalkAction(BaseAction parent, String atid, String fullSpeech) {
		super(parent, true);
		performer = new TalkPerformer(atid, fullSpeech);
		 
		this.isParallel = false;
		 
	}

	 
	@Override
	public void runGameAction() {
		double duration = performer.initializeAndReturnDuration();
		this.run((int)(duration*1000));
	}

	@Override
	protected void onUpdateGameAction(double progress) {

		performer.onUpdateGameAction(progress);
	}

	@Override
	protected boolean onCompleteGameAction() {
		boolean result = performer.onCompleteGameAction();
		return result;
	}

	@Override
	public boolean isParallel() {

		return isParallel;
	}


	public void setNonBlocking(boolean b) {
		isParallel = b;
	}

	public void setHoldLastFrame(boolean isHoldLastFrame) {
		performer.setHoldLastFrame(isHoldLastFrame);
	}

	public void setNonIncrementing(NonIncrementing nonIncrementing) {
		performer.setNonIncrementing(nonIncrementing);
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) 
	{
		performer.setMaster(master);
		performer.setTitleCard(titleCard);
		performer.setScene(scene);
	}
}
