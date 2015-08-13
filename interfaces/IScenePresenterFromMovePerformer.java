package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.action.IGetScreenCoordsPerSecondByOtid;
import com.github.a2g.core.interfaces.action.ICurrentAnimationByAtid;
import com.github.a2g.core.interfaces.action.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.action.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetToInitialAnimationWithoutChangingFrameByOtid;

public interface IScenePresenterFromMovePerformer
extends IGetOtidByCode

, IGetAtidOfCurrentAnimationByOtid
, IGetBaseMiddleXByOtid
, IGetBaseMiddleYByOtid
, IGetScreenCoordsPerSecondByOtid
, ICurrentAnimationByAtid
, IGetNumberOfFramesByAtid
, ISetBaseMiddleXByOtid
, ISetBaseMiddleYByOtid
, ISetToInitialAnimationWithoutChangingFrameByOtid
, ISetCurrentFrameByOtid
{

}
