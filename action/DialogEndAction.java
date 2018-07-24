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
import com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromEndAction;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPanelFromMasterPresenter.GuiStateEnum;

public class DialogEndAction extends DialogChainEndAction{
	private IDialogTreePresenterFromEndAction dialogTree;

	public DialogEndAction(BaseAction parent) {
		super(parent );
	}

	@Override
	public void onUpdate(double progress) {
	}

	@Override
	public void runGameAction() {
		super.run(1);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
	}

	@Override
	protected boolean onCompleteActionAndCheckForGateExit() {
		dialogTree.setActiveGuiState(GuiStateEnum.ActiveScene);
		return false;
	}



	public void setDialogTree(IDialogTreePresenterFromEndAction dialogTree) {
		this.dialogTree = dialogTree;
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			IInventoryPresenterFromActions inventory) {
		setDialogTree(dialogTree);
	}

}
