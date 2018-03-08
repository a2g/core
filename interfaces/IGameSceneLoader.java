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



/**
 * Each scene  is represented by a class that implements the following 6 methods:
 * @author Admin
 *
 */
public interface IGameSceneLoader extends ConstantsForAPI {
	/*! 
		<table>
		<tr>
		<td>
		 @image html IGameScene0000.png
		 onPreLoadList
		</td>
		<td>
	      Is there to load resources. You're may wonder why it's not made so  
	      the list of resources to load is specified by whatever this methods returns. 
	      The reason is because classes that implement this interface (ie Scenes) generally 
	      benefit from @ref code-splitting. And any calls to methods on code-split classes will
	      just return null and keep processing. And the code inside the method will get executed
	      during a free cycle, and any return value will be be lost. So instead of passing back control
	      via returning, it's done via a method called setSceneAsActiveAndKickStartLoading. 
	      Now, it's highly likely that at one time or another, a user will forget to call 
	      that method. So the method is made to return a special
	      class (in this case LoadKickStarter ) and which can only be created by calling
	      the method createLoadKickStarter().
	      It's a bit mean, but it ensures users won't forget to call this method.
		</td>
		</tr>
		</table>
	 */

	public ILoadKickStarter onEnqueueResources(IOnEnqueueResources api);

}
