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

 
import com.github.a2g.core.action.ChainEndAction;
import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI.WalkDirection;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.IGetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetLastSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.game.singles.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.game.singles.IGetValue;
import com.github.a2g.core.interfaces.game.singles.IGetX;
import com.github.a2g.core.interfaces.game.singles.IGetY;
import com.github.a2g.core.interfaces.game.singles.IHide;
import com.github.a2g.core.interfaces.game.singles.IIsInDebugMode;
import com.github.a2g.core.interfaces.game.singles.IIsInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.IIsTrue;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectWalkDirection;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsSceneTalker;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleX;
import com.github.a2g.core.interfaces.game.singles.ISetBaseMiddleY;
import com.github.a2g.core.interfaces.game.singles.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.ISetSoundtrack;
import com.github.a2g.core.interfaces.game.singles.ISetTitleCard;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.IShow;
import com.github.a2g.core.interfaces.game.singles.ISwitchToScene;
import com.github.a2g.core.primitive.ColorEnum;
/**  
 * 
 * @brief All the methods you could possibly need in the IOnEntry handler. 
 * This method gives you the chance to return a chain, so there's plenty
 * of other ways to get stuff done through through the return value.
 *
 */
public interface IOnEntry
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
, IGetSceneGuiWidth
, IGetSceneGuiHeight
// Inventory
, IIsInventoryItemVisible
, ISetInventoryItemVisible
// object
, IHide
, IShow
, IGetBaseMiddleX
, IGetBaseMiddleY
, ISetBaseMiddleX
, ISetBaseMiddleY
, IGetX
, IGetY
// animation
, ISetAnimationAsObjectWalkDirection
, ISetAnimationAsSceneTalker
, ISetAnimationAsObjectInitial
, ISetAnimationAsObjectCurrent
// sound
, ISetSoundtrack
, ISetTitleCard
{
	/**   @name Property access methods */
	//@{
	@Override int getValue(Object key);
	@Override boolean isTrue(Object key);
	@Override IChainBase setValue(Object key, int value);
	//@}

	/**   @name Helpful for game */
	//@{
	@Override String getCurrentSceneName();
	@Override ChainEndAction switchToScene(String name, int entrySegment);
	@Override String getLastSceneName();
	@Override boolean isInDebugMode();
	@Override double getSceneGuiWidth();
	@Override double getSceneGuiHeight();
	@Override IChainBase setTitleCard(String textToDisplay) ;
	//@}

	/**   @name Inventory */
	//@{
	@Override boolean isInventoryItemVisible(int icode);
	@Override IChainBase setInventoryItemVisible(int icode, boolean isVisible);
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
	//@}

	/**   @name Animation */
	//@{
	@Override IChainBase setAnimationAsObjectWalkDirection(String atid, WalkDirection type);
	@Override IChainBase  setAnimationAsSceneTalker(String atid);
	@Override IChainBase  setAnimationAsObjectInitial(String atid);
	@Override IChainBase  setAnimationAsObjectCurrent (String atid);
	//@}

	/**   @name Sound */
	//@{
	@Override IChainBase  setSoundtrack(String stid) ;
	//@}
    IChainBase setDefaultSceneObject(short hero, boolean b);
    IChainBase setAnimationAsSceneDialogUs(String atid);
    IChainBase setAnimationTalkingColor(String atid, ColorEnum color);
    IChainBase setAnimationDuration(String atid, double durationInSecs);
}
