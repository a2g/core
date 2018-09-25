package com.github.a2g.core.objectmodel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.github.a2g.core.interfaces.nongame.IBoundaryCalculator;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromBoundaryCalculator;
import com.google.gwt.touch.client.Point;
import com.github.a2g.core.primitive.RectF;

public class BoundaryCalculator implements Comparator<BoundaryCalculator.Gate>, IBoundaryCalculator,
		Path.IDistanceFunc<PointFWithNeighbours>, Path.IEstimateFunc<PointFWithNeighbours> {
	protected class Gate {
		public String switchTo;
		public int entrySegment;
		public Point a;
		public Point b;

		public Gate(Object dest, int entrySegment, Point first, Point second) {
			this.switchTo = (dest == null) ? "" : dest.toString();
			this.entrySegment = entrySegment;
			this.a = first;
			this.b = second;
		}
	}

	private IScenePresenterFromBoundaryCalculator scene;
	private ArrayList<Gate> gates;
	private ArrayList<RectF> obstacles;
	private Point cachedCalculationOfCentre;
	private List<Point> lastPath;
	private List<PointFWithNeighbours> lastNetworkOfConcaveVertices;

	private static String TREAT_GATE_AS_POINT = "TREAT_GATE_AS_POINT";

	public BoundaryCalculator(IScenePresenterFromBoundaryCalculator master) {
		this.scene = master;
		this.gates = new ArrayList<Gate>();
		this.obstacles = new ArrayList<RectF>();

		updateCentre();
	}

	public ArrayList<Point> getGatePoints() {
		ArrayList<Point> toReturn = new ArrayList<Point>();
		for (int i = 0; i < gates.size(); i++) {
			toReturn.add(gates.get(i).a);// always add first
			if (gates.get(i).switchTo != TREAT_GATE_AS_POINT) {
				toReturn.add(gates.get(i).b);// only add second if not point
			}
		}
		return toReturn;

	}

	void sort() {
		this.updateCentre();
	}

	public void addEdgeSpanToPerimeter(Object switchTo, int entrySegment, Point a, Point b) {
		if (switchTo != null) {
			gates.add(new Gate(switchTo == "" ? null : switchTo, entrySegment, a, b));

		} else {
			gates.add(new Gate(TREAT_GATE_AS_POINT, -1, a, new Point(-1, -1)));
			gates.add(new Gate(TREAT_GATE_AS_POINT, -1, b, new Point(-1, -1)));
		}
		sort();
	}

	public void addBoundaryPoint(Point a) {
		gates.add(new Gate(TREAT_GATE_AS_POINT, -1, a, new Point(-1, -1)));
		sort();
	}

	public void addEdgeRectangle(double x, double y, double right, double bottom) {
		obstacles.add(new RectF(x, y, right-x, bottom-y));
	}

	public void clearBoundaries() {
		this.gates.clear();
		this.obstacles.clear();
	}

	public boolean doSwitchIfBeyondGate(Point tp) {
		int size = gates.size();
		for (int i = 0; i < size; i++) {
			Gate g = gates.get(i);
			if (g.switchTo == null || g.switchTo == TREAT_GATE_AS_POINT)
				continue;

			if (isBetweenSpokesAndOnWrongSide(g.a, g.b, tp)) {
				scene.switchToScene(g.switchTo, g.entrySegment);
				return true;
			}
		}
		return false;
	}

	public boolean isInANoGoZone(Point tp) {
		ArrayList<Point> a = getGatePoints();
		if (a.size() < 3)// need 3 to form an area
			return false;

		Point previousPoint = a.get(a.size() - 1);
		for (int i = 0; i < a.size(); i++) {
			if (isBetweenSpokesAndOnWrongSide(previousPoint, a.get(i), tp))
				return true;
			previousPoint = a.get(i);
		}
		return false;
	}

	private boolean isBetweenSpokesAndOnWrongSide(Point p1, Point p2, Point tp) {
		Point c = getCentreOfSegments();
		Point mp = getMidPoint(p1, p2);
		boolean isBetweenSpokes = arePointsSameSide(c, p1, mp, tp) && arePointsSameSide(c, p2, mp, tp);
		if (!isBetweenSpokes)
			return false;
		boolean isOnSameSideAsCentre = arePointsSameSide(p1, p2, c, tp);
		if (isOnSameSideAsCentre)
			return false;
		return true;
	}

	public Point getCentreOfSegments() {
		return cachedCalculationOfCentre;
	}

	public void updateCentre() {
		cachedCalculationOfCentre = new Point(.5, .5);
		ArrayList<Point> a = getGatePoints();
		if (a.size() > 0) {
			double maxX = a.get(0).getX();
			double minX = a.get(0).getX();
			double maxY = a.get(0).getY();
			double minY = a.get(0).getY();
			for (int i = 0; i < a.size(); i++) {
				double x = a.get(i).getX();
				double y = a.get(i).getY();
				maxX = Math.max(maxX, x);
				minX = Math.min(minX, x);
				minY = Math.min(minY, y);
				maxY = Math.max(maxY, y);
			}
			cachedCalculationOfCentre = new Point(.5 * minX + .5 * maxX, .5 * minY + .5 * maxY);
		}
	}

	private Point getMidPoint(Point a, Point b) {
		return new Point(a.getX() / 2 + b.getX() / 2, a.getY() / 2 + b.getY() / 2);
	}

	private boolean arePointsSameSide(Point A, Point B, Point tp, Point c) {
		// if a point is on a line the result will be zero, when substituted
		// in to the line equation
		// if two points are on the same side, they will have the same sign..
		// so if the two results are multiplied against each other
		// the result is positive if the points are on the same side.
		double result1 = (B.getX() - A.getX()) * (tp.getY() - A.getY())
				- (B.getY() - A.getY()) * (tp.getX() - A.getX());
		double result2 = (B.getX() - A.getX()) * (c.getY() - A.getY()) - (B.getY() - A.getY()) * (c.getX() - A.getX());
		return result1 * result2 > 0;
	}

	@Override
	public int compare(Gate o1, Gate o2) {
		// we sort by the first point in each gate...
		// if they overlap, then its the room author's fault!
		double a1 = getSomeScalarMeasureMentOfAngle(o1.a, cachedCalculationOfCentre);
		double a2 = getSomeScalarMeasureMentOfAngle(o2.a, cachedCalculationOfCentre);
		return (int) (a1 - a2);
	}

	public static double getSomeScalarMeasureMentOfAngle(Point p1, Point p2) {
		double deltaX = p2.getX() - p1.getX();
		double deltaY = p2.getY() - p1.getY();
		return Math.toDegrees(Math.atan2(deltaY, deltaX));
		// http://stackoverflow.com/questions/7586063/how-to-calculate-the-angle-between-a-line-and-the-horizontal-axis
	}

	public static boolean IsLineSegmentIntersectingTheOtherOne(Point a, Point b, Point point, Point point2) {
		double denominator = ((b.getX() - a.getX()) * (point2.getY() - point.getY()))
				- ((b.getY() - a.getY()) * (point2.getX() - point.getX()));

		if (denominator == 0) {
			return false;
		}

		double numerator1 = ((a.getY() - point.getY()) * (point2.getX() - point.getX()))
				- ((a.getX() - point.getX()) * (point2.getY() - point.getY()));

		double numerator2 = ((a.getY() - point.getY()) * (b.getX() - a.getX()))
				- ((a.getX() - point.getX()) * (b.getY() - a.getY()));

		if (numerator1 == 0 || numerator2 == 0) {
			return false;
		}

		double r = numerator1 / denominator;
		double s = numerator2 / denominator;

		return (r > 0 && r < 1) && (s > 0 && s < 1);
	}

	public static boolean IsAConcaveVertex(List<Point> vertices, int vertex) {
		Point current = vertices.get(vertex);
		Point next = vertices.get((vertex + 1) % vertices.size());
		Point previous = vertices.get(vertex == 0 ? vertices.size() - 1 : vertex - 1);

		Point left = new Point(current.getX() - previous.getX(), current.getY() - previous.getY());
		Point right = new Point(next.getX() - current.getX(), next.getY() - current.getY());

		double cross = (left.getX() * right.getY()) - (left.getY() * right.getX());

		return cross < 0;
	}

	public static boolean IsInside(List<Point> polygon, Point position, boolean toleranceOnOutside) {
		Point point = position;

		final float epsilon = 0.5f;

		boolean inside = false;

		// Must have 3 or more edges
		if (polygon.size() < 3)
			return false;

		Point oldPoint = polygon.get(polygon.size() - 1);
		double oldSqDist = distanceSquared(oldPoint, point);

		for (int i = 0; i < polygon.size(); i++) {
			Point newPoint = polygon.get(i);
			double newSqDist = distanceSquared(newPoint, point);

			if (oldSqDist + newSqDist + 2.0f * Math.sqrt(oldSqDist * newSqDist)
					- distanceSquared(newPoint, oldPoint) < epsilon)
				return toleranceOnOutside;

			Point left;
			Point right;
			if (newPoint.getX() > oldPoint.getX()) {
				left = oldPoint;
				right = newPoint;
			} else {
				left = newPoint;
				right = oldPoint;
			}

			if (left.getX() < point.getX() && point.getX() <= right.getX() && (point.getY() - left.getY())
					* (right.getX() - left.getX()) < (right.getY() - left.getY()) * (point.getX() - left.getX()))
				inside = !inside;

			oldPoint = newPoint;
			oldSqDist = newSqDist;
		}

		return inside;
	}

	private static double distanceSquared(Point oldPoint, Point point) {
		double xdiff = oldPoint.getX()-point.getX();
		double ydiff = oldPoint.getY()-point.getY();
		return xdiff*xdiff+ydiff*ydiff;
	}

	@Override
	public void finishedGateAndObstacleAdding() {

	}

	public List<Point> findPath(Point rawStart, Point rawEnd) {
		// first we check for whether it's a legal move or not.
		for (int i = 0; i < this.obstacles.size(); i++) {
			if (this.obstacles.get(i).contains(rawEnd.getX(), rawEnd.getY())) {
				// ...if its not we make it a very short walk indeed.
				rawEnd = rawStart;
				// break;
				return null;
			}
		}

		List<PointFWithNeighbours> verts = getNetworkOfConcaveVertices(rawStart, rawEnd);

		PointFWithNeighbours start = verts.get(0);
		PointFWithNeighbours end = verts.get(1);

		// create the default journey
		List<Point> list = new LinkedList<Point>();
		list.add(start);
		list.add(end);

		// the path is returned such that if we want to dowhile over it
		// and construct an ordered list, then its better to flip end and start
		Path<PointFWithNeighbours> path = Path.findPath(end, start, this, this);
		if (path != null) {
			list.clear();
			list.add(path.getLastStep());
			while (path != null && path.getPreviousSteps() != null) {
				list.add(path.getPreviousSteps().getLastStep());
				path = path.getPreviousSteps();
			}
		}

		lastPath = list;
		return list;
	}

	public List<PointFWithNeighbours> getNetworkOfConcaveVertices(Point rawStart, Point rawEnd) {
		PointFWithNeighbours start = new PointFWithNeighbours(rawStart);
		PointFWithNeighbours end = new PointFWithNeighbours(rawEnd);
		// we need to create a whole network of PointFWithNeighbours,
		// with all the neighbours filled out properly.
		// So we must first create PointFWithNeighbours for
		// all concave vertices, including start and end points...
		// then process them all
		ArrayList<PointFWithNeighbours> concaveVertices = new ArrayList<PointFWithNeighbours>();
		concaveVertices.add(start);
		concaveVertices.add(end);
		for (int i = 0; i < obstacles.size(); i++) {
			concaveVertices.add(new PointFWithNeighbours(obstacles.get(i).getTopLeft()));
			concaveVertices.add(new PointFWithNeighbours(obstacles.get(i).getTopRight()));
			concaveVertices.add(new PointFWithNeighbours(obstacles.get(i).getBottomLeft()));
			concaveVertices.add(new PointFWithNeighbours(obstacles.get(i).getBottomRight()));
		}

		for (int i = 0; i < concaveVertices.size(); i++) {
			PointFWithNeighbours first = concaveVertices.get(i);
			for (int j = 0; j < concaveVertices.size(); j++) {
				PointFWithNeighbours second = concaveVertices.get(j);
				if (second.hasNeighbourlynessBeenTested(first))
					continue;
				// if not yet tested, then let's do a brute force neighbor test.
				// ...they are only neighbors if they can see each other.

				boolean isSecondSeeingFirst = true;
				for (int k = 0; k < obstacles.size(); k++) {
					RectF smallOb = getSmallerSlightlyJumbledRect(obstacles.get(k));
					if (IsLineSegmentIntersectingTheOtherOne(first, second, smallOb.getTopLeft(),
							smallOb.getTopRight())) {
						isSecondSeeingFirst = false;
						break;
					}
					if (IsLineSegmentIntersectingTheOtherOne(first, second, smallOb.getTopRight(),
							smallOb.getBottomRight())) {
						isSecondSeeingFirst = false;
						break;
					}
					if (IsLineSegmentIntersectingTheOtherOne(first, second, smallOb.getBottomRight(),
							smallOb.getBottomLeft())) {
						isSecondSeeingFirst = false;
						break;
					}
					if (IsLineSegmentIntersectingTheOtherOne(first, second, smallOb.getBottomLeft(),
							smallOb.getTopLeft())) {
						isSecondSeeingFirst = false;
						break;
					}
				}

				first.setHasNeighbourlynessBeenTested(second);
				second.setHasNeighbourlynessBeenTested(first);

				if (isSecondSeeingFirst) {
					first.addNeighbour(second);
					second.addNeighbour(first);
				}
			}
		}
		lastNetworkOfConcaveVertices = concaveVertices;

		return concaveVertices;

	}

	private RectF getSmallerSlightlyJumbledRect(RectF rectF) {
		// we need to return a slightly smaller rectangle for boundary testing.
		// otherwise there will seem like the corners of the rect can see their
		// opposite corner, and add them as a neighbour.
		// Point center = rectF.getCenter();

		// even more interesting is that we have to scale the dimensions of this
		// inner rectangle differently, otherwise the line-of-sight line
		// can, mathematically "thread the needle" between the two sides
		// that make up a corner of this rectangle, going in one corner..
		// and out the other..and giving it a diagonal line of sight. we don't
		// want that.
		// TODO: this can be unit tested.
		// double newWidth = rectF.getWidth()*.5;
		// double newHeight = rectF.getWidth()*.5;

		// this rect is minorly inside the input rectF.
		return new RectF(rectF.getLeft() + .01, 
				rectF.getTop() + .01, 
				rectF.getWidth() - .02,
				 rectF.getHeight() - .02);
	}

	@Override
	public double estimate(PointFWithNeighbours n, PointFWithNeighbours dest) {
		// if we return 0 here "then this becomes Dijkstra's algorithm"
		// see
		// http://blogs.msdn.com/b/ericlippert/archive/2007/10/10/path-finding-using-a-in-c-3-0-part-four.aspx
		return 0;
	}

	@Override
	public double distance(PointFWithNeighbours lastStep, PointFWithNeighbours n) {
		return Math.hypot(lastStep.getX() - n.getX(), lastStep.getY() - n.getY());
	}

	public ArrayList<RectF> getObstacles() {
		return obstacles;
	}

	public List<Point> getLastPath() {
		return lastPath;
	}

	@Override
	public List<PointFWithNeighbours> getLastNetworkOfConcaveVertices() {
		return lastNetworkOfConcaveVertices;
	}

}