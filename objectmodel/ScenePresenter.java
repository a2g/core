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

package com.github.a2g.core.objectmodel;


import com.google.gwt.event.shared.EventBus;
import com.github.a2g.bridge.panel.ScenePanel;
import com.github.a2g.bridge.thing.AcceptsOneThing;
import com.github.a2g.core.authoredscene.InternalAPI;
import com.github.a2g.core.primitive.Point;


public class ScenePresenter {
    private int width;
    private int height;
    //private EventBus eventBus;
	private Scene scene;
    private ScenePanel view;

    public ScenePanel getView() {
        return view;
    }

    public void setView(ScenePanel view) {
        this.view = view;
    }

    public ScenePresenter(final AcceptsOneThing panel, EventBus bus, InternalAPI api) 
    {
        this.setWidth(320);
        this.setHeight(180);
        this.scene = new Scene();
      //  this.eventBus = bus;
        this.view = new ScenePanel(bus,api);
        panel.setThing(view);
        view.setVisible(true);
       
        // this.theInventoryItemMap = new TreeMap<Integer, InventoryItem>();
    }

    // start go in panel
     
    public void setPixelSize(int width, int height) {
        
        this.view.setSize(width,height);
    }

    public Point getSizeOfSceneArea() {
        // int width = this.sceneArea.
        return new Point(this.getWidth(),
                this.getHeight());
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
    // end go in panel.

	public void clear() {
		view.clear();
	}
	

	public Scene getModel() {
		// TODO Auto-generated method stub
		return scene;
	}

	public void reset()
	{
		this.scene = new Scene();
	}
	
    
}


;
