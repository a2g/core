package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.action.IGetPath;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.action.IGetScreenCoordsPerSecondByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetSpecialAnimationByOtid;

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
