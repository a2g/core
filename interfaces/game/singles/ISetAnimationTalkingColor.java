package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.primitive.ColorEnum;

public interface ISetAnimationTalkingColor {

	IBaseChain  setAnimationTalkingColor(String atid, ColorEnum color);
}
