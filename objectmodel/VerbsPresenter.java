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
import com.github.a2g.core.interfaces.IHostingPanel;
import com.github.a2g.core.interfaces.IVerbsPresenterFromVerbsPanel;
import com.github.a2g.core.interfaces.IVerbsPanelFromVerbsPresenter;
import com.github.a2g.core.interfaces.IVerbsPresenter;
import com.github.a2g.core.interfaces.IMasterPresenterFromVerbs;
import com.github.a2g.core.primitive.ColorEnum;

public class VerbsPresenter implements IVerbsPresenterFromVerbsPanel,
		IVerbsPresenter {
	private Verbs theVerbs;
	private IVerbsPanelFromVerbsPresenter view;
	private int widthOfScene;
	private int widthOfInventory;
	IMasterPresenterFromVerbs callback;

	public VerbsPresenter(final IHostingPanel panel, EventBus bus,
			IMasterPresenterFromVerbs callback) {
		this.callback = callback;
		this.theVerbs = new Verbs();
		this.view = callback.getFactory().createVerbsPanel(this,
				ColorEnum.Purple, ColorEnum.Black);
		panel.setThing(view);
		this.view.setVerbs(theVerbs);
		this.widthOfScene = 0;
		this.widthOfInventory = 0;
	}

	public void clear() {
		// doesn't change.

	}

	public Verbs getVerbsModel() {
		return theVerbs;
	}

	public IVerbsPanelFromVerbsPresenter getView() {
		return view;
	}

	public void update() {
		view.setVerbs(theVerbs);
		view.update();
	}

	public void setWidthOfScene(int width) {
		this.widthOfScene = width;
		view.setWidth(widthOfScene - widthOfInventory);
	}

	public void setWidthOfInventory(int width) {
		this.widthOfInventory = width;
		view.setWidth(widthOfScene - widthOfInventory);
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
