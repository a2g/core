package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidOfDefaultSceneObject;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.internal.IGetSpecialAnimationByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.internal.ISetToInitialAnimationWithoutChangingFrameByOtid;

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
