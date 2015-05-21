package com.github.a2g.core.interfaces;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.action.DecoratedForSceneBaseAction.SwapType;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.PointF;

public interface IChainRootForScene {
	public BaseAction doBoth(ChainableAction a, ChainableAction b);
	public BaseAction activateDialogTreeMode(int branchId);
	public BaseAction onDoCommand(IGameScene scene, IOnDoCommand api,
			ChainRootAction ba, int verb, SentenceItem itemA,
			SentenceItem itemB, double x, double y);
	public BaseAction subroutine(BaseAction orig);
	public BaseAction switchTo(String sceneName);
	public BaseAction switchWalkTo(double x, double y);
	public BaseAction switchWalkTo(PointF point);
	public BaseAction quit();
	
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
	public ChainableAction playAnimationRepeatWhilstVisible(String animationCode);
	public ChainableAction talk(String animCode, String speech);
	public ChainableAction talk(String speech);
	public ChainableAction talkWithoutIncrementingFrame(String animCode,
			String speech);
	public ChainableAction talkWithoutIncrementingHoldLastFrame(String animCode,
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

	public ChainableAction swapProperty(short ocodeA, short ocodeB, SwapType type);

	public ChainableAction waitForFrame(short ocode, int frame);

	public ChainableAction setInitialAnimation(String atid);


	public ChainableAction walkTo(double x, double y);
	public ChainableAction walkTo(PointF point);
	public ChainableAction walkTo(double x, double y, int delay) ;
	public ChainableAction walkTo(PointF point, int delay);
	public ChainableAction walkTo(short ocode, double x, double y);
	public ChainableAction walkTo(short ocode, PointF point);
	public ChainableAction walkTo(short ocode, double x, double y, int delay);
	public ChainableAction walkTo(short ocode, PointF point, int delay);
	public ChainableAction walkAndScroll(double x, double y, int delay);

	public ChainableAction walkAndScroll(PointF point, int delay);

	public ChainableAction walkAndScrollNonBlocking(double x, double y, int delay);

	public ChainableAction walkAndScrollNonBlocking(PointF point, int delay);

	public ChainableAction walkAndScroll(short ocode, double x, double y,
			int delay);

	public ChainableAction walkAndScroll(short ocode, PointF point, int delay);
	public ChainableAction walkAndScrollNonBlocking(short ocode, double x,
			double y, int delay);

	public ChainableAction walkAndScrollNonBlocking(short ocode, PointF point,
			int delay);

	public ChainableAction showTitleCard(String text);


	public ChainableAction playAnimationNonBlockingHoldLastFrame(
			String animationCode);

	public ChainableAction setValue(String string, int i);

	public ChainableAction moveWhilstAnimatingLinear(short objId, double x,
			double y);

	public ChainableAction moveWhilstAnimatingLinearNonBlocking(short ocode,
			double x, double y);

	public ChainableAction moveWhilstAnimating(short objId, double x, double y);

	public ChainableAction moveWhilstAnimatingInY(short objId, double y);

	public ChainableAction moveWhilstAnimatingNonBlocking(short objId, double x,
			double y);

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


}
