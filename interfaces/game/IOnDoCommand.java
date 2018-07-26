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

package com.github.a2g.core.interfaces.game;

import com.github.a2g.core.interfaces.game.singles.IAddBoundaryGate;
import com.github.a2g.core.interfaces.game.singles.IAddBoundaryPoint;
import com.github.a2g.core.interfaces.game.singles.IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation;
import com.github.a2g.core.interfaces.game.singles.ICreateChainRootAction;
import com.github.a2g.core.interfaces.game.singles.IGetAnimationLastFrame;
import com.github.a2g.core.interfaces.game.singles.IGetAnimationLength;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentAnimation;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentFrame;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetHelperPoint;
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

// helpful for game
IGetCurrentSceneName,
ISwitchToScene,
IGetLastSceneName,
IIsInDebugMode,
ISetIsTalkingAlwaysWithoutIncrementing,
IGetIsTalkingAlwaysWithoutIncrementing,
IShareWinning,
ICreateChainRootAction,

// boundary
IAddBoundaryGate,
IAddBoundaryPoint,
IGetHelperPoint,


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

	
	

}
