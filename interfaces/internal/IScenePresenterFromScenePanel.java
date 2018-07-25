package com.github.a2g.core.interfaces.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.a2g.core.interfaces.methods.internal.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetBoundingRectByFrameAndAtid;
import com.github.a2g.core.interfaces.methods.internal.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByIndex;
import com.github.a2g.core.interfaces.methods.internal.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.methods.internal.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetXByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetYByOtid;
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
	Iterator<RectF> getHeadRectangles();
	List<Point> getHelperPoints();
}
