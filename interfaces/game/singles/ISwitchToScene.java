package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.chain.SceneChainEnd; 

public interface ISwitchToScene {
	SceneChainEnd switchToScene(String scene, int entrySegment);
}
