package com.github.a2g.core.interfaces.nongame;

import java.util.ArrayList;
import java.util.List;

import com.github.a2g.core.objectmodel.PointFWithNeighbours;
import com.google.gwt.touch.client.Point;
import com.github.a2g.core.primitive.RectF;

public interface IBoundaryCalculator {

	void clearBoundaries();

	void addEdgeSpanToPerimeter(Object sceneToSwitchTo, int entrySegment, Point pointF, Point pointF2);

	void addBoundaryPoint(Point pointF);

	boolean isInANoGoZone(Point tp);

	boolean doSwitchIfBeyondGate(Point tp);

	ArrayList<Point> getGatePoints();

	Point getCentreOfSegments();

	void addEdgeRectangle(double x, double y, double right, double bottom);
	
	void finishedGateAndObstacleAdding();

	List<Point> findPath(Point rawStart, Point rawEnd);

	ArrayList<RectF> getObstacles();

	List<Point> getLastPath();

	List<PointFWithNeighbours> getLastNetworkOfConcaveVertices();

}
