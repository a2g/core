package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IGetCameraX;
import com.github.a2g.core.interfaces.methods.internal.IGetCameraY;
import com.github.a2g.core.interfaces.methods.internal.IGetCodeByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetDisplayNameByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiWidth;

public interface IScenePresenterFromSceneMouseOver extends IGetSceneGuiWidth,
IGetSceneGuiHeight, IGetDisplayNameByOtid, IGetCodeByOtid, IGetCameraX,
IGetCameraY {
}
