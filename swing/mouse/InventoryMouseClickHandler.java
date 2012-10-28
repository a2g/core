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
import java.awt.event.MouseListener;
import com.google.gwt.event.shared.EventBus;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.swing.panel.ScenePanel;


public class InventoryMouseClickHandler implements MouseListener
{
    private InternalAPI api;
    static boolean isAddedAlready;

    public InventoryMouseClickHandler(EventBus bus, InternalAPI api) {
        this.api = api;
        
        ScenePanel scenePanel = (ScenePanel)api.getSceneGui().getView();
        scenePanel.addMouseListener(this);
    }
    
    public InternalAPI getAPI()
    {
    	return api;
    }


    @Override
    public void mouseClicked(MouseEvent event) 
    {
    	int x = event.getX();
    	int y = event.getY();
    	api.getCommandLineGui().execute(x, y);
    	/*
    	int x = event.getX();
    	int y  = event.getY();
    	String objectId = scenePanel.getObjectUnderMouse(x,y);
    	if(objectId!="")
    	{
    		String textualAnim = api.getSceneGui().getModel().objectCollection().at(objectId).getCurrentAnimation();
    		Rect r = api.getSceneGui().getModel().objectCollection().at(objectId).getAnimations().at(textualAnim).getFrames().at(0).getBoundingRect();
    		SceneObject ob = api.getSceneGui().getModel().objectCollection().at(objectId);
    		String displayName = "";
    		String textualId = "";
    		short code = 0;
    		if (ob != null) {
    			displayName = "" + x + "," + y + ") " +ob.getDisplayName() + "(" + r.getLeft()+","+r.getTop()+ ")to" + "(" + r.getRight()+","+r.getBottom() +")"; 
    			textualId = ob.getTextualId(); 
    			code = ob.getCode(); 
    		}
    		bus.fireEvent(
    				new SetRolloverEvent(
    						displayName,
    						textualId,
    						code));
    	}*/
    }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
