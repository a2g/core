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


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainedAction;
import com.github.a2g.core.objectmodel.Animation;
import com.github.a2g.core.objectmodel.CommandLinePresenter;
import com.github.a2g.core.objectmodel.DialogTreePresenter;
import com.github.a2g.core.objectmodel.InventoryItem;
import com.github.a2g.core.objectmodel.InventoryPresenter;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.github.a2g.core.objectmodel.VerbsPresenter;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.PointF;


@SuppressWarnings("unused")
public interface InternalAPI
extends ImageAddAPI, OnDoCommandAPI{
	// get by ID
	@Override
	public SceneObject getObject(short  objectCode);
	@Override
	public Animation getAnimation(String  animationCode);
	@Override
	public InventoryItem getInventoryItem(int inventoryItemCode);



	// property access methods
	@Override
	public void setValue(Object name, int value);
	@Override
	public int getValue(Object name);
	@Override
	public boolean isTrue(Object name);

	// gui methods
	@Override
	public DialogTreePresenter getDialogTreeGui();
	@Override
	public VerbsPresenter getVerbsGui();
	@Override
	public InventoryPresenter getInventoryGui();
	@Override
	public CommandLinePresenter getCommandLineGui();
	@Override
	public ScenePresenter getSceneGui();

	// nearly a gui method
	@Override
	public SceneAPI getCurrentScene();

	// helpful for game
	@Override
	public void switchToScene(String scene);
	public void switchToSceneFromAction(String scene);
	@Override
	public String getLastScene();
	@Override
	public boolean isInDebugMode();
	public void executeActionWithDialogActionRunner(BaseAction a);
	public void executeActionWithDoCommandActionRunner(BaseAction a);
	public void setLastCommand(double x, double y, int v, String a, String b);

	// dialog tree methods
	public void executeBranchOnCurrentScene(int place);
	public int getPopupDelay();
	public void restartReloading();

	public void displayTitleCard(String text);

	public MasterPresenterHostAPI getMasterHostAPI();
	public FactoryAPI getFactory();

	public void setDialogTreeActive(boolean isInDialogTreeMode);

	public SceneAPI getSceneByName(String identifier);
	public void setScenePixelSize(int width, int height);
	public void setInventoryPixelSize(int width, int height);

	public ChainRootAction createChainRootAction();
	public void executeChainedAction(ChainedAction ba);
	public String getDefaultSayAnimation();
	public short getDefaultWalker();
	public void setStateOfPopup(boolean visible, double x, double y,
			ColorEnum talkingColor, String string, BaseAction cancelCallback);
	public void clickToContinue();
	//public void setIsSayWithoutIncremementing(boolean isSayWithoutIncremementing);
	//public boolean getIsSayWithoutIncremementing();
	
	public boolean isInANoGoZone(PointF tp);
	public void fireOnMovementBeyondAGateIfRelevant(PointF tp);

}


