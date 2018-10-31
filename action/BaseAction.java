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

import com.github.a2g.core.interfaces.nongame.IActionRunnerFromBaseAction;
import com.github.a2g.core.interfaces.nongame.IBaseActionFromSystemAnimation;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformAnimation;
import com.github.a2g.core.interfaces.nongame.presenter.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromActions;
import com.github.a2g.core.objectmodel.ChainRunner;
/**
 * documentation for BaseAction
 * @author Admin
 *
 */
public abstract class BaseAction 
implements IBaseActionFromSystemAnimation {
	private IPlatformAnimation systemAnimation;
	private IActionRunnerFromBaseAction callbacks;
	protected BaseAction parent;
	private boolean isLinear;
	protected boolean isParallel;


	// all the abstract have GameAction in them to let
	// the client know they are dealing with GameAction and not Animation

	abstract protected void onUpdateGameAction(double progress);

	abstract protected boolean onCompleteActionAndCheckForGateExit();

	abstract public void runGameAction();

	abstract public void setAll(
			IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			IInventoryPresenterFromActions inventory);

	protected BaseAction() {
		this.callbacks = null;// initd in setcallbacks
		this.systemAnimation = null;// initd in setFactory
		this.isParallel = false;
		this.isLinear = false;
	}
	void setParallel(boolean parallel)
	{
		this.isParallel = parallel;
	}

	public boolean isParallel() {
		return isParallel;
	}

	public void setSystemAnimation(IPlatformAnimation systemAnimation) {
		this.systemAnimation = systemAnimation;
		this.systemAnimation.setEaseToAndFrom(!isLinear);
	}

	void setLinear(boolean isLinear)
	{
		this.isLinear = isLinear;
	}

	@Override
	// method in animation
	public void onComplete() {
		boolean isExitedScene = onCompleteActionAndCheckForGateExit();

		// callbacks can be null in a unit test
		if(!isExitedScene && this.callbacks!=null)
		{
			this.callbacks.startTheNextAction(this);
		}
	}

	@Override
	public void onUpdate(double progress) {
		onUpdateGameAction(progress);
	}

	public void cancel() {
		systemAnimation.cancel();
	}

	public void run(int i) {
		if(systemAnimation!=null)// can be null during testing.
		{
			systemAnimation.run(i);
		}
	}

	public void setCallbacks(ChainRunner callbacks) {
		this.callbacks = callbacks;
	}
}
