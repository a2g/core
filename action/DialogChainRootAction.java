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

/*
 * DialogChainRootAction is identified by having a null parent.
 */
public class DialogChainRootAction 
extends DialogChainableAction 
{
	public DialogChainRootAction() {
		super(null,true );
	}

	@Override
	public void runGameAction() {
		super.run(1); // the delayed execution trick
	}

	@Override
	protected boolean onCompleteGameAction() {
		return true;
	}

	@Override
	protected void onUpdateGameAction(double progress) {
	}

	@Override
	public void setParent(BaseAction parent) {
		this.parent = parent;
	}

	@Override
	public boolean isParallel() {

		return false;
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) {
		return;// the chain root doesn't need them

	}

	


}
