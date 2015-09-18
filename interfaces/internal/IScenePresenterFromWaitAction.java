package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByCode;

public interface IScenePresenterFromWaitAction extends
IGetAtidOfCurrentAnimationByOtid, IGetNumberOfFramesByAtid,
IGetCurrentFrameByOtid, IGetOtidByCode {

}
