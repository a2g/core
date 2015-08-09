package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.action.IGetSpecialAnimationByOtid;
import com.github.a2g.core.interfaces.action.ICurrentAnimationByAtid;

public interface IScenePresenterFromWalkPerformer
extends IGetOtidByCode
, IGetSceneGuiWidth
, IGetSceneGuiHeight
, IGetSpecialAnimationByOtid
, ICurrentAnimationByAtid
{

}
