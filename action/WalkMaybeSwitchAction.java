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
import com.github.a2g.core.action.performer.MovePerformer;
import com.github.a2g.core.action.performer.SwitchPerformer;
import com.github.a2g.core.action.performer.WalkPerformer;
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.primitive.PointF;

public class WalkMaybeSwitchAction extends ChainEndAction{

	MovePerformer mover;
	SwitchPerformer switcher;
	WalkPerformer walker;
	short ocode;

	public WalkMaybeSwitchAction(BaseAction parent, short ocode) {
		super(parent);
		this.ocode = ocode;
		mover = new MovePerformer(ocode);
		mover.setToInitialAtEnd(true);// only ChainableAction::walkAndSwitch sets setToInitialAtEnd(false)
		
		switcher = new SwitchPerformer(ocode);
		walker = new WalkPerformer(ocode);
	}

	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) 
	{
		mover.setScene(scene);
		switcher.setScene(scene);
		walker.setScene(scene);
	}


	@Override
	public void runGameAction() {
		switcher.run( );
		double duration = mover.run();
		walker.run(mover.getStartPt(), mover.getEndPt());

		this.run((int) (duration * 1000.0));
	}



	@Override
	protected void onUpdateGameAction(double progress) {
		PointF pt = mover.onUpdateCalculate(progress);
		switcher.onUpdate(progress);
		if(switcher.isStopped()) 
			return;
		mover.onUpdateCalculate(progress, pt);
	}

	@Override
	protected boolean onCompleteGameAction() { 
		onUpdateGameAction(1.0);
		mover.onComplete();
		boolean isExited = switcher.onComplete();
		return isExited;
	}
	
	void setEndX(double endX) {
		mover.setEndX(endX);
		switcher.setEndX(endX);
	}

	void setEndY(double endY) {
		mover.setEndY(endY);
		switcher.setEndY(endY);
	}
 }
