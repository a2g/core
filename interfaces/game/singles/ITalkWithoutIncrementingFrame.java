package com.github.a2g.core.interfaces.game.singles;

 
import com.github.a2g.core.interfaces.game.chainables.IChainBase;

public interface ITalkWithoutIncrementingFrame {
    IChainBase talkWithoutIncrementingFrame(String animCode, String speech);
    IChainBase talkWithoutIncrementingFrameNonBlocking(String animCode, String speech);
    IChainBase talkWithoutIncrementingFrame(String speech);
    IChainBase talkWithoutIncrementingFrameNonBlocking(String speech);
}
