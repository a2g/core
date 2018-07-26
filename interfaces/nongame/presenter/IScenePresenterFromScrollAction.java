package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetCameraX;
import com.github.a2g.core.interfaces.nongame.action.IGetCameraY;
import com.github.a2g.core.interfaces.nongame.action.ISetCameraX;
import com.github.a2g.core.interfaces.nongame.action.ISetCameraY;

public interface IScenePresenterFromScrollAction extends IGetCameraX,
IGetCameraY, ISetCameraX, ISetCameraY {

}
