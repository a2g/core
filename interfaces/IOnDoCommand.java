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
import com.github.a2g.core.interfaces.methods.game.IAlignBaseMiddleOfOldFrameToFrameOfSpecifiedAnimation;
import com.github.a2g.core.interfaces.methods.game.ICreateChainRootAction;
import com.github.a2g.core.interfaces.methods.game.IGetHelperPoint;
import com.github.a2g.core.interfaces.methods.game.ISetDefaultSceneObject;
import com.github.a2g.core.interfaces.methods.game.IGetAnimationLastFrame;
import com.github.a2g.core.interfaces.methods.game.IGetAnimationLength;
import com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.game.IGetCurrentAnimation;
import com.github.a2g.core.interfaces.methods.game.IGetCurrentFrame;
import com.github.a2g.core.interfaces.methods.game.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.methods.game.IGetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.methods.game.IGetLastSceneName;
import com.github.a2g.core.interfaces.methods.game.IGetValue;
import com.github.a2g.core.interfaces.methods.game.IGetX;
import com.github.a2g.core.interfaces.methods.game.IGetY;
import com.github.a2g.core.interfaces.methods.game.IHide;
import com.github.a2g.core.interfaces.methods.game.IHideInventoryItem;
import com.github.a2g.core.interfaces.methods.game.IIsInDebugMode;
import com.github.a2g.core.interfaces.methods.game.IIsInventoryItemVisible;
import com.github.a2g.core.interfaces.methods.game.IIsTrue;
import com.github.a2g.core.interfaces.methods.game.IIsVisible;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsSceneTalker;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationDuration;
import com.github.a2g.core.interfaces.methods.game.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.game.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.game.ISetCurrentFrame;
import com.github.a2g.core.interfaces.methods.game.ISetDisplayName;
import com.github.a2g.core.interfaces.methods.game.ISetInventoryItemDisplayName;
import com.github.a2g.core.interfaces.methods.game.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.methods.game.ISetIsTalkingAlwaysWithoutIncrementing;
import com.github.a2g.core.interfaces.methods.game.ISetValue;
import com.github.a2g.core.interfaces.methods.game.ISetX;
import com.github.a2g.core.interfaces.methods.game.ISetY;
import com.github.a2g.core.interfaces.methods.game.IShareWinning;
import com.github.a2g.core.interfaces.methods.game.IShow;
import com.github.a2g.core.interfaces.methods.game.IShowInventoryItem;
import com.github.a2g.core.interfaces.methods.game.ISwitchToScene;

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
	void setDefaultSceneObject(short harry2);

	

}
