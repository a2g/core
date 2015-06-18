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
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.action.performer.SingleCallPerformer;

public class SingleCallAction 
extends ChainableAction {

	SingleCallPerformer single;
	
	protected SingleCallAction(BaseAction parent, SingleCallPerformer.Type type) {
		super(parent);
		single = new SingleCallPerformer(type);
	}
	
	@Override
	public void runGameAction() {
		int millisecs = single.run();
		super.run(millisecs);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
	}

	@Override
	protected boolean onCompleteGameAction() {
		return single.onComplete();
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) {
		single.setScene(scene);
		single.setInventory(inventory);
		single.setTitleCard(titleCard);
	}
	
	public SingleCallPerformer.Type getType(){ return single.getType();}
 
	public void setDouble(double d){ single.setDouble(d) ;}
	public void setOCode(short o){ single.setOCode(o);}
	public void setAtid(String atid){ single.setAtid(atid);}
	public void setString(String string){ single.setString(string);}
	public void setBoolean(boolean isTrue){ single.setBoolean(isTrue);}
	public void setInt(int intValue){ single.setInt(intValue);}
	public void setICode(int icode) {single.setICode(icode);}
	public void setOCode2(short ocode2) {single.setOCode2(ocode2);}
		
}
