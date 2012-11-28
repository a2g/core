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
    boolean isDialogTreeModeActive;
  
    
    static final String LOADING_WIDGET = "LOADING_WIDGET";
    static final String SCENE_WIDGET = "SCENE_WIDGET";
    static final String TITLECARD_WIDGET = "TITLECARD_WIDGET";
    static final String DIALOGTREE_WIDGET = "TITLECARD_WIDGET";
    static final String COMMANDLINEVERBSINVENTORY_WIDGET = "COMMANDLINEVERBSINVENTORY_WIDGET";
    
    CardLayout sceneCardLayout;
    CardLayout dialogTreeCardLayout;
    JPanel panelForSceneStack;
    JPanel panelForDialogTreeStack;
    
    public MasterPanel(int width, int height) {
    	isDialogTreeModeActive = false;
    	// create all the host panels, that we want to arrange.
        hostForCommandLineF = new SwingHostingPanel();
        hostForInventoryF = new SwingHostingPanel();
        hostForVerbsF = new SwingHostingPanel();
        hostForSceneF = new SwingHostingPanel();
        hostForDialogTreeF = new SwingHostingPanel();
        hostForLoadingF = new SwingHostingPanel();
        hostForTitleCardF = new SwingHostingPanel();
        sceneCardLayout =null;
         
    
        // will be constructed from two vertical stacks.
        panelForDialogTreeStack = new JPanel();

        {
        	// lay the CL/V/I panel and dialog tree - on top of each other
        	JPanel commandLineAndVerbsAndInventoryF = new JPanel();
        	dialogTreeCardLayout = new CardLayout();
        	panelForDialogTreeStack.setLayout(dialogTreeCardLayout);
        	panelForDialogTreeStack.add(hostForDialogTreeF, MasterPanel.DIALOGTREE_WIDGET);   
        	panelForDialogTreeStack.add(commandLineAndVerbsAndInventoryF, MasterPanel.COMMANDLINEVERBSINVENTORY_WIDGET);
         	
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
        	sceneCardLayout = new CardLayout();
        	panelForSceneStack.setLayout(sceneCardLayout);
        	panelForSceneStack.add(hostForSceneF, SCENE_WIDGET);
        	panelForSceneStack.add(hostForLoadingF, LOADING_WIDGET);  
        	panelForSceneStack.add(hostForTitleCardF, TITLECARD_WIDGET);  
        }
        
        // layout the scene at the top, and the rest at te bottom.
        GridLayout topToBottom = new GridLayout(2,1);
		this.setLayout(topToBottom);
        this.add(panelForSceneStack);
        this.add(panelForDialogTreeStack);
        
        
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
	
	@Override
	public void setDialogTreeActive(boolean isInDialogTreeMode)
	{
		isDialogTreeModeActive = true;
		this.hostForCommandLineF.setVisible(!isInDialogTreeMode);
		this.hostForDialogTreeF.setVisible(isInDialogTreeMode);
		this.hostForInventoryF.setVisible(!isInDialogTreeMode);
		this.hostForLoadingF.setVisible(!isInDialogTreeMode);
		this.hostForSceneF.setVisible(isInDialogTreeMode);
		this.hostForTitleCardF.setVisible(!isInDialogTreeMode);
		this.hostForVerbsF.setVisible(!isInDialogTreeMode);
		sceneCardLayout.show(panelForSceneStack, MasterPanel.SCENE_WIDGET);
		dialogTreeCardLayout.show(panelForDialogTreeStack, isInDialogTreeMode? MasterPanel.DIALOGTREE_WIDGET : MasterPanel.COMMANDLINEVERBSINVENTORY_WIDGET);
	}
			
	@Override
	public void setLoadingActive()
	{
		isDialogTreeModeActive = false;
		this.hostForCommandLineF.setVisible(false);
		this.hostForDialogTreeF.setVisible(false);
		this.hostForInventoryF.setVisible(false);
		this.hostForLoadingF.setVisible(true);
		this.hostForSceneF.setVisible(false);
		this.hostForTitleCardF.setVisible(false);
		this.hostForVerbsF.setVisible(false);
		sceneCardLayout.show(panelForSceneStack, MasterPanel.LOADING_WIDGET);
	}
	
	@Override
	public void setTitleCardActive()
	{
		isDialogTreeModeActive = false;
		this.hostForCommandLineF.setVisible(false);
		this.hostForDialogTreeF.setVisible(false);
		this.hostForInventoryF.setVisible(false);
		this.hostForLoadingF.setVisible(false);
		this.hostForSceneF.setVisible(false);
		this.hostForTitleCardF.setVisible(true);
		this.hostForVerbsF.setVisible(false);
		dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanel.COMMANDLINEVERBSINVENTORY_WIDGET);
		
	}
	
	@Override
	public void setSceneActiveForNonInteraction()
	{
		isDialogTreeModeActive = false;
		this.hostForCommandLineF.setVisible(false);
		this.hostForDialogTreeF.setVisible(false);
		this.hostForInventoryF.setVisible(false);
		this.hostForLoadingF.setVisible(false);
		this.hostForSceneF.setVisible(true);
		this.hostForTitleCardF.setVisible(false);
		this.hostForVerbsF.setVisible(false);
		sceneCardLayout.show(panelForSceneStack, MasterPanel.SCENE_WIDGET);
		dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanel.LOADING_WIDGET);
		
	}
	
	@Override
	public void setSceneActiveForInteraction()
	{
		isDialogTreeModeActive = false;
		this.hostForCommandLineF.setVisible(true);
		this.hostForDialogTreeF.setVisible(false);
		this.hostForInventoryF.setVisible(true);
		this.hostForLoadingF.setVisible(false);
		this.hostForSceneF.setVisible(true);
		this.hostForTitleCardF.setVisible(false);
		this.hostForVerbsF.setVisible(true);
		sceneCardLayout.show(panelForSceneStack, MasterPanel.SCENE_WIDGET);
		dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanel.COMMANDLINEVERBSINVENTORY_WIDGET);
		

	}

	@Override
	public boolean isDialogTreeActive() {
		return isDialogTreeModeActive;
	}
	
}	
