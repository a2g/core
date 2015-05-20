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
import com.github.a2g.core.action.SleepAction;
import com.github.a2g.core.action.SwapPropertyAction;
import com.github.a2g.core.action.SwitchToAction;
import com.github.a2g.core.action.DialogTreeBranchAction;
import com.github.a2g.core.action.DialogTreeEndAction;
import com.github.a2g.core.action.DialogTreeDoDialogBranchAction;
import com.github.a2g.core.action.WaitForFrameAction;
import com.github.a2g.core.action.WalkAction;
import com.github.a2g.core.action.BaseDialogTreeAction;
import com.github.a2g.core.interfaces.IOnDoCommand;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.PointF;

public abstract class BaseActionDecorated extends BaseAction{
	
	public enum SwapType {
		Visibility
	}

	protected BaseActionDecorated(BaseAction parent, boolean isLinear) {
		super(parent,isLinear);
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
 

	public BaseDialogTreeAction doDialogBranch(int branchId) {
		return new DialogTreeDoDialogBranchAction(this, branchId);
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


	public ChainedAction setDisplayName(short ocode, String displayName) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetDisplayName);
		a.setOCode(ocode);
		a.setString(displayName);
		return a;
	}

	public ChainedAction setInventoryVisible(int inventoryId, boolean isVisible) {
		return new SetInventoryVisibleAction(this, inventoryId, isVisible, true);
	}

	public void setParent(BaseActionDecorated parent) {
		this.parent = parent;
	}

	public ChainedAction setVisible(short ocode, boolean isVisible) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetVisible);
		a.setOCode(ocode);
		a.setBoolean(isVisible);
		return a;
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

	public ChainedAction setInitialAnimation(String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetAsInitialAnimation);
		a.setAtid(atid);
		return a;
	}

	public ChainedAction walkTo(double x, double y) {
		boolean isLinear = true;
		WalkAction a = new WalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, 0,
				isLinear);

		return a;
	}

	public ChainedAction walkTo(PointF point) {
		boolean isLinear = true;
		WalkAction a = new WalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), 0, isLinear);
		return a;
	}

	public ChainedAction walkTo(double x, double y, int delay) {
		boolean isLinear = true;
		WalkAction a = new WalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, delay,
				isLinear);
		return a;
	}

	public ChainedAction walkTo(PointF point, int delay) {
		boolean isLinear = true;
		return new WalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), delay, isLinear);
	}

	public ChainedAction walkTo(short ocode, double x, double y) {
		boolean isLinear = true;
		return new WalkAction(this, ocode, x, y, 0, isLinear);
	}

	public ChainedAction walkTo(short ocode, PointF point) {
		boolean isLinear = true;
		return new WalkAction(this, ocode, point.getX(), point.getY(), 0,
				isLinear);
	}

	public ChainedAction walkTo(short ocode, double x, double y, int delay) {
		boolean isLinear = true;
		return new WalkAction(this, ocode, x, y, delay, isLinear);
	}

	public ChainedAction walkTo(short ocode, PointF point, int delay) {
		boolean isLinear = true;
		return new WalkAction(this, ocode, point.getX(), point.getY(), delay,
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
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetValue);
		a.setString(string);
		a.setInt(i);
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
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.HideAll);
		return a;
	}

	public ChainedAction setToInitialPosition(short ocode) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetToInitialPosition);
		a.setOCode(ocode);
		return a;
	}

	public ChainedAction alignBaseMiddleOfOldFrameToFrameOfNewAnimation(
			String atid, int frame) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.AlignBaseMiddle);
		a.setAtid(atid);
		a.setInt(frame);
		return a;
	}

	public ChainedAction alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(
			String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.AlignBaseMiddle);
		a.setAtid(atid);
		a.setInt(0);
		return a;
	}

	public ChainedAction alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(
			String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.AlignBaseMiddle);
		a.setAtid(atid);
		a.setInt(-17);
		return a;
	}

	public ChainedAction share(String string) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.ShareWinning);
		a.setString(string);
		return a;
	}
	

	public ChainedAction setSceneTalker(String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetSceneTalker);
		a.setAtid(atid);
		return a;
	}
	
	public ChainedAction quit()
	{
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.Quit);
		return a;
	}
	
	public ChainedAction playSound(String stid)
	{
		PlaySoundAction a =  new PlaySoundAction(this, stid);
		return a;
	}
	public ChainedAction playSoundNonBlocking(String stid)
	{
		PlaySoundAction a =  new PlaySoundAction(this, stid);
		a.setParallel(true);
		return a;
	}

	

}
