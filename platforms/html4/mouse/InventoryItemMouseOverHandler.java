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


import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromInventoryMouseOver;


public class InventoryItemMouseOverHandler implements MouseMoveHandler {
	private final EventBus bus;
	private final String itid;
	private final int icode;
	private final IInventoryPresenterFromInventoryMouseOver api;

	public InventoryItemMouseOverHandler(EventBus bus, IInventoryPresenterFromInventoryMouseOver api, String itid, int  objectCode) {
		this.bus = bus;
		this.itid = itid;
		this.icode = objectCode;
		this.api = api;

	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {

		bus.fireEvent(
				new SetRolloverEvent(
						api.getDisplayNameByItid(this.itid),
						this.itid,
						this.icode));
	}

}