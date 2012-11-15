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


import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.google.gwt.event.shared.EventBus;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.event.SetRolloverEvent;
import com.github.a2g.core.primitive.Rect;
import com.github.a2g.core.swing.panel.ScenePanel;
import com.github.a2g.core.objectmodel.SceneObject;


@SuppressWarnings("unused")
public class SceneMouseOverHandler implements MouseMotionListener {
    private final EventBus bus;
    private InternalAPI api;
    private final ScenePanel scenePanel;
    static boolean isAddedAlready;

    public SceneMouseOverHandler(ScenePanel scenePanel, EventBus bus, InternalAPI api) {
        this.bus = bus;
        this.api = api;
        this.scenePanel = scenePanel;
        if(!isAddedAlready)
        {
        	scenePanel.addMouseMotionListener(this);
        	isAddedAlready = true;
        }
    }
    
    public InternalAPI getAPI()
    {
    	return api;
    }


    @Override
    public void mouseMoved(MouseEvent event) {
    	int x = event.getX();
    	int y  = event.getY();
    	String objectId = scenePanel.getObjectUnderMouse(x,y);
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
    		api.getCommandLineGui().onSetMouseOver(displayName, textualId, code);
    		bus.fireEvent(
    				new SetRolloverEvent(
    						displayName,
    						textualId,
    						code));
    	}
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
