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
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.objectmodel.SceneObject;
import com.github.a2g.core.platforms.html5.panel.ScenePanelForHtml5;


public class SceneMouseOverHandler
implements MouseMoveHandler
{
	private final EventBus bus;
	private final InternalAPI api;
	private final ScenePanelForHtml5 scenePanel;

	public SceneMouseOverHandler(ScenePanelForHtml5 scenePanel, EventBus bus, InternalAPI api)
	{
		this.bus = bus;
		this.api = api;
		this.scenePanel = scenePanel;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event)
	{
		int w = scenePanel.getWidth();
		int h = scenePanel.getHeight();
		double x = event.getX()/(double)w;
		double y  = (event.getY()+2)/(double)h;
		String objectId = scenePanel.getObjectUnderMouse(event.getX(),event.getY());
		if(objectId!="")
		{
			String textualAnim = api.getSceneGui().getModel().objectCollection().at(objectId).getCurrentAnimation();
			api.getSceneGui().getModel().objectCollection().at(objectId).getAnimations().at(textualAnim).getFrames().at(0).getBoundingRect();
			SceneObject ob = api.getSceneGui().getModel().objectCollection().at(objectId);
			String displayName = "";
			String textualId = "";
			short code = 0;
			if (ob != null) {
				//displayName = "" + x + "," + y + ") " +ob.getDisplayName() + "(" + r.getLeft()+","+r.getTop()+ ")to" + "(" + r.getRight()+","+r.getBottom() +")";
				displayName = ob.getDisplayName();
				textualId = ob.getTextualId();
				code = ob.getCode();
			}
			double camx = api.getSceneGui().getCameraX();
			double camy = api.getSceneGui().getCameraY();
			api.getCommandLineGui().setXYForDebugging(x+camx, y+camy);
			api.getCommandLineGui().onSetMouseOver(displayName, textualId, code);
			bus.fireEvent(
					new SetRolloverEvent(
							displayName,
							textualId,
							code));
		}
	}
}