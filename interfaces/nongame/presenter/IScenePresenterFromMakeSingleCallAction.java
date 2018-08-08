package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IAlignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByIndex;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.nongame.action.ISetAnimationAsObjectSpecial;
import com.github.a2g.core.interfaces.nongame.action.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationAndFrame;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetDisplayNameByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetInitialAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetSceneTalkerByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetValue;
import com.github.a2g.core.interfaces.nongame.action.ISetVisibleByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetXByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetYByOtid;
import com.github.a2g.core.interfaces.nongame.action.IShareWinning;

public interface IScenePresenterFromMakeSingleCallAction
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
, ISetAnimationAsObjectSpecial

{


}
