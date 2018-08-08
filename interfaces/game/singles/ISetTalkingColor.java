package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.github.a2g.core.primitive.ColorEnum;

public interface ISetTalkingColor {

	IChainBase  setTalkingColor(short ocode, ColorEnum color);
}
