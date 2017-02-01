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

import com.github.a2g.core.interfaces.internal.IDialogTreePanelFromDialogTreePresenter;
import com.github.a2g.core.interfaces.internal.IDialogTreePresenter;
import com.github.a2g.core.interfaces.internal.IHostingPanel;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromDialogTree;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public class DialogTreePresenter
implements IDialogTreePresenter {
	private DialogTree theDialogTree;
	private IDialogTreePanelFromDialogTreePresenter view;
	private Set<String> recordOfSaidSpeech;

	public DialogTreePresenter(final IHostingPanel panel, EventBus bus,
			IMasterPresenterFromDialogTree callbacks) {
		this.theDialogTree = new DialogTree();
		this.view = callbacks.createDialogTreePanel(bus, ColorEnum.Purple,
				ColorEnum.Black, ColorEnum.Red);
		panel.setThing(view);
		recordOfSaidSpeech = new HashSet<String>();
	}

	public void clearBranches() {
		theDialogTree.clear();
		view.update(theDialogTree);
	}

	public void resetRecordOfSaidSpeech() {
		recordOfSaidSpeech.clear();
	}

	public IDialogTreePanelFromDialogTreePresenter getView() {
		return view;
	}

	public void addSpeechToSaidList(String text) {
		recordOfSaidSpeech.add(text);
	}

	//@Override
	public void addBranch(int subBranchId, String lineOfDialog,
			boolean isAddableToSaidList) {
		if (!recordOfSaidSpeech.contains(lineOfDialog)) {
			theDialogTree.addSubBranch(subBranchId, lineOfDialog, isAddableToSaidList);
			view.update(theDialogTree);
		}
	}


	public void setDialogTreeVisible(boolean isInDialogTreeMode) {
		view.setVisible(isInDialogTreeMode);
	}

	public void updateDialogTree(DialogTree theDialogTree) {
		view.update(theDialogTree);
	}

	String getLineOfDialogForId(int id)
	{
		return theDialogTree.getDialogForId(id);
	}

	int getNumberOfVisibleBranches()
	{
		int size = theDialogTree.getLinesOfDialog().size();
		return size;
	}

	public void setScenePixelSize(int width, int height) {
		view.setWidthAndHeight(width, height);
	}

	public boolean isAddableToSaidList(int branchId) {
		return theDialogTree.isAddableToSaidList(branchId);
	}

	public boolean isBranchValid(int branchId) {
		return getLineOfDialogForId(branchId).length()>0;
	}


}
