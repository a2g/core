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
import com.github.a2g.core.interfaces.IChainRootForDialog;

public abstract class DialogChainableAction extends DialogChainEndAction
implements IChainRootForDialog
{

	DialogChainableAction(BaseAction parent) {
		super(parent);

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
		DialogTreeTalkAction s = new DialogTreeTalkAction(this, TalkPerformer.SCENE_TALKER, speech);
		return s;
	} 

	@Override
	public DialogChainEndAction switchTo(String string) {
		DialogSwitchAction s = new DialogSwitchAction(this, string );
		return s;
	}

	@Override
	public DialogChainableAction setValue(String key, int value) {
		DialogTreeSingleCallAction a =  new DialogTreeSingleCallAction(this, Type.SetValue);
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
		DialogTreeSingleCallAction a =  new DialogTreeSingleCallAction(this, Type.Sleep);
		a.setInt(milliseconds);
		return a;
	}

	@Override
	public DialogChainableAction setInitialAnimation(String atid)
	{
		DialogTreeSingleCallAction a =  new DialogTreeSingleCallAction(this, Type.SetAsInitialAnimation);
		a.setAtid(atid);
		return a;
	}
}
