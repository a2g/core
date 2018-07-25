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
import com.github.a2g.core.action.ChainToDialogAction;
import com.github.a2g.core.action.WaitForFrameAction;
import com.github.a2g.core.action.WalkAction;
import com.github.a2g.core.action.performer.SingleCallPerformer.Type;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.interfaces.internal.IChainRootForScene;
import com.github.a2g.core.interfaces.methods.IOnDoCommand;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.A2gException;
import com.google.gwt.touch.client.Point; 

public abstract class ChainableAction 
extends ChainEndAction 
implements IChainRootForScene {

	public enum SwapType {
		Visibility
	}

	protected ChainableAction(BaseAction parent) {
		super(parent);
	}

	@Override
	public ChainEndAction doBoth(ChainableAction a, ChainableAction b) {
		return a.subroutine(b);
	}

	@Override
	public ChainableAction doNothing() {
		DoNothingAction a = new DoNothingAction(this);
		return a;
	}

	@Override
	public ChainEndAction activateDialogTreeMode(int branchId) {
		return new ChainToDialogAction(this, branchId);
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
	public ChainableAction playAnimationBackwardsHoldLastFrame(String atid) {
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
	public ChainableAction playAnimationHoldLastFrameNonBlocking(String atid) {
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
	public ChainableAction setAnimationAsObjectCurrent(String atid) {
		SingleCallAction a = new SingleCallAction(this,
				Type.SetCurrentAnimation);
		a.setAtid(atid);
		return a;
	}

	@Override
	public ChainableAction setAnimationAsObjectCurrentAndSetFrame(String atid, int frame) {
		SingleCallAction a = new SingleCallAction(this,
				Type.SetCurrentAnimationAndFrame);
		a.setAtid(atid);
		a.setInt(frame);
		return a;
	}

	@Override
	public ChainableAction setCurrentFrame(short ocode, int frame) {
		SingleCallAction a = new SingleCallAction(this, Type.SetCurrentFrame);
		a.setOCode(ocode);
		a.setInt(frame);
		return a;
	}

	@Override
	public ChainableAction setBaseMiddleX(short ocode, double x) {
		SingleCallAction a = new SingleCallAction(this, Type.SetBaseMiddleX);
		a.setOCode(ocode);
		a.setDouble(x);
		return a;
	}

	@Override
	public ChainableAction setBaseMiddleY(short ocode, double y) {
		SingleCallAction a = new SingleCallAction(this, Type.SetBaseMiddleY);
		a.setOCode(ocode);
		a.setDouble(y);
		return a;
	}

	@Override
	public ChainableAction setDisplayName(short ocode, String displayName) {
		SingleCallAction a = new SingleCallAction(this, Type.SetDisplayName);
		a.setOCode(ocode);
		a.setString(displayName);
		return a;
	}

	@Override
	public ChainableAction setInventoryItemVisible(int icode, boolean isVisible) {
		SingleCallAction a = new SingleCallAction(this,
				Type.SetInventoryVisible);
		a.setICode(icode);
		a.setBoolean(isVisible);
		return a;
	}

	@Override
	public ChainableAction setVisible(short ocode, boolean isVisible) {
		SingleCallAction a = new SingleCallAction(this, Type.SetVisible);
		a.setOCode(ocode);
		a.setBoolean(isVisible);
		return a;
	}

	@Override
	public ChainableAction sleep(int milliseconds) {
		SingleCallAction a = new SingleCallAction(this, Type.Sleep);
		a.setInt(milliseconds);
		return a;
	}

	@Override
	public ChainEndAction onDoCommand(IGameScene scene, IOnDoCommand api,
			ChainRootAction ba, int verb, SentenceItem itemA,
			SentenceItem itemB, double x, double y) throws A2gException {
		ChainEndAction secondStep = scene.onDoCommand(api,
				api.createChainRootAction(), verb, itemA, itemB, x, y);
		return subroutine(secondStep);
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
	public ChainEndAction subroutine(ChainEndAction secondStep) {
		// find root of orig
		BaseAction somewhereInOrig = secondStep;

		while (somewhereInOrig.getParent().getParent() != null) {
			somewhereInOrig = somewhereInOrig.getParent();
		}
		somewhereInOrig.setParent(this);
		return secondStep;
	}

	@Override
	public ChainableAction swapVisibility(short ocodeA, short ocodeB) {
		SingleCallAction a = new SingleCallAction(this, Type.SwapVisibility);
		a.setOCode(ocodeA);
		a.setOCode2(ocodeB);
		return a;
	}

	@Override
	public ChainEndAction switchTo(String sceneName, int arrivalSegment) {
		SingleCallAction a = new SingleCallAction(this, Type.Switch);
		a.setString(sceneName);
		a.setInt(arrivalSegment);
		return a;
	}

	@Override
	public ChainableAction waitForFrame(short ocode, int frame) {
		return new WaitForFrameAction(this, ocode, frame);
	}


	@Override
	public ChainableAction walkNeverSwitch(double x, double y) {
		return walkNeverSwitch(new Point(x, y));
	}

	@Override
	public ChainableAction walkNeverSwitch(Point end) {
		WalkAction a = new WalkAction(this, ScenePresenter.DEFAULT_SCENE_OBJECT);
		a.setEndX(end.getX());
		a.setEndY(end.getY());
		return a;
	}

	@Override
	public ChainEndAction walkTo(double x, double y) {
		return walkTo(new Point(x, y));
	}

	@Override
	public ChainEndAction walkTo(Point end) {
		short dso = ScenePresenter.DEFAULT_SCENE_OBJECT;
		WalkMaybeSwitchAction a = new WalkMaybeSwitchAction(this, dso);
		a.setEndX(end.getX());
		a.setEndY(end.getY());
		return a;
	}

	@Override
	public ChainEndAction walkAlwaysSwitch(double x, double y, String sceneName, int arrivalSegment) throws A2gException {
		// best to throw this exception now, inside the Scene handler, rather
		// than when it is executed, which might be much later at the 
		// end of an asycnhronous animation execution chain.
		if(sceneName==null)
			throw new A2gException ("ChainableAction::walkAlwaysSwitch");
		return this.walkAlwaysSwitch( new Point(x,y), sceneName, arrivalSegment);
	}

	@Override
	public ChainEndAction walkAlwaysSwitch(Point p, String sceneName,
			int arrivalSegment) throws A2gException {
		// best to throw this exception now, inside the Scene handler, rather
		// than when it is executed, which might be much later at the 
		// end of an asycnhronous animation execution chain.
		if(sceneName==null)
			throw new A2gException (sceneName);
		WalkAction a = new WalkAction(this, ScenePresenter.DEFAULT_SCENE_OBJECT);
		a.setEndX(p.getX());
		a.setEndY(p.getY());
		a.setToInitialAtEnd(false);

		SingleCallAction b = new SingleCallAction(a, Type.Switch);
		b.setString(sceneName);
		b.setInt(arrivalSegment);

		return b;
	}

	@Override
	public ChainEndAction walkAndScaleAlwaysSwitch(short ocode, Point p,
			double startScale, double endScale, String sceneName,
			int arrivalSegment) throws A2gException {
		// best to throw this exception now, inside the Scene handler, rather
		// than when it is executed, which might be much later at the 
		// end of an asycnhronous animation execution chain.
		if(sceneName==null)
			throw new A2gException(sceneName);
		WalkAction a = new WalkAction(this, ocode);
		a.setEndX(p.getX());
		a.setEndY(p.getY());
		a.setStartScale(startScale);
		a.setEndScale(endScale);
		a.setToInitialAtEnd(false);

		SingleCallAction b = new SingleCallAction(a, Type.Switch);
		b.setString(sceneName);
		b.setInt(arrivalSegment);

		return b;
	}

	@Override
	public ChainableAction walkNeverSwitch(short ocode, double x, double y) {
		return walkNeverSwitch(ocode, new Point(x, y));
	}

	@Override
	public ChainableAction walkNeverSwitch(short ocode, Point end) {
		WalkAction a = new WalkAction(this, ocode);
		a.setEndX(end.getX());
		a.setEndY(end.getY());
		return a;
	}

	@Override
	public ChainableAction playTitleCard(String text, double durationInSecs) {
		return new TitleCardAction(this, text, durationInSecs);
	}

	@Override
	public ChainableAction playAnimationNonBlockingHoldLastFrame(String atid) {
		PlayAnimationAction a = new PlayAnimationAction(this, atid);

		a.setHoldLastFrame(true);
		a.setParallel(true);
		return a;
	}

	@Override
	public ChainableAction setValue(Object object, int i) {
		SingleCallAction a = new SingleCallAction(this, Type.SetValue);
		a.setString(object.toString());
		a.setInt(i);
		return a;
	}

	@Override
	public ChainableAction moveWhilstAnimating(short ocode, double x, double y) {
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this, ocode);
		a.setEndX(x);
		a.setEndY(y);
		return a;
	}

	@Override
	public ChainableAction moveWhilstAnimatingInY(short objId, double y) {
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this, objId);
		a.setEndY(y);
		return a;
	}

	@Override
	public ChainableAction moveWhilstAnimatingNonBlocking(short objId,
			double x, double y) {
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this, objId);
		a.setEndX(x);
		a.setEndY(y);
		a.setParallel(true);
		return a;
	}

	@Override
	public ChainableAction moveWhilstAnimatingLinear(short objId, double x,
			double y) {
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this, objId);
		a.setEndX(x);
		a.setEndY(y);
		a.setLinear(true);
		return a;
	}

	@Override
	public ChainableAction moveWhilstAnimatingLinearNonBlocking(short objId,
			double x, double y) {
		MoveWhilstAnimatingAction a = new MoveWhilstAnimatingAction(this, objId);
		a.setEndX(x);
		a.setEndY(y);
		a.setLinear(true);
		a.setParallel(true);
		return a;
	}

	@Override
	public ChainableAction moveCameraToNewXPosition(double x,
			double durationInSecs) {
		ScrollCameraXAction a = new ScrollCameraXAction(this, x, durationInSecs);
		a.setParallel(false);
		return a;
	}

	@Override
	public ChainableAction moveCameraToNewXPositionNonBlocking(double x,
			double durationInSecs) {
		ScrollCameraXAction a = new ScrollCameraXAction(this, x, durationInSecs);
		a.setParallel(true);
		return a;
	}

	@Override
	public ChainableAction moveCameraToNewYPosition(double y,
			double durationInSecs) {
		ScrollCameraYAction a = new ScrollCameraYAction(this, y, durationInSecs);
		a.setParallel(false);
		return a;
	}

	@Override
	public ChainableAction moveCameraToNewYPositionNonBlocking(double y,
			double durationInSecs) {
		ScrollCameraYAction a = new ScrollCameraYAction(this, y, durationInSecs);
		a.setParallel(true);
		return a;
	}

	@Override
	public ChainableAction hideAll() {
		SingleCallAction a = new SingleCallAction(this, Type.HideAll);
		return a;
	}

	@Override
	public ChainableAction setToInitialPosition(short ocode) {
		SingleCallAction a = new SingleCallAction(this,
				Type.SetToInitialPosition);
		a.setOCode(ocode);
		return a;
	}

	
	@Override
	public ChainableAction alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation(
			String atid, int frame) {
		SingleCallAction a = new SingleCallAction(this, Type.AlignBaseMiddle);
		a.setAtid(atid);
		a.setInt(frame);
		return a;
	}

	@Override
	public ChainableAction alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(
			String atid) {
		SingleCallAction a = new SingleCallAction(this, Type.AlignBaseMiddle);
		a.setAtid(atid);
		a.setInt(0);
		return a;
	}

	@Override
	public ChainableAction alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(
			String atid) {
		SingleCallAction a = new SingleCallAction(this, Type.AlignBaseMiddle);
		a.setAtid(atid);
		a.setInt(-17);
		return a;
	}

	@Override
	public ChainableAction shareWinning(String string) {
		SingleCallAction a = new SingleCallAction(this, Type.ShareWinning);
		a.setString(string);
		return a;
	}



	@Override
	public ChainableAction setSoundtrack(String stid) {
		SingleCallAction a = new SingleCallAction(this, Type.SetSoundtrack);
		a.setString(stid);
		return a;
	}

	@Override
	public ChainableAction playSound(String stid) {
		PlaySoundAction a = new PlaySoundAction(this, stid);
		return a;
	}

	@Override
	public ChainableAction playSoundNonBlocking(String stid) {
		PlaySoundAction a = new PlaySoundAction(this, stid);
		a.setParallel(true);
		return a;
	}

	@Override
	public ChainableAction setAnimationAsObjectWalkDirection(String atid,
			WalkDirection type) {
		SingleCallAction s = new SingleCallAction(this,
				Type.SetAnimationSpecial);
		s.setAtid(atid);
		s.setString(type.toString());
		s.setInt(type.toInt());
		return s;
	}

	@Override
	public ChainableAction setAnimationAsSceneTalker(String atid) {
		SingleCallAction s = new SingleCallAction(this,
				Type.SetAnimationAsSceneTalker);
		s.setAtid(atid);
		return s;
	}

	@Override
	public ChainableAction setAnimationAsObjectInitial(String atid) {
		SingleCallAction s = new SingleCallAction(this,
				Type.SetAnimationObjectInitial);
		s.setAtid(atid);
		return s;
	}

	@Override
	public ChainableAction setHeadRectangleForObject(short ocode, int index) {
		SingleCallAction s = new SingleCallAction(this, Type.SetHeadRectangle);
		s.setOCode(ocode);
		s.setInt(index);
		return s;
	}

	@Override
	public ChainableAction walkAndTalkNeverSwitch(short ocode, double x,
			double y, String speech) {
		WalkAndTalkAction s = new WalkAndTalkAction(this, ocode, speech);
		s.setNonIncrementing(TalkPerformer.NonIncrementing.True);
		s.setEndX(x);
		s.setEndY(y);
		return s;

	}

	@Override
	public ChainableAction walkAndScaleNeverSwitch(short ocode, Point p,
			double startScale, double endScale) {
		WalkAction s = new WalkAction(this, ocode);
		s.setEndX(p.getX());
		s.setEndY(p.getY());
		s.setStartScale(startScale);
		s.setEndScale(endScale);
		return s;

	}

	@Override
	public ChainableAction walkNeverSwitchNonBlocking(short ocode, double x,
			double y) {
		WalkAction s = new WalkAction(this, ocode);
		s.setEndX(x);
		s.setEndY(y);
		s.setParallel(true);
		return s;
	}
	
	@Override
	public ChainableAction look(String atid, double duration) {
		LookAction s = new LookAction(this, atid, duration);
		return s;
	}

	
}
