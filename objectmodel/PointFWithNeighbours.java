package com.github.a2g.core.objectmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.github.a2g.core.interfaces.internal.IGetNeighbours;
import com.github.a2g.core.primitive.PointF;

public class PointFWithNeighbours extends PointF implements IGetNeighbours<PointFWithNeighbours> 
{
	ArrayList<PointFWithNeighbours> neighbours;
	private HashSet<PointFWithNeighbours> tested;

	PointFWithNeighbours(PointF thePoint)
	{
		super(thePoint.getX(), thePoint.getY());
		neighbours = new ArrayList<PointFWithNeighbours>();
		tested = new HashSet<PointFWithNeighbours>();
	}
 
	void addNeighbour(PointFWithNeighbours pt)
	{
		neighbours.add(pt);
	}

	boolean hasNeighbourlynessBeenTested(PointFWithNeighbours pt)
	{
		if(tested.contains(pt))
			return true;
		return false;
	}

	@Override
	public Iterator<PointFWithNeighbours> getNeighbours() {
		return neighbours.iterator();
	}

	public void setHasNeighbourlynessBeenTested(PointFWithNeighbours pt) {
		tested.add(pt);
	}

}
