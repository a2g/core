package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.nongame.action.IGetSpecialAnimationByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationByAtid;

public interface IScenePresenterFromWalkPerformer
extends IGetOtidByCode
, IGetSceneGuiWidth
, IGetSceneGuiHeight
, IGetSpecialAnimationByOtid
, ISetCurrentAnimationByAtid
{

}
