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
import com.github.a2g.core.action.performer.LookPerformer;
import com.github.a2g.core.interfaces.nongame.presenter.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromLookPerformer;

public class LookAction extends BaseAction {
	LookPerformer looker;

	public LookAction(String atid, double durationInSeconds) {
		looker = new LookPerformer(atid, durationInSeconds);
	}
	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			IInventoryPresenterFromActions inventory)
	{
		looker.setScene(scene);
	}


	@Override
	public void runGameAction() {
		double duration = looker.run();
		this.run((int)(duration*1000));
	}

	@Override
	protected void onUpdateGameAction(double progress) {

		looker.onUpdate(progress);
	}

	@Override
	protected boolean onCompleteActionAndCheckForGateExit() {
		boolean result = looker.onComplete();
		return result;
	}


	public void setScene(IScenePresenterFromLookPerformer scene) {
		looker.setScene(scene);
	}

}
