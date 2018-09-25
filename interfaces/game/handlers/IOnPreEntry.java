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

import com.github.a2g.core.interfaces.game.singles.IAddEdgeSpanToPerimeter ;
import com.github.a2g.core.action.ChainEndAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.interfaces.game.singles.IAddEdgePointToPerimeter ;
import com.github.a2g.core.interfaces.game.singles.IAddHelperPoint;
import com.github.a2g.core.interfaces.game.singles.IAddEdgeRectangle;
import com.github.a2g.core.interfaces.game.singles.IAddHelperRectangle;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.IClearSaidSpeech;
import com.github.a2g.core.interfaces.game.singles.IClearValuesAndSaidSpeech;
import com.github.a2g.core.interfaces.game.singles.ICreateChainRootAction;
import com.github.a2g.core.interfaces.game.singles.IGetAnimationLength;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetDefaultSceneObject;
import com.github.a2g.core.interfaces.game.singles.IGetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.game.singles.IGetLastSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetOCodeByAtid;
import com.github.a2g.core.interfaces.game.singles.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.game.singles.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.game.singles.IGetValue;
import com.github.a2g.core.interfaces.game.singles.IGetX;
import com.github.a2g.core.interfaces.game.singles.IGetY;
import com.github.a2g.core.interfaces.game.singles.IHide;
import com.github.a2g.core.interfaces.game.singles.IHideInventoryItem;
import com.github.a2g.core.interfaces.game.singles.IHideInventoryItemsAllOfThem;
import com.github.a2g.core.interfaces.game.singles.IIsInDebugMode;
import com.github.a2g.core.interfaces.game.singles.IIsInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.IIsTrue;
import com.github.a2g.core.interfaces.game.singles.IIsVisible;
import com.github.a2g.core.interfaces.game.singles.IRemoveVerbByCode;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectCurrentAndSetFrame;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectWalkDirection;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsSceneDialogThem;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsSceneDialogUs;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsSceneTalker;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationDuration;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationTalkingColor;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.ISetClumpWithPrevious;
import com.github.a2g.core.interfaces.game.singles.ISetDefaultSceneObject;
import com.github.a2g.core.interfaces.game.singles.ISetDisplayName;
import com.github.a2g.core.interfaces.game.singles.ISetHeadRectangleForAnimation;
import com.github.a2g.core.interfaces.game.singles.ISetHeadRectangleForObject;
import com.github.a2g.core.interfaces.game.singles.ISetInventoryItemDisplayName;
import com.github.a2g.core.interfaces.game.singles.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.ISetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.game.singles.ISetParallaxX;
import com.github.a2g.core.interfaces.game.singles.ISetScale;
import com.github.a2g.core.interfaces.game.singles.ISetScreenCoordsPerSecond;
import com.github.a2g.core.interfaces.game.singles.ISetTalkingColor;
import com.github.a2g.core.interfaces.game.singles.ISetTitleCard;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.ISetVisible;
import com.github.a2g.core.interfaces.game.singles.ISetX;
import com.github.a2g.core.interfaces.game.singles.ISetY;
import com.github.a2g.core.interfaces.game.singles.IShareWinning;
import com.github.a2g.core.interfaces.game.singles.IShow;
import com.github.a2g.core.interfaces.game.singles.IShowInventoryItem;
import com.github.a2g.core.interfaces.game.singles.ISwitchToScene;
import com.github.a2g.core.interfaces.game.singles.IUpdateVerbUI;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.RectF;

/**  
 * 
 * @author Admin
 *
 */
public interface IOnPreEntry
extends
// property access methods
IGetValue,
IIsTrue,
ISetValue,


// helpful for game
IGetCurrentSceneName,
ISwitchToScene,
IGetLastSceneName,
IIsInDebugMode,
ISetIsTalkingAlwaysWithoutIncrementing,
IGetIsTalkingAlwaysWithoutIncrementing,
IShareWinning,
ICreateChainRootAction,
IGetSceneGuiHeight,
IGetSceneGuiWidth,
IGetOCodeByAtid,
ISetTitleCard,
IGetDefaultSceneObject,
IClearValuesAndSaidSpeech,
IClearSaidSpeech,


// boundary and helper
IAddEdgeSpanToPerimeter ,
IAddEdgePointToPerimeter ,
IAddEdgeRectangle,
IAddHelperRectangle,
IAddHelperPoint,

// object
IHide,
IShow,
IGetBaseMiddleX,
IGetBaseMiddleY,
ISetBaseMiddleX,
ISetBaseMiddleY,
IIsVisible,
ISetX,
ISetY,
ISetDisplayName,
ISetScreenCoordsPerSecond,
ISetParallaxX,
IGetX,
IGetY,
ISetVisible,
ISetHeadRectangleForObject,
ISetTalkingColor,
ISetClumpWithPrevious,
ISetScale,

// inventory
IShowInventoryItem,
IHideInventoryItem,
ISetInventoryItemDisplayName,
IIsInventoryItemVisible,
ISetInventoryItemVisible,
IHideInventoryItemsAllOfThem,

// verb
IRemoveVerbByCode,
IUpdateVerbUI,

// animation
IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation,
ISetAnimationAsObjectInitial,
ISetAnimationAsSceneTalker,
ISetAnimationAsObjectCurrent,
ISetAnimationDuration,
ISetAnimationAsObjectWalkDirection,
ISetDefaultSceneObject,
ISetAnimationAsObjectCurrentAndSetFrame,
ISetAnimationAsSceneDialogUs,
ISetAnimationAsSceneDialogThem,
ISetHeadRectangleForAnimation,
ISetAnimationTalkingColor,
IGetAnimationLength

{

	/**   @name Property access methods */
	//@{
	@Override int getValue(Object key);
	@Override boolean isTrue(Object key);
	@Override IChainBase setValue(Object key, int value);
	//@}

	/**   @name Helpful for game */
	//@{
	@Override ChainEndAction switchToScene(String name, int entrySegment);
	@Override boolean isInDebugMode();
	@Override double getSceneGuiWidth();
	@Override double getSceneGuiHeight();
	@Override String getLastSceneName();
	@Override IChainBase shareWinning(String string);
	@Override ChainRootAction createChainRootAction();
	@Override short getDefaultSceneObject();
	@Override void clearValuesAndSaidSpeech();
	@Override void clearSaidSpeech();
	@Override String getCurrentSceneName();
	@Override short getOCodeByAtid(String atid);
	@Override IChainBase setTitleCard(String stid) ;
	//@}


	/** @name EdgeDetection and Helper */
	//@{
	@Override void addEdgeRectangle(double x, double y, double right, double bottom);
	@Override void addEdgeRectangle(double x, double y, int helper);
	@Override void addEdgeRectangle(int helper, double x, double y);
	@Override void addEdgeRectangle(int helper, int helper2);
	@Override void addEdgeSpanToPerimeter(double tlx, double tly, double blx, double bly, Object toStringIsCalledOnThisToDetermineTheNameToSwitchToWhenGateIsEntered, int entrySegment);
	@Override void addEdgePointToPerimeter(double tlx, double tly);
	@Override int addHelperPoint(double x, double y);
	@Override int addHelperRectangle(RectF rectF);
	//@}

	/**   @name Inventory */
	//@{
	@Override IChainBase showInventoryItem(int icode);
	@Override IChainBase hideInventoryItem(int code);
	@Override IChainBase setInventoryItemDisplayName(int icode, String displayName);
	@Override boolean isInventoryItemVisible(int icode);
	@Override IChainBase setInventoryItemVisible(int icode, boolean isVisible);
	@Override IChainBase hideInventoryItemsAllOfThem();
	//@}

	/**   @name Object */
	//@{
	@Override void removeVerbByCode(int vcode);
	@Override IChainBase updateVerbUI();
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
	@Override IChainBase setDisplayName(short ocode, String displayName);
	@Override IChainBase setDefaultSceneObject(short ocode);
	@Override IChainBase setScreenCoordsPerSecond(short ocode, double coordsPerSecond);
	@Override IChainBase setParallaxX(short ocode, double parallaxInX);
	@Override IChainBase setVisible(short ocode, boolean isVisible);
	@Override IChainBase setHeadRectangleForObject(short ocode, int helperIndex);
	@Override IChainBase setTalkingColor(short ocode, ColorEnum talkingColor);
	@Override IChainBase setClumpWithPrevious(short ocode, boolean clumpWithPrevious);
	@Override IChainBase setScale(short ocode, double scale);

	//@}


	/**   @name Animation */
	//@{
	@Override IChainBase setAnimationAsObjectWalkDirection(String atid, WalkDirection type);
	@Override IChainBase setAnimationAsSceneTalker(String atid);
	@Override IChainBase setAnimationAsObjectInitial(String atid); 
	@Override IChainBase alignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation(String atid, int frame);
	@Override int getAnimationLength(String atid);
	@Override IChainBase setAnimationDuration(String atid, double duration);
	@Override IChainBase setAnimationAsObjectCurrent(String atid);
	@Override IChainBase setAnimationAsObjectCurrentAndSetFrame(String atid, int frame);
	@Override IChainBase setAnimationAsSceneDialogUs(String atid);
	@Override IChainBase setAnimationAsSceneDialogThem(String atid);
	@Override IChainBase setHeadRectangleForAnimation(String atid, int index);
	@Override IChainBase setAnimationTalkingColor(String atid, ColorEnum color);
	//@}
 




}



