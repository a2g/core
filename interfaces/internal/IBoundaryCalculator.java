package com.github.a2g.core.interfaces.internal;

import java.util.ArrayList;
import java.util.List;

import com.github.a2g.core.primitive.PointF;

public interface IBoundaryCalculator {

	void clearBoundaries();

	void addBoundaryGate(Object sceneToSwitchTo, PointF pointF, PointF pointF2);

	void addBoundaryPoint(PointF pointF);

	boolean isInANoGoZone(PointF tp);

	boolean doSwitchIfBeyondGate(PointF tp);

	ArrayList<PointF> getGatePoints();

	PointF getGatePointsCentre();

	void addObstacleRect(double top, double left, double bottom, double right);
	
	void finishedGateAndObstacleAdding();

	List<PointF> findPath(PointF rawStart, PointF rawEnd);

}
