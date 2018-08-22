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

package com.github.a2g.core.interfaces.game.handlers;

import com.github.a2g.core.interfaces.game.singles.IAddEdgeSpanToPerimeter;
import com.github.a2g.core.interfaces.game.singles.IAddHelperPoint;
import com.github.a2g.core.interfaces.game.singles.IAddHelperRectangle;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToLastFrameOfNewAnimation;
import com.github.a2g.core.action.ChainEndAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.github.a2g.core.interfaces.game.singles.IAddEdgePointToPerimeter;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.IClearValuesAndSaidSpeech;
import com.github.a2g.core.interfaces.game.singles.ICreateChainRootAction;
import com.github.a2g.core.interfaces.game.singles.IGetAnimationLastFrame;
import com.github.a2g.core.interfaces.game.singles.IGetAnimationLength;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentAnimation;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentFrame;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetHelperPoint;
import com.github.a2g.core.interfaces.game.singles.IGetHelperRectangle;
import com.github.a2g.core.interfaces.game.singles.IGetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.game.singles.IGetLastSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetValue;
import com.github.a2g.core.interfaces.game.singles.IGetX;
import com.github.a2g.core.interfaces.game.singles.IGetY;
import com.github.a2g.core.interfaces.game.singles.IHide;
import com.github.a2g.core.interfaces.game.singles.IHideInventoryItem;
import com.github.a2g.core.interfaces.game.singles.IIsInDebugMode;
import com.github.a2g.core.interfaces.game.singles.IIsInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.IIsTrue;
import com.github.a2g.core.interfaces.game.singles.IIsVisible;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsSceneTalker;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationDuration;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.ISetCurrentFrame;
import com.github.a2g.core.interfaces.game.singles.ISetDefaultSceneObject;
import com.github.a2g.core.interfaces.game.singles.ISetDisplayName;
import com.github.a2g.core.interfaces.game.singles.ISetInventoryItemDisplayName;
import com.github.a2g.core.interfaces.game.singles.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.ISetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.game.singles.ISetScreenCoordsPerSecond;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.ISetX;
import com.github.a2g.core.interfaces.game.singles.ISetY;
import com.github.a2g.core.interfaces.game.singles.IShareWinning;
import com.github.a2g.core.interfaces.game.singles.IShow;
import com.github.a2g.core.interfaces.game.singles.IShowInventoryItem;
import com.github.a2g.core.interfaces.game.singles.ISwitchToScene;
import com.github.a2g.core.primitive.RectF;

/**  
 * 
 * @author Admin
 *
 */
public interface IOnDoCommand
extends
// property access methods
IGetValue,
IIsTrue,
ISetValue,
IClearValuesAndSaidSpeech,

// helpful for game
IGetCurrentSceneName,
ISwitchToScene,
IGetLastSceneName,
IIsInDebugMode,
ISetIsTalkingAlwaysWithoutIncrementing,
IGetIsTalkingAlwaysWithoutIncrementing,
IShareWinning,
ICreateChainRootAction,

// boundary and helper
IAddEdgeSpanToPerimeter ,
IAddEdgePointToPerimeter ,
IGetHelperPoint,
IAddHelperPoint,
IGetHelperRectangle,
IAddHelperRectangle,


// object
IHide,
IShow,
IGetBaseMiddleX,
IGetBaseMiddleY,
ISetBaseMiddleX,
ISetBaseMiddleY,
ISetX,
ISetY,
IGetX,
IGetY,
IIsVisible,
IGetCurrentAnimation,
IGetCurrentFrame,
ISetCurrentFrame,
ISetDisplayName,
ISetDefaultSceneObject,
ISetScreenCoordsPerSecond,

// animation
IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation,
IAlignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation,
IAlignBaseMiddleOfOldFrameToLastFrameOfNewAnimation,
ISetAnimationAsObjectInitial,
ISetAnimationAsSceneTalker,
ISetAnimationAsObjectCurrent,
IGetAnimationLastFrame,
IGetAnimationLength,
ISetAnimationDuration,

// inventory
IShowInventoryItem,
IHideInventoryItem,
IIsInventoryItemVisible,
ISetInventoryItemDisplayName,
ISetInventoryItemVisible
{

	/**   @name Property access methods */
	//@{
	@Override int getValue(Object key);
	@Override boolean isTrue(Object key);
	@Override IChainBase setValue(Object key, int value);
	@Override  void clearValuesAndSaidSpeech();
	//@}

	/**   @name Helpful for game */
	//@{
	@Override String getCurrentSceneName();
	@Override ChainEndAction switchToScene(String name, int arrivalSegment);
	@Override boolean isInDebugMode();
	@Override String getLastSceneName();
	@Override IChainBase shareWinning(String string);
	@Override ChainRootAction createChainRootAction();
	
	//@}

	/** @name EdgeDetection and Helper */
	//@{
	@Override void addEdgeSpanToPerimeter(double tlx, double tly, double blx, double bly, Object toStringIsCalledOnThisToDetermineTheNameToSwitchToWhenGateIsEntered, int arrivalSegment);
	@Override void addEdgePointToPerimeter(double tlx, double tly);
	@Override int addHelperPoint(double x, double y);
	@Override int addHelperRectangle(RectF rectF);
	//@}
	
	/**   @name Inventory */
	//@{
	@Override IChainBase showInventoryItem(int icode);
	@Override IChainBase hideInventoryItem(int icode);
	@Override boolean isInventoryItemVisible(int code);
	@Override IChainBase setInventoryItemDisplayName(int icode, String displayName);
	@Override IChainBase setInventoryItemVisible(int icode, boolean isVisible);
	//@}

	/**   @name Object */
	//@{
	@Override IChainBase show(short ocode);
	@Override IChainBase hide(short ocode);
	@Override IChainBase setBaseMiddleX(short ocode, double bmx);
	@Override IChainBase setBaseMiddleY(short ocode, double bmy);
	@Override double getBaseMiddleX(short ocode);
	@Override double getBaseMiddleY(short ocode);
	@Override int getX(short ocode);
	@Override int getY(short ocode);
	@Override IChainBase setX(short ocode, double x);
	@Override IChainBase setY(short ocode, double y);
	@Override boolean isVisible(short ocode);
	@Override String getCurrentAnimation(short ocode);
	@Override int getCurrentFrame(short ocode);
	@Override IChainBase setCurrentFrame(short ocode, int frame);
	@Override IChainBase setDisplayName(short ocode, String displayName);
	@Override IChainBase setDefaultSceneObject(short ocode);
	@Override IChainBase setScreenCoordsPerSecond(short ocode, double coordsPerSecond);
	//@}

	/**   @name Animation */
	//@{
	@Override IChainBase setAnimationAsSceneTalker(String atid);
	@Override IChainBase setAnimationAsObjectInitial(String atid);
	@Override IChainBase setAnimationAsObjectCurrent (String atid);
	@Override IChainBase alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation(String atid, int frame);
	@Override IChainBase alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(String atid);
	@Override IChainBase alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(String atid);
	@Override int getAnimationLastFrame(String atid);
	@Override int getAnimationLength(String atid);
	@Override IChainBase setAnimationDuration(String atid, double duration);
	//@}
 
	
	

}
