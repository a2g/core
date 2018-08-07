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

import com.github.a2g.core.interfaces.game.singles.IGetCurrentSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetLastSceneName;
import com.github.a2g.core.interfaces.game.singles.IGetValue;
import com.github.a2g.core.interfaces.game.singles.IIsInDebugMode;
import com.github.a2g.core.interfaces.game.singles.IIsInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.IIsTrue;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsSceneDialogThem;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsSceneDialogUs;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.ISwitchToScene;

/**  
 * 
 * @author Admin
 *
 */
public interface IOnDialogTree extends
// property access methods
IGetValue, IIsTrue, ISetValue
// animation
, ISetAnimationAsSceneDialogUs
, ISetAnimationAsSceneDialogThem
, ISetAnimationAsObjectInitial
// helpful for game
, IGetCurrentSceneName, ISwitchToScene, IGetLastSceneName, IIsInDebugMode
// inventory
, IIsInventoryItemVisible {

}
