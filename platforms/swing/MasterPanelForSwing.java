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

package com.github.a2g.core.platforms.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.github.a2g.core.interfaces.internal.IHostingPanel;
import com.github.a2g.core.interfaces.internal.IMasterPanelFromMasterPresenter;
import com.github.a2g.core.platforms.swing.dependencies.HostingPanelForSwing;
import com.github.a2g.core.primitive.ColorEnum;


@SuppressWarnings("serial")
public class MasterPanelForSwing
extends JPanel
implements IMasterPanelFromMasterPresenter
{
	HostingPanelForSwing hostForCommandLineF;
	HostingPanelForSwing hostForInventoryF;
	HostingPanelForSwing hostForVerbsF;
	HostingPanelForSwing hostForSceneF;
	HostingPanelForSwing hostForDialogTreeF;
	HostingPanelForSwing hostForLoadingF;
	HostingPanelForSwing hostForTitleCardF;
	GuiStateEnum GuiStateEnum;

	static final String LOADING_WIDGET = "LOADING_WIDGET";
	static final String SCENE_WIDGET = "SCENE_WIDGET";
	static final String TITLECARD_WIDGET = "TITLECARD_WIDGET";
	static final String DIALOGTREE_WIDGET = "TITLECARD_WIDGET";
	static final String COMMANDLINEVERBSINVENTORY_WIDGET = "COMMANDLINEVERBSINVENTORY_WIDGET";

	CardLayout sceneCardLayout;
	CardLayout dialogTreeCardLayout;
	JPanel panelForSceneStack;
	JPanel panelForDialogTreeStack;
	private HostingPanelForSwing sizePlaceholderForCommandLineF;
	private HostingPanelForSwing sizePlaceholderForVerbsF;
	private HostingPanelForSwing sizePlaceholderForInventoryF;

	public MasterPanelForSwing(int width, int height, ColorEnum back) {
		this.setBackground(new Color(back.r, back.g, back.b));
		
	
		// create all the host panels, that we want to arrange.
		hostForCommandLineF = new HostingPanelForSwing(); 
		hostForInventoryF = new HostingPanelForSwing(); 
		hostForVerbsF = new HostingPanelForSwing(); 
		hostForSceneF = new HostingPanelForSwing(); 
		hostForDialogTreeF = new HostingPanelForSwing(); 
		hostForLoadingF = new HostingPanelForSwing(); 
		hostForTitleCardF = new HostingPanelForSwing(); 

		
		sizePlaceholderForCommandLineF =  new HostingPanelForSwing(hostForCommandLineF);
		sizePlaceholderForCommandLineF.setPreferredSize(new Dimension(320,20));
		sizePlaceholderForVerbsF = new HostingPanelForSwing(hostForVerbsF);
		sizePlaceholderForInventoryF =  new HostingPanelForSwing(hostForInventoryF);
		
		
		sceneCardLayout =null;
		hostForDialogTreeF.setBackground(new Color(back.r, back.g, back.b));



		// will be constructed from two vertical stacks.
		panelForDialogTreeStack = new JPanel(); panelForDialogTreeStack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		panelForDialogTreeStack.setBackground(new Color(back.r, back.g, back.b));

		{
			// lay the CL/V/I panel and dialog tree - on top of each other
			JPanel commandLineAndVerbsAndInventoryF = new JPanel(); commandLineAndVerbsAndInventoryF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			commandLineAndVerbsAndInventoryF.setBackground(new Color(back.r, back.g, back.b));

			dialogTreeCardLayout = new CardLayout();
			//dialogTreeCardLayout.setHgap(0);
			//dialogTreeCardLayout.setVgap(0);

			panelForDialogTreeStack.setLayout(dialogTreeCardLayout);
			panelForDialogTreeStack.add(hostForDialogTreeF, MasterPanelForSwing.DIALOGTREE_WIDGET);
			panelForDialogTreeStack.add(commandLineAndVerbsAndInventoryF, MasterPanelForSwing.COMMANDLINEVERBSINVENTORY_WIDGET);

			{
				// layout the command line and the panel below it - vertically.
				JPanel verbsAndInventoryF = new JPanel();verbsAndInventoryF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				verbsAndInventoryF.setBackground(new Color(0,255,0));

				commandLineAndVerbsAndInventoryF.add(sizePlaceholderForCommandLineF);
				commandLineAndVerbsAndInventoryF.add(verbsAndInventoryF);

				{
					// layout the verbs and inventory - from left to right
					FlowLayout leftToRight = new FlowLayout(FlowLayout.LEFT,0,0);
					verbsAndInventoryF.setLayout(leftToRight);
					verbsAndInventoryF.add(sizePlaceholderForVerbsF, "hostForVerbs");
					verbsAndInventoryF.add(sizePlaceholderForInventoryF, "hostForInventory");
				}
			}
		}

		panelForSceneStack = new JPanel();panelForSceneStack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		//panelForSceneStack.setPreferredSize(new Dimension(320,180));
		{
			sceneCardLayout = new CardLayout();
			//sceneCardLayout.setHgap(0);
			//sceneCardLayout.setVgap(0);

			panelForSceneStack.setLayout(sceneCardLayout);
			panelForSceneStack.add(hostForSceneF, SCENE_WIDGET);
			panelForSceneStack.add(hostForLoadingF, LOADING_WIDGET);
			panelForSceneStack.add(hostForTitleCardF, TITLECARD_WIDGET);
		}

		// layout the scene at the top, and the rest at te bottom.
		BorderLayout topToBottom = new BorderLayout(2,1);
		topToBottom.setHgap(0);
		topToBottom.setVgap(0);
		this.setLayout(topToBottom);
		this.add(panelForSceneStack, BorderLayout.NORTH);
		this.add(panelForDialogTreeStack, BorderLayout.CENTER);


		this.setVisible(true);
	}

	@Override
	public IHostingPanel getHostForCommandLine() {
		return hostForCommandLineF;
	}

	@Override
	public IHostingPanel getHostForInventory() {
		return hostForInventoryF;
	}

	@Override
	public IHostingPanel getHostForVerbs()
	{
		return hostForVerbsF;
	}

	@Override
	public IHostingPanel getHostForScene() {
		return hostForSceneF;
	}

	@Override
	public IHostingPanel getHostForDialogTree() {
		return hostForDialogTreeF;
	}

	@Override
	public IHostingPanel getHostForLoading() {
		return hostForLoadingF;
	}

	@Override
	public IHostingPanel getHostForTitleCard(){
		return this.hostForTitleCardF;
	}

	@Override
	public void setActiveState(GuiStateEnum state) {
		this.GuiStateEnum = state;
		switch(state)
		{
		case DialogTree:
			this.hostForCommandLineF.setVisible(false);
			this.hostForDialogTreeF.setVisible(true);
			this.hostForInventoryF.setVisible(false);
			this.hostForLoadingF.setVisible(false);
			this.hostForSceneF.setVisible(true);
			this.hostForTitleCardF.setVisible(false);
			this.hostForVerbsF.setVisible(false);
			sceneCardLayout.show(panelForSceneStack, MasterPanelForSwing.SCENE_WIDGET);
			dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanelForSwing.DIALOGTREE_WIDGET);
			break;
		case OnEnterScene:
		case CutScene:
			this.hostForCommandLineF.setVisible(false);
			this.hostForDialogTreeF.setVisible(false);
			this.hostForInventoryF.setVisible(true);
			this.hostForLoadingF.setVisible(false);
			this.hostForSceneF.setVisible(true);
			this.hostForTitleCardF.setVisible(false);
			this.hostForVerbsF.setVisible(false);
			sceneCardLayout.show(panelForSceneStack, MasterPanelForSwing.SCENE_WIDGET);
			dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanelForSwing.LOADING_WIDGET);
			break;
		case ActiveScene:
			this.hostForCommandLineF.setVisible(true);
			this.hostForDialogTreeF.setVisible(false);
			this.hostForInventoryF.setVisible(true);
			this.hostForLoadingF.setVisible(false);
			this.hostForSceneF.setVisible(true);
			this.hostForTitleCardF.setVisible(false);
			this.hostForVerbsF.setVisible(true);
			sceneCardLayout.show(panelForSceneStack, MasterPanelForSwing.SCENE_WIDGET);
			dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanelForSwing.COMMANDLINEVERBSINVENTORY_WIDGET);
			break;
		case Loading:
			this.hostForCommandLineF.setVisible(false);
			this.hostForDialogTreeF.setVisible(false);
			this.hostForInventoryF.setVisible(false);
			this.hostForLoadingF.setVisible(true);
			this.hostForSceneF.setVisible(false);
			this.hostForTitleCardF.setVisible(false);
			this.hostForVerbsF.setVisible(false);
			sceneCardLayout.show(panelForSceneStack, MasterPanelForSwing.LOADING_WIDGET);
			break;
		case TitleCardOverOnEnterScene:
		case TitleCardOverActiveScene:
		case TitleCardOverCutScene:
		case TitleCardOverDialogTree:
		case TitleCardOverLoading:
			this.hostForCommandLineF.setVisible(false);
			this.hostForDialogTreeF.setVisible(false);
			this.hostForInventoryF.setVisible(false);
			this.hostForLoadingF.setVisible(false);
			this.hostForSceneF.setVisible(false);
			this.hostForTitleCardF.setVisible(true);
			this.hostForVerbsF.setVisible(false);
			dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanelForSwing.COMMANDLINEVERBSINVENTORY_WIDGET);
		}
	}

	@Override
	public GuiStateEnum getActiveState() {
		return GuiStateEnum;
	}

}




