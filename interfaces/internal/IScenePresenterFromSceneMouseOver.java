package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.IGetCameraX;
import com.github.a2g.core.interfaces.methods.action.IGetCameraY;
import com.github.a2g.core.interfaces.methods.action.IGetCodeByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetDisplayNameByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiWidth;

public interface IScenePresenterFromSceneMouseOver extends IGetSceneGuiWidth,
IGetSceneGuiHeight, IGetDisplayNameByOtid, IGetCodeByOtid, IGetCameraX,
IGetCameraY {
}
