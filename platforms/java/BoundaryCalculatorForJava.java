package com.github.a2g.core.platforms.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.a2g.core.interfaces.internal.IBoundaryCalculator;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromBoundaryCalculator;
import com.github.a2g.core.primitive.PointF;
import com.github.a2g.core.primitive.RectF;

public class BoundaryCalculatorForJava 
implements Comparator<BoundaryCalculatorForJava.Gate>
, IBoundaryCalculator
, Path.IDistanceFunc<PointFWithNeighbours>
, Path.IEstimateFunc<PointFWithNeighbours>
{
	protected class Gate 
	{
		public String switchTo;
		public PointF a;
		public PointF b;

		public Gate(Object dest, PointF first, PointF second)
		{
			this.switchTo = (dest==null)? "" : dest.toString();
			this.a = first;
			this.b = second;
		}
	}
	private IScenePresenterFromBoundaryCalculator scene;
	private ArrayList<Gate> gates;
	private ArrayList<RectF> obstacles; 
	private PointF cachedCalculationOfCentre;

	private static String TREAT_GATE_AS_POINT = "TREAT_GATE_AS_POINT";


	public BoundaryCalculatorForJava(IScenePresenterFromBoundaryCalculator master)
	{
		this.scene = master;
		this.gates = new ArrayList<Gate>(); 
		this.obstacles = new ArrayList<RectF>(); 
 

		updateCentre();
	}

	public ArrayList<PointF> getGatePoints()
	{ 
		ArrayList<PointF> toReturn = new ArrayList<PointF>();
		for(int i=0;i<gates.size();i++)
		{
			toReturn.add(gates.get(i).a);// always add first
			if(gates.get(i).switchTo!=TREAT_GATE_AS_POINT)
			{
				toReturn.add(gates.get(i).b);// only add second if not point
			}
		}
		return toReturn;

	}

	void sort()
	{
		this.updateCentre();
		Collections.sort(gates, this);
	}

	public void addBoundaryGate(Object switchTo, PointF a, PointF b) {
		gates.add(new Gate(switchTo==""? null : switchTo, a, b));
		sort();
	}

	public void addBoundaryPoint(PointF a) {
		gates.add(new Gate(TREAT_GATE_AS_POINT,  a, new PointF(-1, -1)));
		sort();
	}

	public void addObstacleRect(double top, double left, double bottom, double right)
	{
		obstacles.add(new RectF(top,left,bottom,right));
	}


	public void clearBoundaries() {
		this.gates.clear();
	}

	public boolean doSwitchIfBeyondGate(PointF tp)
	{
		int size = gates.size();
		for (int i = 0; i < size; i++) {
			Gate g = gates.get(i);
			if (g.switchTo==null||g.switchTo==TREAT_GATE_AS_POINT)
				continue;

			if (isBetweenSpokesAndOnWrongSide(g.a, g.b, tp)) 
			{
				scene.switchToScene(g.switchTo);
				return true;
			}
		}
		return false;
	}

	public boolean isInANoGoZone(PointF tp)
	{
		ArrayList<PointF> a = getGatePoints();
		if (a.size() < 3)// need 3 to form an area
			return false;

		PointF previousPoint = a.get(a.size() - 1);
		for (int i = 0; i < a.size(); i++) {
			if (isBetweenSpokesAndOnWrongSide(previousPoint, a.get(i), tp))
				return true;
			previousPoint = a.get(i);
		}
		return false;
	}


	private boolean isBetweenSpokesAndOnWrongSide(PointF p1, PointF p2, PointF tp) {
		PointF c = getGatePointsCentre();
		PointF mp = getMidPoint(p1, p2);
		boolean isBetweenSpokes = arePointsSameSide(c, p1, mp, tp)
				&& arePointsSameSide(c, p2, mp, tp);
		if (!isBetweenSpokes)
			return false;
		boolean isOnSameSideAsCentre = arePointsSameSide(p1, p2, c, tp);
		if (isOnSameSideAsCentre)
			return false;
		return true;
	}


	public PointF getGatePointsCentre() {
		return cachedCalculationOfCentre;
	}

	public void updateCentre()
	{
		cachedCalculationOfCentre = new PointF(.5,.5);
		ArrayList<PointF> a = getGatePoints();
		if(a.size()>0)
		{
			double maxX = a.get(0).getX();
			double minX = a.get(0).getX();
			double maxY = a.get(0).getY();
			double minY = a.get(0).getY();
			for(int i=0; i<a.size(); i++)
			{
				double x = a.get(i).getX();
				double y = a.get(i).getY();
				maxX = Math.max(maxX, x);
				minX = Math.min(minX, x);
				minY = Math.min(minY, y);
				maxY = Math.max(maxY, y);
			}
			cachedCalculationOfCentre = new PointF(.5*minX+.5*maxX, .5*minY+.5*maxY);
		}
	}

	private PointF getMidPoint(PointF a, PointF b) {
		return new PointF(a.getX()/2 + b.getX()/2
				, a.getY()/2 + b.getY()/2);
	}

	private boolean arePointsSameSide(PointF A, PointF B, PointF tp, PointF c) {
		// if a point is on a line the result will be zero, when substituted
		// in to the line equation
		// if two points are on the same side, they will have the same sign..
		// so if the two results are multiplied against each other
		// the result is positive if the points are on the same side.
		double result1 = (B.getX() - A.getX()) * (tp.getY() - A.getY())
				- (B.getY() - A.getY()) * (tp.getX() - A.getX());
		double result2 = (B.getX() - A.getX()) * (c.getY() - A.getY())
				- (B.getY() - A.getY()) * (c.getX() - A.getX());
		return result1 * result2 > 0;
	}

	@Override
	public int compare(Gate o1, Gate o2) {
		// we sort by the first point in each gate...
		// if they overlap, then its the room author's fault!
		double a1 = getSomeScalarMeasureMentOfAngle(o1.a, cachedCalculationOfCentre);
		double a2 = getSomeScalarMeasureMentOfAngle(o2.a, cachedCalculationOfCentre);
		return (int)(a1-a2);
	}

	public static double getSomeScalarMeasureMentOfAngle(PointF p1, PointF p2) { 
		double deltaX = p2.getX() - p1.getX(); 
		double deltaY = p2.getY() - p1.getY(); 
		return Math.toDegrees(Math.atan2(deltaY, deltaX));
		//http://stackoverflow.com/questions/7586063/how-to-calculate-the-angle-between-a-line-and-the-horizontal-axis
	} 

	public static boolean IsLineSegmentIntersectingTheOtherOne(PointF a, PointF b, PointF c, PointF d)
	{
		double denominator = ((b.getX() - a.getX()) * (d.getY() - c.getY())) - ((b.getY() - a.getY()) * (d.getX() - c.getX()));

		if (denominator == 0)
		{
			return false;
		}

		double numerator1 = ((a.getY() - c.getY()) * (d.getX() - c.getX())) - ((a.getX() - c.getX()) * (d.getY() - c.getY()));

		double numerator2 = ((a.getY() - c.getY()) * (b.getX() - a.getX())) - ((a.getX() - c.getX()) * (b.getY() - a.getY()));

		if (numerator1 == 0 || numerator2 == 0)
		{
			return false;
		}

		double r = numerator1 / denominator;
		double s = numerator2 / denominator;

		return (r > 0 && r < 1) && (s > 0 && s < 1);
	}

	public static boolean IsAConcaveVertex(List<PointF> vertices, int vertex)
	{
		PointF current = vertices.get(vertex);
		PointF next = vertices.get((vertex + 1) % vertices.size());
		PointF previous = vertices.get(vertex == 0 ? vertices.size() - 1 : vertex - 1);

		PointF left = new PointF(current.getX() - previous.getX(), current.getY() - previous.getY());
		PointF right = new PointF(next.getX() - current.getX(), next.getY() - current.getY());

		double cross = (left.getX() * right.getY()) - (left.getY() * right.getX());

		return cross < 0;
	}
	public static boolean IsInside(  List<PointF> polygon, PointF position, boolean toleranceOnOutside)
	{
		PointF point = position;

		final float epsilon = 0.5f;

		boolean inside = false;

		// Must have 3 or more edges
		if (polygon.size() < 3) return false;

		PointF oldPoint = polygon.get(polygon.size() - 1);
		double oldSqDist = PointF.DistanceSquared(oldPoint, point);

		for (int i = 0; i < polygon.size(); i++)
		{
			PointF newPoint = polygon.get(i);
			double newSqDist = PointF.DistanceSquared(newPoint, point);

			if (oldSqDist + newSqDist + 2.0f * Math.sqrt(oldSqDist * newSqDist) - PointF.DistanceSquared(newPoint, oldPoint) < epsilon)
				return toleranceOnOutside;

			PointF left;
			PointF right;
			if (newPoint.getX() > oldPoint.getX())
			{
				left = oldPoint;
				right = newPoint;
			}
			else
			{
				left = newPoint;
				right = oldPoint;
			}

			if (left.getX() < point.getX() && point.getX() <= right.getX() && (point.getY() - left.getY()) * (right.getX() - left.getX()) < (right.getY() - left.getY()) * (point.getX() - left.getX()))
				inside = !inside;

			oldPoint = newPoint;
			oldSqDist = newSqDist;
		}

		return inside;
	}

	@Override
	public void finishedGateAndObstacleAdding() {

	}
 
	public List<PointFWithNeighbours> getNetworkOfConcaveVertices(PointF rawStart, PointF rawEnd)
	{
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
		for(int i=0;i<obstacles.size();i++)
		{
			concaveVertices.add(new PointFWithNeighbours(obstacles.get(i).getTopLeft()));
			concaveVertices.add(new PointFWithNeighbours(obstacles.get(i).getTopRight()));
			concaveVertices.add(new PointFWithNeighbours(obstacles.get(i).getBottomLeft()));
			concaveVertices.add(new PointFWithNeighbours(obstacles.get(i).getBottomRight()));
		}
		
		for(int i=0;i<concaveVertices.size();i++)
		{
			PointFWithNeighbours first = concaveVertices.get(i);
			for(int j=0;j<concaveVertices.size();j++)
			{
				PointFWithNeighbours second = concaveVertices.get(j);
				if(second.hasNeighbourlynessBeenTested(first))
					continue;
				// if not yet tested, then let's do a brute force neighbor test.
				// ...they are only neighbors if they can see each other.

				boolean isSecondSeeingFirst = true;
				for(int k=0;k<obstacles.size();k++)
				{
					RectF smallOb = getSmallerSlightlyJumbledRect(obstacles.get(k));
					if(IsLineSegmentIntersectingTheOtherOne(first, second, smallOb.getTopLeft(), smallOb.getTopRight()))
					{
						isSecondSeeingFirst = false;
						break;
					}
					if(IsLineSegmentIntersectingTheOtherOne(first, second, smallOb.getTopRight(), smallOb.getBottomRight()))
					{
						isSecondSeeingFirst = false;
						break;
					}
					if(IsLineSegmentIntersectingTheOtherOne(first, second, smallOb.getBottomRight(), smallOb.getBottomLeft()))
					{
						isSecondSeeingFirst = false;
						break;
					}
					if(IsLineSegmentIntersectingTheOtherOne(first, second, smallOb.getBottomLeft(),  smallOb.getTopLeft()))
					{
						isSecondSeeingFirst = false;
						break;
					}
				}
				
				first.setHasNeighbourlynessBeenTested(second);
				second.setHasNeighbourlynessBeenTested(first);
				
				if(isSecondSeeingFirst)
				{
					first.addNeighbour(second);
					second.addNeighbour(first);
				}
			}
		}

		return concaveVertices;
	
	}

	private RectF getSmallerSlightlyJumbledRect(RectF rectF) 
	{
		// we need to return a slightly smaller rectangle for boundary testing.
		// otherwise there will seem like the corners of the rect can see their
		// opposite corner, and add them as a neighbour.
		PointF center = rectF.getCenter(); 
		
		// even more interesting is that we have to scale the dimensions of this
		// inner rectangle differently, otherwise the line-of-sight line
		// can, mathematically "thread the needle" between the two sides 
		// that make up a corner of this rectangle, going in one corner..
		// and out the other..and givingit a diagonal line of sight. we don't want that.
		// TODO: this can be unit tested.
		double newWidth = rectF.getWidth()*.99;
		double newHeight = rectF.getWidth()*.98;
		
		return new RectF(center.getX()-newWidth/2, center.getY()-newHeight/2, newWidth, newHeight);
	}
	 

 

	@Override
	public double estimate(PointFWithNeighbours n, PointFWithNeighbours dest) {
		// if we return 0 here "then this becomes Dijkstra's algorithm"
		// see http://blogs.msdn.com/b/ericlippert/archive/2007/10/10/path-finding-using-a-in-c-3-0-part-four.aspx
		return 0;
	}

	@Override
	public double distance(PointFWithNeighbours lastStep, PointFWithNeighbours n) {
		return Math.hypot(lastStep.getX()-n.getX(), lastStep.getY()-n.getY());
	}

	public ArrayList<RectF>  getObstacles() { 
		return obstacles;
	}
}