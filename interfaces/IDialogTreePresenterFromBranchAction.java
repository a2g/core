package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.IMasterPanelFromMasterPresenter.GuiStateEnum;

public interface IDialogTreePresenterFromBranchAction {

	void addBranch(int branchId, String text, boolean isAlwaysPresent);

	void setActiveGuiState(GuiStateEnum dialogtree);

}
