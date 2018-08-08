package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformMasterPanel.GuiStateEnum;

public interface ISetActiveGuiState {
	IChainBase setActiveGuiState(GuiStateEnum state);

}
