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

package com.github.a2g.core.platforms.html4.mouse;


import com.google.gwt.event.dom.client.ClickHandler;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromDialogTreeMouse;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Label;


public class DialogTreeMouseClickHandler implements ClickHandler {
	private int branchId;
	private IMasterPresenterFromDialogTreeMouse master;
	

	public DialogTreeMouseClickHandler(IMasterPresenterFromDialogTreeMouse master, Label label, int branchId) {
		this.branchId = branchId;
		this.master=master;
	}

	@Override
	public void onClick(ClickEvent event) {
		master.saySpeechAndThenExecuteBranchWithBranchId(branchId);

	}
}
