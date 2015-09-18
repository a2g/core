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

package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.methods.game.IAddBoundaryGate;
import com.github.a2g.core.interfaces.methods.game.IAddBoundaryPoint;
import com.github.a2g.core.interfaces.methods.game.IAddObstacleRect;
import com.github.a2g.core.interfaces.methods.game.IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation;
import com.github.a2g.core.interfaces.methods.game.ICreateChainRootAction;
import com.github.a2g.core.interfaces.methods.game.IDefaultSceneObject;
import com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.game.IGetCurrentScene;
import com.github.a2g.core.interfaces.methods.game.IGetIsSayAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.methods.game.IGetLastScene;
import com.github.a2g.core.interfaces.methods.game.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.game.IGetValue;
import com.github.a2g.core.interfaces.methods.game.IGetX;
import com.github.a2g.core.interfaces.methods.game.IGetY;
import com.github.a2g.core.interfaces.methods.game.IHide;
import com.github.a2g.core.interfaces.methods.game.IHideAllInventory;
import com.github.a2g.core.interfaces.methods.game.IHideInventoryItem;
import com.github.a2g.core.interfaces.methods.game.IIsInDebugMode;
import com.github.a2g.core.interfaces.methods.game.IIsInventoryItemVisible;
import com.github.a2g.core.interfaces.methods.game.IIsTrue;
import com.github.a2g.core.interfaces.methods.game.IIsVisible;
import com.github.a2g.core.interfaces.methods.game.IRemoveVerbByCode;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectCurrentAndSetFrame;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectSpecial;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsSceneAnswerer;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsSceneAsker;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsSceneTalker;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationDuration;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationMaxTalkRect;
import com.github.a2g.core.interfaces.methods.game.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.game.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.game.ISetDisplayName;
import com.github.a2g.core.interfaces.methods.game.ISetInventoryItemDisplayName;
import com.github.a2g.core.interfaces.methods.game.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.methods.game.ISetIsSayAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.methods.game.ISetParallaxX;
import com.github.a2g.core.interfaces.methods.game.ISetScreenCoordsPerSecond;
import com.github.a2g.core.interfaces.methods.game.ISetTalkingColor;
import com.github.a2g.core.interfaces.methods.game.ISetTitleCard;
import com.github.a2g.core.interfaces.methods.game.ISetValue;
import com.github.a2g.core.interfaces.methods.game.ISetVisible;
import com.github.a2g.core.interfaces.methods.game.ISetX;
import com.github.a2g.core.interfaces.methods.game.ISetY;
import com.github.a2g.core.interfaces.methods.game.IShareWinning;
import com.github.a2g.core.interfaces.methods.game.IShow;
import com.github.a2g.core.interfaces.methods.game.IShowInventoryItem;
import com.github.a2g.core.interfaces.methods.game.ISwitchToScene;
import com.github.a2g.core.interfaces.methods.game.IUpdateVerbUI;

public interface IOnPreEntry
extends
// property access methods
IGetValue,
IIsTrue,
ISetValue,
// helpful for game
IGetCurrentScene,
ISwitchToScene,
IGetLastScene,
IIsInDebugMode,
ISetIsSayAlwaysWithoutIncrementing,
IGetIsSayAlwaysWithoutIncrementing,
IShareWinning,
ICreateChainRootAction,
IGetSceneGuiHeight,
// boundary
IAddBoundaryGate,
IAddBoundaryPoint,
IAddObstacleRect,
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
ISetTalkingColor,
ISetParallaxX,
IGetX,
IGetY,
ISetVisible,
// inventory
IShowInventoryItem,
IHideInventoryItem,
ISetInventoryItemDisplayName,
IIsInventoryItemVisible,
ISetInventoryItemVisible,
IHideAllInventory,

//titlecard
ISetTitleCard,

// verb
IRemoveVerbByCode,
IUpdateVerbUI,

// animation
IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation,
ISetAnimationAsObjectInitial,
ISetAnimationAsSceneTalker,
ISetAnimationAsObjectCurrent,
ISetAnimationDuration,
ISetAnimationAsObjectSpecial,
IDefaultSceneObject,
ISetAnimationAsObjectCurrentAndSetFrame,
ISetAnimationAsSceneAsker,
ISetAnimationAsSceneAnswerer,
ISetAnimationMaxTalkRect
{

}



