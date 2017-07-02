package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.action.IGetOtidOfDefaultSceneObject;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.action.IGetSpecialAnimationByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetToInitialAnimationWithoutChangingFrameByOtid;

public interface IScenePresenterFromWalkAction
extends IGetBaseMiddleXByOtid,
IGetBaseMiddleYByOtid
, IGetSceneGuiWidth
, IGetSceneGuiHeight
, ISetToInitialAnimationWithoutChangingFrameByOtid
, ISetCurrentAnimationByAtid
, IGetSpecialAnimationByOtid
, IGetOtidOfDefaultSceneObject
, IGetOtidByCode
{

}
