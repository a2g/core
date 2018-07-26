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
package com.github.a2g.core.platforms.swing.mouse;


import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromInventoryPanel;


public class InventoryMouseClickHandler extends MouseAdapter
{
	private IInventoryPresenterFromInventoryPanel api;
	public InventoryMouseClickHandler(JPanel parent, IInventoryPresenterFromInventoryPanel api2) {
		this.api = api2;
	}

	public IInventoryPresenterFromInventoryPanel getAPI()
	{
		return api;
	}

	// use mousePressed (not mouseClicked) so allows half-clicks will also be caught
	@Override
	public void mousePressed(MouseEvent e) {
		api.doClick();
	}



}
