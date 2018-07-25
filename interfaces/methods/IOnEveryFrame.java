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

import com.github.a2g.core.interfaces.methods.direct.ICreateChainRootAction;
import com.github.a2g.core.interfaces.methods.direct.IDisplayTitleCard;
import com.github.a2g.core.interfaces.methods.direct.IExecuteChainedAction;
import com.github.a2g.core.interfaces.methods.direct.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.direct.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.direct.IGetCurrentAnimation;
import com.github.a2g.core.interfaces.methods.direct.IGetCurrentFrame;
import com.github.a2g.core.interfaces.methods.direct.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.methods.direct.IGetHelperPoint;
import com.github.a2g.core.interfaces.methods.direct.IGetLastSceneName;
import com.github.a2g.core.interfaces.methods.direct.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.direct.IGetValue;
import com.github.a2g.core.interfaces.methods.direct.IGetViewportWidth;
import com.github.a2g.core.interfaces.methods.direct.IGetX;
import com.github.a2g.core.interfaces.methods.direct.IGetY;
import com.github.a2g.core.interfaces.methods.direct.IHide;
import com.github.a2g.core.interfaces.methods.direct.IIncremementFrameWithWraparound;
import com.github.a2g.core.interfaces.methods.direct.IIsAnimation;
import com.github.a2g.core.interfaces.methods.direct.IIsInDebugMode;
import com.github.a2g.core.interfaces.methods.direct.IIsTrue;
import com.github.a2g.core.interfaces.methods.direct.IIsVisible;
import com.github.a2g.core.interfaces.methods.direct.ISetActiveGuiState;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.methods.direct.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.direct.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.direct.ISetCurrentFrame;
import com.github.a2g.core.interfaces.methods.direct.ISetValue;
import com.github.a2g.core.interfaces.methods.direct.ISetX;
import com.github.a2g.core.interfaces.methods.direct.ISetY;
import com.github.a2g.core.interfaces.methods.direct.IShareWinning;
import com.github.a2g.core.interfaces.methods.direct.IShow;
import com.github.a2g.core.interfaces.methods.direct.ISwitchToScene;
import com.github.a2g.core.interfaces.methods.direct.IUpdateObjectToCorrectImage;
import com.github.a2g.core.interfaces.methods.internal.ISetStateOfPopup;

/**  
 * 
 * @author Admin
 *
 */
public interface IOnEveryFrame
extends
// property access methods
IGetValue,
IIsTrue,
ISetValue
// helpful for game
,
IGetCurrentSceneName,
ISwitchToScene,
IGetLastSceneName,
IIsInDebugMode,
ISetActiveGuiState,
IGetSceneGuiWidth
// these two are needed for starting animations in the onEveryFrame
// handler
,
ICreateChainRootAction,
IExecuteChainedAction
// helper
,

IShareWinning
, ISetStateOfPopup
, IDisplayTitleCard
// object
, IShow, IHide, IIncremementFrameWithWraparound,
IUpdateObjectToCorrectImage, IGetCurrentFrame, ISetCurrentFrame,
ISetBaseMiddleX, ISetBaseMiddleY, IGetBaseMiddleX, IGetBaseMiddleY,
IGetCurrentAnimation, ISetX, ISetY, IGetX, IGetY, IIsVisible
// animation
, IIsAnimation
, ISetAnimationAsObjectCurrent
, IGetHelperPoint
, IGetViewportWidth
{

	

 
	 
	 
}
