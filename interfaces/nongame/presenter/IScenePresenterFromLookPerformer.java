package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentFrameByOtid;

public interface IScenePresenterFromLookPerformer
extends IGetOtidByCode 
, ISetCurrentAnimationByAtid 
, IGetCurrentFrameByOtid
, ISetCurrentFrameByOtid
, IGetAtidOfCurrentAnimationByOtid
, IGetOtidByAtid
{

}
