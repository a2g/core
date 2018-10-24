package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;

public interface IPlaySound {
    IChainBase playSound(String stid);
    IChainBase playSoundNonBlocking(String stid);
}
