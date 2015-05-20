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

import com.github.a2g.core.action.PlayAnimationAction;
import com.github.a2g.core.action.PlayAnimationRepeatWhilstVisibleAction;
import com.github.a2g.core.action.TalkAction;
import com.github.a2g.core.action.SetInventoryVisibleAction;
import com.github.a2g.core.action.DialogTreeBranchAction;
import com.github.a2g.core.action.DialogTreeEndAction;
import com.github.a2g.core.action.DialogTreeDoDialogBranchAction;
import com.github.a2g.core.action.BaseDialogTreeAction;
import com.github.a2g.core.interfaces.IActionRunnerFromBaseAction;
import com.github.a2g.core.interfaces.ISystemAnimation;
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.interfaces.IBaseActionFromSystemAnimation;

public abstract class BaseAction implements IBaseActionFromSystemAnimation {
	private ISystemAnimation systemAnimation;
	private IActionRunnerFromBaseAction callbacks;
	protected BaseAction parent;
	private boolean isLinear;
 

	// all the abstract have GameAction in them to let
	// the client know they are dealing with GameAction and not Animation
	abstract public boolean isParallel();

	abstract protected void onUpdateGameAction(double progress);

	abstract protected void onCompleteGameAction();

	abstract public void runGameAction();

	abstract public void setAll(
			IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory);

	protected BaseAction(BaseAction parent, boolean isLinear) {
		this.parent = parent;
		this.isLinear = isLinear;
		this.callbacks = null;// initd in setcallbacks
		this.systemAnimation = null;// initd in setFactory
	}

	void setSystemAnimation(ISystemAnimation systemAnimation) {
		this.systemAnimation = systemAnimation;
		this.systemAnimation.setLinear(isLinear);
	}

	public BaseDialogTreeAction branch(int branchId, String text) {
		return new DialogTreeBranchAction(this, text, branchId);
	}

	public ChainedAction branch(int branchId, final boolean isKey, String text) {
		if (!isKey)
			return doNothing();
		DialogTreeBranchAction a = new DialogTreeBranchAction(this, text,
				branchId);
		return a;
	}

	public ChainedAction doBoth(ChainedAction a, ChainedAction b) {
		return a.subroutine(b);
	}

	public ChainedAction doNothing() {
		DoNothingAction a =  new DoNothingAction(this);
		return a;
	}

	public BaseDialogTreeAction endDialogTree() {
		return new DialogTreeEndAction(this);
	}

	public BaseAction getParent() {
		return this.parent;
	}

	public BaseDialogTreeAction doDialogBranch(int branchId) {
		return new DialogTreeDoDialogBranchAction(this, branchId);
	}

	@Override
	// method in animation
	public void onComplete() {
		onCompleteGameAction();

		// callbacks can be null in a unit test
		if(this.callbacks!=null)
		{
			this.callbacks.startTheNextAction(this);
		}
	}

	@Override
	public void onUpdate(double progress) {
		onUpdateGameAction(progress);
	}

	// plain..
	public ChainedAction playAnimation(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		return a;
	}

	// simple backwards
	public ChainedAction playAnimationBackwards(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setBackwards(true);
		return a;
	}

	// simple hold last frame
	public ChainedAction playAnimationHoldLastFrame(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setHoldLastFrame(true);
		return a;
	}

	// simple non blocking
	public ChainedAction playAnimationNonBlocking(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setNonBlocking(true);
		return a;
	}

	// double combo1of3: backwards + hold last frame
	public ChainedAction playAnimationBackwardsHoldLastFrame(
			String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setBackwards(true);
		a.setHoldLastFrame(true);
		return a;
	}

	// double combo2of3: backwards + nonblocking
	public ChainedAction playAnimationBackwardsNonBlocking(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setBackwards(true);
		a.setNonBlocking(true);
		return a;
	}

	// double combo2of3: holdLastFrame + nonblocking
	public ChainedAction playAnimationHoldLastFrameNonBlocking(
			String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		return a;
	}

	// ..and one method with the whole lot!
	public ChainedAction playAnimationBackwardsHoldLastFrameNonBlocking(
			String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setBackwards(true);
		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction playAnimationRepeatWhilstVisible(String animationCode) {
		boolean isLinear = true;
		return new PlayAnimationRepeatWhilstVisibleAction(this, animationCode,
				isLinear);
	}

	public ChainedAction talk(String animCode, String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		return s;
	}

	public ChainedAction talk(String speech) {
		TalkAction s = new TalkAction(this, TalkAction.SCENE_TALKER, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.FromAPI);
		return s;
	}

	public ChainedAction talkWithoutIncrementingFrame(String animCode,
			String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		return s;
	}

	public ChainedAction talkWithoutIncrementingHoldLastFrame(String animCode,
			String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		s.setHoldLastFrame(true);
		return s;
	}

	public ChainedAction talkWithoutIncrementingFrameNonBlocking(
			String animCode, String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		s.setNonBlocking(true);
		return s;
	}

	public ChainedAction talkWithoutIncrementingFrame(String speech) {
		TalkAction s = new TalkAction(this, TalkAction.SCENE_TALKER, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		return s;
	}

	public ChainedAction talkWithoutIncrementingFrameNonBlocking(String speech) {
		TalkAction s = new TalkAction(this, TalkAction.SCENE_TALKER, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		s.setNonBlocking(true);
		return s;
	}

	public ChainedAction setCurrentAnimation(String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetCurrentAnimation );
		a.setAtid(atid);
		return a;
	}
public ChainedAction setCurrentAnimationAndFrame(String atid, int frame) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetCurrentAnimationAndFrame );
		a.setAtid(atid);
		a.setInt(frame);
		return a;
	}
	public ChainedAction setCurrentFrame(short ocode, int frame) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetCurrentFrame );
		a.setOCode(ocode);
		a.setInt(frame);
		return a;
	}

	public ChainedAction setBaseMiddleX(short ocode, double x) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetBaseMiddleX);
		a.setOCode(ocode);
		a.setDouble(x);
		return a;
	}

	public ChainedAction setBaseMiddleY(short ocode, double y) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetBaseMiddleX);
		a.setOCode(ocode);
		a.setDouble(y);
		return a;
	}

	public void setCallbacks(IActionRunnerFromBaseAction callbacks) {
		this.callbacks = callbacks;
	}

	public ChainedAction setDisplayName(short ocode, String displayName) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetDisplayName);
		a.setOCode(ocode);
		a.setString(displayName);
		return a;
	}

	public ChainedAction setInventoryVisible(int inventoryId, boolean isVisible) {
		return new SetInventoryVisibleAction(this, inventoryId, isVisible, true);
	}

	public void setParent(BaseAction parent) {
		this.parent = parent;
	}

	public ChainedAction setVisible(short ocode, boolean isVisible) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetVisible);
		a.setOCode(ocode);
		a.setBoolean(isVisible);
		return a;
	}





	public void cancel() {
		systemAnimation.cancel();

	}

	public void run(int i) {
		systemAnimation.run(i);

	}

}
