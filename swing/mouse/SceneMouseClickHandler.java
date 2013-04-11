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
package com.github.a2g.core.swing.mouse;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.google.gwt.event.shared.EventBus;
import com.github.a2g.core.event.ExecuteCommandEvent;
import com.github.a2g.core.interfaces.InternalAPI;


public class SceneMouseClickHandler extends MouseAdapter
{
	private InternalAPI api;
	private EventBus bus;

	public SceneMouseClickHandler(EventBus bus, InternalAPI api)
	{
		this.api = api;
		this.bus = bus;
	}

	public InternalAPI getAPI()
	{
		return api;
	}


	// use mousePressed (not mouseClicked) so allows half-clicks will also be caught
	@Override
	public void mousePressed(MouseEvent event) {
		double width = api.getSceneGui().getWidth();
		double height = api.getSceneGui().getHeight();
		double x = event.getX()/width;
		double y = event.getY()/height;
		bus.fireEvent(
				new ExecuteCommandEvent(x, y));

	}
}
