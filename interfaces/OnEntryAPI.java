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

import com.github.a2g.core.objectmodel.Animation;
import com.github.a2g.core.objectmodel.CommandLinePresenter;
import com.github.a2g.core.objectmodel.DialogTreePresenter;
import com.github.a2g.core.objectmodel.InventoryItem;
import com.github.a2g.core.objectmodel.InventoryPresenter;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.github.a2g.core.objectmodel.VerbsPresenter;

public interface OnEntryAPI {
	   // get by ID
    public SceneObject getObject(short code);
    public Animation getAnimation(int a);
    public InventoryItem getInventoryItem(int i);

    // property access methods
    public void setValue(Object name, int value);
    public int getValue(Object name);
    public boolean isTrue(Object name);
    
    // gui methods
    public DialogTreePresenter getDialogTreeGui();
    public VerbsPresenter getVerbsGui();
    public InventoryPresenter getInventoryGui();
    public CommandLinePresenter getCommandLineGui();
    public ScenePresenter getSceneGui();

    // nearly a gui method
    public SceneAPI getCurrentScene();

    // helpful for game
    public void switchToScene(String scene);
    public String getLastScene();
    public boolean isInDebugMode();
 
}
