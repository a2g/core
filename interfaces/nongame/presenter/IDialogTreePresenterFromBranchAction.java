package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformMasterPanel.GuiStateEnum;

public interface IDialogTreePresenterFromBranchAction {

	void addBranch(int subBranchId, String lineOfDialog,
			boolean isAddableAsSaidSpeech);
	void setGuiState(GuiStateEnum dialogtree);

}
