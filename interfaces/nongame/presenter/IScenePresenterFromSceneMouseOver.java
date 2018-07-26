package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetCameraX;
import com.github.a2g.core.interfaces.nongame.action.IGetCameraY;
import com.github.a2g.core.interfaces.nongame.action.IGetCodeByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetDisplayNameByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiWidth;

public interface IScenePresenterFromSceneMouseOver extends IGetSceneGuiWidth,
IGetSceneGuiHeight, IGetDisplayNameByOtid, IGetCodeByOtid, IGetCameraX,
IGetCameraY {
}
