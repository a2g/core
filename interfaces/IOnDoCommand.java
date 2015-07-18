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

import com.github.a2g.core.interfaces.game.IAddBoundaryGate;
import com.github.a2g.core.interfaces.game.IAddBoundaryPoint;
import com.github.a2g.core.interfaces.game.IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation;
import com.github.a2g.core.interfaces.game.ICreateChainRootAction;
import com.github.a2g.core.interfaces.game.IGetAnimationLastFrame;
import com.github.a2g.core.interfaces.game.IGetAnimationLength;
import com.github.a2g.core.interfaces.game.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.game.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.game.IGetCurrentAnimation;
import com.github.a2g.core.interfaces.game.IGetCurrentFrame;
import com.github.a2g.core.interfaces.game.IGetCurrentScene;
import com.github.a2g.core.interfaces.game.IGetIsSayAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.game.IGetLastScene;
import com.github.a2g.core.interfaces.game.IGetValue;
import com.github.a2g.core.interfaces.game.IGetX;
import com.github.a2g.core.interfaces.game.IGetY;
import com.github.a2g.core.interfaces.game.IHide;
import com.github.a2g.core.interfaces.game.IHideInventoryItem;
import com.github.a2g.core.interfaces.game.IIsInDebugMode;
import com.github.a2g.core.interfaces.game.IIsInventoryItemVisible;
import com.github.a2g.core.interfaces.game.IIsTrue;
import com.github.a2g.core.interfaces.game.IIsVisible;
import com.github.a2g.core.interfaces.game.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.game.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.game.ISetAnimationAsSceneTalker;
import com.github.a2g.core.interfaces.game.ISetAnimationDuration;
import com.github.a2g.core.interfaces.game.ISetAsSceneTalker;
import com.github.a2g.core.interfaces.game.ISetAsDefaultSceneObject;
import com.github.a2g.core.interfaces.game.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.game.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.game.ISetCurrentFrame;
import com.github.a2g.core.interfaces.game.ISetDisplayName;
import com.github.a2g.core.interfaces.game.ISetInventoryItemDisplayName;
import com.github.a2g.core.interfaces.game.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.game.ISetIsSayAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.game.ISetValue;
import com.github.a2g.core.interfaces.game.ISetX;
import com.github.a2g.core.interfaces.game.ISetY;
import com.github.a2g.core.interfaces.game.IShareWinning;
import com.github.a2g.core.interfaces.game.IShow;
import com.github.a2g.core.interfaces.game.IShowInventoryItem;
import com.github.a2g.core.interfaces.game.ISwitchToScene;

public interface IOnDoCommand
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

// boundary
IAddBoundaryGate,
IAddBoundaryPoint,

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
ISetAsDefaultSceneObject,
ISetAsSceneTalker,

// animation
IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation,
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

	@Override
	void setAsDefaultSceneObject(short harry2);

}
