package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.action.IGetOtidByCode;

public interface IScenePresenterFromWaitAction extends
		IGetAtidOfCurrentAnimationByOtid, IGetNumberOfFramesByAtid,
		IGetCurrentFrameByOtid, IGetOtidByCode {

}
