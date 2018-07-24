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
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromSingleCallPerformer; 

public class TitleCardAction extends ChainableAction {
	private IScenePresenterFromSingleCallPerformer titleCard;
	String stringValue;
	double durationInSecs;
	public TitleCardAction(BaseAction parent, String text, double durationInSecs) {
		super(parent);
		this.stringValue = text;
		this.durationInSecs = durationInSecs;
	}

	@Override
	public void runGameAction() {
		double totalInMilliseconds = durationInSecs * 1000;

		titleCard.displayTitleCard(stringValue);
		this.run( (int) totalInMilliseconds);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		titleCard.displayTitleCard(stringValue);
	}

	@Override
	protected boolean onCompleteActionAndCheckForGateExit() {
 		titleCard.displayTitleCard("");
		return false;
	}



	public void setScene(IScenePresenterFromSingleCallPerformer titleCard) {
		this.titleCard = titleCard;
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			IInventoryPresenterFromActions inventory) {
		setScene(scene);

	}

}
