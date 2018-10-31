package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;

public interface ISetDefaultSceneObject {
    IBaseChain  setDefaultSceneObject(short ocode);
    IBaseChain  setDefaultSceneObject(short ocode, boolean isUsingEdgePerimeterDetection);
}
