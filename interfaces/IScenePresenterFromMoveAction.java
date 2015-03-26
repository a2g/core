package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IFireOnMovementBeyondAGateIfRelevant;
import com.github.a2g.core.interfaces.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.action.IGetNumberOfFramesByAtid;
import com.github.a2g.core.interfaces.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.action.IGetOtidOfDefaultWalkObject;
import com.github.a2g.core.interfaces.action.IGetScreenCoordsPerSecondByOtid;
import com.github.a2g.core.interfaces.action.IIsInANoGoZone;
import com.github.a2g.core.interfaces.action.ISetAsACurrentAnimationByAtid;
import com.github.a2g.core.interfaces.action.ISetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.action.ISetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.action.ISetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.ISetToInitialAnimationWithoutChangingFrameByOtid;

public interface IScenePresenterFromMoveAction 
extends 
IGetScreenCoordsPerSecondByOtid
,IGetNumberOfFramesByAtid
,IGetAtidOfCurrentAnimationByOtid
,IGetBaseMiddleXByOtid
,IGetBaseMiddleYByOtid
,IIsInANoGoZone
,IFireOnMovementBeyondAGateIfRelevant
,ISetBaseMiddleXByOtid
,ISetBaseMiddleYByOtid
,ISetAsACurrentAnimationByAtid
,ISetCurrentFrameByOtid
,ISetToInitialAnimationWithoutChangingFrameByOtid
,IGetOtidByCode
,IGetOtidOfDefaultWalkObject
{

	
	
}

