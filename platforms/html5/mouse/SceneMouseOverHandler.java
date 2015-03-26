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
import com.github.a2g.core.interfaces.ICommandLinePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.platforms.html5.panel.ScenePanelForHtml5;


public class SceneMouseOverHandler
implements MouseMoveHandler
{
	private final EventBus bus;
	private final IScenePresenterFromSceneMouseOver toScene;
	private final ICommandLinePresenterFromSceneMouseOver toCommandLine;
	private final ScenePanelForHtml5 scenePanel;

	public SceneMouseOverHandler(ScenePanelForHtml5 scenePanel, EventBus bus, IScenePresenterFromSceneMouseOver toScene, ICommandLinePresenterFromSceneMouseOver toCommandLine)
	{
		this.bus = bus;
		this.toScene = toScene;
		this.toCommandLine = toCommandLine;
		this.scenePanel = scenePanel;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event)
	{
		int w = scenePanel.getWidth();
		int h = scenePanel.getHeight();
		double x = event.getX()/(double)w;
		double y  = (event.getY()+2)/(double)h;
		String otid = scenePanel.getObjectUnderMouse(event.getX(),event.getY());
		if(otid!="")
		{
			//String atid = api.getSceneGui().getAtidOfCurrentAnimationByOtid(otid);
			String displayName = toScene.getDisplayNameByOtid(otid);
			short code = toScene.getCodeByOtid(otid);
			
			double camx = toScene.getCameraX();
			double camy = toScene.getCameraY();
			toCommandLine.setXYForDebugging(x+camx, y+camy);
			toCommandLine.setCommandLineMouseOver(displayName, otid, code);
			bus.fireEvent(
					new SetRolloverEvent(
							displayName,
							otid,
							code));
		}
	}
}