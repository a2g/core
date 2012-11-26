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
import com.github.a2g.core.primitive.ColorEnum;



public class TitleCardAction extends BaseAction {
	String text;
	ColorEnum color;

	public TitleCardAction(BaseAction parent, String text, ColorEnum color) {
		super(parent, parent.getApi());
		this.text = text;
		this.color = color;
	}

	@Override
	public void runGameAction() {

		int totalDuration = getApi().getPopupDelay()*50;
		
		getApi().displayTitleCard(text, color);
		this.run(totalDuration);
	}

	@Override
	protected void onUpdateGameAction(double progress) {

	}

	@Override
	protected void onCompleteGameAction() {
		getApi().displayTitleCard("", color);	
	}

	@Override
	public boolean isParallel() {

		return false;
	}
	
}
