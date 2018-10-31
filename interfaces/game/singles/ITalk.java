package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;

public interface ITalk {
    IBaseChain talk(String animCode, String speech);
    IBaseChain talk(String speech);
}
