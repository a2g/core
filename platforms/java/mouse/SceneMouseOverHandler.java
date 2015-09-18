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


import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.google.gwt.event.shared.EventBus;
import com.github.a2g.core.interfaces.internal.ICommandLinePresenterFromSceneMouseOver;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromSceneMouseOver;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.platforms.java.panel.ScenePanelForJava;
import com.github.a2g.core.objectmodel.SceneObject;


@SuppressWarnings("unused")
public class SceneMouseOverHandler implements MouseMotionListener {
	private final EventBus bus;
	private IScenePresenterFromSceneMouseOver toScene;
	private ICommandLinePresenterFromSceneMouseOver toCommandLine;
	private final ScenePanelForJava scenePanel;
	static boolean isAddedAlready;

	public SceneMouseOverHandler(ScenePanelForJava scenePanel, EventBus bus, IScenePresenterFromSceneMouseOver toScene, ICommandLinePresenterFromSceneMouseOver toCommandLine) {
		this.bus = bus;
		this.toScene = toScene;
		this.toCommandLine = toCommandLine;
		this.scenePanel = scenePanel;
		if(!isAddedAlready)
		{
			scenePanel.addMouseMotionListener(this);
			isAddedAlready = true;
		}
	}

	public IScenePresenterFromSceneMouseOver getAPI()
	{
		return toScene;
	}


	@Override
	public void mouseMoved(MouseEvent event)
	{
		int w = scenePanel.getWidth();
		int h = scenePanel.getHeight();
		double x = event.getX()/(double)scenePanel.getWidth();
		double y  = (event.getY()+2)/(double)scenePanel.getHeight();
		String otid = scenePanel.getObjectUnderMouse(event.getX(),event.getY());
		if(otid!="")
		{
			//displayName = "" + x + "," + y + ") " +ob.getDisplayName() + "(" + r.getLeft()+","+r.getTop()+ ")to" + "(" + r.getRight()+","+r.getBottom() +")";
			String displayName = toScene.getDisplayNameByOtid(otid);
			short ocode = toScene.getCodeByOtid(otid);
			double camx = toScene.getCameraX();
			double camy = toScene.getCameraY();
			toCommandLine.setXYForDebugging(x+camx, y+camy);
			toCommandLine.setCommandLineMouseOver(displayName, otid, ocode);
			bus.fireEvent(
					new SetRolloverEvent(
							displayName,
							otid,
							ocode));
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
