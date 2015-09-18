package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.internal.IMasterPanelFromMasterPresenter.GuiStateEnum;

public interface IDialogTreePresenterFromBranchAction {

	void addBranch(int subBranchId, String lineOfDialog,
			boolean isAddableAsSaidSpeech);
	void setActiveGuiState(GuiStateEnum dialogtree);

}
