package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.action.ChainEndAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.interfaces.IOnDoCommand;
import com.github.a2g.core.interfaces.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.primitive.A2gException;

public interface IChainRootForScene {
	// all the ChainEndActions can only be the last
	// action in a chain.
	// subroutine is an exeption since there is both a ChainEndAction
	// and ChainableAction version of it.
	public ChainEndAction doBoth(ChainableAction a, ChainableAction b);
	public ChainEndAction activateDialogTreeMode(int branchId);
	public ChainEndAction onDoCommand(IGameScene scene, IOnDoCommand api,
			ChainRootAction ba, int verb, SentenceItem itemA,
			SentenceItem itemB, double x, double y) throws A2gException;
	public ChainEndAction subroutine(ChainEndAction orig);
	public ChainEndAction switchTo(String sceneName, int arrivalSegment);
	public ChainEndAction walkTo(double x, double y);// should not have ocode here because switching implies main character
	public ChainEndAction walkTo(PointF point);
	
	// there's 2 choices
	// 1) BaseAction walkSwitch and ChainAbleAction walkTo
	// 2) BaseAction walkTo, and ChainableAction walkWithNoSwitch
	// client is likely to code with walkTo
	// in case 1, using walkTo in its wrong scenario (as
	// a terminator) would not be picked up by compiler.
	// since a chainableAction would pass as a baseAction.
	// in case 2, using walkTo in its wrong scenario (in
	// the middle of a chain) where client expects it not
	// to switch, WOULD generate a compiler error.
	// Thus 2 is better, because it protects the user
	// against unexpected results.
	public ChainableAction walkNeverSwitch(double x, double y);
	public ChainableAction walkNeverSwitch(PointF point);
	public ChainableAction walkNeverSwitchNonBlocking(short ocode, double x, double y);
	public ChainableAction walkNeverSwitch(short ocode, double x, double y);
	public ChainableAction walkNeverSwitch(short ocode, PointF point);
	public ChainableAction walkAndTalkNeverSwitch(short ocode, double x, double y, String speech);
	public ChainableAction walkAndScaleNeverSwitch(short ocode, PointF p, double startScale, double endScale);
	public ChainEndAction walkAlwaysSwitch(double x, double y, String sceneName, int arrivalSegment) throws A2gException ;
	public ChainEndAction walkAlwaysSwitch(PointF point, String string, int arrivalSegment) throws A2gException ;
	public ChainEndAction walkAndScaleAlwaysSwitch(short ocode, PointF p, double startScale, double endScale, String sceneName, int arrivalSegment) throws A2gException;
	
	public ChainableAction subroutine(ChainableAction orig);

	public ChainableAction doNothing();

	// plain..
	public ChainableAction playAnimation(String animationCode);

	// simple backwards
	public ChainableAction playAnimationBackwards(String animationCode);

	// simple hold last frame
	public ChainableAction playAnimationHoldLastFrame(String animationCode);

	// simple non blocking
	public ChainableAction playAnimationNonBlocking(String animationCode);

	// double combo1of3: backwards + hold last frame
	public ChainableAction playAnimationBackwardsHoldLastFrame(
			String animationCode);

	// double combo2of3: backwards + nonblocking
	public ChainableAction playAnimationBackwardsNonBlocking(String animationCode);

	// double combo2of3: holdLastFrame + nonblocking
	public ChainableAction playAnimationHoldLastFrameNonBlocking(
			String animationCode);
	// ..and one method with the whole lot!
	public ChainableAction playAnimationBackwardsHoldLastFrameNonBlocking(
			String animationCode);
	public ChainableAction talk(String animCode, String speech);
	public ChainableAction talk(String speech);
	public ChainableAction talkWithoutIncrementingFrame(String animCode,
			String speech);
	public ChainableAction talkWithoutIncrementingFrameNonBlocking(
			String animCode, String speech);
	public ChainableAction talkWithoutIncrementingFrame(String speech);
	public ChainableAction talkWithoutIncrementingFrameNonBlocking(String speech);
	public ChainableAction setCurrentAnimation(String atid);
	public ChainableAction setCurrentAnimationAndFrame(String atid, int frame);
	public ChainableAction setCurrentFrame(short ocode, int frame);
	public ChainableAction setBaseMiddleX(short ocode, double x);
	public ChainableAction setBaseMiddleY(short ocode, double y);


	public ChainableAction setDisplayName(short ocode, String displayName);
	public ChainableAction setInventoryVisible(int inventoryId, boolean isVisible);
	public ChainableAction setVisible(short ocode, boolean isVisible);

	public ChainableAction sleep(int milliseconds);

	public ChainableAction swapVisibility(short ocodeA, short ocodeB);

	public ChainableAction waitForFrame(short ocode, int frame);

	public ChainableAction setInitialAnimation(String atid);


	public ChainableAction showTitleCard(String text);


	public ChainableAction playAnimationNonBlockingHoldLastFrame(
			String animationCode);

	public ChainableAction setValue(String string, int i);

	public ChainableAction moveWhilstAnimating(short objId, double x, double y);

	public ChainableAction moveWhilstAnimatingInY(short objId, double y);

	public ChainableAction moveWhilstAnimatingNonBlocking(short objId, double x,
			double y);
	public ChainableAction moveWhilstAnimatingLinearNonBlocking(short objId, double x,
			double y);
	public ChainableAction moveWhilstAnimatingLinear(short objId, double x, double y);
	

	public ChainableAction moveCameraToNewXPosition(double x,
			double durationInSecs);

	public ChainableAction moveCameraToNewXPositionNonBlocking(double x,
			double durationInSecs);

	public ChainableAction moveCameraToNewYPosition(double y,
			double durationInSecs);

	public ChainableAction moveCameraToNewYPositionNonBlocking(double y,
			double durationInSecs);

	public ChainableAction hideAll();
	public ChainableAction setToInitialPosition(short ocode) ;
	public ChainableAction alignBaseMiddleOfOldFrameToFrameOfNewAnimation(
			String atid, int frame);
	public ChainableAction alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(String atid);
	public ChainableAction alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(String atid);
	public ChainableAction share(String string);
	public ChainableAction setSceneTalker(String atid);

	public ChainableAction playSound(String stid);
	public ChainableAction playSoundNonBlocking(String stid);
	public ChainableAction setSoundtrack(String stid);
	public ChainableAction setAnimationAsObjectSpecial(String atid, WalkDirection type);
	public ChainableAction setAnimationAsSceneTalker(String atid);
	public ChainableAction setAnimationAsObjectInitial(String atid);
	public ChainableAction setSpeechBubble(short ocode, int index);
	

}
