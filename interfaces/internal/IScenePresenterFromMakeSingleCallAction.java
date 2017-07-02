package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.IAlignBaseMiddleOfOldFrameToFrameOfThisAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByIndex;
import com.github.a2g.core.interfaces.methods.action.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.methods.action.ISetAnimationAsObjectSpecial;
import com.github.a2g.core.interfaces.methods.action.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetCurrentAnimationAndFrame;
import com.github.a2g.core.interfaces.methods.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetDisplayNameByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetInitialAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.ISetSceneTalkerByAtid;
import com.github.a2g.core.interfaces.methods.action.ISetVisibleByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetXByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetYByOtid;
import com.github.a2g.core.interfaces.methods.action.IShareWinning;
import com.github.a2g.core.interfaces.methods.game.ISetValue;

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
