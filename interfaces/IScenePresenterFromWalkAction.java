package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.action.IGetOtidOfDefaultSceneObject;
import com.github.a2g.core.interfaces.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.action.IGetSpecialAnimationByOtid;
import com.github.a2g.core.interfaces.action.ISetAsACurrentAnimationByAtid;
import com.github.a2g.core.interfaces.action.ISetToInitialAnimationWithoutChangingFrameByOtid;

public interface IScenePresenterFromWalkAction
extends IGetBaseMiddleXByOtid,
IGetBaseMiddleYByOtid
, IGetSceneGuiWidth
, IGetSceneGuiHeight
, ISetToInitialAnimationWithoutChangingFrameByOtid
, ISetAsACurrentAnimationByAtid
, IGetSpecialAnimationByOtid 
, IGetOtidOfDefaultSceneObject
, IGetOtidByCode
{

}
