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


import com.github.a2g.core.interfaces.IInventoryPresenterFromInventoryPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;


public class InventoryItemMouseClickHandler implements ClickHandler {
	private final AbsolutePanel absolutePanel;

	private final IInventoryPresenterFromInventoryPanel mouseToPresenter;

	public InventoryItemMouseClickHandler(AbsolutePanel abs, IInventoryPresenterFromInventoryPanel mouseToPresenter) {
		this.absolutePanel = abs;
		this.mouseToPresenter = mouseToPresenter;
	}

	@Override
	public void onClick(ClickEvent event) {
		double x = -1;
		double y = -1;

		if (this.absolutePanel != null) {
			x = event.getRelativeX(
					this.absolutePanel.getElement());
			y = event.getRelativeY(
					this.absolutePanel.getElement());
			y /= this.absolutePanel.getOffsetHeight();
			x /= this.absolutePanel.getOffsetWidth();
		}

		//bus.fireEvent(
		//	new ExecuteCommandEvent(x, y));
		mouseToPresenter.doClick(x,y);
	}
}