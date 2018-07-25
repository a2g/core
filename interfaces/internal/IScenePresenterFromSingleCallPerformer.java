package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.direct.IDisplayTitleCard;
import com.github.a2g.core.interfaces.methods.direct.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.methods.direct.ISetValue;
import com.github.a2g.core.interfaces.methods.direct.ISwitchToScene;
import com.github.a2g.core.interfaces.methods.internal.IAlignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByIndex;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.methods.internal.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetAnimationAsObjectSpecial;
import com.github.a2g.core.interfaces.methods.internal.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentAnimationAndFrame;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetDisplayNameByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetHeadRectangleByIndex;
import com.github.a2g.core.interfaces.methods.internal.ISetInitialAnimationByAtid;
import com.github.a2g.core.interfaces.methods.internal.ISetSceneTalkerByAtid;
import com.github.a2g.core.interfaces.methods.internal.ISetSoundtrack;
import com.github.a2g.core.interfaces.methods.internal.ISetVisibleByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetXByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetYByOtid;
import com.github.a2g.core.interfaces.methods.internal.IShareWinning;

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
, IDisplayTitleCard
{
	 

	
}
