package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetDurationByAtid;
import com.github.a2g.core.interfaces.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.action.IGetOtidOfAtid;
import com.github.a2g.core.interfaces.action.ISetAsACurrentAnimationByAtid;
import com.github.a2g.core.interfaces.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetVisibleByOtid;

public interface IScenePresenterFromPlayAction 
extends 
IGetNumberOfFramesByAtid
,IGetOtidOfAtid
,IGetDurationByAtid
,ISetCurrentFrameByOtid
,ISetAsACurrentAnimationByAtid
,ISetToInitialAnimationWithoutChangingFrameByOtid
,ISetVisibleByOtid //repeat whilst visible only
{

}

