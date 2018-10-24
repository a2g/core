package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;

public interface ITalk {
    IChainBase talk(String animCode, String speech);
    IChainBase talk(String speech);
}
