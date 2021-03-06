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

import com.github.a2g.core.chain.SceneChainEnd;
import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.interfaces.game.chainables.ISceneChain;
import com.github.a2g.core.interfaces.game.chainables.ISceneChainRoot;
import com.github.a2g.core.interfaces.game.singles.ICreateChainRootAction;
import com.github.a2g.core.interfaces.game.singles.IDisplayTitleCard;
import com.github.a2g.core.interfaces.game.singles.IExecuteChainedAction;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentAnimation;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentFrame;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetHelperPoint;
import com.github.a2g.core.interfaces.game.singles.IGetLastSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.game.singles.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.game.singles.IGetValue;
import com.github.a2g.core.interfaces.game.singles.IGetViewportWidth;
import com.github.a2g.core.interfaces.game.singles.IGetX;
import com.github.a2g.core.interfaces.game.singles.IGetY;
import com.github.a2g.core.interfaces.game.singles.IHide;
import com.github.a2g.core.interfaces.game.singles.IIncremementFrameWithWraparound;
import com.github.a2g.core.interfaces.game.singles.IIsAnimation;
import com.github.a2g.core.interfaces.game.singles.IIsInDebugMode;
import com.github.a2g.core.interfaces.game.singles.IIsTrue;
import com.github.a2g.core.interfaces.game.singles.IIsVisible;
import com.github.a2g.core.interfaces.game.singles.ISetGuiState;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.ISetCurrentFrame;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.ISetX;
import com.github.a2g.core.interfaces.game.singles.ISetY;
import com.github.a2g.core.interfaces.game.singles.IShareWinning;
import com.github.a2g.core.interfaces.game.singles.IShow;
import com.github.a2g.core.interfaces.game.singles.ISwitchToScene;
import com.github.a2g.core.interfaces.game.singles.IUpdateObjectToCorrectImage;
import com.github.a2g.core.interfaces.nongame.action.ISetStateOfPopup;

/**  
 * 
 * @author Admin
 *
 */
public interface IOnEveryFrame
extends
// property access methods
IGetValue
, IIsTrue
, ISetValue
// helpful for game

, IGetCurrentSceneName
, ISwitchToScene
, IGetLastSceneName
, IIsInDebugMode
, ISetGuiState
, IGetSceneGuiWidth
, IGetSceneGuiHeight

// these two are needed for starting animations in the onEveryFrame
// handler
, ICreateChainRootAction
, IExecuteChainedAction

// helper
, IShareWinning
, ISetStateOfPopup
, IDisplayTitleCard
, IGetHelperPoint
, IGetViewportWidth

// object
, IShow 
, IHide
, IIncremementFrameWithWraparound
, IUpdateObjectToCorrectImage 
, IGetCurrentFrame 
, ISetCurrentFrame
, ISetBaseMiddleX 
, ISetBaseMiddleY 
, IGetBaseMiddleX 
, IGetBaseMiddleY
, IGetCurrentAnimation 
, ISetX 
, ISetY 
, IGetX 
, IGetY
, IIsVisible

// animation
, IIsAnimation
, ISetAnimationAsObjectCurrent

{
	/**   @name Property access methods */
	//@{
	@Override int getValue(Object key);
	@Override boolean isTrue(Object key);
	@Override IBaseChain setValue(Object key, int value);
	//@}

	/**   @name Helpful for game */
	//@{
	@Override SceneChainEnd switchToScene(String name, int entrySegment);
	@Override boolean isInDebugMode();
	@Override double getSceneGuiWidth();
	@Override double getSceneGuiHeight();
	@Override String getLastSceneName();
	@Override IBaseChain shareWinning(String string);
	@Override ISceneChainRoot createChainRootAction();
	@Override String getCurrentSceneName();
	@Override void executeSceneChain(ISceneChain ba);
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
	//@}


	/**   @name Animation */
	//@{
	@Override IBaseChain setAnimationAsObjectCurrent(String atid);
	boolean isAnimation(String atid);
	//@}

}
