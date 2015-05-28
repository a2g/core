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
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.primitive.PointF;

public class MoveWhilstAnimatingAction extends ChainableAction{

	MovePerformer mover;
	public MoveWhilstAnimatingAction(BaseAction parent, short ocode) {
		super(parent);
		mover = new MovePerformer(ocode);
	}


	@Override
	public void setAll(IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dialogTree,
			ITitleCardPresenterFromActions titleCard, IInventoryPresenterFromActions inventory) 
	{
		mover.setScene(scene);
	}
	
	@Override
	public void runGameAction() {
		double duration = mover.run();
		
		this.run((int) (duration * 1000.0));

	}

	@Override
	protected void onUpdateGameAction(double progress) {
		PointF pt = mover.onUpdateCalculate(progress);
		mover.onUpdateCalculate(progress, pt);
	}

	@Override
	// method in animation
	protected boolean onCompleteGameAction() { 
		onUpdateGameAction(1.0);
		mover.onComplete();
		return false;
	}


	void setEndX(double endX) {
		mover.setEndX(endX);
	}

	void setEndY(double endY) {
		mover.setEndY(endY);
	}

	 



}
