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
import com.github.a2g.core.interfaces.IChainRootForDialog;

public abstract class DecoratedForDialogBaseAction extends BaseAction
implements IChainRootForDialog
{

	DecoratedForDialogBaseAction(BaseAction parent) {
		super(parent, true);

	}
	@Override
	public DialogChainableAction branch(int branchId, final boolean isOkToAdd, String text) {

		DialogTreeBranchAction a = new DialogTreeBranchAction(this, text,
				branchId, isOkToAdd);
		return a;
	}
	@Override
	public DialogChainableAction branch(int branchId, String text) {
		return new DialogTreeBranchAction(this, text, branchId, true);
	}
	@Override
	public DialogChainEndAction endDialogTree() {
		return new DialogTreeEndAction(this);
	}
	public DialogChainEndAction chainTo(int branchId) {
		return new DialogTreeChainToAction(this, branchId);
	}

	@Override
	public	DialogChainableAction talk(String animCode, String speech) {
		DialogTreeTalkAction s = new DialogTreeTalkAction(this, animCode, speech);
		return s;
	} 

	@Override
	public	DialogChainableAction talk(String speech) {
		DialogTreeTalkAction s = new DialogTreeTalkAction(this, TalkAction.SCENE_TALKER, speech);
		return s;
	} 

	@Override
	public BaseAction switchTo(String string) {
		SwitchHardAction s = new SwitchHardAction(this, string );
		return s;
	}

	@Override
	public DialogChainableAction setValue(String key, int value) {
		DialogTreeMakeSingleCallAction a =  new DialogTreeMakeSingleCallAction(this, DialogTreeMakeSingleCallAction.Type.SetValue);
		a.setString(key);
		a.setInt(value);
		return a;
	}

	@Override
	public DialogChainableAction setInventoryVisible(int key, boolean value) {
		DialogTreeSetInventoryVisibleAction a =  new DialogTreeSetInventoryVisibleAction(this,  key, value);
		return a;
	}

	@Override
	public DialogChainableAction sleep(int milliseconds){
		DialogTreeMakeSingleCallAction a =  new DialogTreeMakeSingleCallAction(this, DialogTreeMakeSingleCallAction.Type.Sleep);
		a.setMilliseconds(milliseconds);
		return a;
	}

	@Override
	public DialogChainableAction setInitialAnimation(String atid)
	{
		DialogTreeMakeSingleCallAction a =  new DialogTreeMakeSingleCallAction(this, DialogTreeMakeSingleCallAction.Type.SetAsInitialAnimation);
		a.setAtid(atid);
		return a;
	}
}
