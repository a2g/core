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

import com.github.a2g.core.interfaces.methods.direct.IAddBoundaryGate;
import com.github.a2g.core.interfaces.methods.direct.IAddBoundaryPoint;
import com.github.a2g.core.interfaces.methods.direct.IAlignBaseMiddleOfOldFrameToSpecifiedFrameOfNewAnimation;
import com.github.a2g.core.interfaces.methods.direct.ICreateChainRootAction;
import com.github.a2g.core.interfaces.methods.direct.IGetAnimationLastFrame;
import com.github.a2g.core.interfaces.methods.direct.IGetAnimationLength;
import com.github.a2g.core.interfaces.methods.direct.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.direct.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.direct.IGetCurrentAnimation;
import com.github.a2g.core.interfaces.methods.direct.IGetCurrentFrame;
import com.github.a2g.core.interfaces.methods.direct.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.methods.direct.IGetHelperPoint;
import com.github.a2g.core.interfaces.methods.direct.IGetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.methods.direct.IGetLastSceneName;
import com.github.a2g.core.interfaces.methods.direct.IGetValue;
import com.github.a2g.core.interfaces.methods.direct.IGetX;
import com.github.a2g.core.interfaces.methods.direct.IGetY;
import com.github.a2g.core.interfaces.methods.direct.IHide;
import com.github.a2g.core.interfaces.methods.direct.IHideInventoryItem;
import com.github.a2g.core.interfaces.methods.direct.IIsInDebugMode;
import com.github.a2g.core.interfaces.methods.direct.IIsInventoryItemVisible;
import com.github.a2g.core.interfaces.methods.direct.IIsTrue;
import com.github.a2g.core.interfaces.methods.direct.IIsVisible;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsSceneTalker;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationDuration;
import com.github.a2g.core.interfaces.methods.direct.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.direct.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.direct.ISetCurrentFrame;
import com.github.a2g.core.interfaces.methods.direct.ISetDefaultSceneObject;
import com.github.a2g.core.interfaces.methods.direct.ISetDisplayName;
import com.github.a2g.core.interfaces.methods.direct.ISetInventoryItemDisplayName;
import com.github.a2g.core.interfaces.methods.direct.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.methods.direct.ISetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.methods.direct.ISetScreenCoordsPerSecond;
import com.github.a2g.core.interfaces.methods.direct.ISetValue;
import com.github.a2g.core.interfaces.methods.direct.ISetX;
import com.github.a2g.core.interfaces.methods.direct.ISetY;
import com.github.a2g.core.interfaces.methods.direct.IShareWinning;
import com.github.a2g.core.interfaces.methods.direct.IShow;
import com.github.a2g.core.interfaces.methods.direct.IShowInventoryItem;
import com.github.a2g.core.interfaces.methods.direct.ISwitchToScene;

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
