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
import com.github.a2g.core.interfaces.IHostingPanel;
import com.github.a2g.core.interfaces.IMasterPanelFromMasterPresenter;
import com.github.a2g.core.platforms.html4.HostingPanelForHtml4;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class MasterPanel
extends VerticalPanel
implements IMasterPanelFromMasterPresenter
{
	HostingPanelForHtml4 hostForCommandLine;
	HostingPanelForHtml4 hostForInventory;
	HostingPanelForHtml4 hostForVerbs;
	HostingPanelForHtml4 hostForScene;
	HostingPanelForHtml4 hostForDialogTree;
	HostingPanelForHtml4 hostForLoading;
	HostingPanelForHtml4 hostForTitleCard;
	GuiStateEnum state;

	public MasterPanel(int width, int height, ColorEnum back) {

		// create all the host panels, that we want to arrange.
		hostForCommandLine = new HostingPanelForHtml4();
		hostForInventory = new HostingPanelForHtml4();
		hostForVerbs = new HostingPanelForHtml4();
		hostForScene = new HostingPanelForHtml4();
		hostForDialogTree = new HostingPanelForHtml4();
		hostForLoading = new HostingPanelForHtml4();
		hostForTitleCard = new HostingPanelForHtml4();

		// will be constructed from two vertical stacks.
		AbsolutePanel stackForSceneAndLoading = new AbsolutePanel();
		AbsolutePanel stackForDialogTreeInvAndCommand = new AbsolutePanel();

		stackForDialogTreeInvAndCommand.getElement().getStyle().setProperty("backgroundColor", back.toString());
		stackForSceneAndLoading.getElement().getStyle().setProperty("backgroundColor", back.toString());



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
	public IHostingPanel getHostForCommandLine() {
		return hostForCommandLine;
	}


	@Override
	public IHostingPanel getHostForInventory() {
		return hostForInventory;
	}

	@Override
	public IHostingPanel getHostForVerbs() {
		return hostForVerbs;
	}


	@Override
	public IHostingPanel getHostForScene() {
		return hostForScene;
	}


	@Override
	public IHostingPanel getHostForDialogTree() {
		return hostForDialogTree;
	}

	@Override
	public IHostingPanel getHostForLoading() {
		return hostForLoading;
	}



	@Override
	public IHostingPanel getHostForTitleCard() {
		return hostForTitleCard;
	}

	@Override
	public void setActiveState(GuiStateEnum state)
	{
		this.state = state;

		switch(state)
		{
		case DialogTree:
			hostForDialogTree.setVisible(true);
			hostForScene.setVisible(true);
			hostForLoading.setVisible(false);
			hostForTitleCard.setVisible(false);
			hostForCommandLine.setVisible(false);
			hostForInventory.setVisible(false);
			hostForVerbs.setVisible(false);
			break;
		case CutScene:
		case OnEnterScene:
			hostForScene.setVisible(true);
			hostForDialogTree.setVisible(false);
			hostForLoading.setVisible(false);
			hostForTitleCard.setVisible(false);
			hostForCommandLine.setVisible(false);
			hostForInventory.setVisible(true);
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
		case TitleCardOverOnEnterScene:
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
