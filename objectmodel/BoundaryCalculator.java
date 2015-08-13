package com.github.a2g.core.objectmodel;

import java.util.ArrayList;

import com.github.a2g.core.interfaces.IScenePresenterFromBoundaryCalculator;
import com.github.a2g.core.primitive.PointF;

public class BoundaryCalculator {
	
	private IScenePresenterFromBoundaryCalculator scene;
	private ArrayList<PointF> gatePoints;
	private ArrayList<String> gateDests;

	private static String TREAT_GATE_AS_POINT = "TREAT_GATE_AS_POINT";

	
	public BoundaryCalculator(IScenePresenterFromBoundaryCalculator master)
	{
		this.scene = master;
		this.gatePoints = new ArrayList<PointF>();
		this.gateDests = new ArrayList<String>();
	
	}
	public ArrayList<PointF> getGatePoints(){ return gatePoints;}
	
	public void addBoundaryGate(Object name, PointF a, PointF b) {
		gateDests.add(name==null? "" : name.toString());
		gatePoints.add(a);
		gatePoints.add(b);
	}

	public void addBoundaryPoint(PointF a) {
		gateDests.add(TREAT_GATE_AS_POINT );
		gatePoints.add(new PointF(-1, -1));
		// important for the valid point to be the last one,
		// due to how the span calculations use the last one.
		gatePoints.add(a);

	}
	public void clearBoundaries() {
		this.gatePoints.clear();
		this.gateDests.clear();
	}
	
	public boolean doSwitchIfBeyondGate(PointF tp) 
	{
		String foundDest = "";
		if (gatePoints.size() > 2) {
			int size = gateDests.size();
			for (int i = 0; i < size; i++) {
				if (gateDests.get(i)==null || gateDests.get(i).length()==0)
					continue;

				PointF a = gatePoints.get(i * 2);
				PointF b = gatePoints.get(i * 2 + 1);

				if (isBetweenSpokesAndOnWrongSide(a, b, tp)) {
					foundDest = gateDests.get(i);
					if(foundDest!=null && foundDest.length()>0)
					{
						scene.switchToScene(foundDest);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isInANoGoZone(PointF tp)
	{
		if (gatePoints.size() < 2)
			return false;

		// this following line relies craftily on addPoint to
		// put a dummy value in the first slot, and
		// the valid value in the last slot
		PointF previousPoint = gatePoints.get(gatePoints.size() - 1);

		int size = gateDests.size();
		for (int i = 0; i < size; i++) {
			// only gates get their first point
			// (so if it is a point it gets ignored)
			if (gateDests.get(i)!=TREAT_GATE_AS_POINT) {
				PointF firstPoint = gatePoints.get(i * 2 + 0);
				if (isBetweenSpokesAndOnWrongSide(previousPoint, firstPoint, tp))
					return true;
				previousPoint = firstPoint;
			}

			// every gate/point has their second point processed.
			PointF secondPoint = gatePoints.get(i * 2 + 1);
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
		double totalX = 0;
		double totalY = 0;
		int numberOfExtras = 0;
		double size = gateDests.size();
		for (int i = 0; i < size; i++) {
			// if it's NOT a point, then we process the first 
			// point in the pair.
			if (gateDests.get(i)!=TREAT_GATE_AS_POINT) {
				PointF bp = gatePoints.get(i * 2);
				totalX += bp.getX();
				totalY += bp.getY();
				numberOfExtras++;
			}

			// both gates and single points 
			// have a valid second point
			// so we always process
			PointF bp = gatePoints.get(i * 2 + 1);
			totalX += bp.getX();
			totalY += bp.getY();
		}
		double numberOfPoints = size + numberOfExtras;
		return new PointF(totalX / numberOfPoints
				        , totalY / numberOfPoints);
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
	
}
