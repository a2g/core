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
import com.github.a2g.core.action.SwitchHardAction;
import com.github.a2g.core.action.ActivateDialogTreeModeAction;
import com.github.a2g.core.action.WaitForFrameAction;
import com.github.a2g.core.action.WalkAction;
import com.github.a2g.core.interfaces.IChainRootForScene;
import com.github.a2g.core.interfaces.IOnDoCommand;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.PointF;

public abstract class DecoratedForSceneBaseAction 
extends BaseAction
implements IChainRootForScene
{
	
	public enum SwapType {
		Visibility
	}

	protected DecoratedForSceneBaseAction(BaseAction parent, boolean isLinear) {
		super(parent,isLinear);
	}

	@Override
	public BaseAction doBoth(ChainableAction a, ChainableAction b) {
		return a.subroutine(b);
	}
	
	
	@Override
	public ChainableAction doNothing() {
		DoNothingAction a =  new DoNothingAction(this);
		return a;
	}
	@Override
	public BaseAction activateDialogTreeMode(int branchId) {
		return new ActivateDialogTreeModeAction(this, branchId);
	}

	// plain..
	@Override
	public ChainableAction playAnimation(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		return a;
	}

	// simple backwards
	@Override
	public ChainableAction playAnimationBackwards(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setBackwards(true);
		return a;
	}

	// simple hold last frame
	@Override
	public ChainableAction playAnimationHoldLastFrame(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setHoldLastFrame(true);
		return a;
	}

	// simple non blocking
	@Override
	public ChainableAction playAnimationNonBlocking(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setNonBlocking(true);
		return a;
	}

	// double combo1of3: backwards + hold last frame
	
	@Override
	public ChainableAction playAnimationBackwardsHoldLastFrame(
			String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setBackwards(true);
		a.setHoldLastFrame(true);
		return a;
	}

	// double combo2of3: backwards + nonblocking
	@Override
	public ChainableAction playAnimationBackwardsNonBlocking(String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setBackwards(true);
		a.setNonBlocking(true);
		return a;
	}

	// double combo2of3: holdLastFrame + nonblocking
	@Override
	public ChainableAction playAnimationHoldLastFrameNonBlocking(
			String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		return a;
	}

	// ..and one method with the whole lot!
	@Override
	public ChainableAction playAnimationBackwardsHoldLastFrameNonBlocking(
			String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setBackwards(true);
		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		return a;
	}

	@Override
	public ChainableAction playAnimationRepeatWhilstVisible(String animationCode) {
		boolean isLinear = true;
		return new PlayAnimationRepeatWhilstVisibleAction(this, animationCode,
				isLinear);
	}
	@Override
	public ChainableAction talk(String animCode, String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		return s;
	}
	@Override
	public ChainableAction talk(String speech) {
		TalkAction s = new TalkAction(this, TalkAction.SCENE_TALKER, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.FromAPI);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingFrame(String animCode,
			String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingHoldLastFrame(String animCode,
			String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		s.setHoldLastFrame(true);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingFrameNonBlocking(
			String animCode, String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		s.setNonBlocking(true);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingFrame(String speech) {
		TalkAction s = new TalkAction(this, TalkAction.SCENE_TALKER, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingFrameNonBlocking(String speech) {
		TalkAction s = new TalkAction(this, TalkAction.SCENE_TALKER, speech);
		s.setNonIncrementing(TalkAction.NonIncrementing.True);
		s.setNonBlocking(true);
		return s;
	}
	@Override
	public ChainableAction setCurrentAnimation(String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetCurrentAnimation );
		a.setAtid(atid);
		return a;
	}
	@Override
public ChainableAction setCurrentAnimationAndFrame(String atid, int frame) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetCurrentAnimationAndFrame );
		a.setAtid(atid);
		a.setInt(frame);
		return a;
	}
	@Override
	public ChainableAction setCurrentFrame(short ocode, int frame) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetCurrentFrame );
		a.setOCode(ocode);
		a.setInt(frame);
		return a;
	}

	public ChainableAction setBaseMiddleX(short ocode, double x) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetBaseMiddleX);
		a.setOCode(ocode);
		a.setDouble(x);
		return a;
	}

	public ChainableAction setBaseMiddleY(short ocode, double y) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetBaseMiddleX);
		a.setOCode(ocode);
		a.setDouble(y);
		return a;
	}


	public ChainableAction setDisplayName(short ocode, String displayName) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetDisplayName);
		a.setOCode(ocode);
		a.setString(displayName);
		return a;
	}

	public ChainableAction setInventoryVisible(int inventoryId, boolean isVisible) {
		return new SetInventoryVisibleAction(this, inventoryId, isVisible);
	}
 
	public ChainableAction setVisible(short ocode, boolean isVisible) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetVisible);
		a.setOCode(ocode);
		a.setBoolean(isVisible);
		return a;
	}

	public ChainableAction sleep(int milliseconds) {
		return new SleepAction(this, milliseconds);
	}

	public BaseAction onDoCommand(IGameScene scene, IOnDoCommand api,
			ChainRootAction ba, int verb, SentenceItem itemA,
			SentenceItem itemB, double x, double y) {
		BaseAction secondStep = scene.onDoCommand(api,
				api.createChainRootAction(), verb, itemA, itemB, x, y);
		return subroutine(secondStep);
	}
	@Override
	public BaseAction subroutine(BaseAction secondStep) {
		// find root of orig
		BaseAction somewhereInOrig = secondStep;

		while (somewhereInOrig.getParent().getParent() != null) {
			somewhereInOrig = somewhereInOrig.getParent();
		}
		somewhereInOrig.setParent(this);
		return secondStep;
	}
	
	@Override
	public ChainableAction subroutine(ChainableAction secondStep) {
		// find root of orig
		BaseAction somewhereInOrig = secondStep;

		while (somewhereInOrig.getParent().getParent() != null) {
			somewhereInOrig = somewhereInOrig.getParent();
		}
		somewhereInOrig.setParent(this);
		return secondStep;
	}
	 
	public ChainableAction swapProperty(short ocodeA, short ocodeB, SwapType type) {
		return new SwapPropertyAction(this, ocodeA, ocodeB, type);
	}

	public BaseAction switchTo(String sceneName) {
		return new SwitchHardAction(this, sceneName);
		// return toReturn;
	}

	public ChainableAction waitForFrame(short ocode, int frame) {
		return new WaitForFrameAction(this, ocode, frame);
	}

	public ChainableAction setInitialAnimation(String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetAsInitialAnimation);
		a.setAtid(atid);
		return a;
	}

	public ChainableAction walkTo(double x, double y) {
		boolean isLinear = true;
		WalkAction a = new WalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, 0,
				isLinear);

		return a;
	}

	public ChainableAction walkTo(PointF point) {
		boolean isLinear = true;
		WalkAction a = new WalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), 0, isLinear);
		return a;
	}

	@Override
	public BaseAction switchWalkTo(double x, double y) {
		boolean isLinear = true;
		SwitchWalkAction a = new SwitchWalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, 0,
				isLinear);

		return a;
	}
	@Override
	public BaseAction switchWalkTo(PointF point) {
		boolean isLinear = true;
		SwitchWalkAction a = new SwitchWalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), 0, isLinear);
		return a;
	}
	
	public ChainableAction walkTo(double x, double y, int delay) {
		boolean isLinear = true;
		WalkAction a = new WalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, delay,
				isLinear);
		return a;
	}

	public ChainableAction walkTo(PointF point, int delay) {
		boolean isLinear = true;
		return new WalkAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), delay, isLinear);
	}

	public ChainableAction walkTo(short ocode, double x, double y) {
		boolean isLinear = true;
		return new WalkAction(this, ocode, x, y, 0, isLinear);
	}

	public ChainableAction walkTo(short ocode, PointF point) {
		boolean isLinear = true;
		return new WalkAction(this, ocode, point.getX(), point.getY(), 0,
				isLinear);
	}

	public ChainableAction walkTo(short ocode, double x, double y, int delay) {
		boolean isLinear = true;
		return new WalkAction(this, ocode, x, y, delay, isLinear);
	}

	public ChainableAction walkTo(short ocode, PointF point, int delay) {
		boolean isLinear = true;
		return new WalkAction(this, ocode, point.getX(), point.getY(), delay,
				isLinear);
	}

	public ChainableAction walkAndScroll(double x, double y, int delay) {
		boolean isLinear = true;
		return new WalkAndScrollXAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, delay,
				isLinear);
	}

	public ChainableAction walkAndScroll(PointF point, int delay) {
		boolean isLinear = true;
		return new WalkAndScrollXAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), delay, isLinear);
	}

	public ChainableAction walkAndScrollNonBlocking(double x, double y, int delay) {
		boolean isLinear = true;
		WalkAndScrollXAction a = new WalkAndScrollXAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, x, y, delay,
				isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainableAction walkAndScrollNonBlocking(PointF point, int delay) {
		boolean isLinear = true;
		WalkAndScrollXAction a = new WalkAndScrollXAction(this,
				MoveWhilstAnimatingAction.DEFAULT_WALK_OBJECT, point.getX(),
				point.getY(), delay, isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainableAction walkAndScroll(short ocode, double x, double y,
			int delay) {
		boolean isLinear = true;
		return new WalkAndScrollXAction(this, ocode, x, y, delay, isLinear);
	}

	public ChainableAction walkAndScroll(short ocode, PointF point, int delay) {
		boolean isLinear = true;
		return new WalkAndScrollXAction(this, ocode, point.getX(),
				point.getY(), delay, isLinear);
	}

	public ChainableAction walkAndScrollNonBlocking(short ocode, double x,
			double y, int delay) {
		boolean isLinear = true;
		WalkAndScrollXAction a = new WalkAndScrollXAction(this, ocode, x, y,
				delay, isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainableAction walkAndScrollNonBlocking(short ocode, PointF point,
			int delay) {
		boolean isLinear = true;
		WalkAndScrollXAction a = new WalkAndScrollXAction(this, ocode,
				point.getX(), point.getY(), delay, isLinear);
		a.setNonBlocking(true);
		return a;
	}

	public ChainableAction showTitleCard(String text) {
		return new TitleCardAction(this, text);
	}

	

	public ChainableAction playAnimationNonBlockingHoldLastFrame(
			String animationCode) {
		boolean isLinear = true;
		PlayAnimationAction a = new PlayAnimationAction(this, animationCode,
				isLinear);

		a.setHoldLastFrame(true);
		a.setNonBlocking(true);
		return a;
	}

	public ChainableAction setValue(String string, int i) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetValue);
		a.setString(string);
		a.setInt(i);
		return a;
	}

	public ChainableAction moveWhilstAnimatingLinear(short objId, double x,
			double y) {
		boolean isLinear = true;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId, isLinear);
		a.setEndX(x);
		a.setEndY(y);
		return a;
	}

	public ChainableAction moveWhilstAnimatingLinearNonBlocking(short ocode,
			double x, double y) {
		boolean isLinear = true;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				ocode, isLinear);
		a.setEndX(x);
		a.setEndY(y);
		a.setNonBlocking(true);
		return a;
	}

	public ChainableAction moveWhilstAnimating(short objId, double x, double y) {
		boolean isLinear = false;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId, isLinear);
		a.setEndX(x);
		a.setEndY(y);
		return a;
	}

	public ChainableAction moveWhilstAnimatingInY(short objId, double y) {
		boolean isLinear = false;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId, isLinear);
		a.setEndY(y);
		return a;
	}

	@Override
	public ChainableAction moveWhilstAnimatingNonBlocking(short objId, double x,
			double y) {
		boolean isLinear = false;
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId, isLinear);
		a.setEndX(x);
		a.setEndY(y);
		a.setNonBlocking(true);
		return a;
	}

	public ChainableAction moveCameraToNewXPosition(double x,
			double durationInSecs) {
		boolean isLinear = false;
		ScrollCameraXAction a = new ScrollCameraXAction(this, x,
				durationInSecs, isLinear);
		a.setNonBlocking(false);
		return a;
	}

	@Override
	public ChainableAction moveCameraToNewXPositionNonBlocking(double x,
			double durationInSecs) {
		boolean isLinear = false;
		ScrollCameraXAction a = new ScrollCameraXAction(this, x,
				durationInSecs, isLinear);
		a.setNonBlocking(true);
		return a;
	}

	@Override
	public ChainableAction moveCameraToNewYPosition(double y,
			double durationInSecs) {
		boolean isLinear = false;
		ScrollCameraYAction a = new ScrollCameraYAction(this, y,
				durationInSecs, isLinear);
		a.setNonBlocking(false);
		return a;
	}
	@Override
	public ChainableAction moveCameraToNewYPositionNonBlocking(double y,
			double durationInSecs) {
		boolean isLinear = false;
		ScrollCameraYAction a = new ScrollCameraYAction(this, y,
				durationInSecs, isLinear);
		a.setNonBlocking(true);
		return a;
	}
	@Override
	public ChainableAction hideAll() {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.HideAll);
		return a;
	}
	@Override
	public ChainableAction setToInitialPosition(short ocode) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetToInitialPosition);
		a.setOCode(ocode);
		return a;
	}
	@Override
	public ChainableAction alignBaseMiddleOfOldFrameToFrameOfNewAnimation(
			String atid, int frame) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.AlignBaseMiddle);
		a.setAtid(atid);
		a.setInt(frame);
		return a;
	}
	@Override
	public ChainableAction alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(
			String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.AlignBaseMiddle);
		a.setAtid(atid);
		a.setInt(0);
		return a;
	}
	@Override
	public ChainableAction alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(
			String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.AlignBaseMiddle);
		a.setAtid(atid);
		a.setInt(-17);
		return a;
	}
	@Override
	public ChainableAction share(String string) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.ShareWinning);
		a.setString(string);
		return a;
	}
	
	@Override
	public ChainableAction setSceneTalker(String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetSceneTalker);
		a.setAtid(atid);
		return a;
	}
	@Override
	public ChainableAction quit()
	{
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.Quit);
		return a;
	}
	@Override
	public ChainableAction playSound(String stid)
	{
		PlaySoundAction a =  new PlaySoundAction(this, stid);
		return a;
	}
	@Override
	public ChainableAction playSoundNonBlocking(String stid)
	{
		PlaySoundAction a =  new PlaySoundAction(this, stid);
		a.setParallel(true);
		return a;
	}

	

}
