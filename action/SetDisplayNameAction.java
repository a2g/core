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


public class SetDisplayNameAction extends BaseAction {
	private short objId;
	private String displayName;

	public SetDisplayNameAction(BaseAction parent, short objId, String displayName) {
		super(parent, parent.getApi());
		this.displayName = displayName;
		this.objId = objId;
	}

	@Override
	public void runGameAction() {
		super.run(1);
	}

	@Override
	protected void onUpdateGameAction(double progress) {}

	@Override
	protected void onCompleteGameAction() {
		getApi().getObject(this.objId).setDisplayName(
				this.displayName);
	}

	@Override
	public boolean isParallel() {

		return false;
	}

}
