package com.github.a2g.core.interfaces.internal;

import java.util.ArrayList;

import com.github.a2g.core.interfaces.methods.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetBoundingRectByFrameAndAtid;
import com.github.a2g.core.interfaces.methods.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByIndex;
import com.github.a2g.core.interfaces.methods.action.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.methods.action.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetXByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetYByOtid;
import com.github.a2g.core.primitive.PointF;

public interface IScenePresenterFromScenePanel
extends
IScenePresenterFromSceneMouseOver,
IScenePresenterFromSceneTouch,
IScenePresenterFromJavaPopupPanel,// no java stuff here, it just needs
// getView
IGetSceneObjectCount,
IGetOtidByIndex,
IGetVisibleByOtid,
IGetAtidOfCurrentAnimationByOtid,
IGetCurrentFrameByOtid,
IGetBoundingRectByFrameAndAtid,
IGetXByOtid,
IGetYByOtid
{
	ArrayList<PointF> getBoundaryPoints();
	PointF getBoundaryPointsCentre();
}