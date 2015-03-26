package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetCameraX;
import com.github.a2g.core.interfaces.action.IGetCameraY;
import com.github.a2g.core.interfaces.action.IGetCodeByOtid;
import com.github.a2g.core.interfaces.action.IGetDisplayNameByOtid;
import com.github.a2g.core.interfaces.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.action.IGetSceneGuiWidth;

public interface IScenePresenterFromSceneMouseOver extends IGetSceneGuiWidth,
		IGetSceneGuiHeight, IGetDisplayNameByOtid, IGetCodeByOtid, IGetCameraX,
		IGetCameraY {
}
