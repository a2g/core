package com.github.a2g.core.interfaces.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.a2g.core.interfaces.methods.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetBoundingRectByFrameAndAtid;
import com.github.a2g.core.interfaces.methods.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByIndex;
import com.github.a2g.core.interfaces.methods.action.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.methods.action.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetXByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetYByOtid; 
import com.github.a2g.core.objectmodel.PointFWithNeighbours;
import com.google.gwt.touch.client.Point;
import com.github.a2g.core.primitive.RectF;

public interface IScenePresenterFromScenePanel
extends
IScenePresenterFromSceneMouseOver,
IScenePresenterFromSceneTouch,
IScenePresenterFromSwingPopupPanel,// no java stuff here, it just needs
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
	ArrayList<Point> getBoundaryPoints();
	Point getBoundaryPointsCentre();
	List<RectF> getObstacles();
	List<PointFWithNeighbours> getLastNetworkOfConcaveVertices();
	List<Point> getLastPath();
	Iterator<RectF> getSpeechRects();
	List<Point> getHelperPoints();
}
