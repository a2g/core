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


import com.github.a2g.core.interfaces.MouseToVerbsPresenterAPI;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;


public class VerbMouseClickHandler
implements ClickHandler {
	private final MouseToVerbsPresenterAPI mouseToPresenter;
	String textualId;
	String displayName;
	int code;

	public VerbMouseClickHandler(MouseToVerbsPresenterAPI mouseToPresenter, String displayName, String textualId, int code) {
		this.mouseToPresenter = mouseToPresenter;
		this.code = code;
		this.displayName =displayName;
		this.textualId = textualId;
	}

	@Override
	public void onClick(ClickEvent event)
	{
		mouseToPresenter.doClick(displayName, textualId,code);
	}
}