package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByCode;

public interface IScenePresenterFromWaitAction extends
IGetAtidOfCurrentAnimationByOtid, IGetNumberOfFramesByAtid,
IGetCurrentFrameByOtid, IGetOtidByCode {

}
