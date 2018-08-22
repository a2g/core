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

package com.github.a2g.core.platforms.html5.mouse;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromInventoryMouseOver;
import com.github.a2g.core.platforms.html5.InventoryPanelForHtml5;


public class InventoryMouseOverHandler
implements MouseMoveHandler
{
	private final IInventoryPresenterFromInventoryMouseOver toPresenter; 
	private final InventoryPanelForHtml5 invPanel;

	public InventoryMouseOverHandler(InventoryPanelForHtml5 invPanel, EventBus bus, IInventoryPresenterFromInventoryMouseOver toPresenter)
	{
		this.toPresenter = toPresenter; 
		this.invPanel = invPanel;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event)
	{
		double x = event.getX();
		double y = event.getY();
		x/=invPanel.getOffsetWidth();
		y/=invPanel.getOffsetHeight();
		toPresenter.setMouseOver(x,y);
	}
}