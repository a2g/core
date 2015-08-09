package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.action.IGetAtidOfSceneAnswerer;
import com.github.a2g.core.interfaces.action.IGetAtidOfSceneAsker;
import com.github.a2g.core.interfaces.action.IGetAtidOfSceneTalker;
import com.github.a2g.core.interfaces.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.IGetDurationByAtid;
import com.github.a2g.core.interfaces.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.action.IGetOtidByAtid;
import com.github.a2g.core.interfaces.action.ICurrentAnimationByAtid;
import com.github.a2g.core.interfaces.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetStateOfPopup;
import com.github.a2g.core.interfaces.action.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetVisibleByOtid;

public interface IScenePresenterFromTalkPerformer
extends ISetStateOfPopup
,IGetNumberOfFramesByAtid
, IGetDurationByAtid
, ISetVisibleByOtid
, ISetCurrentFrameByOtid
, ISetToInitialAnimationWithoutChangingFrameByOtid
, ICurrentAnimationByAtid
, IGetOtidByAtid
, IGetAtidOfSceneTalker
, IGetAtidOfSceneAsker
, IGetAtidOfSceneAnswerer
, IGetAtidOfCurrentAnimationByOtid
, IGetCurrentFrameByOtid

{

}
