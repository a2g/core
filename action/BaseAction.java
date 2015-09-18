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

import com.github.a2g.core.action.performer.SingleCallPerformer;
import com.github.a2g.core.interfaces.internal.IActionRunnerFromBaseAction;
import com.github.a2g.core.interfaces.internal.IBaseActionFromSystemAnimation;
import com.github.a2g.core.interfaces.internal.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.internal.ISystemAnimation;
import com.github.a2g.core.interfaces.internal.ITitleCardPresenterFromActions;

public abstract class BaseAction implements IBaseActionFromSystemAnimation {
	private ISystemAnimation systemAnimation;
	private IActionRunnerFromBaseAction callbacks;
	protected BaseAction parent;
	private boolean isLinear;
	protected boolean isParallel;


	// all the abstract have GameAction in them to let
	// the client know they are dealing with GameAction and not Animation

	abstract protected void onUpdateGameAction(double progress);

	abstract protected boolean onCompleteGameAction();

	abstract public void runGameAction();

	abstract public void setAll(
			IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory);

	protected BaseAction(BaseAction parent) {
		this.parent = parent;
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

	void setSystemAnimation(ISystemAnimation systemAnimation) {
		this.systemAnimation = systemAnimation;
		this.systemAnimation.setEaseToAndFrom(!isLinear);
	}

	void setLinear(boolean isLinear)
	{
		this.isLinear = isLinear;
	}


	public BaseAction doBoth(ChainableAction a, ChainableAction b) {
		return a.subroutine(b);
	}

	public ChainableAction doNothing() {
		DoNothingAction a =  new DoNothingAction(this);
		return a;
	}

	public BaseAction getParent() {
		return this.parent;
	}


	@Override
	// method in animation
	public void onComplete() {
		boolean isExitedScene = onCompleteGameAction();

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

	public void setParent(BaseAction parent) {
		this.parent = parent;
	}



	public ChainableAction setVisible(short ocode, boolean isVisible) {
		SingleCallAction a =  new SingleCallAction(this, SingleCallPerformer.Type.SetVisible);
		a.setOCode(ocode);
		a.setBoolean(isVisible);
		return a;
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

	public void setCallbacks(ActionRunner callbacks) {
		this.callbacks = callbacks;
	}



}
