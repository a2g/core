package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;

public interface ISetDefaultSceneObject {
    IChainBase  setDefaultSceneObject(short ocode);
    IChainBase  setDefaultSceneObject(short ocode, boolean isUsingEdgePerimeterDetection);
}
