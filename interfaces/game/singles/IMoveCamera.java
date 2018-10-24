package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;

public interface IMoveCamera {
    IChainBase moveCameraToNewXPosition(double x,double durationInSecs);
    IChainBase moveCameraToNewXPositionNonBlocking(double x,double durationInSecs);
    IChainBase moveCameraToNewYPosition(double y,double durationInSecs);
    IChainBase moveCameraToNewYPositionNonBlocking(double y,double durationInSecs);
}
