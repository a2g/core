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

package com.github.a2g.core.objectmodel;

import com.google.gwt.event.shared.EventBus;
import com.github.a2g.core.interfaces.nongame.IHostingPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformVerbsPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromVerbs;
import com.github.a2g.core.interfaces.nongame.presenter.IVerbsPresenter;
import com.github.a2g.core.interfaces.nongame.presenter.IVerbsPresenterFromVerbsPanel;
import com.github.a2g.core.primitive.GuiConstants;

public class VerbsPresenter implements IVerbsPresenterFromVerbsPanel,
IVerbsPresenter {
	private Verbs theVerbs;
	private IPlatformVerbsPanel view; 
	IMasterPresenterFromVerbs callback;

	public VerbsPresenter(final IHostingPanel panel, EventBus bus,
			IMasterPresenterFromVerbs callback) {
		this.callback = callback;
		this.theVerbs = new Verbs();
		this.view = callback.getFactory().createVerbsPanel(this,
				GuiConstants.TEXT_NORMAL, GuiConstants.BACKGROUND_FILL);
		panel.setThing(view);
		this.view.setVerbs(theVerbs);
 	}

	public void clear() {
		// doesn't change.

	}

	public Verbs getVerbsModel() {
		return theVerbs;
	}

	public IPlatformVerbsPanel getView() {
		return view;
	}

	public void updateVerbs() {
		view.setVerbs(theVerbs);
		view.update();
	}

	public void setScenePixelSize(int width, int height) {
		view.setWidth(width/2);
	}


	@Override
	public void setMouseOver(String displayName, String vtid, int vcode) {
		callback.onMouseOverVerbsOrInventory(displayName, vtid, vcode);

	}

	@Override
	public void doClick() {
		callback.onClickVerbsOrInventory();
	}

	@Override
	public void doClick(String displayName, String vtid, int vcode) {
		this.setMouseOver(displayName, vtid, vcode);
		this.doClick();
	}

	public void removeByCode(int vcode) {
		this.theVerbs.items().removeByCode(vcode);

	}
}
