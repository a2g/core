package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformMasterPanel.GuiStateEnum;

public interface ISetActiveGuiState {
	IBaseChain setActiveGuiState(GuiStateEnum state);

}
