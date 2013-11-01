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
import com.github.a2g.core.interfaces.PopupPanelAPI;
import com.github.a2g.core.action.ChainedAction;



public class SayWithoutAnimationAction extends ChainedAction {
	private String speech;
	private PopupPanelAPI popup;

	public SayWithoutAnimationAction(BaseAction parent, int objId, String speech, boolean isLinear) {
		super(parent, parent.getApi(), isLinear);

		this.speech = speech;
		this.popup = null;
	}

	@Override
	public void runGameAction() {
		int delay = 0;
		int duration = (this.speech.length()
				* (2 + delay))
				* 40;

		this.popup = this.getApi().getFactory().createPopupPanel( this);
		this.popup.updateText(speech);
		this.popup.setPopupPosition(20, 20);
		this.popup.show();

		this.run(duration);
	}

	@Override
	protected void onUpdateGameAction(double progress) {}

	@Override
	protected void onCompleteGameAction() {
		if (this.popup != null) {
			this.popup.hide();
		}
	}

	@Override
	public boolean isParallel() {

		return false;
	}

}
