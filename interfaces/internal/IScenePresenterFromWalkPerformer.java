package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.internal.IGetSpecialAnimationByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentAnimationByAtid;

public interface IScenePresenterFromWalkPerformer
extends IGetOtidByCode
, IGetSceneGuiWidth
, IGetSceneGuiHeight
, IGetSpecialAnimationByOtid
, ISetCurrentAnimationByAtid
{

}
