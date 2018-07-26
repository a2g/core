package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.IGetPath;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.nongame.action.IGetScreenCoordsPerSecondByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetSpecialAnimationByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationByAtid;

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
