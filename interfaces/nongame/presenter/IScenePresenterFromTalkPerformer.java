package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetAtidOfSceneDialogThem;
import com.github.a2g.core.interfaces.nongame.action.IGetAtidOfSceneDialogUs;
import com.github.a2g.core.interfaces.nongame.action.IGetAtidOfSceneTalker;
import com.github.a2g.core.interfaces.nongame.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetDurationByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetHeadRectangleUsingContingencies;
import com.github.a2g.core.interfaces.nongame.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiHeight;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneGuiWidth;
import com.github.a2g.core.interfaces.nongame.action.IGetSpeechBubbleOffsetForDownwardTail;
import com.github.a2g.core.interfaces.nongame.action.IGetSpeechBubbleOffsetForUpwardTail;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentAnimationByAtid;
import com.github.a2g.core.interfaces.nongame.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetStateOfPopup;
import com.github.a2g.core.interfaces.nongame.action.ISetToInitialAnimationWithoutChangingFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetVisibleByOtid;
import com.github.a2g.core.interfaces.nongame.platform.singles.IMeasureTextWidthAndHeight;
import com.github.a2g.core.primitive.PointI;

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
, IGetSpeechBubbleOffsetForDownwardTail
, IGetSpeechBubbleOffsetForUpwardTail
{

  
	

}
