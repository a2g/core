package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.IGetScreenCoordsPerSecondByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetToInitialAnimationWithoutChangingFrameByOtid;

public interface IScenePresenterFromMovePerformer
extends IGetOtidByCode

, IGetAtidOfCurrentAnimationByOtid
, IGetBaseMiddleXByOtid
, IGetBaseMiddleYByOtid
, IGetScreenCoordsPerSecondByOtid
, ISetCurrentAnimationByAtid
, IGetNumberOfFramesByAtid
, ISetBaseMiddleXByOtid
, ISetBaseMiddleYByOtid
, ISetToInitialAnimationWithoutChangingFrameByOtid
, ISetCurrentFrameByOtid
{

}
