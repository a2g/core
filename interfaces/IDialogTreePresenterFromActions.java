package com.github.a2g.core.interfaces;

import com.github.a2g.core.objectmodel.DialogTree;

public interface IDialogTreePresenterFromActions extends
IDialogTreePresenterFromBranchAction,
IDialogTreePresenterFromDoBranchAction,
IDialogTreePresenterFromEndAction {
	void setDialogTreeVisible(boolean isInDialogTreeMode);

	void updateDialogTree(DialogTree theDialogTree);

	void setScenePixelSize(int width, int height);

	void setDialogTreeTalkAnimation(String atid);

	String getDialogTreeTalkAnimation();
}
