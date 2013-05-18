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



public class TitleCardAction extends ChainedAction {
	String text;

	public TitleCardAction(BaseAction parent, String text) {
		super(parent, parent.getApi(), true);
		this.text = text;
	}

	@Override
	public void runGameAction() {

		if(text.length()>0)
		{
			int totalDuration = 200*getApi().getPopupDelay();

			getApi().displayTitleCard(text);
			this.run(totalDuration);
		}
		else
		{
			getApi().displayTitleCard("");
			this.run(1);
		}
	}

	@Override
	protected void onUpdateGameAction(double progress) {
		getApi().displayTitleCard(text);
	}

	@Override
	protected void onCompleteGameAction() {
		getApi().displayTitleCard("");
	}

	@Override
	public boolean isParallel() {

		return false;
	}

}
