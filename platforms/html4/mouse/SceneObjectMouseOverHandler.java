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
import com.github.a2g.core.interfaces.IScenePresenterFromSceneMouseOver;


public class SceneObjectMouseOverHandler implements MouseMoveHandler {
	private final EventBus bus;
	private final String textualId;
	private final short code;
	private final IScenePresenterFromSceneMouseOver  api;

	public SceneObjectMouseOverHandler(EventBus bus, IScenePresenterFromSceneMouseOver api, String textualId, short code) {
		this.bus = bus;
		this.textualId = textualId;
		this.code = code;
		this.api = api;

	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {

		String displayName = api.getDisplayNameByOtid(textualId);
		bus.fireEvent(
				new SetRolloverEvent(
						displayName,
						this.textualId,
						this.code));
	}
}