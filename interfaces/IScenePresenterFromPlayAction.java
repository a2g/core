package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetDurationByAtid;
import com.github.a2g.core.interfaces.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.action.IGetOtidByAtid;
import com.github.a2g.core.interfaces.action.ICurrentAnimationByAtid;
import com.github.a2g.core.interfaces.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetVisibleByOtid;

public interface IScenePresenterFromPlayAction extends
IGetNumberOfFramesByAtid, IGetOtidByAtid, IGetDurationByAtid,
ISetCurrentFrameByOtid, ICurrentAnimationByAtid,
ISetToInitialAnimationWithoutChangingFrameByOtid, ISetVisibleByOtid // repeat
// whilst
// visible
// only
{

}
