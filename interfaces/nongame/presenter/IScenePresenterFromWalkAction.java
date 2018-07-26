package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidOfDefaultSceneObject;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.nongame.action.IGetSpecialAnimationByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetToInitialAnimationWithoutChangingFrameByOtid;

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
