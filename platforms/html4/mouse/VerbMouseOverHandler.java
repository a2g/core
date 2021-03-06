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


import com.github.a2g.core.interfaces.nongame.presenter.IVerbsPresenterFromVerbsPanel;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;


public class VerbMouseOverHandler implements MouseMoveHandler {
	private final String vtid;
	private final int vcode;
	private final String displayName;
	IVerbsPresenterFromVerbsPanel mouseToPresenter;
	public VerbMouseOverHandler(IVerbsPresenterFromVerbsPanel mouseToPresenter, String displayName, String vtid, int vcode) {
		this.mouseToPresenter = mouseToPresenter;
		this.vtid = vtid;
		this.vcode = vcode;
		this.displayName = displayName;

	}

	@Override
	public void onMouseMove(MouseMoveEvent event)
	{
		mouseToPresenter.setMouseOver(displayName, vtid, vcode);
	}
}