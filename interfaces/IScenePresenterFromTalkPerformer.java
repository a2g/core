package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetAtidOfDefaultSayAnim;
import com.github.a2g.core.interfaces.action.IGetDurationByAtid;
import com.github.a2g.core.interfaces.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.action.IGetOtidOfAtid;
import com.github.a2g.core.interfaces.action.ISetAsACurrentAnimationByAtid;
import com.github.a2g.core.interfaces.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetStateOfPopup;
import com.github.a2g.core.interfaces.action.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetVisibleByOtid;

public interface IScenePresenterFromTalkPerformer
extends IGetNumberOfFramesByAtid
, IGetDurationByAtid
, ISetVisibleByOtid
, ISetCurrentFrameByOtid
, ISetToInitialAnimationWithoutChangingFrameByOtid
, ISetAsACurrentAnimationByAtid
, IGetOtidOfAtid
, IGetAtidOfDefaultSayAnim
, ISetStateOfPopup
{

}
