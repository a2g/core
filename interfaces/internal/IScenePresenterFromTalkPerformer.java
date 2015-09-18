package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.ICurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfSceneAnswerer;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfSceneAsker;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfSceneTalker;
import com.github.a2g.core.interfaces.methods.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetDurationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByAtid;
import com.github.a2g.core.interfaces.methods.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetStateOfPopup;
import com.github.a2g.core.interfaces.methods.action.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.ISetVisibleByOtid;

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
