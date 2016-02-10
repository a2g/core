package com.github.a2g.core.interfaces.internal;

import java.util.ArrayList;
import java.util.List;

import com.github.a2g.core.objectmodel.PointFWithNeighbours;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.primitive.RectF;

public interface IBoundaryCalculator {

	void clearBoundaries();

	void addBoundaryGate(Object sceneToSwitchTo, int arrivalSegment, PointF pointF, PointF pointF2);

	void addBoundaryPoint(PointF pointF);

	boolean isInANoGoZone(PointF tp);

	boolean doSwitchIfBeyondGate(PointF tp);

	ArrayList<PointF> getGatePoints();

	PointF getCentreOfSegments();

	void addObstacleRect(double x, double y, double right, double bottom);
	
	void finishedGateAndObstacleAdding();

	List<PointF> findPath(PointF rawStart, PointF rawEnd);

	ArrayList<RectF> getObstacles();

	List<PointF> getLastPath();

	List<PointFWithNeighbours> getLastNetworkOfConcaveVertices();

}
