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
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


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
    GuiStateEnum state;
    
    public MasterPanel(MasterPresenterHostAPI parent, ColorEnum back) {

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
 
        DOM.setStyleAttribute(stackForDialogTreeInvAndCommand.getElement(),"backgroundColor", back.toString());
        DOM.setStyleAttribute(stackForSceneAndLoading.getElement(),"backgroundColor", back.toString());
     


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
        	stackForSceneAndLoading.add(hostForTitleCard);
        }
        
        this.add(stackForSceneAndLoading);
        this.add(stackForDialogTreeInvAndCommand);
    	
    }

	@Override
	public HostingPanelAPI getHostForCommandLine() {
		return hostForCommandLine;
	}


	@Override
	public HostingPanelAPI getHostForInventory() {
		return hostForInventory;
	}

	@Override
	public HostingPanelAPI getHostForVerbs() {
		return hostForVerbs;
	}


	@Override
	public HostingPanelAPI getHostForScene() {
		return hostForScene;
	}


	@Override
	public HostingPanelAPI getHostForDialogTree() {
		return hostForDialogTree;
	}
	
	@Override
	public HostingPanelAPI getHostForLoading() {
		return hostForLoading;
	}
	


	@Override
	public HostingPanelAPI getHostForTitleCard() {
		return hostForTitleCard;
	}

	@Override
	public void setActiveState(GuiStateEnum state) 
	{
		this.state = state;
		
		switch(state)
		{
		case DialogTreeMode:
			hostForDialogTree.setVisible(true);
			hostForScene.setVisible(true);
			hostForLoading.setVisible(false);
			hostForTitleCard.setVisible(false);
			hostForCommandLine.setVisible(false);
		    hostForInventory.setVisible(false);
		    hostForVerbs.setVisible(false);
			break;
		case CutScene:
			hostForScene.setVisible(true);
			hostForDialogTree.setVisible(false);
			hostForLoading.setVisible(false);
			hostForTitleCard.setVisible(false);
			hostForCommandLine.setVisible(false);
		    hostForInventory.setVisible(false);
		    hostForVerbs.setVisible(false);
		    break;
		case ActiveScene:
			hostForScene.setVisible(true);
			hostForCommandLine.setVisible(true);
		    hostForInventory.setVisible(true);
		    hostForVerbs.setVisible(true);
			hostForDialogTree.setVisible(false);
			hostForLoading.setVisible(false);
			hostForTitleCard.setVisible(false);
			break;
		case Loading:
			hostForScene.setVisible(false);
			hostForLoading.setVisible(true);
			hostForCommandLine.setVisible(false);
		    hostForInventory.setVisible(false);
		    hostForVerbs.setVisible(false);
			hostForDialogTree.setVisible(false);
			hostForTitleCard.setVisible(false);
			break;
		case TitleCardOverActiveScene:
			hostForTitleCard.setVisible(true);
			hostForCommandLine.setVisible(true);
		    hostForInventory.setVisible(true);
		    hostForVerbs.setVisible(true);
			hostForDialogTree.setVisible(false);
			hostForScene.setVisible(false);
			hostForLoading.setVisible(false);
			break;
		case TitleCardOverCutScene:
			hostForTitleCard.setVisible(true);
			hostForCommandLine.setVisible(false);
		    hostForInventory.setVisible(false);
		    hostForVerbs.setVisible(false);
			hostForDialogTree.setVisible(false);
			hostForScene.setVisible(false);
			hostForLoading.setVisible(false);
			break;
		case TitleCardOverDialogTree:
			hostForDialogTree.setVisible(true);
			hostForTitleCard.setVisible(true);
			hostForCommandLine.setVisible(false);
		    hostForInventory.setVisible(false);
		    hostForVerbs.setVisible(false);
			hostForScene.setVisible(false);
			hostForLoading.setVisible(false);
			break;
		case TitleCardOverLoading:
			hostForTitleCard.setVisible(true);
			hostForLoading.setVisible(false);
			hostForDialogTree.setVisible(false);
			hostForCommandLine.setVisible(false);
		    hostForInventory.setVisible(false);
		    hostForVerbs.setVisible(false);
			hostForScene.setVisible(false);
			break;
		default:
			break;
		}
	}

	@Override
	public GuiStateEnum getActiveState() 
	{
		return state;
	}

}	
