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

import com.github.a2g.core.interfaces.internal.ILoad;
import com.github.a2g.core.interfaces.methods.game.ISetValue;

/**  
 * 
 * @author Admin
 *
 */
public interface IOnFillLoadList extends ISetValue {
	void addEssential(ILoad blah);
	void kickStartLoading();
	IGameScene getSceneByName(Object string);
	void clearAllLoadedLoads();
	void setContinueAfterLoad(boolean isContinueImmediatelyAfterLoading);
	void addMP3ForASoundObject(String name, String location);
}
