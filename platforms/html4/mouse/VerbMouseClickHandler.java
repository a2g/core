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

package com.github.a2g.core.platforms.html4.mouse;


import com.google.gwt.event.dom.client.ClickHandler;
import com.github.a2g.core.interfaces.nongame.presenter.IVerbsPresenterFromVerbsPanel;
import com.google.gwt.event.dom.client.ClickEvent;


public class VerbMouseClickHandler
implements ClickHandler {
	private final IVerbsPresenterFromVerbsPanel mouseToPresenter;
	String vtid;
	String displayName;
	int vcode;

	public VerbMouseClickHandler(IVerbsPresenterFromVerbsPanel mouseToPresenter, String displayName, String vtid, int vcode) {
		this.mouseToPresenter = mouseToPresenter;
		this.vcode = vcode;
		this.displayName =displayName;
		this.vtid = vtid;
	}

	@Override
	public void onClick(ClickEvent event)
	{
		mouseToPresenter.doClick(displayName, vtid,vcode);
	}
}