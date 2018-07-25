package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetAtidOfSceneDialogThem;
import com.github.a2g.core.interfaces.methods.internal.IGetAtidOfSceneDialogUs;
import com.github.a2g.core.interfaces.methods.internal.IGetAtidOfSceneTalker;
import com.github.a2g.core.interfaces.methods.internal.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetDurationByAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetHeadRectangleUsingContingencies;
import com.github.a2g.core.interfaces.methods.internal.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.methods.internal.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetStateOfPopup;
import com.github.a2g.core.interfaces.methods.internal.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.ISetVisibleByOtid;

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
