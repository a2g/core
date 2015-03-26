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

package com.github.a2g.core.platforms.java.mouse;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.github.a2g.core.interfaces.IVerbsPresenterFromVerbsPanel;


public class VerbMouseOverHandler extends MouseAdapter
{
	private final String textualId;
	private final int code;
	private final String displayName;
	private final IVerbsPresenterFromVerbsPanel mouseToPresenter;

	public VerbMouseOverHandler(IVerbsPresenterFromVerbsPanel mouseToPresenter, String displayName, String textualId, int code) 
	{
		this.mouseToPresenter = mouseToPresenter;
		this.textualId = textualId;
		this.code = code;
		this.displayName = displayName;

	}

	@Override
	public void mouseEntered(MouseEvent e)  
	{
		mouseToPresenter.setMouseOver(displayName, textualId, code);
	}
}
