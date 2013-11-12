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

package com.github.a2g.core.platforms.java.panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI;
import com.github.a2g.core.primitive.ColorEnum;
import com.github.a2g.core.platforms.java.HostingPanelForJava;


@SuppressWarnings("serial")
public class MasterPanelForJava
extends JPanel
implements MasterPanelAPI
{
	HostingPanelForJava hostForCommandLineF;
	HostingPanelForJava hostForInventoryF;
	HostingPanelForJava hostForVerbsF;
	HostingPanelForJava hostForSceneF;
	HostingPanelForJava hostForDialogTreeF;
	HostingPanelForJava hostForLoadingF;
	HostingPanelForJava hostForTitleCardF;
	GuiStateEnum guiStateEnum;

	static final String LOADING_WIDGET = "LOADING_WIDGET";
	static final String SCENE_WIDGET = "SCENE_WIDGET";
	static final String TITLECARD_WIDGET = "TITLECARD_WIDGET";
	static final String DIALOGTREE_WIDGET = "TITLECARD_WIDGET";
	static final String COMMANDLINEVERBSINVENTORY_WIDGET = "COMMANDLINEVERBSINVENTORY_WIDGET";

	CardLayout sceneCardLayout;
	CardLayout dialogTreeCardLayout;
	JPanel panelForSceneStack;
	JPanel panelForDialogTreeStack;

	public MasterPanelForJava(int width, int height, ColorEnum back) {
		this.setBackground(new Color(back.r, back.g, back.b));
		// create all the host panels, that we want to arrange.
		hostForCommandLineF = new HostingPanelForJava(); hostForCommandLineF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		hostForInventoryF = new HostingPanelForJava(); hostForInventoryF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		hostForVerbsF = new HostingPanelForJava(); hostForVerbsF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		hostForSceneF = new HostingPanelForJava(); hostForSceneF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		hostForDialogTreeF = new HostingPanelForJava(); hostForDialogTreeF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		hostForLoadingF = new HostingPanelForJava(); hostForLoadingF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		hostForTitleCardF = new HostingPanelForJava(); hostForTitleCardF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

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
			panelForDialogTreeStack.add(hostForDialogTreeF, MasterPanelForJava.DIALOGTREE_WIDGET);
			panelForDialogTreeStack.add(commandLineAndVerbsAndInventoryF, MasterPanelForJava.COMMANDLINEVERBSINVENTORY_WIDGET);

			{
				// layout the command line and the panel below it - vertically.
				JPanel verbsAndInventoryF = new JPanel();verbsAndInventoryF.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
				verbsAndInventoryF.setBackground(new Color(0,255,0));

				commandLineAndVerbsAndInventoryF.add(hostForCommandLineF);
				commandLineAndVerbsAndInventoryF.add(verbsAndInventoryF);

				{
					// layout the verbs and inventory - from left to right
					FlowLayout leftToRight = new FlowLayout(FlowLayout.LEFT,0,0);
					verbsAndInventoryF.setLayout(leftToRight);
					verbsAndInventoryF.add(hostForVerbsF, "hostForVerbs");
					verbsAndInventoryF.add(hostForInventoryF, "hostForInventory");
				}
			}
		}

		panelForSceneStack = new JPanel();panelForSceneStack.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

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
		GridLayout topToBottom = new GridLayout(2,1);
		topToBottom.setHgap(0);
		topToBottom.setVgap(0);
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
	public void setActiveState(GuiStateEnum state) {
		this.guiStateEnum = state;
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
			sceneCardLayout.show(panelForSceneStack, MasterPanelForJava.SCENE_WIDGET);
			dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanelForJava.DIALOGTREE_WIDGET);
			break;
		case OnEnterScene:
		case CutScene:
			this.hostForCommandLineF.setVisible(false);
			this.hostForDialogTreeF.setVisible(false);
			this.hostForInventoryF.setVisible(false);
			this.hostForLoadingF.setVisible(false);
			this.hostForSceneF.setVisible(true);
			this.hostForTitleCardF.setVisible(false);
			this.hostForVerbsF.setVisible(false);
			sceneCardLayout.show(panelForSceneStack, MasterPanelForJava.SCENE_WIDGET);
			dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanelForJava.LOADING_WIDGET);
			break;
		case ActiveScene:
			this.hostForCommandLineF.setVisible(true);
			this.hostForDialogTreeF.setVisible(false);
			this.hostForInventoryF.setVisible(true);
			this.hostForLoadingF.setVisible(false);
			this.hostForSceneF.setVisible(true);
			this.hostForTitleCardF.setVisible(false);
			this.hostForVerbsF.setVisible(true);
			sceneCardLayout.show(panelForSceneStack, MasterPanelForJava.SCENE_WIDGET);
			dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanelForJava.COMMANDLINEVERBSINVENTORY_WIDGET);
			break;
		case Loading:
			this.hostForCommandLineF.setVisible(false);
			this.hostForDialogTreeF.setVisible(false);
			this.hostForInventoryF.setVisible(false);
			this.hostForLoadingF.setVisible(true);
			this.hostForSceneF.setVisible(false);
			this.hostForTitleCardF.setVisible(false);
			this.hostForVerbsF.setVisible(false);
			sceneCardLayout.show(panelForSceneStack, MasterPanelForJava.LOADING_WIDGET);
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
			dialogTreeCardLayout.show(panelForDialogTreeStack, MasterPanelForJava.COMMANDLINEVERBSINVENTORY_WIDGET);
		}
	}

	@Override
	public GuiStateEnum getActiveState() {
		return guiStateEnum;
	}

}



