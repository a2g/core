package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.ICurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.action.IGetScreenCoordsPerSecondByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetScaleOnCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetToInitialAnimationWithoutChangingFrameByOtid;

public interface IScenePresenterFromScalePerformer
extends IGetOtidByCode
, ISetScaleOnCurrentFrameByOtid
{

}
