package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;

public interface IMoveCamera {
    IBaseChain moveCameraToNewXPosition(double x,double durationInSecs);
    IBaseChain moveCameraToNewXPositionNonBlocking(double x,double durationInSecs);
    IBaseChain moveCameraToNewYPosition(double y,double durationInSecs);
    IBaseChain moveCameraToNewYPositionNonBlocking(double y,double durationInSecs);
}
