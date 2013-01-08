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
import com.github.a2g.core.action.SayAction;
import com.github.a2g.core.action.SayWithoutAnimationAction;
import com.github.a2g.core.action.SetActiveAnimationAction;
import com.github.a2g.core.action.SetActiveFrameAction;
import com.github.a2g.core.action.SetBaseMiddleXAction;
import com.github.a2g.core.action.SetBaseMiddleYAction;
import com.github.a2g.core.action.SetDisplayNameAction;
import com.github.a2g.core.action.SetHomeAnimationAction;
import com.github.a2g.core.action.SetInventoryVisibleAction;
import com.github.a2g.core.action.SetTalkingAnimationAction;
import com.github.a2g.core.action.SetTalkingAnimationDelayAction;
import com.github.a2g.core.action.SetVisibleAction;
import com.github.a2g.core.action.SleepAction;
import com.github.a2g.core.action.SwapPropertyAction;
import com.github.a2g.core.action.SwitchToAction;
import com.github.a2g.core.action.DialogTreeBranchAction;
import com.github.a2g.core.action.DialogTreeEndAction;
import com.github.a2g.core.action.DialogTreeDoDialogBranchAction;
import com.github.a2g.core.action.WaitForFrameAction;
import com.github.a2g.core.action.WalkToAction;
import com.github.a2g.core.action.BaseDialogTreeAction;
import com.github.a2g.core.interfaces.ActionCallbackAPI;
import com.github.a2g.core.interfaces.AnimationEnumAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.SystemAnimationAPI;
import com.github.a2g.core.interfaces.SystemAnimationCallbackAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.PointF;
import com.visuals.inventories._70_piranha_inventory.i;



public abstract class BaseAction implements SystemAnimationCallbackAPI
{
	private SystemAnimationAPI systemAnimation;
	private ActionCallbackAPI callbacks;
	protected BaseAction parent;
	private InternalAPI api;

	public enum SwapType {
		Visibility
	}

	// all the abstract have GameAction in them to let
	// the client know they are dealing with GameAction and not Animation
	abstract public boolean isParallel();

	abstract protected void onUpdateGameAction(double progress);

	abstract protected void onCompleteGameAction();

	abstract public void runGameAction();

	protected BaseAction(BaseAction parent, InternalAPI api) {
		this.parent = parent;
		this.callbacks = null;
		this.api = api;
		if(api!=null)
		{
			this.systemAnimation = api.getFactory().createSystemAnimation(this);
		}else
		{
			throw new NullPointerException();
		}
	}


	public BaseDialogTreeAction branch(int branchId, String text) {
		return new DialogTreeBranchAction(this,
				text, branchId);
	}

	public BaseAction branch(int branchId, final boolean isKey, String text) {
		if(isKey)
			 return new DialogTreeBranchAction(this, text, branchId);
		return doNothing();
	}

	public BaseAction doBoth(BaseAction a, BaseAction b) {
		return a.subroutine(b);
	}

	public BaseDialogTreeAction doNothing() {
		return new DoNothingAction(this);
	}

	public BaseDialogTreeAction endDialogTree() {
		return new DialogTreeEndAction(this);
	}

	public InternalAPI getApi() {
		return this.api;
	}

	public BaseAction getParent() {
		return this.parent;
	}

	public BaseDialogTreeAction doDialogBranch(int branchId) {
		return new DialogTreeDoDialogBranchAction(this,
				branchId);
	}

	@Override // method in animation
	public void onComplete() {

		// It might seem like it's a better idea to 
		// run this actions action completion handler 
		// before calling startTheNextAction.
		// However doDialogBranchAction starts execution of another animation tree
		// and if order is 1) onCompleteGameAction 2) startTheNextAction,
		// then on startTheNextAction starts triggering animations in the 
		// newly replenished series of animations that onCompleteGameAction created,
		// giving effectively two simultaneous thrads of
		// executions of the Action chain. This results in an observed bug
		// where only every second Action runs.
		// So either a second action runner instance is needed for dialogs. Or, simpler 
		// , the call order is kept like this.
		this.callbacks.startTheNextAction(
				this);

		onCompleteGameAction();
	}

	@Override
	public void onUpdate(double progress) 
	{
		onUpdateGameAction(progress);
	}

	// plain..
	public BaseAction playAnimation(AnimationEnumAPI animId) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		return a;
	}

	public BaseAction playAnimation(AnimationEnumAPI animId, int delay) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setDelay(delay);
		return a;

	}

	// simple backwards
	public BaseAction playAnimationBackwards(AnimationEnumAPI animId) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setBackwards(true);
		return a;
	}

	public BaseAction playAnimationBackwards(AnimationEnumAPI animId, int delay) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setBackwards(true);
		a.setDelay(delay);
		return a;
	}

	// simple hold last frame
	public BaseAction playAnimationHoldLastFrame(AnimationEnumAPI animId) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setHoldLastFrame(true);
		return a;
	}

	public BaseAction playAnimationHoldLastFrame(AnimationEnumAPI animId, int delay) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setHoldLastFrame(true);
		a.setDelay(delay);
		return a;
	}

	// simple non blocking
	public BaseAction playAnimationNonBlocking(AnimationEnumAPI animId) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setNonBlocking(true);
		return a;
	}

	public BaseAction playAnimationNonBlocking(AnimationEnumAPI animId, int delay) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setNonBlocking(true);
		a.setDelay(delay);
		return a;
	}	

	// double combo1of3: backwards + hold last frame
	public BaseAction playAnimationBackwardsHoldLastFrame(AnimationEnumAPI animId) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setBackwards(true);
		a.setHoldLastFrame(true);
		return a;
	}

	public BaseAction playAnimationBackwardsHoldLastFrame(AnimationEnumAPI animId, int delay) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setBackwards(true);
		a.setHoldLastFrame(true);
		a.setDelay(delay);
		return a;
	}

	// double combo2of3: backwards + nonblocking
	public BaseAction playAnimationBackwardsNonBlocking(AnimationEnumAPI animId) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setBackwards(true);
		a.setNonBlocking(true);
		return a;
	}

	public BaseAction playAnimationBackwardsNonBlocking(AnimationEnumAPI animId, int delay) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setBackwards(true);
		a.setNonBlocking(true);
		a.setDelay(delay);
		return a;
	}

	// double combo2of3: holdLastFrame + nonblocking
	public BaseAction playAnimationHoldLastFrameNonBlocking(AnimationEnumAPI animId) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		return a;
	}

	public BaseAction playAnimationHoldLastFrameNonBlocking(AnimationEnumAPI animId, int delay) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		a.setDelay(delay);
		return a;
	}

	// ..and one method with the whole lot!
	public BaseAction playAnimationBackwardsHoldLastFrameNonBlocking(AnimationEnumAPI animId) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setBackwards(true);
		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		return a;
	}

	public BaseAction playAnimationBackwardsHoldLastFrameNonBlocking(AnimationEnumAPI animId, int delay) {
		PlayAnimationAction a = new PlayAnimationAction(
				this, animId);

		a.setBackwards(true);
		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		a.setDelay(delay);
		return a;
	}

	public BaseAction playAnimationRepeatWhilstVisible(AnimationEnumAPI animId) {
		return new PlayAnimationRepeatWhilstVisibleAction(
				this, animId);
	}

	public BaseAction say(short objectCode, String speech) {
		return new SayAction(this, objectCode,
				speech);
		// return toReturn;
	}

	public BaseAction sayWithoutAnimation(short objectCode, String speed) {
		return new SayWithoutAnimationAction(
				this, objectCode, speed);
	}

	public BaseAction setActiveAnimation(AnimationEnumAPI animId) {
		return new SetActiveAnimationAction(
				this, animId);
	}

	public BaseAction setActiveFrame(short objectCode, int frame) {
		return new SetActiveFrameAction(this,
				objectCode, frame);
	}

	protected void setApi(InternalAPI api) {
		if(api==null)
		{
			throw new NullPointerException();
		}
		this.api = api;
	}

	public BaseAction setBaseMiddleX(short objectCode, double x) {
		return new SetBaseMiddleXAction(this,
				objectCode, x);
	}

	public BaseAction setBaseMiddleY(short objectCode, double y) {
		return new SetBaseMiddleYAction(this,
				objectCode, y);
		// return toReturn;
	}

	public void setCallbacks(ActionCallbackAPI callbacks) {
		this.callbacks = callbacks;
	}

	public BaseAction setDisplayName(short objectCode, String displayName) {
		return new SetDisplayNameAction(this,
				objectCode, displayName);
	}

	public BaseAction setInventoryVisible(int inventoryId, boolean isVisible) {
		return new SetInventoryVisibleAction(
				this, inventoryId, isVisible);
	}

	public void setParent(BaseAction parent) {
		this.parent = parent;
	}

	public BaseAction setTalkingAnimation(AnimationEnumAPI animId) {
		return new SetTalkingAnimationAction(
				this, animId);
		// return toReturn;
	}

	public BaseAction setTalkingAnimationDelay(short objectCode, int delay) {
		return new SetTalkingAnimationDelayAction(
				this, objectCode, delay);
		// return toReturn;
	}
	;

	public BaseAction setVisible(short objectCode, boolean isVisible) {
		return new SetVisibleAction(this,
				objectCode, isVisible);
	}
	;

	public BaseAction sleep(int milliseconds) {
		return new SleepAction(this,
				milliseconds);
	}

	public BaseAction subroutine(BaseAction orig) {
		// find root of orig
		BaseAction somewhereInOrig = orig;

		while (somewhereInOrig.getParent().getParent()
				!= null) {
			somewhereInOrig = somewhereInOrig.getParent();
		}
		somewhereInOrig.setParent(this);
		return orig;
	}

	public BaseAction swapProperty(short objectCode1, short objectCode2,
			SwapType type) {
		return new SwapPropertyAction(this,
				objectCode1, objectCode2, type);
	}

	public BaseDialogTreeAction  switchTo(String sceneName) {
		return new SwitchToAction(this,
				sceneName);
		// return toReturn;
	}

	public BaseAction waitForFrame(short objectCode, int frame) {
		return new WaitForFrameAction(this,
				objectCode, frame);
	}


	public BaseAction setHomeAnimation(AnimationEnumAPI animId) {
		return new SetHomeAnimationAction(this,
				animId);
	}


	public BaseAction walkTo(short objectCode, double x, double y) {
		return new WalkToAction(this, objectCode, x, y,0, false);
	}

	public BaseAction walkTo(short objectCode, PointF point) {
		return new WalkToAction(this, objectCode,
				point.getX(), point.getY(),0, false);
	}

	public BaseAction walkTo(short objectCode, double x, double y, int delay) {
		return new WalkToAction(this, objectCode, x, y, delay, false);
	}

	public BaseAction walkTo(short objectCode, PointF point, int delay) {
		return new WalkToAction(this, objectCode, point.getX(), point.getY(), delay, false);
	}


	public BaseAction walkToNonBlocking(short objectCode, double x, double y) {
		return new WalkToAction(this, objectCode, x, y,0, true);
	}

	public BaseAction walkToNonBlocking(short objectCode, PointF point) {
		return new WalkToAction(this, objectCode,
				point.getX(), point.getY(),0, true);
	}

	public BaseAction walkToNonBlocking(short objectCode, double x, double y, int delay) {
		return new WalkToAction(this, objectCode, x,
				y, delay, true);
	}

	public BaseAction walkToNonBlocking(short objectCode, PointF point, int delay) {
		return new WalkToAction(this, objectCode,
				point.getX(), point.getY(), delay, true);
	}    

	public BaseAction showTitleCard(String text, ColorEnum color)
	{
		return new TitleCardAction(this, text, color);
	}

	public void cancel() {
		systemAnimation.cancel();

	}

	public void run(int i) {
		systemAnimation.run(i);

	}

	public BaseAction playAnimationNonBlockingHoldLastFrame(AnimationEnumAPI animId) {
		PlayAnimationAction a = new PlayAnimationAction(
					this, animId);

		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		return a;
	}

}


;
