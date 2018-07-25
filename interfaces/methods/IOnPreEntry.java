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

package com.github.a2g.core.interfaces.methods;

import com.github.a2g.core.interfaces.internal.IClearSaidSpeech;
import com.github.a2g.core.interfaces.internal.IClearValueRegistry;
import com.github.a2g.core.interfaces.methods.direct.IAddBoundaryGate;
import com.github.a2g.core.interfaces.methods.direct.IAddBoundaryPoint;
import com.github.a2g.core.interfaces.methods.direct.IAddHelperPoint;
import com.github.a2g.core.interfaces.methods.direct.IAddObstacleRect;
import com.github.a2g.core.interfaces.methods.direct.IAddRectangle;
import com.github.a2g.core.interfaces.methods.direct.IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation;
import com.github.a2g.core.interfaces.methods.direct.ICreateChainRootAction;
import com.github.a2g.core.interfaces.methods.direct.IGetAnimationLength;
import com.github.a2g.core.interfaces.methods.direct.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.direct.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.direct.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.methods.direct.IGetDefaultSceneObject;
import com.github.a2g.core.interfaces.methods.direct.IGetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.methods.direct.IGetLastSceneName;
import com.github.a2g.core.interfaces.methods.direct.IGetOCodeByAtid;
import com.github.a2g.core.interfaces.methods.direct.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.direct.IGetValue;
import com.github.a2g.core.interfaces.methods.direct.IGetX;
import com.github.a2g.core.interfaces.methods.direct.IGetY;
import com.github.a2g.core.interfaces.methods.direct.IHide;
import com.github.a2g.core.interfaces.methods.direct.IHideAllInventory;
import com.github.a2g.core.interfaces.methods.direct.IHideInventoryItem;
import com.github.a2g.core.interfaces.methods.direct.IIsInDebugMode;
import com.github.a2g.core.interfaces.methods.direct.IIsInventoryItemVisible;
import com.github.a2g.core.interfaces.methods.direct.IIsTrue;
import com.github.a2g.core.interfaces.methods.direct.IIsVisible;
import com.github.a2g.core.interfaces.methods.direct.IRemoveVerbByCode;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsObjectCurrentAndSetFrame;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsObjectWalkDirection;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsSceneDialogThem;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsSceneDialogUs;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsSceneTalker;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationDuration;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationTalkingColor;
import com.github.a2g.core.interfaces.methods.direct.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.direct.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.direct.ISetClumpWithPrevious;
import com.github.a2g.core.interfaces.methods.direct.ISetDefaultSceneObject;
import com.github.a2g.core.interfaces.methods.direct.ISetDisplayName;
import com.github.a2g.core.interfaces.methods.direct.ISetHeadRectangleForAnimation;
import com.github.a2g.core.interfaces.methods.direct.ISetHeadRectangleForObject;
import com.github.a2g.core.interfaces.methods.direct.ISetInventoryItemDisplayName;
import com.github.a2g.core.interfaces.methods.direct.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.methods.direct.ISetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.methods.direct.ISetParallaxX;
import com.github.a2g.core.interfaces.methods.direct.ISetScale;
import com.github.a2g.core.interfaces.methods.direct.ISetScreenCoordsPerSecond;
import com.github.a2g.core.interfaces.methods.direct.ISetTalkingColor;
import com.github.a2g.core.interfaces.methods.direct.ISetTitleCard;
import com.github.a2g.core.interfaces.methods.direct.ISetValue;
import com.github.a2g.core.interfaces.methods.direct.ISetVisible;
import com.github.a2g.core.interfaces.methods.direct.ISetX;
import com.github.a2g.core.interfaces.methods.direct.ISetY;
import com.github.a2g.core.interfaces.methods.direct.IShareWinning;
import com.github.a2g.core.interfaces.methods.direct.IShow;
import com.github.a2g.core.interfaces.methods.direct.IShowInventoryItem;
import com.github.a2g.core.interfaces.methods.direct.ISwitchToScene;
import com.github.a2g.core.interfaces.methods.direct.IUpdateVerbUI;

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
IAddRectangle,
IAddHelperPoint,
IGetOCodeByAtid,
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
ISetParallaxX,
IGetX,
IGetY,
ISetVisible,
ISetHeadRectangleForObject,
ISetTalkingColor,
ISetClumpWithPrevious,

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
IGetAnimationLength,
IGetDefaultSceneObject,
IClearValueRegistry,
IClearSaidSpeech,
ISetScale
{

	
	

}



