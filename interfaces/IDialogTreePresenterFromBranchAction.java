package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.IMasterPanelFromMasterPresenter.GuiStateEnum;

public interface IDialogTreePresenterFromBranchAction {

	void addBranch(int subBranchId, String lineOfDialog,
			boolean isAddableAsSaidSpeech);
	void setActiveGuiState(GuiStateEnum dialogtree);

}
