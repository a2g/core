package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.action.ChainableAction;
import com.github.a2g.core.interfaces.ConstantsForAPI.WalkDirection;

public interface ISetAnimationAsObjectWalkDirection {
	ChainableAction setAnimationAsObjectWalkDirection(String atid, WalkDirection type);

}
