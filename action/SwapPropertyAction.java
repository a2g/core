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
import com.github.a2g.core.interfaces.IScenePresenterFromSwapAction;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;

public class SwapPropertyAction extends ChainedAction {
	private IScenePresenterFromSwapAction scene;
	private short ocodeA;
	private short ocodeB;
	private String otidA;
	private String otidB;
	private SwapType type;
	boolean newA;
	boolean newB;

	public SwapPropertyAction(BaseAction parent, short ocodeA, short ocodeB,
			SwapType type) {
		super(parent, true);
		this.ocodeA = ocodeA;
		this.ocodeB = ocodeB;
		this.type = type;
	}

	@Override
	public void runGameAction() {

		super.run(1);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
	}

	@Override
	protected void onCompleteGameAction() {
		otidA = scene.getOtidByCode(ocodeA);
		otidB = scene.getOtidByCode(ocodeB);
		switch (this.type) {
		case Visibility:
			boolean newA = scene.getVisibleByOtid(otidB);
			boolean newB = scene.getVisibleByOtid(otidA);

			scene.setVisibleByOtid(otidA, newA);
			scene.setVisibleByOtid(otidB, newB);

		}

	}

	@Override
	public boolean isParallel() {

		return false;
	}

	public void setScene(IScenePresenterFromSwapAction scene) {
		this.scene = scene;
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) {
		setScene(scene);

	}

}
