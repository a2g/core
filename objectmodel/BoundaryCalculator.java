package com.github.a2g.core.objectmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.github.a2g.core.interfaces.IScenePresenterFromBoundaryCalculator;
import com.github.a2g.core.primitive.PointF;

public class BoundaryCalculator implements Comparator<BoundaryCalculator.Gate>{
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
	private PointF cachedCalculationOfCentre;

	private static String TREAT_GATE_AS_POINT = "TREAT_GATE_AS_POINT";


	public BoundaryCalculator(IScenePresenterFromBoundaryCalculator master)
	{
		this.scene = master;
		this.gates = new ArrayList<Gate>(); 
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
	
}
