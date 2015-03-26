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
import com.github.a2g.core.action.SetCurrentAnimationAction;
import com.github.a2g.core.action.SetActiveFrameAction;
import com.github.a2g.core.action.SetBaseMiddleXAction;
import com.github.a2g.core.action.SetBaseMiddleYAction;
import com.github.a2g.core.action.SetDisplayNameAction;
import com.github.a2g.core.action.SetInitialAnimationAction;
import com.github.a2g.core.action.SetInventoryVisibleAction;
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
import com.github.a2g.core.interfaces.IActionRunnerFromBaseAction;
import com.github.a2g.core.interfaces.IFactory;
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IOnDoCommand;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.interfaces.ISystemAnimation;
import com.github.a2g.core.interfaces.IBaseActionFromSystemAnimation;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.PointF;

public abstract class BaseAction implements IBaseActionFromSystemAnimation {
	private ISystemAnimation systemAnimation;
	private IActionRunnerFromBaseAction callbacks;
	protected BaseAction parent;
	private boolean isLinear;

	public enum SwapType {
		Visibility
	}

	// all the abstract have GameAction in them to let
	// the client know they are dealing with GameAction and not Animation
	abstract public boolean isParallel();

	abstract protected void onUpdateGameAction(double progress);

	abstract protected void onCompleteGameAction();

	abstract public void runGameAction();

	abstract public void setAll(IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard,
			IInventoryPresenterFromActions inventory);

	protected BaseAction(BaseAction parent, boolean isLinear) {
		this.parent = parent;
		this.isLinear = isLinear;
		this.callbacks = null;// initd in setcallbacks
		this.systemAnimation = null;// initd in setFactory
	}

	void setFactory(IFactory api) {
		this.systemAnimation = api.createSystemAnimation(this, isLinear);
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
		return new DoNothingAction(this);
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

		this.callbacks.startTheNextAction(this);
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

	public ChainedAction say(String animCode, String speech) {
		SayAction s = new SayAction(this, animCode, speech);
		return s;
	}

	public ChainedAction say(String speech) {
		SayAction s = new SayAction(this, SayAction.DEFAULT_SAY_ANIM, speech);
		s.setNonIncrementing(SayAction.NonIncrementing.FromAPI);
		return s;
	}

	public ChainedAction sayWithoutIncrementingFrame(String animCode,
			String speech) {
		SayAction s = new SayAction(this, animCode, speech);
		s.setNonIncrementing(SayAction.NonIncrementing.True);
		return s;
	}

	public ChainedAction sayWithoutIncrementingHoldLastFrame(String animCode,
			String speech) {
		SayAction s = new SayAction(this, animCode, speech);
		s.setNonIncrementing(SayAction.NonIncrementing.True);
		s.setHoldLastFrame(true);
		return s;
	}

	public ChainedAction sayWithoutIncrementingFrameNonBlocking(
			String animCode, String speech) {
		SayAction s = new SayAction(this, animCode, speech);
		s.setNonIncrementing(SayAction.NonIncrementing.True);
		s.setNonBlocking(true);
		return s;
	}

	public ChainedAction sayWithoutIncrementingFrame(String speech) {
		SayAction s = new SayAction(this, SayAction.DEFAULT_SAY_ANIM, speech);
		s.setNonIncrementing(SayAction.NonIncrementing.True);
		return s;
	}

	public ChainedAction sayWithoutIncrementingFrameNonBlocking(String speech) {
		SayAction s = new SayAction(this, SayAction.DEFAULT_SAY_ANIM, speech);
		s.setNonIncrementing(SayAction.NonIncrementing.True);
		s.setNonBlocking(true);
		return s;
	}

	public ChainedAction setCurrentAnimation(String animationCode) {
		return new SetCurrentAnimationAction(this, animationCode);
	}

	public ChainedAction setActiveFrame(short ocode, int frame) {
		return new SetActiveFrameAction(this, ocode, frame);
	}

	public ChainedAction setBaseMiddleX(short ocode, double x) {
		return new SetBaseMiddleXAction(this, ocode, x);
	}

	public ChainedAction setBaseMiddleY(short ocode, double y) {
		return new SetBaseMiddleYAction(this, ocode, y);
		// return toReturn;
	}

	public void setCallbacks(IActionRunnerFromBaseAction callbacks) {
		this.callbacks = callbacks;
	}

	public ChainedAction setDisplayName(short ocode, String displayName) {
		return new SetDisplayNameAction(this, ocode, displayName, true);
	}

	public ChainedAction setInventoryVisible(int inventoryId, boolean isVisible) {
		return new SetInventoryVisibleAction(this, inventoryId, isVisible, true);
	}

	public void setParent(BaseAction parent) {
		this.parent = parent;
	}

	public ChainedAction setVisible(short ocode, boolean isVisible) {
		return new SetVisibleAction(this, ocode, isVisible, true);
	}

	public ChainedAction sleep(int milliseconds) {
		return new SleepAction(this, milliseconds);
	}

	public ChainedAction onDoCommand(IGameScene scene, IOnDoCommand api,
			ChainRootAction ba, int verb, SentenceItem itemA,
			SentenceItem itemB, double x, double y) {
		ChainedAction secondStep = scene.onDoCommand(api,
				api.createChainRootAction(), verb, itemA, itemB, x, y);
		return subroutine(secondStep);
	}

	public ChainedAction subroutine(ChainedAction orig) {
		// find root of orig
		BaseAction somewhereInOrig = orig;

		while (somewhereInOrig.getParent().getParent() != null) {
			somewhereInOrig = somewhereInOrig.getParent();
		}
		somewhereInOrig.setParent(this);
		return orig;
	}

	public ChainedAction swapProperty(short ocodeA, short ocodeB, SwapType type) {
		return new SwapPropertyAction(this, ocodeA, ocodeB, type);
	}

	public BaseDialogTreeAction switchTo(String sceneName) {
		return new SwitchToAction(this, sceneName);
		// return toReturn;
	}

	public ChainedAction waitForFrame(short ocode, int frame) {
		return new WaitForFrameAction(this, ocode, frame);
	}

	public ChainedAction setInitialAnimation(String animationCode) {
		return new SetInitialAnimationAction(this, animationCode, true);
	}

	public ChainedAction walkTo(double x, double y) {
		boolean isLinear = true;
		WalkToAction a = new WalkToAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, 0,
				isLinear);

		return a;
	}

	public ChainedAction walkTo(PointF point) {
		boolean isLinear = true;
		WalkToAction a = new WalkToAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), 0, isLinear);
		return a;
	}

	public ChainedAction walkTo(double x, double y, int delay) {
		boolean isLinear = true;
		WalkToAction a = new WalkToAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, delay,
				isLinear);
		return a;
	}

	public ChainedAction walkTo(PointF point, int delay) {
		boolean isLinear = true;
		return new WalkToAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), delay, isLinear);
	}

	public ChainedAction walkTo(short ocode, double x, double y) {
		boolean isLinear = true;
		return new WalkToAction(this, ocode, x, y, 0, isLinear);
	}

	public ChainedAction walkTo(short ocode, PointF point) {
		boolean isLinear = true;
		return new WalkToAction(this, ocode, point.getX(), point.getY(), 0,
				isLinear);
	}

	public ChainedAction walkTo(short ocode, double x, double y, int delay) {
		boolean isLinear = true;
		return new WalkToAction(this, ocode, x, y, delay, isLinear);
	}

	public ChainedAction walkTo(short ocode, PointF point, int delay) {
		boolean isLinear = true;
		return new WalkToAction(this, ocode, point.getX(), point.getY(), delay,
				isLinear);
	}

	public ChainedAction walkAndScroll(double x, double y, int delay) {
		boolean isLinear = true;
		return new WalkAndScrollXAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, delay,
				isLinear);
	}

	public ChainedAction walkAndScroll(PointF point, int delay) {
		boolean isLinear = true;
		return new WalkAndScrollXAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), delay, isLinear);
	}

	public ChainedAction walkAndScrollNonBlocking(double x, double y, int delay) {
		boolean isLinear = true;
		WalkAndScrollXAction a = new WalkAndScrollXAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, delay,
				isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction walkAndScrollNonBlocking(PointF point, int delay) {
		boolean isLinear = true;
		WalkAndScrollXAction a = new WalkAndScrollXAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), delay, isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction walkAndScroll(short ocode, double x, double y,
			int delay) {
		boolean isLinear = true;
		return new WalkAndScrollXAction(this, ocode, x, y, delay, isLinear);
	}

	public ChainedAction walkAndScroll(short ocode, PointF point, int delay) {
		boolean isLinear = true;
		return new WalkAndScrollXAction(this, ocode, point.getX(),
				point.getY(), delay, isLinear);
	}

	public ChainedAction walkAndScrollNonBlocking(short ocode, double x,
			double y, int delay) {
		boolean isLinear = true;
		WalkAndScrollXAction a = new WalkAndScrollXAction(this, ocode, x, y,
				delay, isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction walkAndScrollNonBlocking(short ocode, PointF point,
			int delay) {
		boolean isLinear = true;
		WalkAndScrollXAction a = new WalkAndScrollXAction(this, ocode,
				point.getX(), point.getY(), delay, isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction showTitleCard(String text) {
		return new TitleCardAction(this, text);
	}

	public void cancel() {
		systemAnimation.cancel();

	}

	public void run(int i) {
		systemAnimation.run(i);

	}

	public ChainedAction playAnimationNonBlockingHoldLastFrame(
			String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction setValue(String string, int i) {
		SetValueAction a = new SetValueAction(this, string, i);
		return a;
	}

	public ChainedAction moveWhilstAnimatingLinear(short objId, double x,
			double y) {
		boolean isLinear = true;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId, isLinear);
		a.setEndX(x);
		a.setEndY(y);
		return a;
	}

	public ChainedAction moveWhilstAnimatingLinearNonBlocking(short ocode,
			double x, double y) {
		boolean isLinear = true;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				ocode, isLinear);
		a.setEndX(x);
		a.setEndY(y);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction moveWhilstAnimating(short objId, double x, double y) {
		boolean isLinear = false;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId, isLinear);
		a.setEndX(x);
		a.setEndY(y);
		return a;
	}

	public ChainedAction moveWhilstAnimatingInY(short objId, double y) {
		boolean isLinear = false;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId, isLinear);
		a.setEndY(y);
		return a;
	}

	public ChainedAction moveWhilstAnimatingNonBlocking(short objId, double x,
			double y) {
		boolean isLinear = false;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId, isLinear);
		a.setEndX(x);
		a.setEndY(y);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction moveCameraToNewXPosition(double x,
			double durationInSecs) {
		boolean isLinear = false;
		ScrollCameraXAction a = new ScrollCameraXAction(this, x,
				durationInSecs, isLinear);
		a.setNonBlocking(false);
		return a;
	}

	public ChainedAction moveCameraToNewXPositionNonBlocking(double x,
			double durationInSecs) {
		boolean isLinear = false;
		ScrollCameraXAction a = new ScrollCameraXAction(this, x,
				durationInSecs, isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction moveCameraToNewYPosition(double y,
			double durationInSecs) {
		boolean isLinear = false;
		ScrollCameraYAction a = new ScrollCameraYAction(this, y,
				durationInSecs, isLinear);
		a.setNonBlocking(false);
		return a;
	}

	public ChainedAction moveCameraToNewYPositionNonBlocking(double y,
			double durationInSecs) {
		boolean isLinear = false;
		ScrollCameraYAction a = new ScrollCameraYAction(this, y,
				durationInSecs, isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainedAction hideAll() {
		HideAllAction h = new HideAllAction(this);
		return h;
	}

	public ChainedAction setToInitialPosition(short object) {
		SetToInitialPositionAction h = new SetToInitialPositionAction(this,
				object);
		return h;
	}

	public ChainedAction alignBaseMiddleOfOldFrameToFrameOfNewAnimation(
			String anim, int frame) {
		AlignBaseMiddleAction h = new AlignBaseMiddleAction(this, anim, frame);
		return h;
	}

	public ChainedAction alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(
			String anim) {
		AlignBaseMiddleAction h = new AlignBaseMiddleAction(this, anim, 0);
		return h;
	}

	public ChainedAction alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(
			String anim) {
		AlignBaseMiddleAction h = new AlignBaseMiddleAction(this, anim, -17);
		return h;
	}

	public ChainedAction share(String blah) {
		ShareAction s = new ShareAction(this, blah);
		return s;
	}

}
