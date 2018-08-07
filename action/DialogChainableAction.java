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

package com.github.a2g.core.action;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.performer.SingleCallPerformer.Type;
import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.interfaces.game.chainables.IChainRootForDialog;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI;
import com.github.a2g.core.objectmodel.ScenePresenter;
import com.google.gwt.touch.client.Point;

public abstract class DialogChainableAction extends DialogChainEndAction
implements IChainRootForDialog
{

	DialogChainableAction(BaseAction parent) {
		super(parent);

	}
	@Override
	public DialogChainableAction branchNormal(int branchId, boolean isOkToAdd, String text) {

		DialogBranchAction a = new DialogBranchAction(this, text,
				branchId, isOkToAdd);
		return a;
	}
	@Override
	public DialogChainableAction branchNormal(int branchId, String text) {
		return new DialogBranchAction(this, text, branchId, true);
	}

	@Override
	public DialogChainEndAction endDialogTree() {
		return new DialogEndAction(this);
	}
	@Override
	public DialogChainEndAction chainTo(int branchId) {
		return new DialogChainToDialogAction(this, branchId);
	}

	@Override
	public	DialogChainableAction them(String speech) {
		DialogTalkAction s = new DialogTalkAction(this, TalkPerformer.SCENE_DIALOG_THEM, speech);
		return s;
	}

	@Override
	public	DialogChainableAction us(String speech) {
		DialogTalkAction s = new DialogTalkAction(this, TalkPerformer.SCENE_DIALOG_US, speech);
		return s;
	}

	@Override
	public DialogChainEndAction switchTo(String sceneName, int arrivalSegment) {
		DialogSingleCallAction a =  new DialogSingleCallAction(this, Type.Switch);
		a.setString(sceneName);
		a.setInt(arrivalSegment);
		return a;
	}

	@Override
	public DialogChainableAction setValue(Object key, int value) {
		DialogSingleCallAction a =  new DialogSingleCallAction(this, Type.SetValue);
		a.setString(key.toString());
		a.setInt(value);
		return a;
	}

	@Override
	public DialogChainableAction setInventoryItemVisible(int icode, boolean value) {
		DialogSingleCallAction a =  new DialogSingleCallAction(this, Type.SetInventoryVisible);
		a.setICode(icode);
		a.setBoolean(value);
		return a;
	}

	@Override
	public DialogChainableAction sleep(int milliseconds){
		DialogSingleCallAction a =  new DialogSingleCallAction(this, Type.Sleep);
		a.setInt(milliseconds);
		return a;
	}

	@Override
	public DialogChainableAction setAnimationAsObjectInitial(String atid)
	{
		DialogSingleCallAction a =  new DialogSingleCallAction(this, Type.SetInitialAnimation);
		a.setAtid(atid);
		return a;
	}
	@Override
	public DialogChainableAction branchSticky(int branchId, String text) {
		DialogBranchAction a = new DialogBranchAction(this, text, branchId, true);
		a.setIsExemptFromSaidList(true);
		return a;
	}
	@Override
	public DialogChainableAction branchSticky(int branchId, boolean isOkToAdd, String text) {
		DialogBranchAction a = new DialogBranchAction(this, text, branchId, isOkToAdd);
		a.setIsExemptFromSaidList(true);
		return a;
	}
	@Override
	public DialogChainEndAction branchTheObligatoryExit(String text) {
		DialogBranchAction a = new DialogBranchAction(this, text, ConstantsForAPI.EXIT_DLG, true);
		a.setIsExemptFromSaidList(true);
		return a;
	}
	 
	
	@Override
	public DialogChainEndAction walkAlwaysSwitch(double x, double y, String sceneName, int arrivalSegment) {
		return this.walkAlwaysSwitch( new Point(x,y), sceneName, arrivalSegment);
	}
	
	@Override
	public DialogChainEndAction walkAlwaysSwitch(Point p, String sceneName, int arrivalSegment) {
		DialogWalkAction a = new DialogWalkAction(this, ScenePresenter.DEFAULT_SCENE_OBJECT);
		a.setEndX(p.getX());
		a.setEndY(p.getY());
		a.setToInitialAtEnd(false);

		DialogSingleCallAction b =  new DialogSingleCallAction(a, Type.Switch);
		b.setString(sceneName);
		b.setInt(arrivalSegment);

		return b;
	}
	 
}
