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
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromSetToInitialPosAction;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;


public class SetToInitialPositionAction extends ChainedAction {
	private IScenePresenterFromSetToInitialPosAction scene;
	short ocode;
	private String otid;

	public SetToInitialPositionAction(BaseAction parent, short ocode) {
		super(parent, true);
		this.ocode = ocode;
	}

	@Override
	public void runGameAction() {
		super.run(0);
	}

	@Override
	protected void onUpdateGameAction(double progress)
	{
	
	}

	@Override
	protected void onCompleteGameAction() 
	{
		this.otid = scene.getOtidByCode(ocode);
		scene.setXByOtid(otid,0);
		scene.setYByOtid(otid,0);
	}

	@Override
	public boolean isParallel() {

		return false;
	}
 

	public void setScene(IScenePresenterFromSetToInitialPosAction scene) {
		this.scene = scene;
	}

	@Override
	public void setAll(IScenePresenterFromActions scene, IDialogTreePresenterFromActions dialogTree, ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) {
		setScene(scene);
		
	}

}
