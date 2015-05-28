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
import com.github.a2g.core.interfaces.IScenePresenterFromMakeSingleCallAction;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.action.ChainableAction;

public class MakeSingleCallAction extends ChainableAction {
	enum Type 
	{
		SetBaseMiddleX
		, SetBaseMiddleY
		, AlignBaseMiddle
		, SetCurrentFrame
		, HideAll
		, SetCurrentAnimation
		, SetVisible
		, SetDisplayName
		, SetAsInitialAnimation
		, ShareWinning
		, SetValue
		, SetToInitialPosition
		, SetSceneTalker
		, SetCurrentAnimationAndFrame
		, Quit
	}
	private Type type;
	private double d;
	private boolean isTrue;
	private String atid;
	private short ocode;
	private String stringValue;
	private int intValue;
	private IScenePresenterFromMakeSingleCallAction scene;

	public MakeSingleCallAction(BaseAction parent, Type type) {
		super(parent );
		this.type = type;
	}
	
	void setDouble(double d){ this.d =d ;}
	void setOCode(short o){ this.ocode = o;}
	void setAtid(String atid){ this.atid = atid;}
	void setString(String string){ this.stringValue = string;}
	void setBoolean(boolean isTrue){ this.isTrue = isTrue;}
	void setInt(int intValue){ this.intValue = intValue;}

	@Override
	public void runGameAction() {
		super.run(1);
	}

	@Override
	protected void onUpdateGameAction(double progress) {
	}

	@Override
	protected boolean onCompleteGameAction() {
		String otid;
		switch(type)
		{
		case SetBaseMiddleX:
			otid = scene.getOtidByCode(ocode);
			scene.setBaseMiddleXByOtid(otid, this.d);
			return false;
		case SetBaseMiddleY:
			otid = scene.getOtidByCode(ocode);
			scene.setBaseMiddleYByOtid(otid, this.d);
			return false;
		case AlignBaseMiddle:
			scene.alignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid(atid, intValue);
			return false;
		case SetCurrentFrame:
			otid = scene.getOtidByCode(ocode);
			scene.setCurrentFrameByOtid(otid, intValue);
			return false;
		case  HideAll:
			int count = scene.getSceneObjectCount();
			for (int i = 0; i < count; i++) {
				otid = scene.getOtidByIndex(i);
				scene.setVisibleByOtid(otid, false);
			}
			return false;
		case SetCurrentAnimation:
			scene.setAsACurrentAnimationByAtid(atid);
			return false;
		case SetVisible:
			otid = scene.getOtidByCode(ocode);
			scene.setVisibleByOtid(otid, this.isTrue);
			return false;
		case SetDisplayName:
			otid = scene.getOtidByCode(ocode);
			scene.setDisplayNameByOtid(otid, this.stringValue);
			return false;
		case SetAsInitialAnimation:
			scene.setAsAnInitialAnimationByAtid(atid);
			return false;
		case SetValue:
			scene.setValue(stringValue, intValue);
			return false;
		case ShareWinning:
			scene.shareWinning(stringValue);
			return false;
		case SetToInitialPosition:
			otid = scene.getOtidByCode(ocode);
			scene.setXByOtid(otid, 0);
			scene.setYByOtid(otid, 0);
			return false;
		case SetSceneTalker:
			scene.setSceneTalkerByAtid(atid);
			return false;
		case SetCurrentAnimationAndFrame:
			scene.setCurrentAnimationAndFrame(atid, intValue);
			return false;
		}
		return false;
	}

	 
	public void setScene(IScenePresenterFromMakeSingleCallAction scene) {
		this.scene = scene;
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) {
		setScene(scene);
	}
	
	public Type getType(){ return type;}
	
 
}
