package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IGetDurationByAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByAtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetVisibleByOtid;

public interface IScenePresenterFromPlayAction extends
IGetNumberOfFramesByAtid, IGetOtidByAtid, IGetDurationByAtid,
ISetCurrentFrameByOtid, ISetCurrentAnimationByAtid,
ISetToInitialAnimationWithoutChangingFrameByOtid, ISetVisibleByOtid // repeat
// whilst
// visible
// only
{

}
