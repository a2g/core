package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.action.ISetCurrentFrameByOtid;

public interface IScenePresenterFromLookPerformer
extends IGetOtidByCode 
, ISetCurrentAnimationByAtid 
, IGetCurrentFrameByOtid
, ISetCurrentFrameByOtid
, IGetAtidOfCurrentAnimationByOtid
, IGetOtidByAtid
{

}
