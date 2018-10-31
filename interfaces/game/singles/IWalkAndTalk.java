package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;

public interface IWalkAndTalk {
    IBaseChain walkAndTalkNeverSwitch(short o, double x, double y, String speech);
}
