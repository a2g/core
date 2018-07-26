package com.github.a2g.core.interfaces.nongame.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.a2g.core.interfaces.nongame.IBoundaryCalculator;
import com.github.a2g.core.interfaces.nongame.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetBoundingRectByFrameAndAtid;
import com.github.a2g.core.interfaces.nongame.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByIndex;
import com.github.a2g.core.interfaces.nongame.action.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.nongame.action.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetXByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetYByOtid;
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
	IBoundaryCalculator getBoundaryCalculator();
}
