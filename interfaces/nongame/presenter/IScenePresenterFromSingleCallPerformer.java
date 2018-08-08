package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IAlignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByIndex;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.nongame.action.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetAnimationAsObjectSpecial;
import com.github.a2g.core.interfaces.nongame.action.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationAndFrame;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetDisplayNameByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetHeadRectangleByIndex;
import com.github.a2g.core.interfaces.nongame.action.ISetInitialAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetSceneTalkerByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetSoundtrack;
import com.github.a2g.core.interfaces.nongame.action.ISetVisibleByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetXByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetYByOtid;
import com.github.a2g.core.interfaces.nongame.action.IShareWinning;
import com.github.a2g.core.interfaces.nongame.action.ISetValue;
import com.github.a2g.core.interfaces.nongame.action.ISwitchToScene;
import com.github.a2g.core.interfaces.nongame.action.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.nongame.action.ISetTitleCard;

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
, ISetCurrentAnimationByAtid
, ISetDisplayNameByOtid
, ISetInitialAnimationByAtid
, ISetXByOtid
, ISetYByOtid
, ISetValue
, IShareWinning
, ISetSceneTalkerByAtid
, ISetCurrentAnimationAndFrame
, IGetVisibleByOtid
, ISwitchToScene
, ISetAnimationAsObjectSpecial
, ISetAnimationAsObjectInitial
, ISetSoundtrack
, ISetHeadRectangleByIndex
, ISetTitleCard
{
	 

	
}
