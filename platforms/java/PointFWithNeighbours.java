package com.github.a2g.core.platforms.java;

import java.util.ArrayList;
import java.util.Iterator; 
import java.util.TreeSet;

import com.github.a2g.core.primitive.PointF;

public class PointFWithNeighbours extends PointF implements IGetNeighbours<PointFWithNeighbours> 
{
	ArrayList<PointFWithNeighbours> neighbours;
	private TreeSet<PointFWithNeighbours> tested;

	PointFWithNeighbours(PointF thePoint)
	{
		super(thePoint.getX(), thePoint.getY());
		neighbours = new ArrayList<PointFWithNeighbours>();
		tested = new TreeSet<PointFWithNeighbours>();
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
