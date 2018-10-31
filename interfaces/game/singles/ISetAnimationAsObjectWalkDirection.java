package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI.WalkDirection;

public interface ISetAnimationAsObjectWalkDirection {
	IBaseChain setAnimationAsObjectWalkDirection(String atid, WalkDirection type);

}
