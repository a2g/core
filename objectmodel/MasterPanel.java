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
import com.github.a2g.core.gwt.factory.GWTHostingPanel;
import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI;
import com.github.a2g.core.interfaces.MasterPresenterHostAPI;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class MasterPanel 
extends VerticalPanel 
implements MasterPanelAPI
{
	GWTHostingPanel hostForCommandLine;
    GWTHostingPanel hostForInventory;
    GWTHostingPanel hostForVerbs;
    GWTHostingPanel hostForScene;
    GWTHostingPanel hostForDialogTree;
    GWTHostingPanel hostForLoading;
    GWTHostingPanel hostForTitleCard;
    
    public MasterPanel(MasterPresenterHostAPI parent) {

    	// create all the host panels, that we want to arrange.
        hostForCommandLine = new GWTHostingPanel();
        hostForInventory = new GWTHostingPanel();
        hostForVerbs = new GWTHostingPanel();
        hostForScene = new GWTHostingPanel();
        hostForDialogTree = new GWTHostingPanel();
        hostForLoading = new GWTHostingPanel();
        hostForTitleCard = new GWTHostingPanel();

        // will be constructed from two vertical stacks.
    	AbsolutePanel stackForSceneAndLoading = new AbsolutePanel();
    	AbsolutePanel stackForDialogTreeInvAndCommand = new AbsolutePanel();
 
        DOM.setStyleAttribute(
        		stackForDialogTreeInvAndCommand.getElement(),
                "backgroundColor", "Black");
        DOM.setStyleAttribute(
        		stackForSceneAndLoading.getElement(),
                "backgroundColor", "Black");
     


        {
        	HorizontalPanel verbsAndInventory = new HorizontalPanel();

        	verbsAndInventory.add(hostForVerbs);
        	verbsAndInventory.add(
        			hostForInventory);

        	VerticalPanel commandLineAndVerbsAndInventory = new VerticalPanel();

        	commandLineAndVerbsAndInventory.add(
        			hostForCommandLine);
        	commandLineAndVerbsAndInventory.add(
        			verbsAndInventory);
        	stackForDialogTreeInvAndCommand.add(commandLineAndVerbsAndInventory);
        	stackForDialogTreeInvAndCommand.add(hostForDialogTree);
        }
        {
        	stackForSceneAndLoading.add(hostForScene);
        	stackForSceneAndLoading.add(hostForLoading);
        	stackForSceneAndLoading.add((Widget)hostForTitleCard);
        }
        
        this.add(stackForSceneAndLoading);
        this.add(stackForDialogTreeInvAndCommand);
    	
    }

	public HostingPanelAPI getHostForCommandLine() {
		return hostForCommandLine;
	}


	public HostingPanelAPI getHostForInventory() {
		return hostForInventory;
	}

	public HostingPanelAPI getHostForVerbs() {
		return hostForVerbs;
	}


	public HostingPanelAPI getHostForScene() {
		return hostForScene;
	}


	public HostingPanelAPI getHostForDialogTree() {
		return hostForDialogTree;
	}
	
	public HostingPanelAPI getHostForLoading() {
		return hostForLoading;
	}
	


	public HostingPanelAPI getHostForTitleCard() {
		return hostForTitleCard;
	}
	
}	