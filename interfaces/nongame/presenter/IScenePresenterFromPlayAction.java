package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetDurationByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetVisibleByOtid;

public interface IScenePresenterFromPlayAction extends
IGetNumberOfFramesByAtid, IGetOtidByAtid, IGetDurationByAtid,
ISetCurrentFrameByOtid, ISetCurrentAnimationByAtid,
ISetToInitialAnimationWithoutChangingFrameByOtid, ISetVisibleByOtid // repeat
// whilst
// visible
// only
{

}
