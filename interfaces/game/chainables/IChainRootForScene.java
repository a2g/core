package com.github.a2g.core.interfaces.game.chainables;

import com.github.a2g.core.action.ChainEndAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.interfaces.game.handlers.IOnDoCommand;
import com.github.a2g.core.interfaces.game.scene.IGameScene;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToLastFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectCurrentAndSetFrame;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectWalkDirection;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsSceneTalker;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.ISetCurrentFrame;
import com.github.a2g.core.interfaces.game.singles.ISetDisplayName;
import com.github.a2g.core.interfaces.game.singles.ISetHeadRectangleForObject;
import com.github.a2g.core.interfaces.game.singles.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.ISetSoundtrack;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.ISetVisible;
import com.github.a2g.core.interfaces.game.singles.IShareWinning;
import com.github.a2g.core.interfaces.game.singles.ISleep;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.google.gwt.touch.client.Point;
import com.github.a2g.core.primitive.A2gException;

/** @brief This interface lists all the methods you string together as a big return object from either @ref IOnEntry or @ref IOnDoCommand */
public interface IChainRootForScene extends IChainRootCommon
, ISetVisible
, ISetAnimationAsObjectInitial
, ISetAnimationAsSceneTalker 
, ISetHeadRectangleForObject 
, ISetAnimationAsObjectWalkDirection
, ISetSoundtrack 
, IShareWinning
, IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation
, IAlignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation
, IAlignBaseMiddleOfOldFrameToLastFrameOfNewAnimation
, ISetValue
, ISetAnimationAsObjectCurrent 
, ISetAnimationAsObjectCurrentAndSetFrame 
, ISetBaseMiddleX 
, ISetBaseMiddleY 
, ISetCurrentFrame 
, ISetDisplayName 
, ISetInventoryItemVisible 
, ISleep
{
	
	/**   @name End-of-chain-only methods that return ChainEndActions 
	 *  These do something uncontinuable, and so they've been
	 *  made to return ChainEndAction, which has no methods on it.
	 * The exception here is  "subroutine" which has both a ChainEndAction
	 *   and ChainableAction version of it.
	 */
	//@{
	public ChainEndAction doBoth(ChainableAction a, ChainableAction b);
	public ChainEndAction activateDialogTreeMode(int branchId);
	public ChainEndAction onDoCommand(IGameScene scene, IOnDoCommand api,
			ChainRootAction ba, int verb, SentenceItem itemA,
			SentenceItem itemB, double x, double y) throws A2gException;
	public ChainEndAction subroutine(ChainEndAction orig);
	public ChainEndAction switchTo(String sceneName, int arrivalSegment);
	//@}
	
	
	/** @name End-of-chain-only Walk using edge detection 
	 * These use the scene walker so they don't need any otid.
	 */
	//@{
	public ChainEndAction walkTo(double x, double y); 
	public ChainEndAction walkTo(Point point);
	//@}

	/** @name End-of-chain-only Walk, but no edge detection 
	 *  These all take a parameter for the "sceneName" that they switch to upon termination.
	 */
	//@{
	public ChainEndAction walkAlwaysSwitch(double x, double y, String sceneName, int arrivalSegment) throws A2gException ;
	public ChainEndAction walkAlwaysSwitch(Point point, String sceneName, int arrivalSegment) throws A2gException ;
	public ChainEndAction walkAndScaleAlwaysSwitch(short ocode, Point p, double startScale, double endScale, String sceneName, int arrivalSegment) throws A2gException;
	//@}
	
	/**  @name Walk with no edge detection
	 * All the methods from this point on can be positioned in the start, end, or middle of method chains.
     */
	//@{
	public ChainableAction walkNeverSwitch(double x, double y);
	public ChainableAction walkNeverSwitch(Point point);
	public ChainableAction walkNeverSwitchNonBlocking(short ocode, double x, double y);
	public ChainableAction walkNeverSwitch(short ocode, double x, double y);
	public ChainableAction walkNeverSwitch(short ocode, Point point);
	public ChainableAction walkAndTalkNeverSwitch(short ocode, double x, double y, String speech);
	public ChainableAction walkAndScaleNeverSwitch(short ocode, Point p, double startScale, double endScale);
	//@}
	

	/** @name Play animation methods starting with plain.. */
	//@{
	public ChainableAction playAnimation(String animationCode);
	//@}

	/** @name ..simple backwards ...*/
	//@{
	public ChainableAction playAnimationBackwards(String animationCode);
	//@}

	/** @name ..simple hold last frame ... */
	//@{
	public ChainableAction playAnimationHoldLastFrame(String animationCode);
	//@}

	/** @name ... simple non blocking ... */
	//@{
	public ChainableAction playAnimationNonBlocking(String animationCode);
	//@}

	/** @name ... double combo1of3: backwards + hold last frame ... */
	//@{
	public ChainableAction playAnimationBackwardsHoldLastFrame(String animationCode);
	//@}

	/** @name ... double combo2of3: backwards + nonblocking ... */
	//@{
	public ChainableAction playAnimationBackwardsNonBlocking(String animationCode);
	//@}
	
	/** @name ... double combo2of3: holdLastFrame + nonblocking ... */
	//@{
	public ChainableAction playAnimationHoldLastFrameNonBlocking(String animationCode);
	public ChainableAction playAnimationNonBlockingHoldLastFrame(String animationCode);
	//@}
	
    /** @name ..and one method with the whole lot! */
	//@{
	public ChainableAction playAnimationBackwardsHoldLastFrameNonBlocking(String animationCode);
	//@}


	 
	/** @name Play commands that don't take animation ids*/
	//@{
	public ChainableAction playSound(String stid);
	public ChainableAction playSoundNonBlocking(String stid);
	/**  play as opposed to setTitleCard in the direct api */
	public ChainableAction playTitleCard(String text, double durationInSecs);
	//@}
	
	/** @name Talk methods */
	//@{
	public ChainableAction talk(String animCode, String speech);
	public ChainableAction talk(String speech);
	public ChainableAction talkWithoutIncrementingFrame(String animCode, String speech);
	public ChainableAction talkWithoutIncrementingFrameNonBlocking(String animCode, String speech);
	public ChainableAction talkWithoutIncrementingFrame(String speech);
	public ChainableAction talkWithoutIncrementingFrameNonBlocking(String speech);
	//@}
	
	/** @name Move-whilst-animating - less animation frame hungry way of moving */
	//@{
	public ChainableAction moveWhilstAnimating(short objId, double x, double y);
	public ChainableAction moveWhilstAnimatingInY(short objId, double y);
	public ChainableAction moveWhilstAnimatingNonBlocking(short objId, double x,double y);
	public ChainableAction moveWhilstAnimatingLinearNonBlocking(short objId, double x,double y);
	public ChainableAction moveWhilstAnimatingLinear(short objId, double x, double y);
	//@}
	
	/** @name  Move camera... */
	//@{
	public ChainableAction moveCameraToNewXPosition(double x,double durationInSecs);
	public ChainableAction moveCameraToNewXPositionNonBlocking(double x,double durationInSecs);
	public ChainableAction moveCameraToNewYPosition(double y,double durationInSecs);
	public ChainableAction moveCameraToNewYPositionNonBlocking(double y,double durationInSecs);
	//@}
	
	/** @name Look */
	//@{
	/** I added "look" a new verb that just takes the first frame of the animation*/
	public ChainableAction look(String atid, double duration);
	//@}
	
	/** @name Commands that seem direct, but all have a temporal aspect */
	//@{
	@Override public ChainableAction sleep(int milliseconds);
	public ChainableAction swapVisibility(short ocodeA, short ocodeB);
	public ChainableAction waitForFrame(short ocode, int frame);
	//@}
	
	/** @name Direct api candidates! 
	 * these should really be in the direct api */
	//@{
	public ChainableAction hideAll();
	public ChainableAction setToInitialPosition(short ocode) ;
	//@}
	
	/** @name Utility methods */
	//@{
	public ChainableAction subroutine(ChainableAction orig);
	public ChainableAction doNothing();
	//@}
	

/** @name Direct API commands
 *  All the methods in the interfaces below are also available.
 *  The methods are named the same as the interfaces they are included within.
 *  They are done like this so that when the Direct API methods change, these change too. 
 */
///@{
	@Override
	public ChainableAction 	setVisible(short ocode, boolean visible);
///@}
 
}
