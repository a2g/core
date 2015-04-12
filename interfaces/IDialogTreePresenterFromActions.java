package com.github.a2g.core.interfaces;

import com.github.a2g.core.objectmodel.DialogTree;
import com.google.gwt.event.shared.EventBus;

public interface IDialogTreePresenterFromActions extends
IDialogTreePresenterFromBranchAction,
IDialogTreePresenterFromDoBranchAction,
IDialogTreePresenterFromEndAction {
	void setDialogTreeVisible(boolean isInDialogTreeMode);

	void updateDialogTree(DialogTree theDialogTree, EventBus bus);

	void setScenePixelSize(int width, int height);

	void setDialogTreeTalkAnimation(String atid);

	String getDialogTreeTalkAnimation();
}
