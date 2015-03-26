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


import java.util.HashSet;
import java.util.Set;

import com.github.a2g.core.interfaces.IDialogTreePanelFromDialogTreePresenter;
import com.github.a2g.core.interfaces.IHostingPanel;
import com.github.a2g.core.interfaces.IMasterPresenterFromDialogTree;
import com.github.a2g.core.interfaces.IDialogTreePresenter;
import com.github.a2g.core.primitive.ColorEnum;
//import com.google.gwt.dev.util.collect.HashSet;
import com.google.gwt.event.shared.EventBus;


public class DialogTreePresenter implements IDialogTreePresenter
{
	private EventBus bus;
	private DialogTree theDialogTree;
	private IDialogTreePanelFromDialogTreePresenter view;
	private String atidOfDialogTreeTalkAnimation;
	private Set<String> recordOfSaidSpeech;
	private IMasterPresenterFromDialogTree callbacks;


	public DialogTreePresenter(final IHostingPanel panel, EventBus bus, IMasterPresenterFromDialogTree callbacks) {
		this.bus = bus;
		this.callbacks = callbacks;
		this.theDialogTree = new DialogTree();
		this.view = callbacks.createDialogTreePanel(bus, ColorEnum.Purple, ColorEnum.Black, ColorEnum.Red);
		panel.setThing(view);
		recordOfSaidSpeech = new HashSet<String>();
	}

	public void clearBranches() {
		theDialogTree.clear();
		view.update(theDialogTree, bus);
	}

	public void resetRecordOfSaidSpeech()
	{
		recordOfSaidSpeech.clear();
	}


	@Override
	public void addBranch(int subBranchId, String lineOfDialog, boolean isAlwaysShown) {
		if(isAlwaysShown || !recordOfSaidSpeech.contains(lineOfDialog))
		{
			theDialogTree.addSubBranch(subBranchId, lineOfDialog);
			view.update(theDialogTree, bus);
		}
	}


	@Override
	public void setDialogTreeTalkAnimation(String atid) {
		this.atidOfDialogTreeTalkAnimation = atid;

	}

	@Override
	public String getDialogTreeTalkAnimation() {
		return this.atidOfDialogTreeTalkAnimation;
	}

	
	public IDialogTreePanelFromDialogTreePresenter getView()
	{
		return view;
	}

	public void markSpeechAsSaid(String text)
	{
		recordOfSaidSpeech.add(text);
	}

	@Override
	public void setDialogTreeVisible(boolean isInDialogTreeMode) {
		view.setVisible(isInDialogTreeMode);
	}

	@Override
	public void updateDialogTree(DialogTree theDialogTree, EventBus bus) {
		view.update(theDialogTree, bus);

	}

	@Override
	public void executeBranchOnCurrentScene(int branchId) {
		callbacks.executeBranchOnCurrentScene(branchId);

	}

	@Override
	public void setDialogTreeActive(boolean isActive) {
		callbacks.setDialogTreeActive(isActive);

	}

	@Override
	public void setScenePixelSize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
