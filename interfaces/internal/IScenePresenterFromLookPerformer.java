package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentFrameByOtid;

public interface IScenePresenterFromLookPerformer
extends IGetOtidByCode 
, ISetCurrentAnimationByAtid 
, IGetCurrentFrameByOtid
, ISetCurrentFrameByOtid
, IGetAtidOfCurrentAnimationByOtid
, IGetOtidByAtid
{

}
