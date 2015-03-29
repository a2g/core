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
import com.github.a2g.core.action.ChainedAction;
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromTitleCardAction;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;

public class TitleCardAction extends ChainedAction {
	private ITitleCardPresenterFromTitleCardAction titleCard;
	String text;

	public TitleCardAction(BaseAction parent, String text) {
		super(parent, true);
		this.text = text;
	}

	@Override
	public void runGameAction() {

		if (text.length() > 0) {
			double totalInMilliseconds = 4 * titleCard
					.getPopupDisplayDuration() * 1000;

			titleCard.displayTitleCard(text);
			this.run((int) totalInMilliseconds);
		} else {
			titleCard.displayTitleCard("");
			this.run(1);
		}
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		titleCard.displayTitleCard(text);
	}

	@Override
	protected void onCompleteGameAction() {
		titleCard.displayTitleCard("");
	}

	@Override
	public boolean isParallel() {

		return false;
	}

	public void setTitleCard(ITitleCardPresenterFromTitleCardAction titleCard) {
		this.titleCard = titleCard;
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) {
		setTitleCard(titleCard);

	}

}
