package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IAlignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid;
import com.github.a2g.core.interfaces.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.action.IGetOtidByIndex;
import com.github.a2g.core.interfaces.action.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.action.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.action.ISetAnimationAsObjectSpecial;
import com.github.a2g.core.interfaces.action.ISetAsACurrentAnimationByAtid;
import com.github.a2g.core.interfaces.action.ISetAsAnInitialAnimationByAtid;
import com.github.a2g.core.interfaces.action.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.action.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.action.ISetCurrentAnimationAndFrame;
import com.github.a2g.core.interfaces.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetDisplayNameByOtid;
import com.github.a2g.core.interfaces.action.ISetSceneTalkerByAtid;
import com.github.a2g.core.interfaces.action.ISetVisibleByOtid;
import com.github.a2g.core.interfaces.action.ISetXByOtid;
import com.github.a2g.core.interfaces.action.ISetYByOtid;
import com.github.a2g.core.interfaces.action.IShareWinning;
import com.github.a2g.core.interfaces.game.ISetValue;
import com.github.a2g.core.interfaces.game.ISwitchToScene;

public interface IScenePresenterFromSingleCallPerformer
extends
ISetBaseMiddleXByOtid
, ISetBaseMiddleYByOtid
, IGetOtidByCode 
, IAlignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid
, ISetCurrentFrameByOtid
, IGetOtidByIndex
, ISetVisibleByOtid
, IGetSceneObjectCount
, ISetAsACurrentAnimationByAtid 
, ISetDisplayNameByOtid
, ISetAsAnInitialAnimationByAtid
, ISetXByOtid
, ISetYByOtid 
, ISetValue
, IShareWinning
, ISetSceneTalkerByAtid
, ISetCurrentAnimationAndFrame
, IGetVisibleByOtid
, ISwitchToScene
, ISetAnimationAsObjectSpecial
{

	
}
