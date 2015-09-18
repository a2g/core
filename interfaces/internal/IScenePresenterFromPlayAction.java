package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.ICurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetDurationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByAtid;
import com.github.a2g.core.interfaces.methods.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetVisibleByOtid;

public interface IScenePresenterFromPlayAction extends
IGetNumberOfFramesByAtid, IGetOtidByAtid, IGetDurationByAtid,
ISetCurrentFrameByOtid, ICurrentAnimationByAtid,
ISetToInitialAnimationWithoutChangingFrameByOtid, ISetVisibleByOtid // repeat
// whilst
// visible
// only
{

}
