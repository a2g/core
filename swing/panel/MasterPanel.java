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

package com.github.a2g.core.swing.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI;
import com.github.a2g.core.swing.factory.SwingHostingPanel;


@SuppressWarnings("serial")
public class MasterPanel 
extends JPanel
implements MasterPanelAPI
{
    SwingHostingPanel hostForCommandLineF;
    SwingHostingPanel hostForInventoryF;
    SwingHostingPanel hostForVerbsF;
    SwingHostingPanel hostForSceneF;
    SwingHostingPanel hostForDialogTreeF;
    SwingHostingPanel hostForLoadingF;
    SwingHostingPanel hostForTitleCardF;
    JPanel panelForSceneStack;
    
    static final String LOADING_WIDGET = "loadingWidget";
    static final String SCENE_WIDGET = "sceneWidget";
    static final String CUECARD_WIDGET = "cueCardWidget";
    
    CardLayout sceneStack;
    public MasterPanel(int width, int height) {

    	// create all the host panels, that we want to arrange.
        hostForCommandLineF = new SwingHostingPanel();
        hostForInventoryF = new SwingHostingPanel();
        hostForVerbsF = new SwingHostingPanel();
        hostForSceneF = new SwingHostingPanel();
        hostForDialogTreeF = new SwingHostingPanel();
        hostForLoadingF = new SwingHostingPanel();
        hostForTitleCardF = new SwingHostingPanel();
        sceneStack =null;
         
    
        // will be constructed from two vertical stacks.
        JPanel stackForDialogTreeInvAndCommandF = new JPanel();

        {
        	// lay the CL/V/I panel and dialog tree - on top of each other
        	JPanel commandLineAndVerbsAndInventoryF = new JPanel();
        	CardLayout card = new CardLayout();
        	stackForDialogTreeInvAndCommandF.setLayout(card);
        	stackForDialogTreeInvAndCommandF.add(commandLineAndVerbsAndInventoryF, "commandLineAndVerbsAndInventory");
        	stackForDialogTreeInvAndCommandF.add(hostForDialogTreeF, "hostForDialogTree");

        	{
        		// layout the command line and the panel below it - vertically.
        		JPanel verbsAndInventoryF = new JPanel();
        		commandLineAndVerbsAndInventoryF.add(hostForCommandLineF);
        		commandLineAndVerbsAndInventoryF.add(verbsAndInventoryF);

        		{
        			// layout the verbs and inventory - from left to right
        			FlowLayout leftToRight = new FlowLayout();
        			leftToRight.setHgap(0);
        			leftToRight.setVgap(0);
        			verbsAndInventoryF.setLayout(leftToRight);
        			verbsAndInventoryF.add(hostForVerbsF, "hostForVerbs");
        			verbsAndInventoryF.add(hostForInventoryF, "hostForInventory");
        		}
        	}
        } 

        panelForSceneStack = new JPanel();
        {
        	sceneStack = new CardLayout();
        	panelForSceneStack.setLayout(sceneStack);
        	panelForSceneStack.add(hostForSceneF, SCENE_WIDGET);
        	panelForSceneStack.add(hostForLoadingF, LOADING_WIDGET);  
        	panelForSceneStack.add(hostForTitleCardF, CUECARD_WIDGET);  
        }
        
        // layout the scene at the top, and the rest at te bottom.
        GridLayout topToBottom = new GridLayout(2,1);
		this.setLayout(topToBottom);
        this.add(panelForSceneStack);
        this.add(stackForDialogTreeInvAndCommandF);
        
        
        this.setVisible(true);
    }

	@Override
	public HostingPanelAPI getHostForCommandLine() {
		return hostForCommandLineF;
	}

	@Override
	public HostingPanelAPI getHostForInventory() {
		return hostForInventoryF;
	}

	@Override
	public HostingPanelAPI getHostForVerbs() 
	{
		return hostForVerbsF;
	}

	@Override
	public HostingPanelAPI getHostForScene() {
		return hostForSceneF;
	}

	@Override
	public HostingPanelAPI getHostForDialogTree() {
		return hostForDialogTreeF;
	}
	
	@Override
	public HostingPanelAPI getHostForLoading() {
		return hostForLoadingF;
	}
	
	@Override
	public HostingPanelAPI getHostForTitleCard(){
		return this.hostForTitleCardF;
	}
	
	
}	
