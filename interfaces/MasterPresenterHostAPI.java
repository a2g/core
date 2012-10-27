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

import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.user.client.ui.Widget;

/**
* These are the methods a class needs to implement to be able to host 
* the @ref MasterPresenter class
*/
public interface MasterPresenterHostAPI {

    /**
     * Is there to persist state data at the time the state data change occurs.
     * The setting of most types of state data is ultimately done by this method.
     * It is needed for the inventory to remember it contains an object
     * when moving from one scene to the next. It is also called in reponse
     * to calls of @ref com.github.a2g.core.authoredscene.InternalAPI::setValue(String name, int value);	
     */
    public void setValue(String name, int value);

    /**
     * The retrieval of most types of state data is ultimately done by this method.
     * It is needed by the inventory to remember it contains an object when moving from
     * one scene to the next. It is also called in response
     * to calls of @ref com.github.a2g.core.authoredscene.InternalAPI::getValue(String name);	
     */
    public int getValue(String name);

    /**
     * Is there to switchScenes. The scene loading isn't managed
     * by the core package, and there is no progress bar for it. So instead, it is
     * passed back to the host. Whilst you load the scene you can do whatever you like, 
     * and put up a splash screen or something. There is no progress bar because there 
     * is no way to split a scene up in to the discreet chunks needed for progress bar 
     * style loading. Perhaps each doCommand handler could be a discreet package, that 
     * can be arbitrarily bundled and loaded via a progress bar..hmmmm.
     * 
     */
    public void instantiateSceneAndCallSetSceneBackOnTheMasterPresenter(String scene);

    /**
     * Is there merely to export analytical data so it may be analyzed, and perhaps used to
     * improve the game. A stub implementation will suffice if analytics data is to be ignored.
     */	
    public void setLastCommand(double x, double y, int v, String a, String b);
  
    
 
	void alert(String string);

	FactoryAPI getFactory();

    
}
