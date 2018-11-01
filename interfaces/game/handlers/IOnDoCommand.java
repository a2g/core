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
import com.github.a2g.core.chain.SceneChainEnd;
import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.interfaces.game.chainables.ISceneChainRoot;
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
	@Override IBaseChain setValue(Object key, int value);
	@Override  void clearValuesAndSaidSpeech();
	//@}

	/**   @name Helpful for game */
	//@{
	@Override String getCurrentSceneName();
	@Override SceneChainEnd switchToScene(String name, int entrySegment);
	@Override boolean isInDebugMode();
	@Override String getLastSceneName();
	@Override IBaseChain shareWinning(String string);
	@Override ISceneChainRoot createChainRootAction();
	
	//@}

	/** @name EdgeDetection and Helper */
	//@{
	@Override void addEdgeSpanToPerimeter(double tlx, double tly, double blx, double bly, Object toStringIsCalledOnThisToDetermineTheNameToSwitchToWhenGateIsEntered, int entrySegment);
	@Override void addEdgePointToPerimeter(double tlx, double tly);
	@Override int addHelperPoint(double x, double y);
	@Override int addHelperRectangle(RectF rectF);
	//@}
	
	/**   @name Inventory */
	//@{
	@Override IBaseChain showInventoryItem(int icode);
	@Override IBaseChain hideInventoryItem(int icode);
	@Override boolean isInventoryItemVisible(int code);
	@Override IBaseChain setInventoryItemDisplayName(int icode, String displayName);
	@Override IBaseChain setInventoryItemVisible(int icode, boolean isVisible);
	//@}

	/**   @name Object */
	//@{
	@Override IBaseChain show(short ocode);
	@Override IBaseChain hide(short ocode);
	@Override IBaseChain setBaseMiddleX(short ocode, double bmx);
	@Override IBaseChain setBaseMiddleY(short ocode, double bmy);
	@Override double getBaseMiddleX(short ocode);
	@Override double getBaseMiddleY(short ocode);
	@Override int getX(short ocode);
	@Override int getY(short ocode);
	@Override IBaseChain setX(short ocode, double x);
	@Override IBaseChain setY(short ocode, double y);
	@Override boolean isVisible(short ocode);
	@Override String getCurrentAnimation(short ocode);
	@Override int getCurrentFrame(short ocode);
	@Override IBaseChain setCurrentFrame(short ocode, int frame);
	@Override IBaseChain setDisplayName(short ocode, String displayName);
	@Override IBaseChain setDefaultSceneObject(short ocode, boolean isUsingEdgePerimeterDetection);
	@Override IBaseChain setScreenCoordsPerSecond(short ocode, double coordsPerSecond);
	//@}

	/**   @name Animation */
	//@{
	@Override IBaseChain setAnimationAsSceneTalker(String atid);
	@Override IBaseChain setAnimationAsObjectInitial(String atid);
	@Override IBaseChain setAnimationAsObjectCurrent (String atid);
	@Override IBaseChain alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation(String atid, int frame);
	@Override IBaseChain alignBaseMiddleOfOldFrameToFirstFrameOfNewAnimation(String atid);
	@Override IBaseChain alignBaseMiddleOfOldFrameToLastFrameOfNewAnimation(String atid);
	@Override int getAnimationLastFrame(String atid);
	@Override int getAnimationLength(String atid);
	@Override IBaseChain setAnimationDuration(String atid, double duration);
	//@}
 
	
	

}
