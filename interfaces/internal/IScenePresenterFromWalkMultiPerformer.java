package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.internal.IGetPath;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.internal.IGetScreenCoordsPerSecondByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetSpecialAnimationByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentAnimationByAtid;

public interface IScenePresenterFromWalkMultiPerformer
extends IGetOtidByCode
, IGetSceneGuiWidth
, IGetSceneGuiHeight
, IGetSpecialAnimationByOtid
, ISetCurrentAnimationByAtid
, IGetPath
, IGetBaseMiddleXByOtid
, IGetBaseMiddleYByOtid
, IGetScreenCoordsPerSecondByOtid
{

	 
	 
}
