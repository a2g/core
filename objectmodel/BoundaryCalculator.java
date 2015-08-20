package com.github.a2g.core.objectmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.github.a2g.core.interfaces.IScenePresenterFromBoundaryCalculator;
import com.github.a2g.core.primitive.PointF;

public class BoundaryCalculator implements Comparator<BoundaryCalculator.Gate>{
	public class Gate 
	{
		public String destination;
		public PointF first;
		public PointF second;

		public Gate(Object dest, PointF first, PointF second)
		{
			this.destination = (dest==null)? "" : dest.toString();
			this.first = first;
			this.second = second;
		}
	}
	private IScenePresenterFromBoundaryCalculator scene;
	private ArrayList<Gate> gateDests;
	private PointF cachedCalculationOfCentre;

	private static String TREAT_GATE_AS_POINT = "TREAT_GATE_AS_POINT";


	public BoundaryCalculator(IScenePresenterFromBoundaryCalculator master)
	{
		this.scene = master;
		this.gateDests = new ArrayList<Gate>(); 
		updateCentre();
	}
	public ArrayList<Gate> getGatePoints(){ return gateDests;}

	void sort()
	{
		this.updateCentre();
		Collections.sort(gateDests, this);
	}
	public void addBoundaryGate(Object name, PointF a, PointF b) {
		gateDests.add(new Gate(name, a, b));
		sort();
	}

	public void addBoundaryPoint(PointF a) {
		gateDests.add(new Gate(TREAT_GATE_AS_POINT, new PointF(-1, -1), a));
		sort();
	}
	public void clearBoundaries() {
		this.gateDests.clear();
	}

	public boolean doSwitchIfBeyondGate(PointF tp)
	{
		String foundDest = "";
		int size = gateDests.size();
		for (int i = 0; i < size; i++) {
			if (gateDests.get(i).destination==null 
					|| gateDests.get(i).destination.length()==0)
				continue;

			PointF a = gateDests.get(i).first;
			PointF b = gateDests.get(i).second;

			if (isBetweenSpokesAndOnWrongSide(a, b, tp)) {
				foundDest = gateDests.get(i).destination;
				if(foundDest!=null && foundDest.length()>0)
				{
					scene.switchToScene(foundDest);
					return true;
				}
			}
		}
		return false;
	}

	public boolean isInANoGoZone(PointF tp)
	{
		if (gateDests.size() < 2)// 2
			return false;

		PointF previousPoint = gateDests.get(gateDests.size() - 1).second;

		int size = gateDests.size();
		for (int i = 0; i < size; i++) {
			// only gates get their first point
			// (so if it is a point it gets ignored)
			if (gateDests.get(i).destination!=TREAT_GATE_AS_POINT) {
				PointF firstPoint = gateDests.get(i).first;
				if (isBetweenSpokesAndOnWrongSide(previousPoint, firstPoint, tp))
					return true;
				previousPoint = firstPoint;
			}

			// every gate/point has their second point processed.
			PointF secondPoint = gateDests.get(i).second;
			if (isBetweenSpokesAndOnWrongSide(previousPoint, secondPoint, tp))
				return true;
			previousPoint = secondPoint;
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

		if(gateDests.size()>0)
		{
			double maxX = gateDests.get(0).second.getX();
			double minX = gateDests.get(0).second.getX();
			double maxY = gateDests.get(0).second.getY();
			double minY = gateDests.get(0).second.getY();
			for(int i=0; i<gateDests.size(); i++)
			{
				{
					double firstX = gateDests.get(i).first.getX();
					double firstY = gateDests.get(i).first.getY();

					if(firstX>0)
					{
						maxX = Math.max(maxX, firstX);
						maxY = Math.max(maxY, firstY);

						minX = Math.min(minX, firstX);
						minY = Math.min(minY, firstY);
					}
				}

				{
					double secondX = gateDests.get(i).second.getX();
					double secondY = gateDests.get(i).second.getY();

					if(secondX>0)
					{
						maxX = Math.max(maxX, secondX);
						maxY = Math.max(maxY, secondY);

						minX = Math.min(minX, secondX);
						minY = Math.min(minY, secondY);
					}
				}
			}

			cachedCalculationOfCentre = new PointF(.5*minX+.5*maxX, .5*maxY+.5*minY);
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
		double a1 = getSomeScalarMeasureMentOfAngle(o1.second, cachedCalculationOfCentre);
		double a2 = getSomeScalarMeasureMentOfAngle(o2.second, cachedCalculationOfCentre);
		return (int)(a1-a2);
	}
	
	public static double getSomeScalarMeasureMentOfAngle(PointF p1, PointF p2) { 
		double deltaX = p2.getX() - p1.getX(); 
		double deltaY = p2.getY() - p1.getY(); 
		return Math.toDegrees(Math.atan2(deltaY, deltaX));
		//http://stackoverflow.com/questions/7586063/how-to-calculate-the-angle-between-a-line-and-the-horizontal-axis
	} 
	
}
