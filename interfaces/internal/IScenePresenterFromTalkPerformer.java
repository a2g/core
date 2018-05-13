package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfSceneDialogThem;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfSceneDialogUs;
import com.github.a2g.core.interfaces.methods.action.IGetAtidOfSceneTalker;
import com.github.a2g.core.interfaces.methods.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetDurationByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByAtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.action.IGetHeadRectangleUsingContingencies;
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
, ISetCurrentAnimationByAtid
, IGetOtidByAtid
, IGetAtidOfSceneTalker
, IGetAtidOfSceneDialogUs
, IGetAtidOfSceneDialogThem
, IGetAtidOfCurrentAnimationByOtid
, IGetCurrentFrameByOtid
, IGetOtidByCode
, IMeasureTextWidthAndHeight 
, IGetSceneGuiWidth
, IGetSceneGuiHeight
, IGetHeadRectangleUsingContingencies
{

	

}
