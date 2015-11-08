package com.github.a2g.core.platforms.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.a2g.core.primitive.PointF;

public class PointFWithNeighbours implements IGetNeighbours<PointFWithNeighbours>{

	private PointF thePoint;
	ArrayList<PointFWithNeighbours> neighbours;
	
	PointFWithNeighbours(PointF thePoint)
	{
		this.thePoint = thePoint;
	}
	
	@Override
	public Iterator<PointFWithNeighbours> getNeighbours() {
		return neighbours.iterator();
	}

}
