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
import com.github.a2g.core.action.SetInventoryVisibleAction;
import com.github.a2g.core.action.SleepAction;
import com.github.a2g.core.action.SwapPropertyAction;
import com.github.a2g.core.action.SwitchAction;
import com.github.a2g.core.action.ActivateDialogTreeModeAction;
import com.github.a2g.core.action.WaitForFrameAction;
import com.github.a2g.core.action.WalkAction;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.IChainRootForScene;
import com.github.a2g.core.interfaces.IOnDoCommand;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.PointF;

public abstract class DecoratedForSceneBaseAction 
extends BaseAction
implements IChainRootForScene
{
	
	public enum SwapType {
		Visibility
	}

	protected DecoratedForSceneBaseAction(BaseAction parent) {
		super(parent);
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
	public ChainableAction playAnimation(String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);
		return a;
	}

	// simple backwards
	@Override
	public ChainableAction playAnimationBackwards(String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);

		a.setBackwards(true);
		return a;
	}

	// simple hold last frame
	@Override
	public ChainableAction playAnimationHoldLastFrame(String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);

		a.setHoldLastFrame(true);
		return a;
	}

	// simple non blocking
	@Override
	public ChainableAction playAnimationNonBlocking(String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);

		a.setParallel(true);
		return a;
	}

	// double combo1of3: backwards + hold last frame
	
	@Override
	public ChainableAction playAnimationBackwardsHoldLastFrame(
			String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);
		a.setBackwards(true);
		a.setHoldLastFrame(true);
		return a;
	}

	// double combo2of3: backwards + nonblocking
	@Override
	public ChainableAction playAnimationBackwardsNonBlocking(String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);

		a.setBackwards(true);
		a.setParallel(true);
		return a;
	}

	// double combo2of3: holdLastFrame + nonblocking
	@Override
	public ChainableAction playAnimationHoldLastFrameNonBlocking(
			String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);

		a.setHoldLastFrame(true);
		a.setParallel(true);
		return a;
	}

	// ..and one method with the whole lot!
	@Override
	public ChainableAction playAnimationBackwardsHoldLastFrameNonBlocking(
			String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);

		a.setBackwards(true);
		a.setHoldLastFrame(true);
		a.setParallel(true);
		return a;
	}

	@Override
	public ChainableAction playAnimationRepeatWhilstVisible(String atid) {
		return new PlayAnimationRepeatWhilstVisibleAction(this, atid);
	}
	@Override
	public ChainableAction talk(String animCode, String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		return s;
	}
	@Override
	public ChainableAction talk(String speech) {
		TalkAction s = new TalkAction(this, TalkPerformer.SCENE_TALKER, speech);
		s.setNonIncrementing(TalkPerformer.NonIncrementing.FromAPI);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingFrame(String animCode,
			String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingHoldLastFrame(String animCode,
			String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
		s.setHoldLastFrame(true);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingFrameNonBlocking(
			String animCode, String speech) {
		TalkAction s = new TalkAction(this, animCode, speech);
		s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
		s.setParallel(true);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingFrame(String speech) {
		TalkAction s = new TalkAction(this, TalkPerformer.SCENE_TALKER, speech);
		s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
		return s;
	}
	@Override
	public ChainableAction talkWithoutIncrementingFrameNonBlocking(String speech) {
		TalkAction s = new TalkAction(this, TalkPerformer.SCENE_TALKER, speech);
		s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
		s.setParallel(true);
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
	@Override
	public ChainableAction swapProperty(short ocodeA, short ocodeB, SwapType type) {
		return new SwapPropertyAction(this, ocodeA, ocodeB, type);
	}
	@Override
	public BaseAction switchTo(String sceneName) {
		return new SwitchAction(this, sceneName);
		// return toReturn;
	}
	@Override
	public ChainableAction waitForFrame(short ocode, int frame) {
		return new WaitForFrameAction(this, ocode, frame);
	}
	@Override
	public ChainableAction setInitialAnimation(String atid) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetAsInitialAnimation);
		a.setAtid(atid);
		return a;
	}
	@Override
	public ChainableAction walkToWithoutSwitching(double x, double y) {
		return walkToWithoutSwitching(new PointF(x,y));
	}
	@Override
	public ChainableAction walkToWithoutSwitching(PointF end) {
		WalkAction a = new WalkAction(this,
				ScenePresenter.DEFAULT_SCENE_OBJECT);
		a.setEndX(end.getX());
		a.setEndY(end.getY());
		return a;
	}

	@Override
	public BaseAction walkTo(double x, double y) {
		return walkTo(new PointF(x,y));
	}
	@Override
	public BaseAction walkTo(PointF end) {
		WalkMaybeSwitchAction a = new WalkMaybeSwitchAction(this,
				ScenePresenter.DEFAULT_SCENE_OBJECT);
		a.setEndX(end.getX());
		a.setEndY(end.getY());
		return a;
	}
	
	@Override
	public ChainableAction walkToWithoutSwitching(short ocode, double x, double y) {
		return walkToWithoutSwitching(ocode,new PointF(x,y));
	}
	@Override
	public ChainableAction walkToWithoutSwitching(short ocode, PointF end) {
		WalkAction a = new WalkAction(this, ocode);
		a.setEndX(end.getX());
		a.setEndY(end.getY());
		return a;
	}
 
	@Override
	public ChainableAction showTitleCard(String text) {
		return new TitleCardAction(this, text);
	}

	
	@Override
	public ChainableAction playAnimationNonBlockingHoldLastFrame(
			String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);

		a.setHoldLastFrame(true);
		a.setParallel(true);
		return a;
	}
	@Override
	public ChainableAction setValue(String string, int i) {
		MakeSingleCallAction a =  new MakeSingleCallAction(this, MakeSingleCallAction.Type.SetValue);
		a.setString(string);
		a.setInt(i);
		return a;
	}

	@Override
	public ChainableAction moveWhilstAnimating(short ocode, double x, double y) {
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				ocode);
		a.setEndX(x);
		a.setEndY(y);
		return a;
	}

	public ChainableAction moveWhilstAnimatingInY(short objId, double y) {
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId);
		a.setEndY(y);
		return a;
	}

	@Override
	public ChainableAction moveWhilstAnimatingNonBlocking(short objId, double x,
			double y) {
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this,
				objId );
		a.setEndX(x);
		a.setEndY(y);
		a.setParallel(true);
		return a;
	}
	@Override
	public ChainableAction moveCameraToNewXPosition(double x,
			double durationInSecs) {
		ScrollCameraXAction a = new ScrollCameraXAction(this, x,
				durationInSecs);
		a.setParallel(false);
		return a;
	}

	@Override
	public ChainableAction moveCameraToNewXPositionNonBlocking(double x,
			double durationInSecs) {
		ScrollCameraXAction a = new ScrollCameraXAction(this, x,
				durationInSecs );
		a.setParallel(true);
		return a;
	}

	@Override
	public ChainableAction moveCameraToNewYPosition(double y,
			double durationInSecs) {
		ScrollCameraYAction a = new ScrollCameraYAction(this, y,
				durationInSecs );
		a.setParallel(false);
		return a;
	}
	@Override
	public ChainableAction moveCameraToNewYPositionNonBlocking(double y,
			double durationInSecs) {
		ScrollCameraYAction a = new ScrollCameraYAction(this, y,
				durationInSecs );
		a.setParallel(true);
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
