package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.action.ChainEndAction; 

public interface ISwitchToScene {
	ChainEndAction switchToScene(String scene, int arrivalSegment);
}
