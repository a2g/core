package com.github.a2g.core.interfaces.game.chainables;

import com.github.a2g.core.interfaces.game.handlers.IOnDoCommand;
import com.github.a2g.core.interfaces.game.scene.IGameScene;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToLastFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.IDoNothing;
import com.github.a2g.core.interfaces.game.singles.IHideAll;
import com.github.a2g.core.interfaces.game.singles.ILook;
import com.github.a2g.core.interfaces.game.singles.IMoveCamera;
import com.github.a2g.core.interfaces.game.singles.ITalkWithoutIncrementingFrame;
import com.github.a2g.core.interfaces.game.singles.IWaitForFrame;
import com.github.a2g.core.interfaces.game.singles.IMoveWhilstAnimating;
import com.github.a2g.core.interfaces.game.singles.IPlayAnimation;
import com.github.a2g.core.interfaces.game.singles.IPlaySound;
import com.github.a2g.core.interfaces.game.singles.IPlayTitleCard;
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
import com.github.a2g.core.interfaces.game.singles.ISetTitleCard;
import com.github.a2g.core.interfaces.game.singles.ISetToInitialPosition;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.ISetVisible;
import com.github.a2g.core.interfaces.game.singles.IShareWinning;
import com.github.a2g.core.interfaces.game.singles.ISleep;
import com.github.a2g.core.interfaces.game.singles.ISwapVisibility;
import com.github.a2g.core.interfaces.game.singles.ITalk;
import com.github.a2g.core.interfaces.game.singles.IWalkAlwaysSwitch;
import com.github.a2g.core.interfaces.game.singles.IWalkAndScale;
import com.github.a2g.core.interfaces.game.singles.IWalkAndTalk;
import com.github.a2g.core.interfaces.game.singles.IWalkNeverSwitch;
import com.github.a2g.core.interfaces.game.singles.IWalkTo;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.google.gwt.touch.client.Point;
import com.github.a2g.core.primitive.A2gException;

/** @brief This facilitates the returning of chains in @ref IOnEntry or @ref IOnDoCommand */
public interface ISceneChain extends ISceneChainEnd
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
, ISetTitleCard
, ISleep
, IWalkTo
, IWalkAlwaysSwitch
, IDoNothing
, ISetToInitialPosition
, ILook
, IHideAll
, IMoveWhilstAnimating
, ITalkWithoutIncrementingFrame
, ITalk
, IMoveCamera
, IPlayAnimation
, IPlaySound
, IPlayTitleCard
, ISwapVisibility
, IWaitForFrame
, IWalkAndTalk
, IWalkAndScale
, IWalkNeverSwitch
{
	
	/**   @name End-of-chain-only methods that return ChainEndActions 
	 *  These do something uncontinuable, and so they've been
	 *  made to return ChainEndAction, which has no methods on it.
	 * The exception here is  "subroutine" which has both a ChainEndAction
	 *   and ISceneChain version of it.
	 */
	//@{
	public ISceneChainEnd doBoth(ISceneChain a, ISceneChain b);
	public ISceneChainEnd activateDialogTreeMode(int branchId);
	public ISceneChainEnd onDoCommand(IGameScene scene, IOnDoCommand api,
			ISceneChainRoot ba, int verb, SentenceItem itemA,
			SentenceItem itemB, double x, double y) throws A2gException;
	public ISceneChain  subroutine(ISceneChain b);
	public ISceneChainEnd  switchTo(String sceneName, int entrySegment);
	//@}
	
	
	/** @name End-of-chain-only Walk using edge detection 
	 * These use the scene walker so they don't need any otid.
	 */
	//@{
	@Override ISceneChainEnd  walkTo(double x, double y); 
	@Override ISceneChainEnd  walkTo(Point point);
	//@}

	/** @name End-of-chain-only Walk, but no edge detection 
	 *  These all take a parameter for the "sceneName" that they switch to upon termination.
	 */
	//@{
	@Override ISceneChainEnd walkAlwaysSwitch(double x, double y, String sceneName, int entrySegment) throws A2gException ;
	@Override ISceneChainEnd  walkAlwaysSwitch(Point point, String sceneName, int entrySegment) throws A2gException ;
	@Override ISceneChainEnd  walkAndScaleAlwaysSwitch(short ocode, Point p, double startScale, double endScale, String sceneName, int entrySegment) throws A2gException;
	//@}
	
	/**  @name Walk with no edge detection
	 * All the methods from this point on can be positioned in the start, end, or middle of method chains.
     */
	//@{
	@Override ISceneChain walkNeverSwitch(double x, double y);
	@Override ISceneChain walkNeverSwitch(Point point);
	@Override ISceneChain walkNeverSwitchNonBlocking(short ocode, double x, double y);
	@Override ISceneChain walkNeverSwitch(short ocode, double x, double y);
	@Override ISceneChain walkNeverSwitch(short ocode, Point point);
	@Override ISceneChain walkAndTalkNeverSwitch(short ocode, double x, double y, String speech);
	@Override ISceneChain walkAndScaleNeverSwitch(short ocode, Point p, double startScale, double endScale);
	//@}
	

	/** @name Play animation methods starting with plain.. */
	//@{
	@Override ISceneChain playAnimation(String animationCode);
	//@}

	/** @name ..simple backwards ...*/
	//@{
	@Override ISceneChain playAnimationBackwards(String animationCode);
	//@}

	/** @name ..simple hold last frame ... */
	//@{
	@Override ISceneChain playAnimationHoldLastFrame(String animationCode);
	//@}

	/** @name ... simple non blocking ... */
	//@{
	@Override ISceneChain playAnimationNonBlocking(String animationCode);
	//@}

	/** @name ... double combo1of3: backwards + hold last frame ... */
	//@{
	@Override ISceneChain playAnimationBackwardsHoldLastFrame(String animationCode);
	//@}

	/** @name ... double combo2of3: backwards + nonblocking ... */
	//@{
	@Override ISceneChain playAnimationBackwardsNonBlocking(String animationCode);
	//@}
	
	/** @name ... double combo2of3: holdLastFrame + nonblocking ... */
	//@{
	@Override ISceneChain playAnimationHoldLastFrameNonBlocking(String animationCode);
	@Override ISceneChain playAnimationNonBlockingHoldLastFrame(String animationCode);
	//@}
	
    /** @name ..and one method with the whole lot! */
	//@{
	@Override ISceneChain playAnimationBackwardsHoldLastFrameNonBlocking(String animationCode);
	//@}


	 
	/** @name Play commands that don't take animation ids*/
	//@{
	@Override ISceneChain playSound(String stid);
	@Override ISceneChain playSoundNonBlocking(String stid);
	
	/**  playTitleCard has a duration, as opposed to setTitleCard in the direct api, which doesn't  */
	@Override ISceneChain playTitleCard(String text, double durationInSecs);
	//@}
	
	/** @name Talk methods */
	//@{
	@Override ISceneChain talk(String animCode, String speech);
	@Override ISceneChain talk(String speech);
	@Override ISceneChain talkWithoutIncrementingFrame(String animCode, String speech);
	@Override ISceneChain talkWithoutIncrementingFrameNonBlocking(String animCode, String speech);
	@Override ISceneChain talkWithoutIncrementingFrame(String speech);
	@Override ISceneChain talkWithoutIncrementingFrameNonBlocking(String speech);
	//@}
	
	/** @name Move-whilst-animating - less animation frame hungry way of moving */
	//@{
	@Override ISceneChain moveWhilstAnimating(short objId, double x, double y);
	@Override ISceneChain moveWhilstAnimatingInY(short objId, double y);
	@Override ISceneChain moveWhilstAnimatingNonBlocking(short objId, double x,double y);
	@Override ISceneChain moveWhilstAnimatingLinearNonBlocking(short objId, double x,double y);
	@Override ISceneChain moveWhilstAnimatingLinear(short objId, double x, double y);
	//@}
	
	/** @name  Move camera... */
	//@{
	@Override ISceneChain moveCameraToNewXPosition(double x,double durationInSecs);
	@Override ISceneChain moveCameraToNewXPositionNonBlocking(double x,double durationInSecs);
	@Override ISceneChain moveCameraToNewYPosition(double y,double durationInSecs);
	@Override ISceneChain moveCameraToNewYPositionNonBlocking(double y,double durationInSecs);
	//@}
	
	/** @name Look */
	//@{
	/** I added "look" a new verb that just takes the first frame of the animation*/
	@Override ISceneChain look(String atid, double duration);
	//@}
	
	/** @name Commands that seem direct, but all have a temporal aspect */
	//@{
	@Override ISceneChain sleep(int milliseconds);
	@Override ISceneChain swapVisibility(short ocodeA, short ocodeB);
	@Override ISceneChain waitForFrame(short ocode, int frame);
	//@}
	
	/** @name Direct api candidates! 
	 * these should really be in the direct api */
	//@{
	@Override ISceneChain hideAll();
	@Override ISceneChain setToInitialPosition(short ocode) ;
	//@}
	
	/** @name Utility methods */
	//@{
	@Override ISceneChain doNothing();
	//@}
	
	/** @name Methods shared with direct api */
	//@{
	@Override ISceneChain  setVisible(short ocode, boolean isVisible);
	@Override ISceneChain  setAnimationAsObjectInitial(String atid);
	@Override ISceneChain  setAnimationAsSceneTalker (String atid);
	@Override ISceneChain  setHeadRectangleForObject(short ocode, int index);
	@Override ISceneChain  setAnimationAsObjectWalkDirection(String atid, WalkDirection type);
	@Override ISceneChain  setSoundtrack(String stid) ;
	@Override ISceneChain  shareWinning(String token);
	@Override ISceneChain  setTitleCard(String text);
	
	
	@Override ISceneChain  alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(String atid);
	@Override ISceneChain  alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(String atid);
	@Override ISceneChain  alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation(String atid, int frame);
	@Override ISceneChain  setValue(Object key, int value);
	@Override ISceneChain  setAnimationAsObjectCurrent (String atid);
	@Override ISceneChain  setAnimationAsObjectCurrentAndSetFrame(String atid, int frame); 
	@Override ISceneChain  setBaseMiddleX(short ocode, double bmx); 
	@Override ISceneChain  setBaseMiddleY(short ocode, double bmy); 
	@Override ISceneChain  setCurrentFrame(short ocode, int frame) ;
	@Override ISceneChain  setDisplayName(short ocode, String displayName); 
	@Override ISceneChain  setInventoryItemVisible(int icode, boolean isVisible); 
	//@}
	

/** @name Interfaces
 *  @if(nongame) 
 *   The methods are named the same as the interfaces they are included within.
 *  If a direct -api method is to be shared, then this is done by inheriting from direct-api interfaces and overriding.
 *  This means that when direct api methods are renamed in the interfaces, the methods overriding those interfaces are renamed too.
 *  @endif
 */

 
}
