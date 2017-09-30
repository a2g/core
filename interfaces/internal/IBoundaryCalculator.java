package com.github.a2g.core.interfaces.internal;

import java.util.ArrayList;
import java.util.List;

import com.github.a2g.core.objectmodel.PointFWithNeighbours;
import com.github.a2g.core.primitive.Point;
import com.github.a2g.core.primitive.RectF;

public interface IBoundaryCalculator {

	void clearBoundaries();

	void addBoundaryGate(Object sceneToSwitchTo, int arrivalSegment, Point pointF, Point pointF2);

	void addBoundaryPoint(Point pointF);

	boolean isInANoGoZone(Point tp);

	boolean doSwitchIfBeyondGate(Point tp);

	ArrayList<Point> getGatePoints();

	Point getCentreOfSegments();

	void addObstacleRect(double x, double y, double right, double bottom);
	
	void finishedGateAndObstacleAdding();

	List<Point> findPath(Point rawStart, Point rawEnd);

	ArrayList<RectF> getObstacles();

	List<Point> getLastPath();

	List<PointFWithNeighbours> getLastNetworkOfConcaveVertices();

}
