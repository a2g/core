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


import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.github.a2g.core.interfaces.MouseToInventoryPresenterAPI;
import com.github.a2g.core.platforms.java.panel.InventoryPanelForJava;


public class InventoryMouseOverHandler implements MouseMotionListener
{
	private MouseToInventoryPresenterAPI mouseToPresenter;
	private final InventoryPanelForJava inventoryPanel;
	static boolean isAddedAlready;

	public InventoryMouseOverHandler(InventoryPanelForJava inventoryPanel, MouseToInventoryPresenterAPI mouseToPresenter2) {
		this.mouseToPresenter = mouseToPresenter2;
		this.inventoryPanel = inventoryPanel;
		if(!isAddedAlready)
		{
			inventoryPanel.addMouseMotionListener(this);
			isAddedAlready = true;
		}
	}

	@Override
	public void mouseMoved(MouseEvent event)
	{
		Dimension size = inventoryPanel.getSize();
		double x = event.getX();
		double y = event.getY();
		x/=size.width;
		y/=size.height;
		mouseToPresenter.setMouseOver(x,y);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}
}