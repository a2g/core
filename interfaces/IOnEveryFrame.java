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

import com.github.a2g.core.interfaces.methods.action.ISetStateOfPopup;
import com.github.a2g.core.interfaces.methods.game.ICreateChainRootAction;
import com.github.a2g.core.interfaces.methods.game.IDisplayTitleCard;
import com.github.a2g.core.interfaces.methods.game.IExecuteChainedAction;
import com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.game.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.game.IGetCurrentAnimation;
import com.github.a2g.core.interfaces.methods.game.IGetCurrentFrame;
import com.github.a2g.core.interfaces.methods.game.IGetCurrentScene;
import com.github.a2g.core.interfaces.methods.game.IGetLastScene;
import com.github.a2g.core.interfaces.methods.game.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.game.IGetValue;
import com.github.a2g.core.interfaces.methods.game.IGetX;
import com.github.a2g.core.interfaces.methods.game.IGetY;
import com.github.a2g.core.interfaces.methods.game.IHide;
import com.github.a2g.core.interfaces.methods.game.IIncremementFrameWithWraparound;
import com.github.a2g.core.interfaces.methods.game.IIsAnimation;
import com.github.a2g.core.interfaces.methods.game.IIsInDebugMode;
import com.github.a2g.core.interfaces.methods.game.IIsTrue;
import com.github.a2g.core.interfaces.methods.game.IIsVisible;
import com.github.a2g.core.interfaces.methods.game.ISetActiveGuiState;
import com.github.a2g.core.interfaces.methods.game.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.methods.game.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.methods.game.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.methods.game.ISetCurrentFrame;
import com.github.a2g.core.interfaces.methods.game.ISetValue;
import com.github.a2g.core.interfaces.methods.game.ISetX;
import com.github.a2g.core.interfaces.methods.game.ISetY;
import com.github.a2g.core.interfaces.methods.game.IShareWinning;
import com.github.a2g.core.interfaces.methods.game.IShow;
import com.github.a2g.core.interfaces.methods.game.ISwitchToScene;
import com.github.a2g.core.interfaces.methods.game.IUpdateObjectToCorrectImage;

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
IGetCurrentScene,
ISwitchToScene,
IGetLastScene,
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
, IIsAnimation, ISetAnimationAsObjectCurrent

{

	@Override
	void setBaseMiddleY(short harry, double d);

	@Override
	boolean isVisible(short orangeHutDoorOpen);

}
