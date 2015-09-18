package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.ICurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.action.IGetSpecialAnimationByOtid;

public interface IScenePresenterFromWalkPerformer
extends IGetOtidByCode
, IGetSceneGuiWidth
, IGetSceneGuiHeight
, IGetSpecialAnimationByOtid
, ICurrentAnimationByAtid
{

}
