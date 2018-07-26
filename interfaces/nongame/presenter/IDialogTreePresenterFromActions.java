package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.objectmodel.DialogTree;

/**  
 * 
 * @author Admin
 *
 */
public interface IDialogTreePresenterFromActions extends
IDialogTreePresenterFromBranchAction,
IDialogTreePresenterFromDoBranchAction,
IDialogTreePresenterFromEndAction {
	void setDialogTreeVisible(boolean isInDialogTreeMode);

	void updateDialogTree(DialogTree theDialogTree);

	void setScenePixelSize(int width, int height);
}
