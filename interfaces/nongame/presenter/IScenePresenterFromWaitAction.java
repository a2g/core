package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;

public interface IScenePresenterFromWaitAction extends
IGetAtidOfCurrentAnimationByOtid, IGetNumberOfFramesByAtid,
IGetCurrentFrameByOtid, IGetOtidByCode {

}
