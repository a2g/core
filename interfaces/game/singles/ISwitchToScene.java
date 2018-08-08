package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;

public interface ISwitchToScene {
	IChainBase  switchToScene(String scene, int arrivalSegment);
}
