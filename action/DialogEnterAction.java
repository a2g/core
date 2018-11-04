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

import com.github.a2g.core.interfaces.nongame.platform.IPlatformMasterPanel.GuiStateEnum;
import com.github.a2g.core.interfaces.nongame.presenter.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IDialogTreePresenterFromDoBranchAction;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromActions;

public class DialogEnterAction extends BaseAction {

	private int branchId;
	private IDialogTreePresenterFromDoBranchAction dialogTree;

	public DialogEnterAction(int branchId) {
		this.branchId = branchId;
	}

	@Override
	public void onUpdate(double progress) {
	}

	@Override
	public void runGameAction() {
		super.run(1);
	}

	@Override
	protected void onUpdateGameAction(double progress) 
	{
	}

	@Override
	protected boolean onCompleteActionAndCheckForGateExit() 
	{
		dialogTree.setGuiState(GuiStateEnum.OnDialogTree);
		// When this placeholder is encountered in MasterPresenter, it is replaced by what is returned by onDialogTree when it is called with the branchId
		return false;
	}


	public void setBranchId(int branchId) 
	{
		this.branchId = branchId;
	}

	public int getBranchId() 
	{
		return branchId;
	}

	public void setDialogTree(IDialogTreePresenterFromDoBranchAction dialogTree) 
	{
		this.dialogTree = dialogTree;
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			IInventoryPresenterFromActions inventory) 
	{
		setDialogTree(dialogTree);
	}
}
