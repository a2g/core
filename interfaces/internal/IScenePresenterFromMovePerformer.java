package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.internal.IGetScreenCoordsPerSecondByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetToInitialAnimationWithoutChangingFrameByOtid;

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
