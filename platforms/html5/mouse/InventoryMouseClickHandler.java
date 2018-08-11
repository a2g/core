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


import com.github.a2g.core.event.ExecuteCommandEvent;
import com.github.a2g.core.platforms.html5.dependencies.CanvasEtcHtml5;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.web.bindery.event.shared.EventBus;


public class InventoryMouseClickHandler implements ClickHandler {
	private final EventBus bus;
	private final CanvasEtcHtml5 canvasEtcHtml5;

	public InventoryMouseClickHandler(EventBus bus, CanvasEtcHtml5 canvasEtcHtml5) {
		this.bus = bus;
		this.canvasEtcHtml5 = canvasEtcHtml5;
	}

	@Override
	public void onClick(ClickEvent event) {
		double x = -1;
		double y = -1;

		if (this.canvasEtcHtml5 != null) {
			x = event.getRelativeX(
					this.canvasEtcHtml5.getElement());
			y = event.getRelativeY(
					this.canvasEtcHtml5.getElement());
			y /= this.canvasEtcHtml5.getOffsetHeight();
			x /= this.canvasEtcHtml5.getOffsetWidth();
		}
		bus.fireEvent(
				new ExecuteCommandEvent(x, y));
	}
}