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
import com.github.a2g.core.action.ChainedAction;

public class MakeSingleCallAction extends ChainedAction {
	enum Type 
	{
		SetBaseMiddleX
		, SetBaseMiddleY
		, AlignBaseMiddle
		, SetActiveFrame
		, HideAll
		, SetCurrentAnimation
		, SetVisible
		, SetDisplayName
		, SetAsInitialAnimation
		, DoNothing
		, ShareWinning
		, SetValue
		, SetToInitialPosition
		, SetSceneTalker
	}
	private Type type;
	private double d;
	private boolean isTrue;
	private String atid;
	private short ocode;
	private int frame;
	private String stringValue;
	private int intValue;
	private IScenePresenterFromMakeSingleCallAction scene;

	public MakeSingleCallAction(BaseAction parent) {
		super(parent, true);
		this.type = Type.DoNothing;
	}
	
	public MakeSingleCallAction(BaseAction parent, Type type) {
		super(parent, true);
		this.type = type;
	}
	
	void setDouble(double d){ this.d =d ;}
	void setOCode(short o){ this.ocode = o;}
	void setAtid(String atid){ this.atid = atid;}
	void setFrame(int frame){ this.frame = frame;}
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
	protected void onCompleteGameAction() {
		String otid;
		switch(type)
		{
		case SetBaseMiddleX:
			otid = scene.getOtidByCode(ocode);
			scene.setBaseMiddleXByOtid(otid, this.d);
			return;
		case SetBaseMiddleY:
			otid = scene.getOtidByCode(ocode);
			scene.setBaseMiddleYByOtid(otid, this.d);
			return;
		case AlignBaseMiddle:
			scene.alignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid(atid, frame);
			return;
		case SetActiveFrame:
			otid = scene.getOtidByCode(ocode);
			scene.setCurrentFrameByOtid(otid, this.frame);
			return;
		case  HideAll:
			int count = scene.getSceneObjectCount();
			for (int i = 0; i < count; i++) {
				otid = scene.getOtidByIndex(i);
				scene.setVisibleByOtid(otid, false);
			}
			return;
		case SetCurrentAnimation:
			scene.setAsACurrentAnimationByAtid(atid);
			return;
		case SetVisible:
			otid = scene.getOtidByCode(ocode);
			scene.setVisibleByOtid(otid, this.isTrue);
			return;
		case SetDisplayName:
			otid = scene.getOtidByCode(ocode);
			scene.setDisplayNameByOtid(otid, this.stringValue);
			return;
		case SetAsInitialAnimation:
			scene.setAsAnInitialAnimationByAtid(atid);
			return;
		case DoNothing:
			return;
		case SetValue:
			scene.setValue(stringValue, intValue);
		case ShareWinning:
			scene.shareWinning(stringValue);
			return;
		case SetToInitialPosition:
			otid = scene.getOtidByCode(ocode);
			scene.setXByOtid(otid, 0);
			scene.setYByOtid(otid, 0);
		case SetSceneTalker:
			scene.setSceneTalkerByAtid(atid);
		}
	}

	@Override
	public boolean isParallel() {

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
 
}
