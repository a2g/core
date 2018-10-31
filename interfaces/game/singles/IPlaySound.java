package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;

public interface IPlaySound {
    IBaseChain playSound(String stid);
    IBaseChain playSoundNonBlocking(String stid);
}
