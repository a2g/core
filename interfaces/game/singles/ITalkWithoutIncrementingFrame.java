package com.github.a2g.core.interfaces.game.singles;

 
import com.github.a2g.core.interfaces.game.chainables.IBaseChain;

public interface ITalkWithoutIncrementingFrame {
    IBaseChain talkWithoutIncrementingFrame(String animCode, String speech);
    IBaseChain talkWithoutIncrementingFrameNonBlocking(String animCode, String speech);
    IBaseChain talkWithoutIncrementingFrame(String speech);
    IBaseChain talkWithoutIncrementingFrameNonBlocking(String speech);
}
