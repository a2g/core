package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI.WalkDirection;

public interface ISetAnimationAsObjectWalkDirection {
	IChainBase setAnimationAsObjectWalkDirection(String atid, WalkDirection type);

}
