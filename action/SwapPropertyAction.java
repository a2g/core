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
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.action.ChainedAction;


public class SwapPropertyAction extends ChainedAction {

	private short objId1;
	private short objId2;
	private SwapType type;
	boolean newA;
	boolean newB;
	public SwapPropertyAction(BaseAction parent, short objId1, short objId2, SwapType type) {
		super(parent, parent.getApi(), true);
		this.objId1 = objId1;
		;
		this.objId2 = objId2;
		this.type = type;
	}

	@Override
	public void runGameAction() {
		SceneObject a = getApi().getObject(
				this.objId1);
		SceneObject b = getApi().getObject(
				this.objId2);
		newA = b.isVisible();
		newB = a.isVisible();
		super.run(1);
	}

	@Override
	protected void onUpdateGameAction(double progress) {}

	@Override
	protected void onCompleteGameAction() {
		SceneObject a = getApi().getObject(
				this.objId1);
		SceneObject b = getApi().getObject(
				this.objId2);

		switch (this.type) {
		case Visibility:
			//boolean newA = b.isVisible();
			//boolean newB = a.isVisible();

			a.setVisible(newA);
			b.setVisible(newB);

		}

	}

	@Override
	public boolean isParallel() {

		return false;
	}

}
